package rso.usercatalogue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rso.usercatalogue.entity.GameAccount;

public interface GameAccountRepository extends JpaRepository<GameAccount, Long>
{
    Optional<GameAccount> findByAccountId(String accountId);
}
