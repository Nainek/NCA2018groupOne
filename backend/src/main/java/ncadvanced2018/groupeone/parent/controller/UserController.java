package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import ncadvanced2018.groupeone.parent.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private VerificationService verificationService;

    @Autowired
    public UserController(UserService userService,  VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @PostMapping
    public ResponseEntity <User> create(@Valid @RequestBody User user) {
        log.debug("test user: {}",user);
        User createdUser = userService.create(user);
        verificationService.sendEmail(createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId){
        User userInfo = userService.findById(userId);
        return  new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'VIP_CLIENT')")
    @PutMapping("/update")
    public ResponseEntity<User> updateUserInfo(@RequestBody User user){
        User updatedUser = userService.updateUserInfo(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

}