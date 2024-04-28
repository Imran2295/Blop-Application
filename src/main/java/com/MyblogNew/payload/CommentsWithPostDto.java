package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentsWithPostDto {

    private PostDto post;
    private List<CommentDto> comments;
}
