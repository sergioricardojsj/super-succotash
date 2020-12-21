package dev.sergior.cursomc.repositories;

import dev.sergior.cursomc.domain.Categoria;
import dev.sergior.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT produto " +
           "FROM Produto produto " +
           "    INNER JOIN produto.categorias cat " +
           "WHERE produto.nome LIKE %:nome% " +
           "    AND cat IN :categorias"
    )
    Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable request);

}
