package site.book.project.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.domain.User;
import site.book.project.dto.PostListDto;
import site.book.project.dto.ReplyReadDto;
import site.book.project.dto.ReplyRegisterDto;
import site.book.project.dto.ReplyUpdateDto;
import site.book.project.service.PostService;
import site.book.project.service.ReplyService;
import site.book.project.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class PostReplyController {

    private final ReplyService replyService;
    private final UserService userService;
    private final PostService postService;

    // 댓글 전체 리스트
    @GetMapping("/all/{postId}")
    public ResponseEntity<List<ReplyReadDto>> readAllReplies(@PathVariable Integer postId){
        log.info("readAllReplies(postId={})", postId);
        
        List<ReplyReadDto> list = replyService.readReplies(postId);
        
        log.info("# of list = {}", list.size());
       

        return ResponseEntity.ok(list);
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
        log.info("registerReply()");

        Integer replyWriter = replyService.create(dto);

        return ResponseEntity.ok(replyWriter);
    }
    
    // 댓글 수정/삭제 모달창에 가져오기
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyReadDto> getReply(@PathVariable Integer replyId) {
        log.info("getReply(replyId={})", replyId);
        
        ReplyReadDto dto = replyService.readReply(replyId);
        log.info("dto={}", dto);
        return ResponseEntity.ok(dto);
    }
    
    // 댓글 삭제
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyId) {
        log.info("deleteReply(replyId={})", replyId);
        
        Integer result = replyService.delete(replyId);
        return ResponseEntity.ok(result);
    }
    
    // 댓글 수정
    @PutMapping("/{replyId}")
    public ResponseEntity<Integer> updateReply(
            @PathVariable Integer replyId,
            @RequestBody ReplyUpdateDto dto) {
        log.info("updateReply(replyId={}, dto={})", replyId, dto);
        dto.setReplyId(replyId); 

        Integer result = replyService.update(dto);
        return ResponseEntity.ok(result);
    }
    
    // 댓글 갯수 갱신
    @GetMapping("/count/{postId}")
    public ResponseEntity<Integer> updateReplyCount(@PathVariable Integer postId){
        List<ReplyReadDto> list = replyService.readReplies(postId);
        log.info("댓글갯수:{}",list.size());
        return ResponseEntity.ok(list.size());
    }
    
   
    
}