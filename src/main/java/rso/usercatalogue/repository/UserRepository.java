package rso.usercatalogue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rso.usercatalogue.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    @Query("SELECT u FROM User u "
                   + "LEFT JOIN FETCH u.gameAccounts "
                   + "WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Override
    @Query("SELECT u FROM User u "
                   + "LEFT JOIN FETCH u.gameAccounts ")
    List<User> findAll();

    @Override
    @Query("SELECT u FROM User u "
                   + "LEFT JOIN FETCH u.gameAccounts "
                   + "WHERE u.id = :id")
    Optional<User> findById(Long id);
}
