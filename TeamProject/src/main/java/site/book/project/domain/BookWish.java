package site.book.project.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Entity(name= "BOOKWISH")
@SequenceGenerator(name = "BOOKWISH_SEQ_GEN", sequenceName = "BOOKWISH_SEQ", initialValue = 1, allocationSize = 1)
public class BookWish {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKWISH_SEQ_GEN")
    private Integer bookWishId; // 안써도 되지만 PK는 필요해서 넣은 것
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
