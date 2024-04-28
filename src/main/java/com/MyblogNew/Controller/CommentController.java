package com.MyblogNew.Controller;

import com.MyblogNew.Service.CommentService;
import com.MyblogNew.payload.CommentDto;
import com.MyblogNew.payload.CommentsWithPostDto;
import com.MyblogNew.payload.CommentsWithPostPaging;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // http://localhost:8080/api/comment/save/1
    @PostMapping("/save/{id}")
    public ResponseEntity<CommentDto> writeComment(@RequestBody @Valid CommentDto commentDto, @PathVariable Long id) {
        CommentDto dto = commentService.craeteComment(commentDto, id);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/comment/getAll-comments
    @GetMapping("/getAll-comments")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> dtos = commentService.getAllComments();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // http://localhost:8080/api/comment/getAllCommentsWithPost/1
    @GetMapping("/getAllCommentsWithPost/{id}")
    public ResponseEntity<CommentsWithPostDto> getCommentWithPost(@PathVariable long id) {
        CommentsWithPostDto commentsWithPost = commentService.getAllCommentsWithPost(id);

        return ResponseEntity.ok(commentsWithPost);
    }


    // http://localhost:8080/api/comment/comments-with-post-with-pagination/1?
    @GetMapping("/comments-with-post-with-pagination/{id}")
    public ResponseEntity<CommentsWithPostPaging> getCommentWithPostPaging(@PathVariable long id,
                                                                           @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                           @RequestParam(name = "pageSize", defaultValue = "2", required = false) int pageSize,
                                                                           @RequestParam(name = "sortBy", defaultValue = "name", required = false) String sortBy,
                                                                           @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        CommentsWithPostPaging commentsWithPost = commentService.getAllCommentsWithPost(id,
                pageNo, pageSize, sortBy, sortDir);

        return ResponseEntity.ok(commentsWithPost);
    }
}
