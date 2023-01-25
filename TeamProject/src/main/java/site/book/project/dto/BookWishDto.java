package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import site.book.project.domain.Book;
import site.book.project.domain.BookWish;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class BookWishDto {
    
    // (하은) Book, User 데이터 넘겨야해서 만드는 DTO
    
    private Integer bookId;
    private String author;
    private String bookName;
    private String bookImage;
    private int prices;
    private String category;
    private String bookgroup;
    private Integer bookWishId;
    
    public static BookWishDto fromEntity(Book entity) {
        
        BookWishDto bookWishDto = new BookWishDto(
                entity.getBookId(), entity.getAuthor(), entity.getBookName(), entity.getBookImage(), 
                entity.getPrices(), entity.getCategory(), entity.getBookgroup(),null);
        
        return bookWishDto;
    }
    
    

}
