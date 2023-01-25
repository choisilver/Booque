package site.book.project.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;



@Builder
@Data
@ToString(exclude = { "postContent" })
public class PostListDto {

    
    
    private Integer postId; 
    private Integer bookId;
    private Integer userId;
    private int replyCount;
    private String title;
    private String postContent;
    private String bookImage;
    private String postWriter;
    private LocalDateTime modifiedTime;
    private LocalDateTime createdTime;
    private int hit;
    
    
   
    public PostListDto fromEntity(Post p) {
        
        
        
        return PostListDto.builder()
                .userId(p.getUser().getId())
                .postId(p.getPostId())
                .title(p.getTitle())
                .postWriter(p.getPostWriter())
                .bookId(p.getBook().getBookId())
                .bookImage(p.getBook().getBookImage()).modifiedTime(p.getModifiedTime()).createdTime(p.getCreatedTime())
                .postContent(p.getPostContent())
                .build();
    }
    
}
