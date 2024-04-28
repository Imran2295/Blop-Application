package com.MyblogNew.Service.impl;

import com.MyblogNew.Entity.Comment;
import com.MyblogNew.Entity.Post;
import com.MyblogNew.Service.CommentService;
import com.MyblogNew.exception.RecordNotFoundException;
import com.MyblogNew.payload.CommentDto;
import com.MyblogNew.payload.CommentsWithPostDto;
import com.MyblogNew.payload.CommentsWithPostPaging;
import com.MyblogNew.payload.PostDto;
import com.MyblogNew.repo.CommentRepo;
import com.MyblogNew.repo.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto craeteComment(CommentDto commentDto , Long id) {
        Post post = postRepo.findById(id).orElseThrow(()->
                new RecordNotFoundException("Post Not Available with this Id:"+id));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        commentRepo.save(comment);
        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
            List<Comment> commentList = commentRepo.findAll();
            List<CommentDto> dtos = commentList.stream().map((n) ->
                    mapToDto(n)).collect(Collectors.toList());
            return dtos;
    }

    @Override
    public CommentsWithPostDto getAllCommentsWithPost(long id) {
        Post post = postRepo.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No Post Available with this id: " + id));
        List<Comment> comments = commentRepo.findByPostId(id);

        PostDto postDto = modelMapper.map(post, PostDto.class);
        List<CommentDto> commentDtos = comments.stream().map((n) -> mapToDto(n)).collect(Collectors.toList());

        CommentsWithPostDto commentsWithPostDto = new CommentsWithPostDto();
        commentsWithPostDto.setComments(commentDtos);
        commentsWithPostDto.setPost(postDto);

        return commentsWithPostDto;
    }

    @Override
    public CommentsWithPostPaging getAllCommentsWithPost(long id, int pageNo, int pageSize, String sortBy, String sortDir) {
        Post post = postRepo.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No Post Found With this Id: " + id));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo , pageSize , sort);
        Page<Comment> all = commentRepo.findAllByPostId(id, pageable);
        List<Comment> commentList = all.getContent();

        List<CommentDto> commentDtos = commentList.stream().map((n) -> mapToDto(n)).collect(Collectors.toList());
        PostDto postDto = modelMapper.map(post, PostDto.class);

        CommentsWithPostPaging comments = new CommentsWithPostPaging();
        comments.setComments(commentDtos);
        comments.setPost(postDto);
        comments.setPageNo(all.getNumber());
        comments.setPageSize(all.getSize());
        comments.setTotalComments(all.getTotalElements());
        comments.setTotalPages(all.getTotalPages());
        comments.setFirst(all.isFirst());
        comments.setLast(all.isLast());

        return comments;
    }


    CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment , CommentDto.class);
    }

    Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto , Comment.class);
    }
}
