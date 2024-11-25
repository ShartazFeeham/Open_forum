package com.microforum.posts.repository;

import com.microforum.posts.entity.Post;
import com.microforum.posts.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Get tag by name
    @Query("SELECT t FROM Tag t WHERE t.name = :name")
    Optional<Tag> findByName(String name);

    // Get all tags with pagination
    @Query("SELECT t FROM Tag t")
    Page<Tag> findAllTags(Pageable pageable);

    // Get posts by user ID with pagination
    @Query("SELECT p FROM Post p WHERE p.userId = :userId")
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);

    // Get posts by category with pagination
    @Query("SELECT p FROM Post p WHERE p.category = :category")
    Page<Post> findByCategory(@Param("category") String category, Pageable pageable);

}
