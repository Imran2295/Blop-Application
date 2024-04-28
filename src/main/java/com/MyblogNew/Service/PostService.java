package com.MyblogNew.Service;

import com.MyblogNew.payload.PostDto;
import com.MyblogNew.payload.PostDtoPaging;
import org.hibernate.event.internal.PostDeleteEventListenerStandardImpl;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    public PostDto getPostById(long id);

    List<PostDto> findAllPost();

    PostDtoPaging getAllRecordsWithPaging(int pageNO ,int pageSize ,String sortBy ,String sortDir);

    void deletePostById(long id);

    PostDto updatePost(long id, PostDto dto);
}
