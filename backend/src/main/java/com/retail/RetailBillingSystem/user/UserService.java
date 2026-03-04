package com.retail.retailbillingsystem.user;

import com.retail.retailbillingsystem.user.dto.RegisterRequest;
import com.retail.retailbillingsystem.user.dto.LoginRequest;
import com.retail.retailbillingsystem.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.retail.retailbillingsystem.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // ✅ ADD THIS LINE
    private final JwtUtil jwtUtil;

    public String registerUser(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            return "Phone number already exists";
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // 🔐 Encrypt here
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role("MERCHANT")   // automatically assigned
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean passwordMatch =
                passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatch) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token);
    }
}
