package com.Arefin.services;

import com.Arefin.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto , Integer postId);
	
	void deleteComment(Integer commentId);
	
}
