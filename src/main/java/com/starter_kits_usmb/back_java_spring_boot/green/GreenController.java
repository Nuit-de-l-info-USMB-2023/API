package com.starter_kits_usmb.back_java_spring_boot.green;


import com.starter_kits_usmb.back_java_spring_boot.green.dao.GreenDAO;
import com.starter_kits_usmb.back_java_spring_boot.green.dto.GreenCreateDTO;
import io.jsonwebtoken.Jwt;
import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.category.CategoryRepository;
import com.starter_kits_usmb.back_java_spring_boot.user.User;
import com.starter_kits_usmb.back_java_spring_boot.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/green")
@RequiredArgsConstructor
@Tag(name = "Green Management", description = "Endpoints for managing the greens from the users")
public class GreenController {
    private final GreenRepository greenRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    Logger logger = LogManager.getLogger();

    private final String workspaceDir = System.getProperty("user.dir");
    private final File imagesFolder = new File(workspaceDir + "/images/");

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all the greens")
    public List<GreenDAO> getAllPosts() {
        logger.debug("Getting the list of greens");
        List<Green> greens = greenRepository.findAll();
        ArrayList<GreenDAO> greensDAO = new ArrayList<>();
        greens.forEach((green -> {
            try {
                GreenDAO dao = GreenDAO.fromGreen(green);
                InputStream inStream = new FileInputStream(new File(imagesFolder + "/" + green.getImage()));
                byte[] image = inStream.readAllBytes();
                dao.setImage(image);
                inStream.close();
                greensDAO.add(dao);
            } catch (IOException e) {
                logger.error("Error while retreive image " + green.getImage());
            }
        }));
        logger.debug("Found " + greens.size() + " greens");
        return greensDAO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a green with id")
    public Optional<Green> getPostById(@PathVariable long id) {
        return greenRepository.findById(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new green")
    public Green createPost(@ModelAttribute GreenCreateDTO greenDTO, Principal principal) {
        Green green = new Green();
        green.setDate(Date.from(Instant.now()));
        green.setDescription(greenDTO.getDescription());
        Category category = categoryRepository.findById(greenDTO.getCategory()).orElseThrow();
        green.setCategory(category);

        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        green.setUser(user);

        InputStream inStream = null;
        OutputStream outStream = null;
        MultipartFile image = greenDTO.getImage();
        String imageName = "post_" + green.getId() + "_" + image.getOriginalFilename();
        File imageFile = new File(imagesFolder + "/" + imageName);

        logger.info("We try to save the file as " + imageFile + " from user " + green.getUser().getUsername());
        try {
            inStream = image.getInputStream();

            if (!imagesFolder.exists()) {
                if (!imagesFolder.mkdir()) {
                    throw new Exception("Cannot create the folder " + imagesFolder);
                }
            }

            if (!imageFile.exists()) {
                if (!imageFile.createNewFile()) {
                    throw new Exception("Cannot create the file " + imageFile.getAbsolutePath());
                }
            }

            outStream = new FileOutputStream(imageFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inStream.read(bytes)) != -1) {
                outStream.write(bytes, 0, read);
            }
            outStream.close();
            green.setImage(imageName);
            greenRepository.save(green);
            logger.info("Successfully saved the image " + imageName);
            return green;
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
        return null;
    }
}
