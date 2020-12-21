package dev.sergior.cursomc.resources;

import dev.sergior.cursomc.domain.Produto;
import dev.sergior.cursomc.dtos.ProdutoDTO;
import dev.sergior.cursomc.resources.utils.URL;
import dev.sergior.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "order", defaultValue = "nome") String order,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias
    ) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> produtoPage = produtoService.search(nomeDecoded, ids, page, linesPerPage, order, direction);
        Page<ProdutoDTO> produtosDto = produtoPage.map(ProdutoDTO::new);

        return ResponseEntity.status(HttpStatus.OK).body(produtosDto);
    }

}
