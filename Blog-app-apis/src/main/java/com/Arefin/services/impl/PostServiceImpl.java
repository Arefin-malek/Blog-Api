package com.Arefin.services.impl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Arefin.entities.Category;
import com.Arefin.entities.Post;
import com.Arefin.entities.User;
import com.Arefin.exceptions.ResouceNotFoundException;
import com.Arefin.payloads.PostDto;
import com.Arefin.payloads.PostResponse;
import com.Arefin.repositories.CategoryRepo;
import com.Arefin.repositories.PostRepo;
import com.Arefin.repositories.UserRepo;
import com.Arefin.services.PostService;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto ,Integer userId , Integer categoryId) {
		
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User", "UserId", userId)); 
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResouceNotFoundException("Category", "Category_Id", categoryId)); 
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost  = this.postRepo.save(post);
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResouceNotFoundException("Post", "Post_ID", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post post2 = this.postRepo.save(post);
		return this.modelMapper.map(post2, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post throw1 = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post", "Post_ID", postId));
		this.postRepo.delete(throw1);

	}

	@Override
	public PostResponse getAllPost(Integer PageNumber , Integer PageSize , String sortBy , String sortDir) {
		
		org.springframework.data.domain.Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = org.springframework.data.domain.Sort.by(sortBy).ascending();
		}
		else {
			sort = org.springframework.data.domain.Sort.by(sortBy).descending();
		}

		org.springframework.data.domain.Pageable p =  PageRequest.of(PageNumber, PageSize , sort); 
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allposts = pagePost.getContent();
  		List<PostDto> postDtos =allposts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
  		PostResponse postResponse = new PostResponse();
  		postResponse.setContent(postDtos);
  		postResponse.setPageNumber(pagePost.getNumber());
  		postResponse.setPageSize(pagePost.getSize());
  		postResponse.setTotalElements(pagePost.getTotalElements());
  		postResponse.setTotalPages(pagePost.getTotalPages());
  		postResponse.setLastPage(pagePost.isLast());
  		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post", "Post_ID", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category", "Category_ID", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos =posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResouceNotFoundException("Category", "Category_ID", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos =posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		//List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos =posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
