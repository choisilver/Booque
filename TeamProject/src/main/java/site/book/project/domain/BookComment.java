package site.book.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Entity(name = "BOOKCOMMENT")
@SequenceGenerator(name = "BOOKCOMMENT_SEQ_GEN", sequenceName = "BOOKCOMMENT_SEQ", initialValue = 1, allocationSize = 1)
public class BookComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKCOMMENT_SEQ_GEN")
    private Integer commentId;
    
    @OneToOne
    private User user;
    
    @OneToOne
    private Book book;
    
    @Column(nullable = false, length = 500)
    private String commentContent;
    
    @Column(nullable = false)
    private Integer likes;
}
