package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
public class PostDto {

    @NotBlank
    @Size(min = 3 , message = "Title should be minimum 3 characters")
    private String title;

    @NotBlank
    @Size(min = 10 , message = "Description should be minimum 10 characters")
    private String description;

    @NotBlank
    @Size(min = 25 , message = "Content should be minimum 25 characters")
    private String content;
}
