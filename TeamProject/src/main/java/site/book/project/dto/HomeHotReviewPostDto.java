package site.book.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeHotReviewPostDto {
    
    private Integer postId;
    private int replyCount;
    
}