package com.example.parking.user.mapper;


import com.example.parking.user.dto.UserCreateDTO;
import com.example.parking.user.dto.UserDetailsImpl;
import com.example.parking.user.dto.UserMinimalDTO;
import com.example.parking.user.model.Role;
import com.example.parking.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User fromDetailsDto(UserDetailsImpl userDetails);

    User fromDto(UserCreateDTO userCreateDTO);

    UserCreateDTO toDto(User user);

    @Mappings({
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "role", ignore = true)
    })
    UserMinimalDTO userMinimalDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserMinimalDTO userMinimalDTO) {
        userMinimalDTO.setRole(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()).toString());
    }

    @AfterMapping
    default void findRoleForUser(User user, @MappingTarget UserCreateDTO userCreateDTO){
        userCreateDTO.setRole(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).toString());
    }

}
