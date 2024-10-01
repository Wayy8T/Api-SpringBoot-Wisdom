package com.example.CRUD_SpringBoot.repository;
import com.example.CRUD_SpringBoot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUserName(String admin);
}
