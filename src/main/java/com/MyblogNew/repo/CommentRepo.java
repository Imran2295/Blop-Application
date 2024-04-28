package com.MyblogNew.repo;

import com.MyblogNew.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment , Long> {

    public List<Comment> findByPostId(long id);

    public Page<Comment> findAllByPostId(long id , Pageable pageable);
}
