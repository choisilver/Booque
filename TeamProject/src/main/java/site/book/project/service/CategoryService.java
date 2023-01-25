package site.book.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.dto.SearchReadDto;
import site.book.project.repository.CategoryRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<Book> sort(String group, String category, Pageable pageable) {
        Page<Book> list = null;

        if ((group == null || group.equals("")) & (category == null || category.equals(""))) {    // 모든 책 검색
            list = categoryRepository.findAll(pageable);
        } else if(group == null || group.equals("")) {   // 소분류별 검색

            if(category.equals("경제")) {
                category="경제/경영";
            } else if (category.equals("시")) {
                category="시/에세이";
                }

            list = categoryRepository.findByCategory(category, pageable);
        } else if (category == null) {    // 도서 대분류(국내도서/외국도서)별 검색
            list = categoryRepository.findByBookgroup(group, pageable);
        } else {   // 도서 대분류-소분류별 검색

            if(category.equals("경제")) {
                category="경제/경영";
            } else if (category.equals("시")) {
                category="시/에세이";
                }

            list = categoryRepository.findByBookgroupAndCategory(group, category, pageable);
        }
        
        return list;
    }

}