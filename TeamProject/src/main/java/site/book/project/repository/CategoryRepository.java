package site.book.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Book;

public interface CategoryRepository extends JpaRepository<Book, Integer> {

    // (지혜) 분야별 도서 (사이드바 링크)
    Page<Book> findByCategory(String category, Pageable pageable);
    
    // (지혜) 국내도서/외국도서 전체 (사이드바 링크)
    Page<Book> findByBookgroup(String group, Pageable pageable);
    
    // (지혜) 국내도서/외국도서 선택 후 -> 하위 카테고리  (사이드바 링크)

    Page<Book> findByBookgroupAndCategory(String group, String category, Pageable pageable);

    // (홍찬) 메인에서 보여줄 Top8 & category별 & 별점순/리뷰순
    List<Book> findTop4ByCategoryOrderByBookScoreDesc(String category);
    List<Book> findTop4ByCategoryOrderByPostCountDesc(String category);
}
