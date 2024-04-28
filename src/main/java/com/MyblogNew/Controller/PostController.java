package com.MyblogNew.Controller;

import com.MyblogNew.Service.PostService;
import com.MyblogNew.payload.PostDto;
import com.MyblogNew.payload.PostDtoPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {
    @Autowired
    private PostService postService;

    // http://localhost:8080/api/post/save-post
    @PostMapping("/save-post")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        PostDto dto = postService.createPost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/post/post-by-id/1
    @GetMapping("/post-by-id/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
        PostDto postDto = postService.getPostById(id);

        return new ResponseEntity<>(postDto , HttpStatus.OK);
    }

    // http://localhost:8080/api/post/get-all
    @GetMapping("/get-all")
    public ResponseEntity<List<PostDto>> getAllPost(){
      List<PostDto> dto = postService.findAllPost();

      return new ResponseEntity<>(dto , HttpStatus.OK);
    }

    // http://localhost:8080/api/post/allPostWith-paging
    @GetMapping("allPostWith-paging")
    public ResponseEntity<PostDtoPaging> getAllPostWithPaging(
            @RequestParam(name = "pageNo" , defaultValue = "0" , required = false) int pageNO,
            @RequestParam(name = "pageSize" , defaultValue = "3" , required = false) int pageSize,
            @RequestParam(name = "sortBy" , defaultValue = "title" , required = false) String sortBy,
            @RequestParam(name = "sortDir" , defaultValue = "asc" , required = false) String sortDir
    ){
        PostDtoPaging postDtoPaging = postService.getAllRecordsWithPaging(pageNO , pageSize , sortBy , sortDir);

        return new ResponseEntity<>(postDtoPaging , HttpStatus.OK);
    }

    // http://localhost:8080/api/post/delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        postService.deletePostById(id);

        return ResponseEntity.ok("Record is Deleted with id: "+id);
    }

    // http://localhost:8080/api/post/update/1
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id , @RequestBody @Valid PostDto dto){
        PostDto postDto = postService.updatePost(id , dto);

        return new ResponseEntity<>(postDto , HttpStatus.OK);
    }
}
