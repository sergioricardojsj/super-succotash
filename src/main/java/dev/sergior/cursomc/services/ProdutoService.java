package dev.sergior.cursomc.services;

import dev.sergior.cursomc.domain.Categoria;
import dev.sergior.cursomc.domain.Produto;
import dev.sergior.cursomc.exceptions.ObjectNotFoundException;
import dev.sergior.cursomc.repositories.CategoriaRepository;
import dev.sergior.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findOne(Integer id) throws ObjectNotFoundException {
        return produtoRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page,
                                Integer linesPerPage, String orderBy, String direction) {

        PageRequest request = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return produtoRepository.search(nome, categorias, request);
    }

}
