package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Data
@ToString
@NoArgsConstructor
public class CartDto {
    // 장바구니 창에 보여져야 하는 직렬화된 디비
	// 뷰에서 보여지는 정보 정리함.
    
  //  private Integer userId;
    // 국내도서, 소설, 제목, 가격, 포인트(?), 수량, 사진, 
    
    private String group;
    private String category;
    private String title;
    private String author; // 그냥 넣어놈
    private Integer prices;
    private String image;
    
    private Integer count;
    private Integer cartId;
    
    private Integer bookId;

    
}
