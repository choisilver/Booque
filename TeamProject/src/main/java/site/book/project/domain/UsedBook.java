package site.book.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Entity(name = "USEDBOOK")
@SequenceGenerator(name = "USEDBOOK_SEQ_GEN", sequenceName = "USEDBOOK_SEQ", allocationSize = 1)
public class UsedBook {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USEDBOOK_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false)
    private Integer userId;
    
    @Column(nullable = false)
    private Integer bookId;
    
    @Column(nullable = false)
    private Integer price;
    
    @CreatedDate
    private LocalDateTime createdTime;
    
    @Builder.Default
    private LocalDateTime modifiedTime = LocalDateTime.now();
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private String location;
    
    @Builder.Default
    private Integer wishCount = 0;
    
    @Column(nullable = false)
    private String bookLevel;
    
    @Builder.Default
    private Integer hits = 0;
    
    @Builder.Default
    private Integer chats = 0;
        
}
