package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.book.project.domain.PostReply;

public interface ReplyRepository extends JpaRepository<PostReply, Integer> {

    // 해당 글 번호에 해당하는 댓글 리스트 불러오기
    // select * from POSTREPLY where POSTID order by REPLYID;
    @Query("select r from POSTREPLY r where r.post.id = :postId order by r.id desc")
    List<PostReply> readAllReplies(@Param(value="postId") Integer postId);
    
    // 해당 글 번호에 대한 관련 댓글 전부 삭제하기
    @Modifying
    @Query("delete from POSTREPLY r where r.post.id = :postId")
    void deletePostIdwithdAllReply(@Param(value="postId") Integer postId);

    
}
