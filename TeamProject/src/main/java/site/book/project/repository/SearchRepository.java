package site.book.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.book.project.domain.Book;

public interface SearchRepository extends JpaRepository<Book, Integer> {

    // 신상품순, 최저가순, 최고가순에 필요한 쿼리문 시작(Page 타입 리턴하는 쿼리문)
    // 통합(제목, 저자, 출판사, 인트로) 검색
    @Query(
            "select b from BOOKS b"
           + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
           + " or lower(b.author) like lower ('%' || :keyword || '%')"
           + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
           + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.bookName desc"
    )
    Page<Book> unifiedSearchByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);

    // 국내 도서 검색
    @Query(
            "select b from BOOKS b"
           + " where b.bookgroup = '국내도서'"
           + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
           + " or lower(b.author) like lower ('%' || :keyword || '%')"
           + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
           + " or lower(b.bookIntro) like lower ('%' || :keyword || '%'))"
           + " order by b.bookId desc"
    )
    Page<Book> domesticSearchByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);
    
    // 외국 도서 검색
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = '외국도서'"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%'))"
                    + " order by b.bookId desc"
            )
    Page<Book> foreignSearchByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);
    
    // 저자(author) 검색
    @Query(
            "select b from BOOKS b"
           + " where lower(b.author) like lower ('%' || :keyword || '%') order by b.author desc"
    )
    Page<Book> authorSearchByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);
    
    // 최고가순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.prices desc"
            )
    Page<Book> researchOrderAllByHighPrice(@Param(value = "keyword") String keyword, Pageable pageable);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.prices desc"
    )
    Page<Book> researchOrderByHighPrice(@Param(value = "keyword") String keyword, 
                                        @Param(value = "orderType") String orderType, Pageable pageable);
    
    
    // 최저가순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.prices asc"
            )
    Page<Book> researchOrderAllByLowPrice(@Param(value = "keyword") String keyword, Pageable pageable);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.prices asc"
            )
    Page<Book> researchOrderByLowPrice(@Param(value = "keyword") String keyword, 
                                      @Param(value = "orderType") String orderType, Pageable pageable);
    
    // 출판날짜순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.publishedDate desc"
            )
    Page<Book> researchOrderAllByPublishedDate(@Param(value = "keyword") String keyword, Pageable pageable);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.publishedDate desc"
            )
    Page<Book> researchOrderByPublishedDate(@Param(value = "keyword") String keyword,
                                            @Param(value = "orderType") String orderType, Pageable pageable);
 
    // 별점순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.bookScore desc"
            )
    Page<Book> researchOrderAllByhighScore(@Param(value = "keyword") String keyword, Pageable pageable);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.bookScore desc"
    )
    Page<Book> researchOrderByhighScore(@Param(value = "keyword") String keyword, 
                                        @Param(value = "orderType") String orderType, Pageable pageable);
    
    // 정확도순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.bookName desc"
            )
    Page<Book> researchOrderAllByAccuracy(@Param(value = "keyword") String keyword, Pageable pageable);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.bookName desc"
    )
    Page<Book> researchOrderByAccuracy(@Param(value = "keyword") String keyword, 
                                        @Param(value = "orderType") String orderType, Pageable pageable);
    
    
    
    
    // 여기서부터는 리뷰순, 조회순에 필요한 쿼리문 시작(List 타입 리턴하는 쿼리문)
    // 통합(제목, 저자, 출판사, 인트로) 검색
    @Query(
            "select b from BOOKS b"
           + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
           + " or lower(b.author) like lower ('%' || :keyword || '%')"
           + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
           + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.bookName desc"
    )
    List<Book> unifiedSearchByKeyword(@Param(value = "keyword") String keyword);

    // 국내 도서 검색
    @Query(
            "select b from BOOKS b"
           + " where b.bookgroup = '국내도서'"
           + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
           + " or lower(b.author) like lower ('%' || :keyword || '%')"
           + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
           + " or lower(b.bookIntro) like lower ('%' || :keyword || '%'))"
           + " order by b.bookId desc"
    )
    List<Book> domesticSearchByKeyword(@Param(value = "keyword") String keyword);
    
    // 외국 도서 검색
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = '외국도서'"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%'))"
                    + " order by b.bookId desc"
            )
    List<Book> foreignSearchByKeyword(@Param(value = "keyword") String keyword);
    
    // 저자(author) 검색
    @Query(
            "select b from BOOKS b"
           + " where lower(b.author) like lower ('%' || :keyword || '%') order by b.author desc"
    )
    List<Book> authorSearchByKeyword(@Param(value = "keyword") String keyword);
    
    // 최고가순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.prices desc"
            )
    List<Book> researchOrderAllByHighPrice(@Param(value = "keyword") String keyword);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.prices desc"
    )
    List<Book> researchOrderByHighPrice(@Param(value = "keyword") String keyword, 
                                        @Param(value = "orderType") String orderType);
    
    
    // 최저가순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.prices asc"
            )
    List<Book> researchOrderAllByLowPrice(@Param(value = "keyword") String keyword);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.prices asc"
            )
    List<Book> researchOrderByLowPrice(@Param(value = "keyword") String keyword, 
                                      @Param(value = "orderType") String orderType);
    
    // 출판날짜순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.publishedDate desc"
            )
    List<Book> researchOrderAllByPublishedDate(@Param(value = "keyword") String keyword);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.publishedDate desc"
            )
    List<Book> researchOrderByPublishedDate(@Param(value = "keyword") String keyword,
                                            @Param(value = "orderType") String orderType);
 
    // 별점순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.bookScore desc"
            )
    List<Book> researchOrderAllByhighScore(@Param(value = "keyword") String keyword);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.bookScore desc"
    )
    List<Book> researchOrderByhighScore(@Param(value = "keyword") String keyword, 
                                        @Param(value = "orderType") String orderType);
    
    // 정확도순 정렬
    @Query(
            "select b from BOOKS b"
                    + " where lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%') order by b.bookName desc"
            )
    List<Book> researchOrderAllByAccuracy(@Param(value = "keyword") String keyword);
    @Query(
            "select b from BOOKS b"
                    + " where b.bookgroup = :orderType"
                    + " and (lower(b.bookName) like lower ('%' || :keyword || '%')"
                    + " or lower(b.author) like lower ('%' || :keyword || '%')"
                    + " or lower(b.publisher) like lower ('%' || :keyword || '%')"
                    + " or lower(b.bookIntro) like lower ('%' || :keyword || '%')) order by b.bookName desc"
    )
    List<Book> researchOrderByAccuracy(@Param(value = "keyword") String keyword, 
                                        @Param(value = "orderType") String orderType);
    
    
}
