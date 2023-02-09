package com.fastcampus.snsproject.Service;


import com.fastcampus.snsproject.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User join(String userName,String password){
        //이미 있는 사람인지 테스트

        //회원가입 진행 - user를 등록


        return new User(userName,password);
    }

    //JWT - Return String
    public String login(){
        return "";
    }
}
