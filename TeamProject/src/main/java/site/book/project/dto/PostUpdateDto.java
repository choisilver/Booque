package site.book.project.dto;

import lombok.Data;
import lombok.ToString;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.domain.User;


@Data
@ToString(exclude = { "postContent" })
public class PostUpdateDto {

    private Integer postId;
    private Integer bookId;
    private Integer userId;
    private String title;
    private String postContent;
    
    public Post toEntity(Book book, User user) {
        
        return Post.builder().postId(postId).user(user).book(book).title(title).postContent(postContent).build();
    }
}
