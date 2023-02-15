package com.project.borad2.service;

import com.project.borad2.dto.BoardRequestDto;
import com.project.borad2.dto.LoginRequestDto;
import com.project.borad2.dto.MsgResponseDto;
import com.project.borad2.dto.SignupRequestDto;
import com.project.borad2.entity.User;
import com.project.borad2.entity.UserRoleEnum;
import com.project.borad2.jwt.JwtUtil;
import com.project.borad2.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseEntity signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        Optional<User> find = userRepository.findByUsername(username);
        if (find.isPresent()) {
            MsgResponseDto msgResponseDto = new MsgResponseDto("중복 가입입니다.", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(msgResponseDto);
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user);

        MsgResponseDto good = MsgResponseDto.builder()
                .msg("회원가입 완료")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(good);
    }

    @Transactional(readOnly = true)
    public ResponseEntity login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Optional<User> user = userRepository.findByUsername(username); // DB에서 회원 얻어오기
        if (user.isEmpty() || !(user.get().getPassword().equals(password))){
            MsgResponseDto msgResponseDto = new MsgResponseDto("회원 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(msgResponseDto);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(username, user.get().getRole()));

        MsgResponseDto msgResponseDto = new MsgResponseDto("로그인 성공입니다.",HttpStatus.OK.value());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(msgResponseDto);
    }
}