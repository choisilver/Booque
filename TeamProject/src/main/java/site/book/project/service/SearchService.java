package site.book.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.SearchRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchRepository searchRepository;
    
    
    @Transactional(readOnly = true)
    public Page<Book> search(String type, String keyword, String order, Pageable pageable){
        Page<Book> list = null;
        
        // 드랍 다운 검색 타입(value 속성):
        
        if (order.equals("0")) { // 정렬 기준이 없을 때
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.unifiedSearchByKeyword(keyword, pageable);
            }  else if (type.equals("do")) { // 국내 도서 검색
                return list = searchRepository.domesticSearchByKeyword(keyword, pageable);
            } else if (type.equals("fo")) { // 외국 도서 검색
                return list = searchRepository.foreignSearchByKeyword(keyword, pageable);
            } else if (type.equals("au")) { // 저자 검색
                return list = searchRepository.authorSearchByKeyword(keyword, pageable);
            }
        } else if (order.equals("highPrice")) { // 최고가순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByHighPrice(keyword, pageable);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByHighPrice(keyword, orderType, pageable);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByHighPrice(keyword, orderType, pageable);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByHighPrice(keyword, orderType, pageable);
            }
        } else if (order.equals("lowPrice")) { // 최저가순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByLowPrice(keyword, pageable);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByLowPrice(keyword, orderType, pageable);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByLowPrice(keyword, orderType, pageable);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByLowPrice(keyword, orderType, pageable);
            }
        } else if (order.equals("publishedDate")) { // 신상품순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByPublishedDate(keyword, pageable);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByPublishedDate(keyword, orderType, pageable);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByPublishedDate(keyword, orderType, pageable);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByPublishedDate(keyword, orderType, pageable);
            }
        } else if (order.equals("highScore")) { // 별점순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByhighScore(keyword, pageable);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByhighScore(keyword, orderType, pageable);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByhighScore(keyword, orderType, pageable);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByhighScore(keyword, orderType, pageable);
            }
        } else if (order.equals("accuracy")) { // 정확도순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByAccuracy(keyword, pageable);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByAccuracy(keyword, orderType, pageable);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByAccuracy(keyword, orderType, pageable);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByAccuracy(keyword, orderType, pageable);
            }
        }
        
        
        return list;
    }
    
    
    
    @Transactional(readOnly = true)
    public List<Book> search(String type, String keyword, String order){
        List<Book> list = null;
        
        // 드랍 다운 검색 타입(value 속성): 리뷰순, 조회순
        if (order.equals("0")) { // 정렬 기준이 없을 때
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.unifiedSearchByKeyword(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                return list = searchRepository.domesticSearchByKeyword(keyword);
            } else if (type.equals("fo")) { // 외국 도서 검색
                return list = searchRepository.foreignSearchByKeyword(keyword);
            } else if (type.equals("au")) { // 저자 검색
                return list = searchRepository.authorSearchByKeyword(keyword);
            }
        } else if (order.equals("highPrice")) { // 최고가순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByHighPrice(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByHighPrice(keyword, orderType);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByHighPrice(keyword, orderType);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByHighPrice(keyword, orderType);
            }
        } else if (order.equals("lowPrice")) { // 최저가순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByLowPrice(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByLowPrice(keyword, orderType);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByLowPrice(keyword, orderType);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByLowPrice(keyword, orderType);
            }
        } else if (order.equals("publishedDate")) {
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByPublishedDate(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByPublishedDate(keyword, orderType);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByPublishedDate(keyword, orderType);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByPublishedDate(keyword, orderType);
            }
        } else if (order.equals("reviewCount") || order.equals("hitCount")) {
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.unifiedSearchByKeyword(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                return list = searchRepository.domesticSearchByKeyword(keyword);
            } else if (type.equals("fo")) { // 외국 도서 검색
                return list = searchRepository.foreignSearchByKeyword(keyword);
            } else if (type.equals("au")) { // 저자 검색
                return list = searchRepository.authorSearchByKeyword(keyword);
            }
        } else if (order.equals("highScore")) { // 별점순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByhighScore(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByhighScore(keyword, orderType);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByhighScore(keyword, orderType);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByhighScore(keyword, orderType);
            }
        } else if (order.equals("accuracy")) { // 정확도순 정렬
            if(type.equals("all")) { // 통합 검색
                return list = searchRepository.researchOrderAllByAccuracy(keyword);
            }  else if (type.equals("do")) { // 국내 도서 검색
                String orderType = "국내도서";
                return list = searchRepository.researchOrderByAccuracy(keyword, orderType);
            } else if (type.equals("fo")) { // 외국 도서 검색
                String orderType = "외국도서";
                return list = searchRepository.researchOrderByAccuracy(keyword, orderType);
            } else if (type.equals("au")) { // 저자 검색
                String orderType = "저자";
                return list = searchRepository.researchOrderByAccuracy(keyword, orderType);
            }
        }
        
        return list;
    }
}
