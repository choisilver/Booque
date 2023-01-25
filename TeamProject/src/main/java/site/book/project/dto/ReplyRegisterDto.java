package site.book.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.book.project.domain.Post;
import site.book.project.domain.PostReply;
import site.book.project.domain.User;

@NoArgsConstructor
@Data
public class ReplyRegisterDto {

    private Integer postId;
    private String replyContent;
    private String replyWriter;
    private String userImage;
    
    public PostReply toEntity(User user,Post post) {
        return PostReply
                .builder()
                .post(post)
                .user(user)
                .replyContent(replyContent).replyWriter(replyWriter).build();
    }
}
