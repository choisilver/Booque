package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.BookWish;

public interface BookWishRepository extends JpaRepository<BookWish, Integer> {
    
    // (하은) userId로 찜한 리스트 찾기
    List<BookWish> findAllByUserId(Integer id);
    
    // 유저id, 책id 넘겨받아 2개가 동시에 일치하는 BookWish 타입 찾기
    BookWish findByUserIdAndBookBookId(Integer userId, Integer bookId);
    
    
}
