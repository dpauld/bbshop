package group7.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponseDto<T> {

    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;

    // Getters and Setters (omitted for brevity)

}