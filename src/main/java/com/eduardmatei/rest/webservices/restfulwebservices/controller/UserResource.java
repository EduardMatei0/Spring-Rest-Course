package com.eduardmatei.rest.webservices.restfulwebservices.controller;


import com.eduardmatei.rest.webservices.restfulwebservices.entity.Post;
import com.eduardmatei.rest.webservices.restfulwebservices.entity.User;
import com.eduardmatei.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.eduardmatei.rest.webservices.restfulwebservices.service.PostService;
import com.eduardmatei.rest.webservices.restfulwebservices.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    // retrieve all Users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }


    // retrieve User(int id)
    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = userService.findById(id);
        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        // HATEOS
        Resource<User> resource = new Resource<User>(user);

        ControllerLinkBuilder linkToAllUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        ControllerLinkBuilder linkToThisUser = linkTo(methodOn(this.getClass()).retrieveUser(id));
        resource.add(linkToAllUsers.withRel("all-users"));
        resource.add(linkToThisUser.withRel("this-user"));

        return resource;
    }

    // createUser
    // input - details of user
    // output - CREATED & Return the uri
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User saveUser = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
        Post savePost = postService.savePost(id, post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savePost.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userService.findById(id);

        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        } else {
            userService.deleteById(id);
        }
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id) {
        List<Post> posts = postService.findUserPosts(id);
        logger.info("posts --> {}", posts);
       return postService.findUserPosts(id);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post getPostById(@PathVariable int userId, @PathVariable int postId) {

        User user = userService.findById(userId);
        Post post = postService.findPostById(postId);

        if(post == null) {
            throw new UserNotFoundException("id-" + postId);
        }

        return post;
    }
}
