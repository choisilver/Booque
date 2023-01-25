package site.book.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Post;

public interface PostHitsRepository extends JpaRepository<Post, Integer>{
    
    Post findByPostId(Integer postId);
}
