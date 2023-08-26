package com.luizrossetti.rinhabackend2023q3java.controller;

import com.luizrossetti.rinhabackend2023q3java.model.Pessoa;
import com.luizrossetti.rinhabackend2023q3java.model.request.PessoaRequest;
import com.luizrossetti.rinhabackend2023q3java.service.PessoaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> create(@RequestBody @Valid PessoaRequest pessoaRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<Pessoa> completableFuture = pessoaService.create(pessoaRequest);
        CompletableFuture.allOf(completableFuture);
        Pessoa pessoaSalva = completableFuture.get();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoaSalva.getId())
                .toUri();
        return ResponseEntity.created(location).body(pessoaSalva);
    }

    @GetMapping("/pessoas/{uuid}")
    public ResponseEntity<Pessoa> getById(@PathVariable UUID uuid) throws ExecutionException, InterruptedException {
        CompletableFuture<Pessoa> completableFuture = pessoaService.getById(uuid);
        CompletableFuture.allOf(completableFuture);
        Pessoa pessoa = completableFuture.get();

        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> getByTerm(@RequestParam(name = "t") @Valid @NotNull String t) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Pessoa>> completableFuture = pessoaService.getByTerm(t);
        CompletableFuture.allOf(completableFuture);

        List<Pessoa> pessoas = completableFuture.get();

        if (pessoas.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Integer> contagemPessoas() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = pessoaService.contagemPessoas();
        CompletableFuture.allOf(completableFuture);

        Integer count = completableFuture.get();

        return ResponseEntity.ok(count);
    }

}