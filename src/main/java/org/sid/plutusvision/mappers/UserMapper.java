package org.sid.plutusvision.mappers;

import org.sid.plutusvision.dtos.UserDto;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        if (userDto.getRole() != null) {
            user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));
        }
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        if (user.getRole() != null) {
            userDto.setRole(user.getRole().name());
        }
        userDto.setAccountStatus(user.getAccountStatus());
        return userDto;
    }
}
