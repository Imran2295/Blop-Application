package com.MyblogNew.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, unique = true ,length = 100)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "content", nullable = false, length = 1000)
    private String content;

}