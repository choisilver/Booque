package site.book.project.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.repository.BookRepository;
import site.book.project.repository.PostRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    //

	private final BookRepository bookRepository;
	private final PostRepository postRepository;
	
	// BookId로 Book 꺼냄
	public Book read(Integer id) {
		return bookRepository.findById(id).get();
	}
	// 별점순, 최신순 총 4개의 문장이 필요함
	// 유저번호(나중에블로그로 넘어가야함), 유저이름, 제목, 컨텐트, 별점 DTO 필요
	// 
	public List<String> contentList(Integer bookId){
		List<Post> list =postRepository.findByBookBookId(bookId);
		List<String> contentList = new ArrayList<>();
		
		for(Post p : list) {
			contentList.add(p.getPostContent());
			
		}
		return contentList;
	}

//	public void update(Integer bookScore, Integer) {
//		Book entity = bookRepository
//	}
	
	
//	
//	// 별점 소숫점 첫째 자리까지 완.
//	@Transactional
//	public Double scoreAvg(Integer bookId) {
//		// 리턴 값은 1~5점까지 소숫점 첫째자리인, double
//		double avg = 2.5;
//		
//		Book book = bookRepository.findById(bookId).get();
//		List<Post> list =postRepository.findByBookBookId(bookId);
//		
//		if(book.getBookScore() == null) {
//			book.update(25);  // 포스트가 없을 경우를 고려해야 하나??
//			log.info("업데이트가 되었을까요?! {}" , book.getBookScore());
//			return avg;
//		}else {
//			Integer score = book.getBookScore(); // 책에서 꺼낸 현재 점수야
//			log.info("원래 저장되어 있던 점수입니달 {}", score);
//			for(Post p : list) {
//				log.info("포스트에 작성 되어 있던 점수들 {}", p.getMyScore());
//				score+=p.getMyScore()*10;
//			}
//			log.info("원래 저장되 모두 더해진 수~!!! {}", score);
//			score = score/(list.size()+1);  // int여서 38.333아닌 그냥 38이 나옴
//			log.info("원래  길이로 나눔 ~~~~~~~ {}", score);
//			// 여기에 값을 저장해야함.
//			book.update(score);
//			double sAvg = score/10.0; // 
//			log.info("그럼 내가 원하는 3.8이라는 수가 나오나 ?? {}", sAvg);  // 그럼 저장은 언제해야함?
//			
//			return sAvg;
//		}
//	}
	
    // (하은) 작가의 다른 책 정보 read
    public List<Book> readAuthor(String author) {
        log.info("author={}", author);
        List<Book> authorOtherBook = bookRepository.findAllByAuthor(author);

        return authorOtherBook;
    }
    public List<Book> read() {
        
        return bookRepository.findByOrderByBookIdDesc();
    }   
	
}
