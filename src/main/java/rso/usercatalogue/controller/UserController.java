package rso.usercatalogue.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import rso.usercatalogue.dto.AuthDto;
import rso.usercatalogue.dto.UserDto;
import rso.usercatalogue.entity.User;
import rso.usercatalogue.exception.ApiRequestException;
import rso.usercatalogue.service.UserService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Basic user crud operations", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping
    @ApiOperation(value = "Returns all users.", authorizations = {@Authorization(value = "JWT")})
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Returns the user with specified id.", authorizations = {@Authorization(value = "JWT")})
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id).orElseThrow(() -> new ApiRequestException("User with specified id does not exist!"));
    }

    @GetMapping(path = "/auth/{email}")
    @ApiOperation(value = "Returns the user with specified email.", authorizations = {@Authorization(value = "JWT")})
    public AuthDto getUserAuth(@PathVariable("email") String email) {
        return userService.getAuthDtoByEmail(email);
    }

    @PostMapping
    @ApiOperation(value = "Creates new user.", authorizations = {@Authorization(value = "JWT")})
    public User create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value = "Updates the user.", authorizations = {@Authorization(value = "JWT")})
    public User update(@PathVariable("id") Long id, @Valid @NonNull @RequestBody UserDto updatedUser) {
        return userService.update(id, updatedUser);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletes the user.", authorizations = {@Authorization(value = "JWT")})
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
