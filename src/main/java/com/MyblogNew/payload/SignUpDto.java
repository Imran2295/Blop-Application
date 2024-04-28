package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    private String name;
    private String email;
    private String username;
    private String password;
}
