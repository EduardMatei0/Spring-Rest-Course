package com.eduardmatei.rest.webservices.restfulwebservices.service;

import com.eduardmatei.rest.webservices.restfulwebservices.entity.Post;
import com.eduardmatei.rest.webservices.restfulwebservices.entity.User;
import com.eduardmatei.rest.webservices.restfulwebservices.repository.PostRepository;
import com.eduardmatei.rest.webservices.restfulwebservices.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Post> findUserPosts(int id) {
        User user = userRepository.findById(id).get();
        logger.info("User --> {}", user);
        logger.info("User posts--> {}", user.getPosts());
        return user.getPosts();
    }

    public Post findPostById(int postId) {
        return postRepository.findById(postId).get();
    }

    public Post savePost(int userId, Post post) {
        User user = userRepository.findById(userId).get();
        user.addPost(post);
        return postRepository.save(post);
    }
}
