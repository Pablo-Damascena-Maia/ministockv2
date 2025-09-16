package com.senac.ministock.repository;

import com.senac.ministock.entity.Configuracao;
import jakarta.transaction.Transactional;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Configuracao c SET c.status = -1 WHERE c.configuracaoId = :id")
    void apagadoLogicoConfiguracao(@Param("id") Integer configuracaoId);

    @Query("SELECT c FROM Configuracao c WHERE c.status >= 0")
    List<Configuracao> listarConfiguracoes();

    @Query("SELECT c FROM Configuracao c WHERE c.configuracaoId = :id AND c.status >= 0")
    Configuracao obterConfiguracaoPeloId(@Param("id") Integer configuracaoId);

    boolean existsByConfiguracaoValor(String configuracaoValor);

}
