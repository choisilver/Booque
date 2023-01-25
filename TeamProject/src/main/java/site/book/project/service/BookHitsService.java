package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.BookHits;
import site.book.project.repository.BookHitsRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookHitsService {
    
    private final BookHitsRepository bookHitsRepository;
    
    // 조회수 DB에 저장
    @Transactional
    public void viewCountUp(Integer bookId) {
        BookHits isExist = bookHitsRepository.findByBookId(bookId);
        BookHits dto = null;
        log.info("isexist{}", isExist);
        if (isExist != null && isExist.getBookId() != null) {
            dto = bookHitsRepository.findByBookId(bookId);
            log.info("이미 있는 저장이라면 저장 전{}", dto);
            isExist.update(bookId, isExist.getHit()+1);
            log.info("이미 있는 저장이라면 저장 후{}", dto);
        } else {
            dto = BookHits.builder().hit(1).bookId(bookId).build();
            log.info("처음 저장이라면 저장 전{}", dto);
            bookHitsRepository.save(dto);
            log.info("처음 저장이라면 저장 후{}", dto);
        }
    }

    // 조회수 순으로 정렬할 때 필요한 readAll 메서드
    @Transactional(readOnly = true)
    public List<BookHits> readAllBookIdHitCount() {
        return bookHitsRepository.findAll();
    }
}
