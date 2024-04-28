package com.MyblogNew.Service.impl;

import com.MyblogNew.Entity.Post;
import com.MyblogNew.Service.PostService;
import com.MyblogNew.exception.RecordNotFoundException;
import com.MyblogNew.payload.PostDto;
import com.MyblogNew.payload.PostDtoPaging;
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
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepo.save(post);

        return mapToDto(savedPost);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(()->
                new RecordNotFoundException("Record Not found with id:"+id));

        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public List<PostDto> findAllPost() {
        List<Post> posts = postRepo.findAll();
        List<PostDto> dtos = posts.stream().map(n -> mapToDto(n)).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public PostDtoPaging getAllRecordsWithPaging(int pageNO ,int pageSize ,String sortBy ,String sortDir) {
        // getting sortBy on what basis and Direction of sort by using ternary operator.
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        //setting a page according to our parameter to display to client.
        Pageable page = PageRequest.of(pageNO , pageSize , sort);
        // getting All the post in the form of pages using repositories findAll(Pageable pageable) method.
        Page<Post> allPages = postRepo.findAll(page);
        // converting the page form data to list of data.
        List<Post> posts = allPages.getContent();
        // converting the list from entity type to Dto type using stream and modelMapper.
        List<PostDto> dtoList = posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
        // Adding all the values to the Dto class which is created to pass all information at once.
        PostDtoPaging paging = new PostDtoPaging();
        paging.setPostDtoList(dtoList);
        paging.setPageNo(allPages.getNumber());
        paging.setPageSize(allPages.getSize());
        paging.setTotalPages(allPages.getTotalPages());
        paging.setTotalElements(allPages.getTotalElements());
        paging.setFirst(allPages.isFirst());
        paging.setLast(allPages.isLast());

        return paging;
    }

    @Override
    public void deletePostById(long id) {
        if(postRepo.existsById(id)){
            postRepo.deleteById(id);
        }else {
            throw new RecordNotFoundException("record is not available with reference to this id: " + id);
        }
    }

    @Override
    public PostDto updatePost(long id, PostDto dto) {
        Post post = postRepo.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found of this id: " + id));
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());

        Post updated = postRepo.save(post);
        PostDto postDto = mapToDto(updated);

        return postDto;
    }


    public Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    public PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
