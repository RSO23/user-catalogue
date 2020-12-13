package rso.usercatalogue.dto;

import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDto
{
    @ApiModelProperty(value = "Name", notes = "User's full name.", position = 1)
    private String name;

    @ApiModelProperty(value = "Username", notes = "User's username.", position = 2)
    private String username;

    @ApiModelProperty(value = "Password", notes = "User's password.", position = 3)
    private String password;

    @ApiModelProperty(value = "Email", notes = "User's email.", position = 4)
    private String email;

    private Set<GameAccountDto> gameAccountDtos;

}
