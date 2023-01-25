package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookWish;
import site.book.project.domain.User;
import site.book.project.dto.BookWishDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.BookWishRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookWishService {

    private final BookWishRepository bookWishRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    // (하은) 사용자 찜한 리스트 찾기
    // 1) 각 사용자에 저장된 찜한 책 리스트 뽑기
    // 2) 해당 책 id로 책 정보 뽑기
    public List<BookWishDto> searchWishList(Integer id) {
        
        List<BookWish> wishList = bookWishRepository.findAllByUserId(id);
        
        List<BookWishDto> wishBookInfo = new ArrayList<>();
        
        for (BookWish bw : wishList) {
            BookWishDto dto = new BookWishDto(
                    bw.getBook().getBookId(), bw.getBook().getAuthor(), bw.getBook().getBookName(), 
                    bw.getBook().getBookImage(), bw.getBook().getPrices(), 
                    bw.getBook().getCategory(), bw.getBook().getBookgroup(),bw.getBookWishId());
            wishBookInfo.add(dto);
        }

        return wishBookInfo;
    }
    
    
    // (지혜) 위시리스트 담기/취소 및 위시 버튼(하트) 색상 상태 전환
    public String changeWishButton(Integer userId, Integer bookId) {
        log.info("changeWishButton(userId={}, bookId={})", userId, bookId);
        
        // DB에 일치하는 데이터가 있는지(이 유저가 이 책을 위시리스트에 담은 상태인지) 체크
        BookWish checkWish = bookWishRepository.findByUserIdAndBookBookId(userId, bookId);
        
        String result;
        
        if (checkWish == null) {   // 위시리스트에 담지 않은 상태라면
            
            User user = userRepository.findById(userId).get();
            Book book = bookRepository.findById(bookId).get();
            BookWish wish = new BookWish(null, user, book);
            
            // 위시리스트 테이블에 추가 후 "selected" 결과값 리턴
            bookWishRepository.save(wish);
            result = "selected";  // 하트를 꽉 찬(빨강) 하트로 바꾸기 위한 문자열
            log.info("Add To WishList(wishListId={}, userId={}, bookId={}), result={})", 
                    wish.getBookWishId(), wish.getUser().getId(), wish.getBook().getBookId(), result);
        } else {   // 위시리스트에 이미 담긴 상태라면
            
            // 위시리스트 테이블에서 삭제 후 "unselected" 결과값 리턴
            bookWishRepository.delete(checkWish);
            result = "unselected";  // 하트를 빈 하트로 바꾸기 위한 문자열
            log.info("Delete From WishList(wishListId={}, userId={}, bookId={}), result={})",
                    checkWish.getBookWishId(), checkWish.getUser().getId(), checkWish.getBook().getBookId(), result);
            
        }
        
        return result;
        
        
//        try {
//            // 위시리스트 테이블에 추가 후 "selected" 결과값 리턴
//            bookWishRepository.save(wish);
//            result = "selected";
//            log.info("Add To WishList(wishListId={}, userId={}, bookId={}), result={})", 
//                    wish.getBookWishId(), wish.getUser().getId(), wish.getBook().getBookId(), result);
//        } catch (DataAccessException e) {  // 이미 중복값이 DB에 있어서 exception 발생하면
//            // 위시리스트 테이블에서 삭제 후 "unselected" 결과값 리턴
//            bookWishRepository.delete(wish);
//            result = "unselected";
//            log.info("Delete From WishList(wishListId={}, userId={}, bookId={}), result={})",
//                    wish.getBookWishId(), wish.getUser().getId(), wish.getBook().getBookId(), result);
//        }
//        
//        return result;
        
        
    }


    public void deleteWish( Integer bookWishId) {
        bookWishRepository.deleteById(bookWishId);
        
        
    }
    
}
