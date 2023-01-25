package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.domain.User;

@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"postContent"})
public class PostCreateDto {

    private Integer bookId;
    private Integer userId;
    private String title;
    private String postContent;
    private String postWriter; 
    private Integer myScore;
    
    public Post toEntity(Book book, User user) {
        
        return Post.builder().user(user).book(book).title(title).postContent(postContent).postWriter(postWriter).myScore(myScore).build();
    }
}
