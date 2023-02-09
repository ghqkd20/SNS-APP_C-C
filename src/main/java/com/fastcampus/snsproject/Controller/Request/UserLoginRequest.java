package com.fastcampus.snsproject.Controller.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    private String userName;
    private String password;
}
