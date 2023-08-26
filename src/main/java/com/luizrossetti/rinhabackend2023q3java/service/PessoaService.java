package com.luizrossetti.rinhabackend2023q3java.service;

import com.luizrossetti.rinhabackend2023q3java.model.Pessoa;
import com.luizrossetti.rinhabackend2023q3java.model.request.PessoaRequest;
import com.luizrossetti.rinhabackend2023q3java.repository.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Async
    public CompletableFuture<Pessoa> create(PessoaRequest pessoaRequest) {
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(pessoaRequest.getNascimento(), DATEFORMATTER);

        Pessoa pessoa = new Pessoa(pessoaRequest.getApelido(), pessoaRequest.getNome(), ld, pessoaRequest.getStack());

        log.debug("Pessoa: {}", pessoa);
        Pessoa saved = pessoaRepository.save(pessoa);
        return CompletableFuture.completedFuture(saved);
    }

    @Async
    public CompletableFuture<Pessoa> getById(UUID uuid) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(uuid);
        return CompletableFuture.completedFuture(pessoa.orElse(null));
    }

    @Async
    public CompletableFuture<Integer> contagemPessoas() {
        return CompletableFuture.completedFuture(Math.toIntExact(pessoaRepository.count()));
    }

    @Async
    public CompletableFuture<List<Pessoa>> getByTerm(String term) {
        List<Pessoa> pessoas = pessoaRepository.findTop50ByApelidoLikeOrNomeLikeOrStackLike("%" + term + "%", "%" + term + "%", "%" + term + "%");
        return CompletableFuture.completedFuture(pessoas);
    }

}
