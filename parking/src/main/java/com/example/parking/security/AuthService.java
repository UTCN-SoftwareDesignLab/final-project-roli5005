package com.example.parking.security;



import com.example.parking.security.dto.SignupRequest;
import com.example.parking.user.RoleRepository;
import com.example.parking.user.UserRepository;
import com.example.parking.user.model.ERole;
import com.example.parking.user.model.Role;
import com.example.parking.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(@Valid SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();

        String rolesStr = "CLIENT";
        Set<Role> roles = new HashSet<>();

            Role role = roleRepository.findByName(ERole.valueOf(rolesStr))
                    .orElseThrow(() -> new RuntimeException("Cannot find role"));
            roles.add(role);

        user.setRoles(roles);
        userRepository.save(user);
    }
}
