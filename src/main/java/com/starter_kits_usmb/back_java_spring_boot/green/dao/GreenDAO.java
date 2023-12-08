package com.starter_kits_usmb.back_java_spring_boot.green.dao;

import com.starter_kits_usmb.back_java_spring_boot.category.Category;
import com.starter_kits_usmb.back_java_spring_boot.green.Green;
import com.starter_kits_usmb.back_java_spring_boot.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Date;

@Data
public class GreenDAO {
    private long id;
    private User user;
    private String description;
    private Date date;
    private byte[] image;
    private Category category;

    public static GreenDAO fromGreen(Green green){
        GreenDAO greenDAO = new GreenDAO();
        greenDAO.user = green.getUser();
        greenDAO.description = green.getDescription();
        greenDAO.date = green.getDate();
        greenDAO.category = green.getCategory();
        return greenDAO;
    }
}
