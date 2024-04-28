package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentsWithPostPaging {

    private PostDto post;
    private List<CommentDto> comments;

    private int pageSize;
    private int pageNo;
    private  int totalPages;
    private long totalComments;
    private boolean isFirst;
    private boolean isLast;
}
