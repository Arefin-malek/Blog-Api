package com.Arefin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Arefin.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
