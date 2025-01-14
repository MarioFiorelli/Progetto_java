package it.aulab.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.aulab.progetto_finale.models.User;

;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}