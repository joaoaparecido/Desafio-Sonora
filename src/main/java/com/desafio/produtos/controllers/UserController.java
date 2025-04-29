package com.desafio.produtos.controllers;

import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.*;
import com.desafio.produtos.repositories.UserRepository;
import com.desafio.produtos.services.LoginService;
import com.desafio.produtos.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;

    public UserController(UserService userService, UserMapper userMapper, LoginService loginService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.loginService = loginService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> listAll() {
        return userService.listAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityService.hasSameCpf(authentication.principal, #id)")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User newUser = userService.save(user);
        return ResponseEntity.ok(userMapper.toDto(newUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO login) {
        return loginService.login(login.getCpf(), login.getPassword());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityService.hasSameCpf(authentication.principal, #id)")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UpdateUserDTO updateUserDTO) {
        User updatedUser = userService.update(id, updateUserDTO);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
}
