package com.example.demo.service.impl;


import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {



    private UserRepository userRepository;

//    @Autowired
//    private ModelMapper modelMapper2;

    private ModelMapper modelMapper;
    private int count;


    public UserServiceImpl( ModelMapper modelMapper ,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

//    @Autowired
//    public void setUserRepository(UserRepository userRepository) {
//        System.out.println("userRepository is set");
//        this.userRepository = userRepository;
//    }
//    ModelMapper modelMapper3 = new ModelMapper();


    @Override
    public UserDto create(User user) {
        log.info("User created.");
        User newUser = userRepository.save(user);
        return this.modelMapper.map(newUser,UserDto.class);

    }
    @PostConstruct
    public void deneme(){
        System.out.println("deneme is called");
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUser() {
        count++;
        System.out.println(count);
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(user-> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByName(String name) {
        User user = userRepository.findFirstUserByName(name);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findUserById(id);
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public User updateUser(User user, Long id) {

       return userRepository.save(user);
    }

    @Override
    public User updateUserPartially(UserDto user, Long id) {
        User userToUpdate = userRepository.findUserById(id);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setName(user.getName());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(userToUpdate);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with  username  " + username));
        return user;
    }

//
//    @Autowired
//    public void setModelMapper2(ModelMapper modelMapper) {
//        this.modelMapper2 = modelMapper;
//    }




}