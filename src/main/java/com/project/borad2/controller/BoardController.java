package com.project.borad2.controller;

import com.project.borad2.dto.BoardDeleteDto;
import com.project.borad2.dto.BoardRequestDto;
import com.project.borad2.dto.BoardResponseDto;
import com.project.borad2.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/post")
    public ResponseEntity createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {
        return boardService.createBoard(boardRequestDto, httpServletRequest);
    }

    @GetMapping("/api/posts")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/api/post/{id}")
    public BoardResponseDto getBorad(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {
        return boardService.updateBoard(id, boardRequestDto, httpServletRequest);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return boardService.deleteBoard(id, httpServletRequest);
    }
}
