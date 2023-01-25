package site.book.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "BOOKS")
@SequenceGenerator(name = "BOOKS_SEQ_GEN", sequenceName = "BOOKS_SEQ", initialValue = 1, allocationSize = 1)
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKS_SEQ_GEN")
    private Integer bookId; // PK
    
    @Column(nullable = false)
    private String bookName;
    
    @Column(nullable = false)
    private String publisher;
    
    @Column(nullable = false)
    private String publishedDate;
    
   
    private String bookImage;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String pages;
    
    @Column(nullable = false)
    private int prices;
    
    @Column(nullable = false)
    private String category;
    


    @Column(nullable = false)
    private long isbn;
    
    @Column(nullable = false)
    private String bookIntro;
    
    @Column
    private String bookIntroImage;
    
    @Column(nullable = false)
    private String bookgroup;
    
    @Builder.Default
    private Integer bookScore = 0; // 책 평점. double타입이 안되서 그냥 
    
    @Column(columnDefinition = "integer default 0", nullable = false)   // 조회수의 기본 값을 0으로 지정
    private int postCount;
    
    public Book update(Integer bookScore) {
    	this.bookScore = bookScore;
    	
    	return this;
    }
    
    public Book updatePostCount(Integer postCount) {
        this.postCount = postCount;
        return this;
    }
    
    
}
