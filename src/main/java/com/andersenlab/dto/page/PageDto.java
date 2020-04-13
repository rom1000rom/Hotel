package com.andersenlab.dto.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "content")
public class PageDto<T> {
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private int numberOfElements;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    private List<T> content = new ArrayList<>();
}
