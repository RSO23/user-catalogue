package rso.usercatalogue.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rso.usercatalogue.dto.AuthDto;
import rso.usercatalogue.dto.UserDto;
import rso.usercatalogue.entity.User;
import rso.usercatalogue.exception.ApiRequestException;
import rso.usercatalogue.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService
{
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> getById(Long id) {
        return userRepository.findById(id).or(() -> userRepository.findById(id));
    }

    public User create(UserDto userDto) {
        if (!userDto.getEmail().matches(EMAIL_REGEX)) {
            throw new ApiRequestException("Invalid email.");
        } else if (emailExist(userDto.getEmail())) {
            throw new ApiRequestException("Email already exists.");
        } else if (usernameExist(userDto.getUsername())) {
            throw new ApiRequestException("Username already exists.");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public AuthDto getAuthDtoByEmail(String username) {
        return userRepository.findByEmail(username).map(user -> {
            AuthDto authDto = new AuthDto();
            authDto.setId(user.getId());
            authDto.setEmail(user.getEmail());
            authDto.setPassword(user.getPassword());
            return authDto;
        }).orElseThrow(() -> new ApiRequestException("User with this email doesn't exist."));
    }

    public User update(Long id, UserDto userDto) {
        Optional<User> userToBeUpdated = userRepository.findById(id);
        return userToBeUpdated.map(user -> {
            setUserAttributes(user, userDto);
            return userRepository.save(user);
        }).orElseThrow(() -> new ApiRequestException("User with this id already exists."));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void setUserAttributes(User user, UserDto userDto) {

        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }

        if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
            if (!userDto.getUsername().equals(user.getUsername()) && usernameExist(userDto.getUsername())) {
                throw new ApiRequestException("Uporabniško ime že obstaja.");
            } else {
                user.setUsername(userDto.getUsername());
            }
        }

        if (userDto.getEmail() != null) {
            if (!userDto.getEmail().matches(EMAIL_REGEX)) {
                throw new ApiRequestException("Neveljaven elektronski naslov.");
            } else if (!userDto.getEmail().equals(user.getEmail()) && emailExist(userDto.getEmail())) {
                throw new ApiRequestException("Elektronski naslov že obstaja.");
            } else {
                user.setEmail(userDto.getEmail());
            }
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

}
