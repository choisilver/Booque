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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "POSTREPLY")
@SequenceGenerator(name = "POSTREPLY_SEQ_GEN", sequenceName = "POSTREPLY_SEQ", initialValue = 1, allocationSize = 1)
public class PostReply extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTREPLY_SEQ_GEN")
    private Integer replyId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post; // 댓글 여러 개가 하나의 포스트에 달릴 수 있다라는 것, 댓글이 달린 포스트 - Foreign Key
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @Column(nullable = false)
    private String replyWriter;
    
    @Column(nullable = false, length = 1000)
    private String replyContent;
    
    public PostReply update(String replyContent) {
        this.replyContent = replyContent;
        return this;
    }

}
