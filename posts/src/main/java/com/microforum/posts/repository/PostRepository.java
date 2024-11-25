package com.microforum.posts.repository;

import com.microforum.posts.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Get all posts with pagination
    @Query("SELECT p FROM Post p")
    Page<Post> findAllPosts(Pageable pageable);

    // Get posts by user ID with pagination
    @Query("SELECT p FROM Post p WHERE p.userId = :userId")
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);

    // Get posts by category with pagination
    @Query("SELECT p FROM Post p WHERE p.category = :category")
    Page<Post> findByCategory(@Param("category") String category, Pageable pageable);

}
