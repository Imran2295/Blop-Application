package com.MyblogNew.Service;

import com.MyblogNew.payload.CommentDto;
import com.MyblogNew.payload.CommentsWithPostDto;
import com.MyblogNew.payload.CommentsWithPostPaging;

import java.util.List;

public interface CommentService {


    CommentDto craeteComment(CommentDto commentDto , Long id);

    List<CommentDto> getAllComments();

    CommentsWithPostDto getAllCommentsWithPost(long id);

    CommentsWithPostPaging getAllCommentsWithPost(long id, int pageNo, int pageSize, String sortBy, String sortDir);
}
