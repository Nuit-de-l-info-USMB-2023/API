package com.starter_kits_usmb.back_java_spring_boot.post;


import com.starter_kits_usmb.back_java_spring_boot.post.dto.GreenCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Green Management", description = "Endpoints for managing the posts from the users")
public class GreenController {
    private final GreenRepository greenRepository;

    Logger logger = LogManager.getLogger();

    private final String workspaceDir = System.getProperty("user.dir");
    private final File imagesFolder = new File(workspaceDir + "/images/");

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all the posts")
    public List<Green> getAllPosts() {
        return greenRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a post with id")
    public Optional<Green> getPostById(@PathVariable long id) {
        return greenRepository.findById(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new post")
    public Green createPost(@ModelAttribute GreenCreateDTO postDTO) {
        Green green = new Green();
        green.setDate(Date.valueOf(LocalDate.now()));
        green.setDescription(postDTO.getDescription());

        InputStream inStream = null;
        OutputStream outStream = null;
        MultipartFile image = postDTO.getImage();
        String imageName = "post_" + green.getId() + "_" + image.getOriginalFilename();
        File imageFile = new File(imagesFolder + "/" + imageName);

        logger.debug("We try to save the file as " + imageFile);
        try {
            inStream = image.getInputStream();

            if (!imagesFolder.exists()){
                if (!imagesFolder.mkdir()){
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
            while ((read = inStream.read(bytes)) != -1){
                outStream.write(bytes, 0, read);
            }
            outStream.close();
            logger.debug("Successfully saved the image " + image.getOriginalFilename());
            green.setImage(imageName);
            return green;
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
        return null;
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Green an image to the server")
    public void postImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            System.err.println("File in parameters is empty");
        }
        System.out.println("File in parameters is not empty");
    }
}
