package com.eduardmatei.rest.webservices.restfulwebservices.repository;

import com.eduardmatei.rest.webservices.restfulwebservices.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer> {
}
