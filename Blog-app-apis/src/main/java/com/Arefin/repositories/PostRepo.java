package com.Arefin.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Arefin.entities.Category;
import com.Arefin.entities.Post;
import com.Arefin.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.title like : key")
	List<Post> searchByTitle(@Param("key")String title);
	
	List<Post> findByTitleContaining(String title);
}
