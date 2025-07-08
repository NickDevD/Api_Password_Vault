// src/main/java/com/passwordmanager/passwordapi/repository/PasswordEntryRepository.java
package com.passwordmanager.passwordapi.repository;

import com.passwordmanager.passwordapi.model.PasswordEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um componente de repositório
public interface PasswordEntryRepository extends JpaRepository<PasswordEntry, Long> {
    // JpaRepository fornece métodos CRUD prontos para a entidade PasswordEntry e seu ID do tipo Long
}