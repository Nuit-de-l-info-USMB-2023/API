package com.starter_kits_usmb.back_java_spring_boot.green;


import com.starter_kits_usmb.back_java_spring_boot.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

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
    @NotNull
    private Date date;

    @Column(name = "image")
    @NotBlank
    private String image;
}
