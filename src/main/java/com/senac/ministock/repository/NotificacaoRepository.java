package com.senac.ministock.repository;

import com.senac.ministock.entity.Notificacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    // Exclusão lógica (status = -1)
    @Modifying
    @Transactional
    @Query("UPDATE Notificacao n SET n.status = -1 WHERE n.id = :id")
    void apagadoLogicoNotificacao(@Param("id") Integer id);


    @Query("SELECT n FROM Notificacao n WHERE n.status >= 0")
    List<Notificacao> listarNotificacoes();

    // Obter notificação pelo id, apenas se ativa
    @Query("SELECT n FROM Notificacao n WHERE n.id = :id AND n.status >= 0")
    Notificacao obterNotificacaoPeloId(@Param("id") Integer id);
}
