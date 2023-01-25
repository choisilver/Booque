package site.book.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.book.project.domain.Post;

@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = { "content" })
@Setter
public class PostReadDto {

    // post에 있는거 부분만 (serialize? 직렬화 가능하게)
    private Integer bookId;
    private String writer;
    private String content;
    private Integer myScore;
    private LocalDateTime createdTime;
    private Integer replyCount; // 대충 만들어봄 
    private String title;
    private Integer postId;
    private String userImage;
    
    // 작성 시간 안함. 여기에 댓글 수 넣음 되겠다! service에서 만들어서 사용 TODO
    
    public static PostReadDto fromEntity(Post entity) {
        return PostReadDto.builder()
                .postId(entity.getPostId())
                .title(entity.getTitle())
                .bookId(entity.getBook().getBookId())
                .writer(entity.getPostWriter())
                .content(entity.getPostContent())
                .myScore(entity.getMyScore())
                .createdTime(entity.getCreatedTime())
                .build();
        
    }
    
    
    
    
}
