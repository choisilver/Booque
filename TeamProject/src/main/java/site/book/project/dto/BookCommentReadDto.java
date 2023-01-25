package site.book.project.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import site.book.project.domain.Book;
import site.book.project.domain.BookComment;
import site.book.project.domain.Post;
import site.book.project.domain.User;


@AllArgsConstructor
@Builder
@Getter
@ToString
public class BookCommentReadDto {
// 어떤 데이터를 넘길건데? bookid, userid,content 끗? 
    // 책번호, 댓글번호(?), 내용, 작성자, 시간
    // 직렬화가 가능한 필드로만 구성해야함.
    
    private Integer bookId;
    private String writer;
    private String commentText;
    private Integer likes;
    private LocalDateTime createdTime; // 댓글 최초 작성 시간
    private LocalDateTime modifiedTime; // 댓글 최종 수정 시간
    private String bookName;
    private String bookImage;
    private String bookAuthor;
    
    // Entity 객체에서 DTO 객체를 생성해서 리턴하는 메서드
    public static BookCommentReadDto fromEntity(BookComment entity) {
        
        return BookCommentReadDto.builder()
                .bookId(entity.getBook().getBookId())
                .writer(entity.getUser().getUsername())
                .commentText(entity.getCommentContent())
                .likes(entity.getLikes())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifiedTime())
                .bookName(entity.getBook().getBookName())
                .bookImage(entity.getBook().getBookImage())
                .build();
        
    }
    
}
