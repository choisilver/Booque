package site.book.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchReadDto {
    
    private Integer bookId;
    private String bookImage;
    private String bookName;
    private String author;
    private String publisher;
    private String publishedDate;
    private int prices;
    private Integer myScore;
    private int reviewCount;
    private int hit;
    private String bookgroup;
    private String category;
}
