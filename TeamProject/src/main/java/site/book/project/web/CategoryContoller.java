package site.book.project.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.dto.CategoryReadDto;
import site.book.project.service.BookHitsService;
import site.book.project.service.CategoryService;
import site.book.project.service.PostService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
public class CategoryContoller {

    private final CategoryService categoryService;
    private final PostService postService;
    private final BookHitsService bookHitsService;

    @GetMapping("")

    public String sort(CategoryReadDto dto, Model model, @PageableDefault(size = 8) Pageable pageable) {
        log.info("sort: group={}, category={}, page={}", dto.getBookgroup(), dto.getCategory(), dto.getPage());
        String group = dto.getBookgroup();
        String category = dto.getCategory();

        
        Page<Book> list = categoryService.sort(group, category, pageable);

//        List<SearchListDto> reviewCount = new ArrayList<>();
//        for (Book b : list) {
//            Integer count = postService.countPostByBookId(b.getBookId());
//            SearchListDto element = SearchListDto.builder().BookId(b.getBookId()).reviewCount(count).build();
//            reviewCount.add(element);
//        }
        
        // 시작페이지, 끝 페이지
        int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
        int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);        
        model.addAttribute("searchList", list);
        model.addAttribute("bookgroup", group);
        model.addAttribute("category", category);
//        model.addAttribute("reviewCount", reviewCount);

        return "/category";
    }

}