package site.book.project.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"user", "book" })
@Entity(name= "ORDERS")
@SequenceGenerator(name = "ORDERS_SEQ_GEN", sequenceName = "ORDERS_SEQ", initialValue = 1, allocationSize = 1)
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GEN")
    private Integer orderId; // 안써도 되지만 PK는 필요해서 넣은 것
    
//    @Column(nullable = false) 
//    private Integer orderNo; // 주문번호, PK아님
    
    @Column(nullable = false) 
    private Long orderNo; // 주문번호, PK아님
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; 
        
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    
    @CreatedDate // 주문하는 순간의 시간
    private LocalDateTime orderDate;
    
    @Column(nullable = false)
    private int orderBookCount; 
    
    @Column(nullable = false)
    private int total; 
    
    // (하은) 컬럼 추가 -> 배송지 & 메시지 & 결제방식
    @Column
    private Integer postcode;
    
    @Column
    private String address;
    
    @Column
    private String detailAddress;
    
    @Column
    private String payOption;
    
    @Column
    private String message;
    
    @Builder.Default
    private Integer status = 1;
    
    // (하은) update 추가
    public Order update(Integer postcode, String address, String detailAddress, String payOption, String message) {
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.payOption = payOption;
        this.message = message;
        return this;
    }
    
}
