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
@ToString(exclude = {"book"})
@Entity(name = "BOOKHITS")
@SequenceGenerator(name = "BOOKHITS_SEQ_GEN", sequenceName = "BOOKHITS_SEQ", initialValue = 1, allocationSize = 1)
public class BookHits {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKHITS_SEQ_GEN")
    private Integer bookHitsId; //PK
    
    private Integer bookId;
    
    @Column(columnDefinition = "integer default 0", nullable = false)   // 조회수의 기본 값을 0으로 지정
    private int hit;
    
    public BookHits update(Integer bookId, int hit) {
        this.bookId = bookId;
        this.hit = hit;
                
        return this;
    }
}
