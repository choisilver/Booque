package site.book.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookComment;
import site.book.project.domain.User;
import site.book.project.dto.BookCommentReadDto;
import site.book.project.dto.BookCommentRegisterDto;
import site.book.project.repository.BookCommentRepository;
import site.book.project.repository.BookRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookCommentService {

    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    
    // dto는 bookid, comment, username밖에 없음. 그럼comment에 넣으면 다 부족함!
    public Integer create(BookCommentRegisterDto dto, Integer userId) {
        log.info("한줄평 책 번호, 유저 번호, 글 dto {}", dto);
        
        // TODO Repository보다는 Service를 사용하는게 낫겠지? 
        User user = userService.read(userId);
        Book book = bookRepository.findById(dto.getBookId()).get();
//        User user = userRepository.findById(dto.getWriterId()).get();
        
        // User name이 아닌 user번호를 입력을 해야 하나..
        
        // 1206 1150 유저 이름을 올릴 수 있도록 만들기 
        // 1) 유저 이름은 dto에만 존재함
        // 2) 유저 번호를 받고, 그걸 위에 띄워주기 
        // 3) 유저 commentWriter를 유저 번호로 바꿈
        
        BookComment bookComment = BookComment.builder().book(book).commentContent(dto.getCommentText())
                .user(user).likes(10) .build();
        log.info("bookcomment 객체 {} =" , bookComment);
        
        bookCommentRepository.save(bookComment);
        
        // 저장하고 나면 comment id를 리턴
        
        
        
        
        return bookComment.getCommentId();
    }


    public List<BookCommentReadDto> readComment(Integer bookId) {
        log.info("readComment bookid ={}", bookId);
        
        //List<BookComment> list = bookCommentRepository.selectAllComment(bookId);
        // 최근순
        List<BookComment> list = bookCommentRepository.findByBookBookIdOrderByCreatedTimeDesc(bookId);
        
        
        return list.stream().map(BookCommentReadDto:: fromEntity).toList();
    }
    
    
    public List<BookCommentReadDto> readLikeComment(Integer bookId) {
        log.info("readComment like 순으로!!! bookid ={}", bookId);
        
        //List<BookComment> list = bookCommentRepository.selectAllComment(bookId);
        // 최근순
        List<BookComment> list = bookCommentRepository.findByBookBookIdOrderByLikesDesc(bookId);
        
        
        return list.stream().map(BookCommentReadDto:: fromEntity).toList();
    }
    
//    @Transactional
//    public List<BookComment> readByUserId(Integer userId) {
//        
//        //List<BookComment> list = bookCommentRepository.selectAllComment(bookId);
//        // 최근순
//        List<BookComment> list = bookCommentRepository.findByUserIdOrderByCreatedTimeDesc(userId);
//        
//        
//        return list;
//    }
    @Transactional
    public List<BookCommentReadDto> readByUserId(Integer userId) {
        
        //List<BookComment> list = bookCommentRepository.selectAllComment(bookId);
        // 최근순
        List<BookComment> list = bookCommentRepository.findByUserIdOrderByCreatedTimeDesc(userId);
        
        
        return list.stream().map(BookCommentReadDto:: fromEntity).toList();
    }
    

    
    
    
}
