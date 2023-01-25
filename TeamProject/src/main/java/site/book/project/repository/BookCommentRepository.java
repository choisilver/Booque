package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.book.project.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Integer> {

    
    
//    // 좋아요순, 최신순, 별점순?
//    // 최근순
    List<BookComment> findByBookBookIdOrderByCreatedTimeDesc(Integer bookId);
//    // 오래된 순
    List<BookComment> findByBookBookIdOrderByCreatedTime(Integer bookId);
//    // 좋아요 순ss
    
    // bookId에 달린 모든 comment
//    @Query("select * from BOOKCOMMENT c where c.book.book.id = :bookId")
//    List<BookComment> selectAllComment(@Param(value = "bookId") Integer bookId);
    
    // 라이크 높은 순서
    //select * from bookcomment where book_book_id =1 order by likes;
    List<BookComment> findByBookBookIdOrderByLikesDesc(Integer bookId);
    
    List<BookComment> findByUserIdOrderByCreatedTimeDesc(Integer userId);

    
    
    
    
}
