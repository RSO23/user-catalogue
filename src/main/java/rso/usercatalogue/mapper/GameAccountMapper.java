package rso.usercatalogue.mapper;

import rso.usercatalogue.dto.GameAccountDto;
import rso.usercatalogue.entity.GameAccount;

public class GameAccountMapper
{
    private GameAccountMapper()
    {
        // empty
    }

    public static GameAccountDto mapToDto(GameAccount gameAccount) {
        GameAccountDto gameAccountDto = new GameAccountDto();
        gameAccountDto.setUsername(gameAccount.getUsername());
        gameAccountDto.setAccountId(gameAccount.getAccountId());
        gameAccountDto.setUserId(gameAccount.getUser().getId());
        return gameAccountDto;
    }
}
