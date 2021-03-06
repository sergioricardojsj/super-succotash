package dev.sergior.cursomc.services;

import dev.sergior.cursomc.domain.EstadoPagamento;
import dev.sergior.cursomc.domain.PagamentoComBoleto;
import dev.sergior.cursomc.domain.Pedido;
import dev.sergior.cursomc.exceptions.ObjectNotFoundException;
import dev.sergior.cursomc.repositories.ItemPedidoRepository;
import dev.sergior.cursomc.repositories.PagamentoRepository;
import dev.sergior.cursomc.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public List<Pedido> getPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido getPedido(Integer id) throws ObjectNotFoundException {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
    }

    @Transactional
    public Pedido insert(Pedido pedido) throws ObjectNotFoundException {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }

        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        Pedido finalPedido = pedido;
        pedido.getItens().forEach(i -> {
            i.setDesconto(0D);
            try {
                i.setProduto(produtoService.findOne(i.getProduto().getId()));
                i.setPreco(i.getProduto().getPreco());
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
            i.setPedido(finalPedido);
        });

        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationEmail(pedido);

        return pedido;
    }

}
