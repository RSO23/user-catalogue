package rso.usercatalogue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rso.usercatalogue.dto.GameAccountDto;
import rso.usercatalogue.dto.SummonerDto;
import rso.usercatalogue.entity.GameAccount;
import rso.usercatalogue.entity.User;
import rso.usercatalogue.exception.ApiRequestException;
import rso.usercatalogue.feign.DataCatalogueServiceFeign;
import rso.usercatalogue.mapper.GameAccountMapper;
import rso.usercatalogue.repository.GameAccountRepository;
import rso.usercatalogue.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class GameAccountService
{
    private static final Logger log = LoggerFactory.getLogger(GameAccountService.class);

    private final GameAccountRepository gameAccountRepository;

    private final DataCatalogueServiceFeign dataCatalogueServiceFeign;

    private final UserRepository userRepository;

    public GameAccountDto createOrUpdateGameAccount(GameAccountDto gameAccountDto) {
        GameAccount gameAccount = new GameAccount();
        String username = gameAccountDto.getUsername();

        if (gameAccountDto.getAccountId() != null) {
            gameAccount = gameAccountRepository.findByAccountId(gameAccountDto.getAccountId()).orElseThrow(() -> new ApiRequestException("Game account with specified account id doesn't exist!"));
        }
        else {
            SummonerDto summoner = dataCatalogueServiceFeign.getSummonerByUsername(username);
            gameAccount.setAccountId(summoner.getAccountId());

            User user = userRepository.findById(gameAccountDto.getUserId())
                    .orElseThrow(() -> new ApiRequestException("Invalid user id!"));
            gameAccount.setUser(user);

            triggerMatchesUpdateAsync(gameAccount.getAccountId());
        }
        gameAccount.setUsername(username);
        saveGameAccountAsync(gameAccount);
        return GameAccountMapper.mapToDto(gameAccount);
    }

    @Async
    public void saveGameAccountAsync(GameAccount gameAccount) {
        log.info("GameAccountService.saveUserAsync called from thread: " + Thread.currentThread().getName());
        gameAccountRepository.save(gameAccount);
    }

    @Async
    public void triggerMatchesUpdateAsync(String accountId) {
        log.info("GameAccountService.triggerMatchesUpdateAsync called from thread: " + Thread.currentThread().getName());
        dataCatalogueServiceFeign.updateMatches(accountId);
    }

    public GameAccountDto updateGameAccount(GameAccountDto gameAccountDto)
    {
        GameAccount gameAccount = gameAccountRepository.findByAccountId(gameAccountDto.getAccountId()).orElseThrow(() -> new ApiRequestException("Game account with specified account id doesn't exist!"));
        gameAccount.setUsername(gameAccountDto.getUsername());
        gameAccountRepository.save(gameAccount);
        return GameAccountMapper.mapToDto(gameAccount);
    }

    public void deleteGameAccount(String accountId)
    {
        GameAccount gameAccount = gameAccountRepository.findByAccountId(accountId).orElseThrow(() -> new ApiRequestException("Game account with specified account id doesn't exist!"));
        gameAccountRepository.delete(gameAccount);
    }
}
