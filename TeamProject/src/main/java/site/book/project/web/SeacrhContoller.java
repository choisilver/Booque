package site.book.project.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookHits;
import site.book.project.domain.User;
import site.book.project.dto.SearchListDto;
import site.book.project.dto.SearchQueryDataDto;
import site.book.project.dto.SearchReadDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.repository.BookRepository;
import site.book.project.service.BookCommentService;
import site.book.project.service.BookHitsService;
import site.book.project.service.BookService;
import site.book.project.service.CartService;
import site.book.project.service.OrderService;
import site.book.project.service.PostService;
import site.book.project.service.SearchService;
import site.book.project.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class SeacrhContoller {
    
    private final SearchService searchService;
    private final PostService postService;
    private final BookHitsService bookHitsService;
    
    @GetMapping("")
    public String search() {
        log.info("MainSearch()");
        return "/search";
    } 
    
    // 검색 기능 - 검색 결과 정렬(type, keyword를 가지고 다시 order by ?, ?부분만 원하는 order에 따라 바꿔서 검색
    @GetMapping("/s")
    public String search(SearchQueryDataDto dto, Model model, @PageableDefault(size = 5) Pageable pageable) {
        log.info("request : search(type={}, keyword={}, order={})", dto.getType(), dto.getKeyword(), dto.getOrder());
        String type = dto.getType();
        String keyword = dto.getKeyword();
        String order = dto.getOrder();
        int startPage = 0;
        int endPage = 0;
        
        
        // 정렬할 리스트 or 페이지화 할 리스트 
        Page<Book> searchList = searchService.search(type, keyword, order, pageable);
        List<Book> noPageSearchList = searchService.search(type, keyword, order);
        
        
        // 리뷰 수 카운트
        List<SearchListDto> reviewCount = new ArrayList<>(); 
        for (Book b : noPageSearchList) {
            Integer count = postService.countPostByBookId(b.getBookId());
            
            SearchListDto reviewElement = SearchListDto.builder().BookId(b.getBookId()).reviewCount(count).build();
            reviewCount.add(reviewElement);
        }
        
        
        // 리뷰순, 조회수 순 정렬할 때 List 직접 재정렬
        if (order.equals("reviewCount")) { // 리뷰순 정렬
            List<SearchReadDto> list = new ArrayList<>();
            
//            // review 갯수 리스트, comment 갯수 리스트 : 합쳐서 총 리뷰 갯수로 바꾼 리스트
//            List<SearchListDto> reviewPlusCommentCount = new ArrayList<>();
//            
//            // comment 갯수만 담은 리스트
//            List<BookCommentSearchDto> commentCount = new ArrayList<>();
//            for (Book b : noPageSearchList) {
//                Integer count = bookCommentService.countCommentByBookId(b.getBookId());
//                
//                BookCommentSearchDto commentElement = BookCommentSearchDto.builder().bookId(b.getBookId()).commentCount(count).build();
//                commentCount.add(commentElement);
//            }
//            
//            for (BookCommentSearchDto c : commentCount) {
//                log.info("id={}, comment={}", c.getBookId(), c.getCommentCount());
//                for (SearchListDto r : reviewCount) {
//                    log.info("id={}, comment={}", r.getBookId(), r.getReviewCount());
//                    if (c.getBookId().equals(r.getBookId())) {
//                        int reviewPlusComment = r.getReviewCount() + c.getCommentCount();
//                        log.info("합친 갯수={}", reviewPlusComment);
//                        
//                        SearchListDto reviewPlusCommentElement = SearchListDto.builder().BookId(c.getBookId()).reviewPlusCommentCount(reviewPlusComment).build();
//                        reviewPlusCommentCount.add(reviewPlusCommentElement);
//                        break;
//                    }
//                }
//            }
            
            // book리스트 + 리뷰개수리스트 = 합친 리뷰 갯수를 포함하는 bookId정보를 생성
            for (Book s : noPageSearchList) {
                for (SearchListDto l : reviewCount) {
                    if (s.getBookId().equals(l.getBookId())) {
                        SearchReadDto listElement = SearchReadDto.builder().bookId(s.getBookId()).bookName(s.getBookName())
                                .author(s.getAuthor()).publisher(s.getPublisher()).publishedDate(s.getPublishedDate())
                                .prices(s.getPrices()).bookImage(s.getBookImage()).reviewCount(l.getReviewCount())
                                .bookgroup(s.getBookgroup()).category(s.getCategory())
                                .build();
                        list.add(listElement);
                    }
                }
            }
            
            // 리뷰순으로 오름차순 정렬
            list.sort(new Comparator<SearchReadDto>() {
                @Override
                public int compare(SearchReadDto arg0, SearchReadDto arg1) {
                    int reviewCount0 = arg0.getReviewCount();
                    int reviewCount1 = arg1.getReviewCount();
                    
                    if(reviewCount0 == reviewCount1) return 0;
                    else if(reviewCount0 > reviewCount1) return -1;
                    else return 1;
                }
            });
            
            
            // page : 요청으로 들어온 page
            // size : 한 page 당 data 갯수
//            PageRequest pageRequest = PageRequest.of(dto.getPage(), 5);
//            int start = (int) pageRequest.getOffset();
//            int end = (start + pageRequest.getPageSize()) > list.size() ? list.size() : (start + pageRequest.getPageSize());
            
            // 리스트를 Page로 변환 - 그래야 정상적으로 페이징이됨.
            int start= (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());            
            Page<SearchReadDto> reviewList = new PageImpl<>(list.subList(start, end), pageable, list.size());
            
            // 리뷰순에 적용되는 시작페이지, 끝 페이지
            startPage = Math.max(1, reviewList.getPageable().getPageNumber() - 4);
            endPage = Math.min(reviewList.getTotalPages(), reviewList.getPageable().getPageNumber() + 4);
            
            for (SearchReadDto s : reviewList) {
                log.info("id={},count={}", s.getBookId(), s.getReviewCount());
            }
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("searchList", reviewList);
            model.addAttribute("storedType", type);
            model.addAttribute("storedKeyword", keyword);
            model.addAttribute("storedOrder", order);
            model.addAttribute("reviewCount", reviewCount);
            return "/search";
        } else if(order.equals("hitCount")) { // 조회수 순 정렬
            List<BookHits> hitCount = bookHitsService.readAllBookIdHitCount();
            
            List<SearchReadDto> list = new ArrayList<>();
            SearchReadDto listElement = null;
            for (Book s : noPageSearchList) {
                for (BookHits h : hitCount) {
                    if (h.getBookId().equals(s.getBookId())) { // 검색 결과 중에 조회수 정보가 있으면 hit 정보를 그 데이터로 저장
                        boolean isDouble = isNotDouble(s.getBookId(), list); // 값이 존재하면 DB에 있는 hit으로 바꿔 줘야하기 때문에
                        if (isDouble == false) {
                            isDouble = true;
                            if (isDouble) {
                                for (SearchReadDto t : list) {
                                    if (t.getBookId().equals(s.getBookId())) {
                                        t.setHit(h.getHit()); // 0으로 되어 있던 hit 순을 DB에 있는 hit으로 변경
                                        break;
                                    }
                                }
//                                log.info("같지만 0을 getHit으로 바꿔주는 시점={},{}",  s.getBookId(), h.getBookId());
                                break;
                            } 
                        } else {
                        listElement = SearchReadDto.builder().bookId(s.getBookId()).bookName(s.getBookName())
                                .author(s.getAuthor()).publisher(s.getPublisher()).publishedDate(s.getPublishedDate())
                                .prices(s.getPrices()).bookImage(s.getBookImage()).hit(h.getHit())
                                .bookgroup(s.getBookgroup()).category(s.getCategory())
                                .build();
                        list.add(listElement);
//                        log.info("같으면 추가해주는거 빠져나가는 시점={},{}",  s.getBookId(), h.getBookId());
                        break;
                        }
                    }  else if (isNotDouble(s.getBookId(), list)) { 
                        // 검색 결과 중에 조회수 정보가 없어야하며, 리스트에도 저장이 안되어 있을 경우 hit 정보를 0으로 저장
                            listElement = SearchReadDto.builder().bookId(s.getBookId()).bookName(s.getBookName())
                                    .author(s.getAuthor()).publisher(s.getPublisher()).publishedDate(s.getPublishedDate())
                                    .prices(s.getPrices()).bookImage(s.getBookImage()).hit(0)
                                    .bookgroup(s.getBookgroup()).category(s.getCategory())
                                    .build();
                            list.add(listElement);
//                        log.info("다르면 추가해주는거 빠져나가는 시점={},{}",  s.getBookId(), h.getBookId());
                        continue;
                     }
                    }
            }
            
            // 조회수 순으로 오름차순 정렬
            list.sort(new Comparator<SearchReadDto>() {
                @Override
                public int compare(SearchReadDto arg0, SearchReadDto arg1) {
                    int hitCount0 = arg0.getHit() ;
                    int hitCount1 = arg1.getHit();
                    
                    if(hitCount0 == hitCount1) return 0;
                    else if(hitCount0 > hitCount1) return -1;
                    else return 1;
                }
            });
                    
            int start= (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());            
            Page<SearchReadDto> hitList = new PageImpl<>(list.subList(start, end), pageable, list.size());
            
            // 조회순에 적용되는 시작페이지, 끝 페이지
            startPage = Math.max(1, hitList.getPageable().getPageNumber() - 4);
            endPage = Math.min(hitList.getTotalPages(), hitList.getPageable().getPageNumber() + 4);
            
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("searchList", hitList);
            model.addAttribute("storedType", type);
            model.addAttribute("storedKeyword", keyword);
            model.addAttribute("storedOrder", order);
            model.addAttribute("reviewCount", reviewCount);
            return "/search";
        }
        
        
        // 페이징 - 시작페이지, 끝 페이지 (신상품순, 최저가순, 최고가순)
        startPage = Math.max(1, searchList.getPageable().getPageNumber() - 3);
        endPage = Math.min(searchList.getTotalPages(), searchList.getPageable().getPageNumber() + 3);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchList", searchList);
        model.addAttribute("storedType", type);
        model.addAttribute("storedKeyword", keyword);
        model.addAttribute("storedOrder", order);
        model.addAttribute("reviewCount", reviewCount);
        return "/search";
    }
    
    // 조회수 순 리스트 중복 체크(리스트에도 저장이 안되어 있는지 체크) - 저장이 되어 있으면 false, 안되어 있으면 true
    public boolean isNotDouble(Integer bookId, List<SearchReadDto> compareList) {
        boolean result = true;
        List<SearchReadDto> cL = compareList;
        for (SearchReadDto b : cL) {
            if (b.getBookId().equals(bookId)) {
                result = false;
            }
        }
        return result;
    }
    
}
