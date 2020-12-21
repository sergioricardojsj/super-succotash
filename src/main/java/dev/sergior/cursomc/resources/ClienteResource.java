package dev.sergior.cursomc.resources;

import dev.sergior.cursomc.domain.Cliente;
import dev.sergior.cursomc.dtos.ClienteDTO;
import dev.sergior.cursomc.dtos.ClienteNewDTO;
import dev.sergior.cursomc.exceptions.DataIntegrityException;
import dev.sergior.cursomc.exceptions.ObjectNotFoundException;
import dev.sergior.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> clientesDto = clienteService
                .findAll()
                .stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(clientesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientePorId(@PathVariable Integer id) throws ObjectNotFoundException {
        Cliente cliente = clienteService.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "lines", defaultValue = "24") Integer lines,
            @RequestParam(name = "orderBy", defaultValue = "id") String direction,
            @RequestParam(name = "direction", defaultValue = "DESC") String orderBy
    ) {
        Page<Cliente> clientes = clienteService.findAllPage(page, lines, orderBy, direction.toUpperCase());
        Page<ClienteDTO> clientesDto = clientes.map(ClienteDTO::new);

        return ResponseEntity.ok().body(clientesDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO) throws ObjectNotFoundException {
        Cliente cliente = clienteService.fromDto(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.save(cliente);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws DataIntegrityException {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.fromDto(clienteNewDTO);
        cliente = clienteService.insert(cliente);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
