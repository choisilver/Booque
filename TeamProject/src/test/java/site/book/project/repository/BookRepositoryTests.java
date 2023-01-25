package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookComment;
import site.book.project.domain.BookWish;
import site.book.project.domain.Post;
import site.book.project.dto.BookCommentReadDto;
import site.book.project.dto.BookCommentRegisterDto;
import site.book.project.service.BookCommentService;
import site.book.project.service.BookService;
import site.book.project.service.CategoryService;
import site.book.project.service.PostService;

@Slf4j
@SpringBootTest
public class BookRepositoryTests {
	
	@Autowired
	private  PostRepository postRepository;
	@Autowired
	private  PostService postService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookCommentRepository bookCommentRepository;
	
	@Autowired
	private BookCommentService bookCommentService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired 
	private CategoryService categoryService;
	

	
	@Test
	public void test() {
	    Assertions.assertNotNull(bookCommentRepository);
	  //  List<BookComment> list = bookCommentRepository.findByUserIdOrderByCreatedTimeDesc(21);
 	  //  List<BookComment> list =bookCommentService.readByUserId(21);
//	    for(BookComment c : list) {
//	        
//	        log.info("버자 보자아아ㅏ아ㅏ아아앙 리스트트트ㅡ트 {}", c );
//	    }
 	    List<BookCommentReadDto> listd = bookCommentService.readByUserId(21);
	    for(BookCommentReadDto c : listd) {
	        
	        log.info("버자 보자아아ㅏ아ㅏ아아앙 리스트트트ㅡ트 {}", c );
	    }
	    
	}
	
	
	
	
//	@Test
//	public void test() {
//		Assertions.assertNotNull(postRepository);
//		 // bookbookid를 통해 얻어오기
//		List<Post> postList = postRepository.findByBookBookId(1);
//		for(Post p : postList) {
//			log.info(p.toString());
//		}
//		
//		Assertions.assertNotNull(bookService);
//		
//		List<String> content = bookService.contentList(1);
//		for(String s : content) {
//			log.info(s);
//		}
//		
//		log.info(bookService.scoreAvg(1).toString());
//		
//	}
	
//	@Test
	public void testComment() {
	    // 최신순, 오래된순, 좋아요 순
//	    Assertions.assertNotNull(bookCommentRepository);
//	    List<BookComment> list =  bookCommentRepository.findByBookBookIdOrderByCreatedTimeDesc(1);
//	    for(BookComment b : list) {
//	        log.info("comment 1번 책= {}, 시간 {}",b, b.getCreatedTime() );
//	    }
//	    
//	    List<BookComment> com =  bookCommentRepository.findAll();
//	    for(BookComment b : com) {
//	        log.info("comment = {}", b.getCommentContent());
//	    }
	    
	    
	    // bookCommentRepository 잘 되는지 
	    Assertions.assertNotNull(bookCommentService);
	    
	    
	    
	    
	    List<BookCommentReadDto> list = bookCommentService.readLikeComment(2);
	    
	    Assertions.assertNotNull(list);
	    
	    for(BookCommentReadDto c : list) {
	        log.info("책2번에 대한 한줄 평 {}", c);
	        
	    }
	}
	    
//	    BookCommentRegisterDto dto = new BookCommentRegisterDto(1, "어린왕자는 나의 어린 시절을 대표", 1);
//	    
//	     Integer commentId = bookCommentService.create(dto);
//	    
//	    log.info("생성된 코멘트 아이디 {}" , commentId);
	    
//	    @Test
//	    public void testFindAuthor() {
//	        Assertions.assertNotNull(bookRepository);
//	        
//	        bookService.scoreAvg(23);
//	    
//	    }
	    
}
