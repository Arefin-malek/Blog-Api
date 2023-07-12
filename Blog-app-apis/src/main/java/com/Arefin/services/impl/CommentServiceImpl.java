package com.Arefin.services.impl;

import java.io.IOException;

import javax.activation.CommandObject;
import javax.activation.DataHandler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.Arefin.entities.Comment;
import com.Arefin.entities.Post;
import com.Arefin.exceptions.ResouceNotFoundException;
import com.Arefin.payloads.CommentDto;
import com.Arefin.repositories.CommentRepo;
import com.Arefin.repositories.PostRepo;
import com.Arefin.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post" ,"Post ID " , postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment comment2 = this.commentRepo.save(comment);
		return this.modelMapper.map(comment2, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com  = this.commentRepo.findById(commentId).orElseThrow(()-> new ResouceNotFoundException("Comment" ,"Comment ID " , commentId));
		this.commentRepo.delete(com);
	}

}
