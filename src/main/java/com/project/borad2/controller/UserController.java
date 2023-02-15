package com.project.borad2.controller;

import com.project.borad2.dto.LoginRequestDto;
import com.project.borad2.dto.MsgResponseDto;
import com.project.borad2.dto.SignupRequestDto;
import com.project.borad2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/auth/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        //에러가 터지면 BindingResult에 담김.
        if(bindingResult.hasErrors()){
            MsgResponseDto msgResponseDto = new MsgResponseDto(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(msgResponseDto);
        } //에러가 생기면 hasErrors에 개수로 담김
        return userService.signup(signupRequestDto);
    }

    // 데이터를 받을때 보낼때는 DTO를 만들자.
    // JSON -> 객체
    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }
}