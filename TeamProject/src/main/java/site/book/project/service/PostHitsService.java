package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.repository.PostHitsRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostHitsService {

    private final PostHitsRepository postHitsRepository;
    
    // 조회수 DB에 저장
    @Transactional
    public void postHitsUp(Integer postId) {
        
        Post dto = postHitsRepository.findByPostId(postId);
        log.info("조회수 1 올라갈 글의 Id={}, 조회수 올라가기 전={}", dto.getPostId(), dto.getHit());
        dto.update(postId, dto.getHit()+1);
        log.info("조회수 1 올라갈 글의 Id={}, 조회수 올라간 후={}", dto.getPostId(), dto.getHit());
    }

    // 조회수 순으로 정렬할 때 필요한 readAll 메서드
    @Transactional(readOnly = true)
    public List<Post> readAllBookIdHitCount() {
        return postHitsRepository.findAll();
    }
    
}
