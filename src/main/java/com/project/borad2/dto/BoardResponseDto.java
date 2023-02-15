package com.project.borad2.dto;

import com.project.borad2.entity.Board;
import com.project.borad2.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createat;
    private LocalDateTime modifiedat;

    @Builder
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
        this.createat = board.getCreateAt();
        this.modifiedat = board.getModifiedAt();
    }
}
