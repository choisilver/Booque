package site.book.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchListDto {

    // 리뷰순 정렬에 사용되는 Dto변수
    private Integer BookId;
    private Integer reviewCount;
}
