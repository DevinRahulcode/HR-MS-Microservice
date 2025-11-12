package com.example.user_auth_service.service.impl;
import com.example.user_auth_service.config.JwtService;
import com.example.user_auth_service.dto.request.LoginRequestDto;
import com.example.user_auth_service.dto.request.RegisterRequestDto;
import com.example.user_auth_service.dto.response.LoginResponseDto;
import com.example.user_auth_service.entity.User;
import com.example.user_auth_service.enums.Role;
import com.example.user_auth_service.exception.UserAlreadyExistsException;
import com.example.user_auth_service.mapper.UserMapper;
import com.example.user_auth_service.repository.UserRepository;
import com.example.user_auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.expiration-ms}")
    private long jwtExpiration;

    @Override
    public LoginResponseDto register(RegisterRequestDto request) {
        // 1. Check if user already exists
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
                });

        // 2. Map DTO to Entity
        User user = userMapper.registerDtoToUser(request);

        // 3. Set encoded password and default role
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_EMPLOYEE); // Default role

        // 4. Save to database
        userRepository.save(user);

        // 5. Generate JWT
        String token = jwtService.generateToken(user);

        return new LoginResponseDto(token, jwtExpiration);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        // 1. Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        ); // Throws exception if invalid

        // 2. Find user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found after authentication"));

        // 3. Generate JWT
        String token = jwtService.generateToken(user);

        return new LoginResponseDto(token, jwtExpiration);
    }
}
