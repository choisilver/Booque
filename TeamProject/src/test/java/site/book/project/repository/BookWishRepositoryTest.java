package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.BookWish;
import site.book.project.dto.BookWishDto;

@Slf4j
@SpringBootTest
public class BookWishRepositoryTest {
    
    @Autowired
    private BookWishRepository bookWishRepository;
    
//    @Test
    public void testFindBookWishList() {
        Assertions.assertNotNull(bookWishRepository);
        List<BookWish> list = bookWishRepository.findAllByUserId(1);
        
        Assertions.assertNotNull(list);
        
        BookWish a = list.get(1);
        log.info("나와라", a.getBookWishId());

        for (BookWish bw : list) {
            log.info("하은 book_id {}", bw.getBookWishId());
        }
        
    }
    
    
}
