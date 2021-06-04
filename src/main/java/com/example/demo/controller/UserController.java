package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.MyService;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
@RequestMapping("/users")
@Slf4j
//@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    private int countController;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MyService myService;

    @Autowired
    private UserService userService;

    @PreDestroy
    public void dest(){
        System.out.println("object destroyed");
    }

    @GetMapping("/all")
    @ApiOperation(value = "Find All Users",
            notes = "Get all users information")
            @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Successful retrieval"),
            @ApiResponse(code = SC_NOT_FOUND, message = "Users not found")})
    public ResponseEntity<List<UserDto>> getAllUser() {
//        countController++;
//        System.out.println("countController: " + countController);
//        myService.callModelMapper();
//        UserService bean = applicationContext.getBean(UserService.class);
//        List<UserDto> users = bean.getAllUser();
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find Users by id",
            notes = "Provide an id to look up specific user from the list")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Successful retrieval"),
                           @ApiResponse(code = SC_NOT_FOUND, message = "Requested user not found")})
    public ResponseEntity getUserById(@PathVariable(value = "id") Long id) {
        try{
            UserDto user = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch(Exception exception){
//            exception.printStackTrace();
            return ResponseEntity.status( HttpStatus.NOT_FOUND).body("Requested user not found");
        }

    }

    @GetMapping()
    @ApiOperation(value = "Find Users by name",
            notes = "Provide a name to look up specific user from the list")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Successful retrieval"),
                           @ApiResponse(code = SC_NOT_FOUND, message = "Requested user not found"),
    })
    public ResponseEntity getUserByName(@ApiParam(value = "Name value of user that you are looking for", required = true)
                                                 @RequestParam(value = "name") String name) {
        try{
            UserDto user = userService.getUserByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch(Exception exception ){
            return ResponseEntity.status( HttpStatus.NOT_FOUND).body("Requested user not found");
        }

    }

    @ApiOperation(value = "Create a new user",
            notes = "You can add a new user")
            @ApiResponses(value = {@ApiResponse(code = SC_CREATED, message = "Successfully created"),
            @ApiResponse(code = SC_METHOD_NOT_ALLOWED, message = "You cannot give a specific id to create a user"),
    })
    @PostMapping()
    public ResponseEntity createUser(@RequestBody User user) throws URISyntaxException {
            try {
                UserDto newUser = userService.create(user);
                return ResponseEntity.created(new URI("/users/" + newUser.getUserId())).body(newUser);
//            userService.create(user);
//            return new ResponseEntity<>(HttpStatus.CREATED);
            }catch(Exception exception){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not created");
            }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable Long id){
        try{
            User updatedUser = userService.updateUser(user,id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity updateUserPartially(@RequestBody User user, @PathVariable Long id){
        try{
            User updatedUser = userService.updateUser(user,id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested user not found for partial update");
        }
    }

    @ApiOperation(value = "Delete a user by id",
            notes = "You can delete user by id")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Successfully deleted"),
            @ApiResponse(code = SC_METHOD_NOT_ALLOWED, message = "You cannot delete all users"),
            @ApiResponse(code = SC_NOT_FOUND, message = "Requested user not found for delete"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id) {
        try{
            userService.deleteUser(id);
          //  return new ResponseEntity(HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
        }catch(EmptyResultDataAccessException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested user not found for delete");
        }

    }
}