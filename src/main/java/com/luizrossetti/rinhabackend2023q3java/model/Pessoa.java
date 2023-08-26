package com.luizrossetti.rinhabackend2023q3java.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "apelido", length = 32, unique = true, nullable = false)
    private String apelido;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @ElementCollection
    @CollectionTable(name = "stack", joinColumns = {@JoinColumn(name="id")})
    @Column(name = "stack", nullable = false)
    private List<String> stack;

    public Pessoa() {
    }

    public Pessoa(String apelido, String nome, LocalDate nascimento, List<String> stack) {
        this.apelido = apelido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }
}
