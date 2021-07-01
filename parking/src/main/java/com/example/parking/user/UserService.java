package com.example.parking.user;

import com.example.parking.user.dto.UserCreateDTO;
import com.example.parking.user.dto.UserMinimalDTO;
import com.example.parking.user.mapper.UserMapper;
import com.example.parking.user.model.ERole;
import com.example.parking.user.model.Role;
import com.example.parking.user.model.User;
import com.example.parking.user.validator.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalDtoFromUser)
                .collect(toList());
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }

    public Notification<UserCreateDTO> edit(UserCreateDTO userCreateDto){
        Notification<UserCreateDTO> notification = new Notification<>();
        if( UsernameOrEmailExists(userCreateDto))
        {
            notification.addError("Email and/or Username already exists");
        }
        else
        {
            User updatedUser = User.builder()
                    .username(userCreateDto.getUsername())
                    .email(userCreateDto.getEmail())
                    .password(encoder.encode( userCreateDto.getPassword()) )
                    .build();

            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(ERole.valueOf(userCreateDto.getRole()))
                    .orElseThrow(() -> new RuntimeException("Cannot find "+ userCreateDto.getRole()+" role"));
            roles.add(role);

            updatedUser.setRoles(roles);
            userRepository.delete(userRepository.findById(userCreateDto.getId()).get());
            notification.setResult(userMapper.toDto(userRepository.save(updatedUser)));

        }
        return notification;

    }

    public Notification<UserCreateDTO> create(UserCreateDTO userCreateDto){
        Notification<UserCreateDTO> notification = new Notification<>();
        if(UsernameOrEmailExists(userCreateDto)) {

            User newUser = User.builder()
                    .username(userCreateDto.getUsername())
                    .email(userCreateDto.getEmail())
                    .password(encoder.encode(userCreateDto.getPassword()))
                    .build();

            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(ERole.valueOf(userCreateDto.getRole()))
                    .orElseThrow(() -> new RuntimeException("Cannot find " + userCreateDto.getRole() + " role"));
            roles.add(role);

            newUser.setRoles(roles);
            notification.setResult(userMapper.toDto(userRepository.save(newUser)));
        }
        else notification.addError("Email and/or Username already exists");
        return notification;
    }

    public UserCreateDTO getUser(long id){
        return userMapper.toDto(userRepository.findById(id).get());
    }

    private boolean UsernameOrEmailExists(UserCreateDTO userCreateDTO){
        if(userRepository.findByEmail(userCreateDTO.getEmail()).isPresent() &&
        userRepository.findByEmail(userCreateDTO.getEmail()).get().getId()!=userCreateDTO.getId())
            return true;
        else
        if(userRepository.findByUsername(userCreateDTO.getUsername()).isPresent() &&
                userRepository.findByUsername(userCreateDTO.getUsername()).get().getId()!=userCreateDTO.getId())
            return true;
        else
        return false;
    }

}
