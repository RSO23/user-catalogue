package rso.usercatalogue.mapper;

import java.util.stream.Collectors;

import rso.usercatalogue.dto.UserDto;
import rso.usercatalogue.entity.User;

public class UserMapper
{
    private UserMapper()
    {
        // empty
    }

    public static User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setGameAccountDtos(user.getGameAccounts().stream()
                .map(GameAccountMapper::mapToDto)
                .collect(Collectors.toSet()));
        return userDto;
    }
}
