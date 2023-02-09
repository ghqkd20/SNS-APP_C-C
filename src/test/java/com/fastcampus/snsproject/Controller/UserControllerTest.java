package com.fastcampus.snsproject.Controller;

import com.fastcampus.snsproject.Controller.Request.UserJoinRequest;
import com.fastcampus.snsproject.Controller.Request.UserLoginRequest;
import com.fastcampus.snsproject.Service.UserService;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.join(userName,password)).thenReturn(mock(User.class));

        mockMvc.perform(post("api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password)))
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입시_이미_회원가입된_userName으로_회원가입을하는경우() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.join(userName,password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/aoi/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password)))
        ).andDo(print())
                .andExpect(status().isConflict());

    }
    @Test
    public void 로그인() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login()).thenReturn("test_token");

        mockMvc.perform(post("api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인시_회원가입이안된_UserName을입력() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login()).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인시_잘못된패스워드를_입() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login()).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}