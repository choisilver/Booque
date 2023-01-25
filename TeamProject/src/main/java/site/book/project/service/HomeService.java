package site.book.project.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.domain.PostReply;
import site.book.project.dto.HomeHotReviewPostDto;
import site.book.project.dto.HomeTopFiveListDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.CategoryRepository;
import site.book.project.repository.PostRepository;
import site.book.project.repository.ReplyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {
    
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    
    // 전체 별점 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingOrderByBookScore() {
        List<Book> list = null;
        list = bookRepository.findTop4ByOrderByBookScoreDesc();
        
        return list;
    }

    // 전체 리뷰 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingOrderByPostReview() {
        List<Book> list = new ArrayList<>();
        list = bookRepository.findTop4ByOrderByPostCountDesc();
//        
//        List<SearchListDto> reviewCount = new ArrayList<>();
//        for (Post p : list) {
//            
//            Integer count = postService.countPostByBookId(p.getBook().getBookId());
//            
//            SearchListDto reviewElement = SearchListDto.builder().BookId(p.getBook().getBookId()).reviewCount(count).build();
//            reviewCount.add(reviewElement);
//        }
//        
//        List<SearchReadDto> reviewList = new ArrayList<>();
//        for (Post p : list) {
//            for (SearchListDto s : reviewCount) {
//                if (p.getBook().getBookId().equals(s.getBookId())) {
//                    SearchReadDto dto = SearchReadDto.builder().bookId(p.getBook().getBookId()).bookName(p.getBook().getBookName())
//                            .bookImage(p.getBook().getBookImage()).author(p.getBook().getAuthor()).publisher(p.getBook().getPublisher())
//                            .publishedDate(p.getBook().getPublishedDate()).reviewCount(s.getReviewCount()).build();
//                    reviewList.add(dto);                    
//                }
//            }
//        }
//        
//        // 리뷰순으로 오름차순 정렬
//        reviewList.sort(new Comparator<SearchReadDto>() {
//            @Override
//            public int compare(SearchReadDto arg0, SearchReadDto arg1) {
//                int reviewCount0 = arg0.getReviewCount();
//                int reviewCount1 = arg1.getReviewCount();
//                
//                if(reviewCount0 == reviewCount1) return 0;
//                else if(reviewCount0 > reviewCount1) return -1;
//                else return 1;
//            }
//        });
        
        return list;
    }

    // 카테고리별 별점 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingCategoryOrderByBookScore(String category) {
        List<Book> list = new ArrayList<>();
        list = categoryRepository.findTop4ByCategoryOrderByBookScoreDesc(category);
        
        return list;
    }
    
    // 카테고리별 리뷰순 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingCategoryOrderByBookReview(String category) {
        List<Book> list = new ArrayList<>();
        list = categoryRepository.findTop4ByCategoryOrderByPostCountDesc(category);
        
        return list;
    }

    
    // 전체 포스트(리뷰) 중 댓글이 많이 달린 순 1~5위
    @Transactional(readOnly = true)
    public List<HomeTopFiveListDto> readTopFiveHotReviewOrderByPost() {
        List<Post> list = postRepository.findAll();
        
        // postID 당 Reply 갯수 매칭시킨 리스트 만들기
        List<HomeHotReviewPostDto> oneToOnePostIdReplyCount = new ArrayList<>();
        for (Post l : list) {
            List<PostReply> replyList = replyRepository.readAllReplies(l.getPostId());
            int count = replyList.size();
            HomeHotReviewPostDto dto = HomeHotReviewPostDto.builder().postId(l.getPostId()).replyCount(count).build();
            oneToOnePostIdReplyCount.add(dto);
        }
        
        // 리플 많이 달린 순으로 오름차순 정렬
        oneToOnePostIdReplyCount.sort(new Comparator<HomeHotReviewPostDto>() {
            @Override
            public int compare(HomeHotReviewPostDto arg0, HomeHotReviewPostDto arg1) {
                int replyCount0 = arg0.getReplyCount();
                int replyCount1 = arg1.getReplyCount();
                
                if(replyCount0 == replyCount1) return 0;
                else if(replyCount0 > replyCount1) return -1;
                else return 1;
            }
        });
        
        // 만들어진 list 중에서 TOP 5 간추리기
        List<Post> hotReviewTopFiveList = new ArrayList<>();
        int top = 1;
        for (HomeHotReviewPostDto p : oneToOnePostIdReplyCount) {
            if (top < 6) {
                Post elementList = postRepository.findById(p.getPostId()).get();
                hotReviewTopFiveList.add(elementList);
            }
            top++;
        }
        
        // bookname과 bookimage가 추가된 최종 리스트
        List<HomeTopFiveListDto> finalList = new ArrayList<>();
        for (Post p : hotReviewTopFiveList) {
            Book dtoElement = bookRepository.findById(p.getBook().getBookId()).get();
            HomeTopFiveListDto dto = HomeTopFiveListDto.builder().postId(p.getPostId()).postWriter(p.getPostWriter()).title(p.getTitle())
                    .postContent(p.getPostContent().replaceAll("<([^>]+)>", "")).myScore(p.getMyScore()).createdTime(p.getCreatedTime()).modifiedTime(p.getModifiedTime())
                    .hit(p.getHit()).bookId(p.getBook().getBookId()).bookImage(dtoElement.getBookImage()).bookName(dtoElement.getBookName()).build();
                    
            finalList.add(dto);
        }
        
        return finalList;
    }

    // 전체 포스트(조회수순) 1~5위
    @Transactional(readOnly = true)
    public List<HomeTopFiveListDto> readTopFiveBestHitOrderByPost() {
        List<Post> list = new ArrayList<>(); 
        list = postRepository.findTop5ByOrderByHitDesc();
        
        // bookname과 bookimage가 추가된 최종 리스트
        List<HomeTopFiveListDto> finalList = new ArrayList<>();
        for (Post p : list) {
            Book dtoElement = bookRepository.findById(p.getBook().getBookId()).get();
            HomeTopFiveListDto dto = HomeTopFiveListDto.builder().postId(p.getPostId()).postWriter(p.getPostWriter()).title(p.getTitle())
                    .postContent(p.getPostContent().replaceAll("<([^>]+)>", "")).myScore(p.getMyScore()).createdTime(p.getCreatedTime()).modifiedTime(p.getModifiedTime())
                    .hit(p.getHit()).bookId(p.getBook().getBookId()).bookImage(dtoElement.getBookImage()).bookName(dtoElement.getBookName()).build();
                
            finalList.add(dto);
        }
        
        
        return finalList;
    }

    
    

}