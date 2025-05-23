package com.desafio.produtos.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;
    
    @Column(name = "senha")
    private String password;
    
    @Column(name = "cpf")
    private String cpf;
    
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public void setRole(String role) {
        this.role = Role.fromValue(role);
    }
    public void setRole(Role role) {
        this.role = role;
    }

}

