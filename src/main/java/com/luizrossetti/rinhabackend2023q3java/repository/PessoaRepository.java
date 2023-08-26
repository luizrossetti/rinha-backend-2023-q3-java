package com.luizrossetti.rinhabackend2023q3java.repository;

import com.luizrossetti.rinhabackend2023q3java.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    List<Pessoa> findTop50ByApelidoLikeOrNomeLikeOrStackLike(String apelido, String nome, String stack);
}
