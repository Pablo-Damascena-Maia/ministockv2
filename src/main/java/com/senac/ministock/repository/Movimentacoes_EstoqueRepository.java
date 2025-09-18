package com.senac.ministock.repository;

import com.senac.ministock.repository.entity.Movimentacoes_Estoque;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Movimentacoes_EstoqueRepository extends JpaRepository<Movimentacoes_Estoque, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Movimentacoes_Estoque m SET m.status = -1 WHERE m.id = :id")
    void apagadoLogicoMovimentacao(@Param("id") Integer id);

    @Query("SELECT m FROM Movimentacoes_Estoque m WHERE m.status >= 0")
    List<Movimentacoes_Estoque> listarMovimentacoes();

    @Query("SELECT m FROM Movimentacoes_Estoque m WHERE m.id = :id AND m.status >= 0")
    Movimentacoes_Estoque obterMovimentacaoPeloId(@Param("id") Integer id);
}
