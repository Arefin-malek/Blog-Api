package com.Arefin.services;

import java.util.List;

import com.Arefin.entities.Post;
import com.Arefin.payloads.PostDto;
import com.Arefin.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);
	
	PostDto updatePost(PostDto postDto , Integer postId);
	
	void deletePost(Integer postId);
	
    PostResponse getAllPost(Integer PageNumber , Integer PageSize , String sortBy , String sortDir);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getPostByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);
	
}
