package com.project.borad2.service;

import com.project.borad2.dto.BoardDeleteDto;
import com.project.borad2.dto.BoardRequestDto;
import com.project.borad2.dto.BoardResponseDto;
import com.project.borad2.dto.MsgResponseDto;
import com.project.borad2.entity.Board;
import com.project.borad2.entity.User;
import com.project.borad2.jwt.JwtUtil;
import com.project.borad2.repository.BoardRepository;
import com.project.borad2.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    // 게시글 작성
    @Transactional
    public ResponseEntity createBoard(BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {
        // boardRequestDto : 게시글 관련 내용들
        // httpServletRequest : 토큰 관련 변수

        // 1. 토큰 있는지 확인
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims; // 토큰에서 사용자 정보를 위한 변수

        // 토큰 있는지 없는지 확인
        if (token != null) { // 토큰이 있는거
            if (jwtUtil.validateToken(token)) { // 토큰 유효한지
                claims = jwtUtil.getUserInfoFromToken(token);
            } else { // 토큰 안 유효
                return exceptionRes("토큰이 유효하지 않습니다.");
            }

            // 토큰에서 가져 온 사용자 이름으로 사용자 존재 유무 확인
            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if (findUser.isEmpty()) {
                return exceptionRes("사용자가 존재하지 않습니다.");
            }
            Board board = new Board(boardRequestDto, findUser.get());
            boardRepository.save(board);
//            BoardResponseDto boardResponseDto = new BoardResponseDto(board); // 1
            return ResponseEntity.ok().body(new BoardResponseDto(board)); //2
        } else { // 토큰이 없으면
            return exceptionRes("토큰 없습니다.");
        }
    }

    // 예외처리
    private ResponseEntity<MsgResponseDto> exceptionRes(String msg) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(msg, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(msgResponseDto);
    }


    // 게시글 전체 조회
    public List<BoardResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDto tmpBoardResponseDto = new BoardResponseDto(board);
            boardResponseDto.add(tmpBoardResponseDto);
        }
        return boardResponseDto;
    }

    // 게시글 지정 조회
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }


    // 게시글 수정
    @Transactional
    public ResponseEntity updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        if (token != null) {
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }  else{
                return exceptionRes("토큰이 유효하지 않습니다.");
            }

            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if(findUser.isEmpty()) {
                return exceptionRes("사용자가 존재하지 않습니다.");
            }
            // id로 게시글 있는지 확인
            Optional<Board> board = boardRepository.findById(id);
            if(board.isEmpty()) {
                return exceptionRes("게시글이 존재하지 않습니다.");
            }

            // 게시글 작성자와 토큰에서의 정보가 일치하는지 확인, 일피하면 게시글 수정
            Optional<Board> compare = boardRepository.findByIdAndUser(id, findUser.get() );
            if(compare.isEmpty()) {
                return exceptionRes("작성자 정보가 없습니다.");
            }

            // 이외의 경우는 게시글 수정 가능
            board.get().update(boardRequestDto);
            return ResponseEntity.ok().body(new BoardResponseDto(board.get()));

        } else {
            return exceptionRes("토큰 없습니다.");
        }
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity deleteBoard(Long id, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        if (token != null) {
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }  else{
                return exceptionRes("토큰이 유효하지 않습니다.");
            }

            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if(findUser.isEmpty()) {
                return exceptionRes("사용자가 존재하지 않습니다.");
            }
            // id로 게시글 있는지 확인
            Optional<Board> board = boardRepository.findById(id);
            if(board.isEmpty()) {
                return exceptionRes("게시글이 존재하지 않습니다.");
            }

            // 게시글 작성자와 토큰에서의 정보가 일치하는지 확인, 일피하면 게시글 수정
            Optional<Board> compare = boardRepository.findByIdAndUser(id, findUser.get() );
            if(compare.isEmpty()) {
                return exceptionRes("작성자 정보가 없습니다.");
            }

            // 이외의 경우는 게시글 삭제 가능
            boardRepository.deleteById(id);
            return ResponseEntity.ok().body(new BoardResponseDto(board.get()));

        } else {
            return exceptionRes("토큰 없습니다.");
        }

    }


}

