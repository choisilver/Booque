package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 책 상세 페이지 밑에 있는 한줄평
// 유저 번호를 얻어서 유저 이름을 표현할 예정
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCommentRegisterDto {
    
    
    private Integer bookId;
    private String commentText;
  //  private String commentWriter;
    
    // 유저 번호 필요함
//    private Integer writerId;
    
}
