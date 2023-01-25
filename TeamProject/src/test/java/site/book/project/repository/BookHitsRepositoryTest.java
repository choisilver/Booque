package site.book.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.BookHits;
import site.book.project.service.BookHitsService;

@SpringBootTest
@Slf4j
public class BookHitsRepositoryTest {
    
    @Autowired
    private BookHitsRepository bookHitsRepository;
    
    @Autowired
    private BookHitsService bookHitsService;
    
    @Test
    public void test() {
//        Assertions.assertNotNull(bookHitsRepository);
//        BookHits dto = BookHits.builder().hit(1).bookId(52).build();
//        
//        bookHitsRepository.save(dto);;
//        log.info("저장 후{}", dto);
        bookHitsService.viewCountUp(3);
    }
}
