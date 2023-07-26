package io.gnt.bmt.identity.controller;

import io.gnt.bmt.commons.exceptions.InvalidLoginException;
import io.gnt.bmt.identity.model.User;
import io.gnt.bmt.identity.service.IdentityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class IdentityController extends BaseController {

    @Autowired
    IdentityService identityService;

    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> login) {
        try {

            if(login == null) {
                throw new InvalidLoginException("UserName or Password is Empty");
            }

            User userData = identityService.auhtenticateUser(login.get("username"), login.get("password"));

            if(userData == null){
                throw new InvalidLoginException("UserName or Password is Invalid");
            }

            return new ResponseEntity<>(identityService.generateToken(userData), HttpStatus.OK);

        } catch (InvalidLoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return new ResponseEntity<>(identityService.getUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a user by its id")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(
            @Parameter(description = "id of user to be searched") @PathVariable("id") long id) {

        try {
            User user = identityService.getUser(id);

            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
