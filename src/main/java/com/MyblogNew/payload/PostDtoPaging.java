package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDtoPaging {

    private List<PostDto> postDtoList;

    private int pageSize;
    private int pageNo;
    private  int totalPages;
    private long totalElements;
    private boolean isFirst;
    private boolean isLast;
}
