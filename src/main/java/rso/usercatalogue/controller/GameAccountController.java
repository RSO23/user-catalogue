package rso.usercatalogue.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import rso.usercatalogue.dto.GameAccountDto;
import rso.usercatalogue.service.GameAccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Basic operations", produces = "application/json", consumes = "application/json")
public class GameAccountController
{
    private final GameAccountService gameAccountService;

    @PostMapping("/gameAccount")
    public GameAccountDto createOrUpdateGameAccount(@RequestBody GameAccountDto gameAccountDto) {
        return gameAccountService.createOrUpdateGameAccount(gameAccountDto);
    }

    @PutMapping("/gameAccount")
    public GameAccountDto updateGameAccount(@RequestBody GameAccountDto gameAccountDto) {
        return gameAccountService.updateGameAccount(gameAccountDto);
    }

    @DeleteMapping("/gameAccount/{accountId}")
    public void delete(@PathVariable String accountId) {
        gameAccountService.deleteGameAccount(accountId);
    }
}
