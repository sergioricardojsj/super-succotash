package dev.sergior.cursomc.services;

import dev.sergior.cursomc.domain.*;
import dev.sergior.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;


    public void instantiateTestDatabase() throws ParseException {
        Categoria ca1 = new Categoria(null, "Informática");
        Categoria ca2 = new Categoria(null, "Escritório");
        Categoria ca3 = new Categoria(null, "Cama mesa e banho");
        Categoria ca4 = new Categoria(null, "Eletrônicos");
        Categoria ca5 = new Categoria(null, "Jardinagem");
        Categoria ca6 = new Categoria(null, "Decoração");
        Categoria ca7 = new Categoria(null, "Perfumaria");

        Produto p1 = new Produto(null, "Computador", 2000.0);
        Produto p2 = new Produto(null, "Impressora", 800.0);
        Produto p3 = new Produto(null, "Mouse", 80.0);

        Estado mg = new Estado(null, "Minas Gerais");
        Estado sp = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", mg);
        Cidade c2 = new Cidade(null, "São Paulo", sp);
        Cidade c3 = new Cidade(null, "Campinas", sp);

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("30/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 1, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        ca1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        ca2.getProdutos().add(p2);

        p1.getCategorias().add(ca1);
        p2.getCategorias().addAll(Arrays.asList(ca1, ca2));
        p3.getCategorias().add(ca1);

        mg.getCidades().add(c1);
        mg.getCidades().addAll(Arrays.asList(c2, c3));

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().add(ip3);

        p1.getItens().add(ip1);
        p2.getItens().add(ip3);
        p3.getItens().add(ip2);

        categoriaRepository.saveAll(Arrays.asList(ca1, ca2, ca3, ca4, ca5, ca6, ca7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
        estadoRepository.saveAll(Arrays.asList(mg, sp));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
        clienteRepository.save(cli1);
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }

}
