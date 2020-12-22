package dev.sergior.cursomc.services;

import dev.sergior.cursomc.domain.Cidade;
import dev.sergior.cursomc.domain.Cliente;
import dev.sergior.cursomc.domain.Endereco;
import dev.sergior.cursomc.domain.TipoCliente;
import dev.sergior.cursomc.dtos.ClienteDTO;
import dev.sergior.cursomc.dtos.ClienteNewDTO;
import dev.sergior.cursomc.exceptions.DataIntegrityException;
import dev.sergior.cursomc.exceptions.ObjectNotFoundException;
import dev.sergior.cursomc.repositories.ClienteRepository;
import dev.sergior.cursomc.repositories.EnderecoRepository;
import dev.sergior.cursomc.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente find(Integer id) throws ObjectNotFoundException {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado!"));
    }

    public Page<Cliente> findAllPage(Integer page, Integer lines, String direction, String orderBy) {
        PageRequest request = PageRequest.of(page, lines, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(request);
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente save(Cliente cliente) throws ObjectNotFoundException {
        this.find(cliente.getId());
        return clienteRepository.save(cliente);
    }

    public void delete(Integer id) throws DataIntegrityException {
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não foi possível deletar o cliente: " + e.getMessage());
        }
    }

    public Cliente update(Cliente cliente) throws ObjectNotFoundException {
        Cliente novoCliente = find(cliente.getId());
        updateData(cliente, novoCliente);

        return clienteRepository.save(novoCliente);
    }

    public Cliente fromDto(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(),
                clienteDTO.getNome(),
                clienteDTO.getEmail(),
                clienteDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(clienteDTO.getTipoCliente()),
                null);
    }

    public Cliente fromDto(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = new Cliente(
                null,
                clienteNewDTO.getNome(),
                clienteNewDTO.getEmail(),
                clienteNewDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(clienteNewDTO.getTipo()),
                encoder.encode(clienteNewDTO.getSenha())
        );

        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(
                null,
                clienteNewDTO.getLogradouro(),
                clienteNewDTO.getNumero(),
                clienteNewDTO.getComplemento(),
                clienteNewDTO.getBairro(),
                clienteNewDTO.getCep(),
                cliente,
                cidade
        );

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone());

        return cliente;
    }

    private void updateData(Cliente cliente, Cliente novoCliente) {
        novoCliente.setNome(cliente.getNome());
        novoCliente.setEmail(cliente.getEmail());
    }

}
