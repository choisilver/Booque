package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import site.book.project.domain.Book;
import site.book.project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findByOrderByPostIdDesc();

     List<Post> findByUserIdOrderByCreatedTimeDesc(Integer userId);
     
    // 포스트 제목:
    // select * from POSTS where lower(TITLE) like lower(?) order by ID desc
    List<Post> findByTitleIgnoreCaseContainingOrderByPostIdDesc(String title);
    
    // 포스트 내용:
    // select * from POSTS where lower(POSTCONTENT) like lower(?) order by ID desc
    List<Post> findByPostContentIgnoreCaseContainingOrderByPostIdDesc(String postContent);
    
 


	// bookid로 post글 꺼내서 책상세페이지에서 사용할 예정
	// select * from posts where book_book_id = 1; 예전꺼
	List<Post> findByBookBookId(Integer bookId);
	
	
//	List<Post> findByBookId(Integer bookId);

	
	
	
	
	
	
	
	// 책 상세 사용할 post 별점순, 최신순, 댓글순?
	// 댓글의 경우 post별로 댓글의 개수를 찾아야함. 
	// 별점 낮은순 desc(내림차순)
	List<Post> findByBookBookIdOrderByCreatedTimeDesc(Integer bookId);  // 최근순
	List<Post> findByBookBookIdOrderByMyScore(Integer bookId);   // 별점 낮은 순
	List<Post> findByBookBookIdOrderByMyScoreDesc(Integer bookId); // 별점 높은 순

	// 조회수 순위로 Top 5 리스트 출력
    List<Post> findTop5ByOrderByHitDesc();

	

		
	
	
	


}