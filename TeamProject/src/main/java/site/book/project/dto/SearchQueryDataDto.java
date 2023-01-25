package site.book.project.dto;

import lombok.Data;

@Data
public class SearchQueryDataDto {

    private String type;
    private String keyword;
    private String order;
    private int page;
}
