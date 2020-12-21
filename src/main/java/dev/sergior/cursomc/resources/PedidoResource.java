package dev.sergior.cursomc.resources;

import dev.sergior.cursomc.domain.Pedido;
import dev.sergior.cursomc.exceptions.ObjectNotFoundException;
import dev.sergior.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(pedidoService.getPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(pedidoService.getPedido(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insert(@RequestBody Pedido pedido) {
        pedidoService.insert(pedido);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
