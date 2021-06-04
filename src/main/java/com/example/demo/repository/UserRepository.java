package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User findFirstUserByName(String name);
    User findUserById(Long id);

    Optional<Object> findByName(String name);
}
