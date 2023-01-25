package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.book.project.domain.Book;

@AllArgsConstructor
@Builder
@Data
@ToString
@NoArgsConstructor
public class BookOrderDto {
    // order 페이지에 보여줄 간단한 책 데이터
    // private Integer userId;
    private Integer bookId;
    private Integer cartId;
    private Integer prices;
    private Integer count;
    private String bookName;
    private String publisher;
    private String bookImage;
    private String author; 
    private String category;
    private String bookgroup;
    
    public static BookOrderDto fromEntity(Book entity, BuyInfoDto dto) {
        
        BookOrderDto bookOrderDto = new BookOrderDto(
                entity.getBookId(), null, entity.getPrices(), dto.getCount(), entity.getBookName(), entity.getPublisher(),
                entity.getBookImage(), entity.getAuthor(), entity.getCategory(), entity.getBookgroup());
        
        return bookOrderDto;
        
    }
    
    
}
