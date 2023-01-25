package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryReadDto {

    
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
    private int page;
}
