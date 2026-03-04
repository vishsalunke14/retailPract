package com.retail.retailbillingsystem.user;

import com.retail.retailbillingsystem.user.dto.RegisterRequest;
import com.retail.retailbillingsystem.user.dto.LoginRequest;
import com.retail.retailbillingsystem.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}
