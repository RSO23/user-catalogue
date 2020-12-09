package rso.usercatalogue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rso.usercatalogue.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    public User findByUsername(String username);

    public Optional<User> findByEmail(String email);
}
