package ru.skillbox.authentication.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.authentication.Entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
}
