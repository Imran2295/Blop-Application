package com.MyblogNew.repo;

import com.MyblogNew.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post , Long> {


}
