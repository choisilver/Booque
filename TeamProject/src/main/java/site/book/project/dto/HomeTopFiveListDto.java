package site.book.project.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeTopFiveListDto {

    private Integer postId;
    private String postWriter;
    private String title;
    private String postContent;
    private Integer myScore;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private int hit; 
    private Integer bookId;
    private String bookImage;
    private String bookName;
    
}
