package io.repositories;

import io.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
    List<User> findByEmail (@Param("email") String login);
}