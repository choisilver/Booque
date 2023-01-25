package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;

@SpringBootTest
@Slf4j
public class SearchRepositoryTest {
    
    @Autowired
    private SearchRepository searchRepository;
    
    @Test
    public void test() {
        String keyword = "물고기";
//        Assertions.assertNotNull(searchRepository);
////        List<Book> list = searchRepository.unifiedSearchByKeyword(keyword);
//        for (Book b : list) {
//            Assertions.assertTrue(b.getBookName().toLowerCase().contains(keyword.toLowerCase())
//                    ||b.getAuthor().toLowerCase().contains(keyword.toLowerCase())
//                    ||b.getPublisher().toLowerCase().contains(keyword.toLowerCase())
//                    ||b.getBookIntro().toLowerCase().contains(keyword.toLowerCase())
//                    );
//            log.info(b.toString());
//        }
    }
    
}
