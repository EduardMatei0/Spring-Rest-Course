package com.eduardmatei.rest.webservices.restfulwebservices.service;

import com.eduardmatei.rest.webservices.restfulwebservices.entity.User;
import com.eduardmatei.rest.webservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(User user) {
       return repository.save(user);
    }

    public User findById(int id) {
        return repository.findById(id).get();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }


}
