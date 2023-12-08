package com.starter_kits_usmb.back_java_spring_boot.post;


import com.starter_kits_usmb.back_java_spring_boot.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "greens")
@Data
public class Green {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "date")
    @NotBlank
    private Date date;

    @Column(name = "photo")
    private String image;
}
