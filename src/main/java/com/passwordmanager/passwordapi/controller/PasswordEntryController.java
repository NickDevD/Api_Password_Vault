// src/main/java/com/passwordmanager/passwordapi/controller/PasswordEntryController.java
package com.passwordmanager.passwordapi.controller;

import com.passwordmanager.passwordapi.model.PasswordEntry;
import com.passwordmanager.passwordapi.repository.PasswordEntryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marca a classe como um controlador REST
@RequestMapping("/api/passwords") // Define o caminho base para todos os endpoints
@CrossOrigin(origins = "http://127.0.0.1:8000") // Permite requisições do seu frontend (porta padrão do Live Server)
@Tag(name = "Senhas", description = "Operações para gerenciamento de entradas de senhas")
public class PasswordEntryController {

    private final PasswordEntryRepository passwordEntryRepository;

    // Injeção de dependência via construtor (recomendado)
    public PasswordEntryController(PasswordEntryRepository passwordEntryRepository) {
        this.passwordEntryRepository = passwordEntryRepository;
    }

    @Operation(summary = "Obter todas as entradas de senha",
            description = "Retorna uma lista de todas as senhas armazenadas.")
    @ApiResponse(responseCode = "200", description = "Lista de senhas retornada com sucesso")
    @GetMapping
    public List<PasswordEntry> getAllPasswords() {
        return passwordEntryRepository.findAll();
    }

    @Operation(summary = "Obter uma entrada de senha por ID",
            description = "Retorna uma entrada de senha específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha encontrada",
                    content = @Content(schema = @Schema(implementation = PasswordEntry.class))),
            @ApiResponse(responseCode = "404", description = "Senha não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PasswordEntry> getPasswordById(@PathVariable Long id) {
        Optional<PasswordEntry> passwordEntry = passwordEntryRepository.findById(id);
        return passwordEntry.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar uma nova entrada de senha",
            description = "Adiciona uma nova entrada de senha ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Senha criada com sucesso",
                    content = @Content(schema = @Schema(implementation = PasswordEntry.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<PasswordEntry> createPassword(@RequestBody PasswordEntry passwordEntry) {
        PasswordEntry savedEntry = passwordEntryRepository.save(passwordEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
    }

    @Operation(summary = "Atualizar uma entrada de senha existente",
            description = "Atualiza os detalhes (nome do serviço, usuário, senha) de uma entrada de senha existente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = PasswordEntry.class))),
            @ApiResponse(responseCode = "404", description = "Senha não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PasswordEntry> updatePassword(@PathVariable Long id, @RequestBody PasswordEntry passwordEntryDetails) {
        Optional<PasswordEntry> passwordEntryOptional = passwordEntryRepository.findById(id);
        if (passwordEntryOptional.isPresent()) {
            PasswordEntry existingEntry = passwordEntryOptional.get();
            existingEntry.setServiceName(passwordEntryDetails.getServiceName());
            existingEntry.setUsername(passwordEntryDetails.getUsername());
            existingEntry.setPassword(passwordEntryDetails.getPassword()); // Novamente, em produção use hashing!

            PasswordEntry updatedEntry = passwordEntryRepository.save(existingEntry);
            return ResponseEntity.ok(updatedEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir uma entrada de senha",
            description = "Remove uma entrada de senha do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha excluída com sucesso (No Content)"),
            @ApiResponse(responseCode = "404", description = "Senha não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        if (passwordEntryRepository.existsById(id)) {
            passwordEntryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}