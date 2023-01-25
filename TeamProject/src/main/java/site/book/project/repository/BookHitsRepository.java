package site.book.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.book.project.domain.BookHits;

public interface BookHitsRepository extends JpaRepository<BookHits, Integer>{

    BookHits findByBookId(Integer bookId);

}
