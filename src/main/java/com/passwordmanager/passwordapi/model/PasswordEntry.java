// src/main/java/com/passwordmanager/passwordapi/model/PasswordEntry.java
package com.passwordmanager.passwordapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Marca a classe como uma entidade JPA para mapeamento de banco de dados
public class PasswordEntry {

    @Id // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura geração automática de ID
    private Long id;
    private String serviceName; // Nome do serviço (ex: Google, Facebook)
    private String username;    // Nome de usuário/email para o serviço
    private String password;    // A senha (EM PROJETOS REAIS, SEMPRE ARMAZENE HASHES DE SENHAS!)
    // Para este exemplo, vamos simplificar para focar na API.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}