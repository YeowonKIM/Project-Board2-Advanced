package com.project.borad2.repository;

import com.project.borad2.entity.Board;
import com.project.borad2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
//    Optional<Board> findById (Long id);
    Optional<Board> findByIdAndUser (Long id, User user);
}
