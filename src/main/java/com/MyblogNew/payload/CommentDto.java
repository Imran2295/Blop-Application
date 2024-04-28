package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentDto {

    @NotBlank(message = "Cannot be Blank")
    private String name;

    @NotBlank
    @Size(min = 3 ,message = "message should be more than 3 character")
    private String message;
}
