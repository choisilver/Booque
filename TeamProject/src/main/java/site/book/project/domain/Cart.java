package site.book.project.domain;

import javax.persistence.Column;
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
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"user","book"})
@Entity(name= "CARTS")
@SequenceGenerator(name = "CARTS_SEQ_GEN", sequenceName = "CARTS_SEQ", initialValue = 1, allocationSize = 1)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARTS_SEQ_GEN")
    private Integer cartId; // 안써도 되지만 PK는 필요해서 넣은 것
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; 
        
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    
    @Column(nullable = false)
    private int cartBookCount;
    
    // (하은) 유저당 중복되는 책 수량 증가시키기
    public Cart update(Integer count) {
        this.cartBookCount = count;
        return this;
    }
    
}
