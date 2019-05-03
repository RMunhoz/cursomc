package com.rmunhoz.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rmunhoz.cursomc.domain.Categoria;
import com.rmunhoz.cursomc.domain.Cidade;
import com.rmunhoz.cursomc.domain.Cliente;
import com.rmunhoz.cursomc.domain.Endereco;
import com.rmunhoz.cursomc.domain.Estado;
import com.rmunhoz.cursomc.domain.Pagamento;
import com.rmunhoz.cursomc.domain.PagamentoComBoleto;
import com.rmunhoz.cursomc.domain.PagamentoComCartao;
import com.rmunhoz.cursomc.domain.Pedido;
import com.rmunhoz.cursomc.domain.Produto;
import com.rmunhoz.cursomc.domain.enums.EstadoPagamento;
import com.rmunhoz.cursomc.domain.enums.TipoCliente;
import com.rmunhoz.cursomc.repositories.CategoriaRepository;
import com.rmunhoz.cursomc.repositories.CidadeRepository;
import com.rmunhoz.cursomc.repositories.ClienteRepository;
import com.rmunhoz.cursomc.repositories.EnderecoRepository;
import com.rmunhoz.cursomc.repositories.EstadoRepository;
import com.rmunhoz.cursomc.repositories.PagamentoRepository;
import com.rmunhoz.cursomc.repositories.PedidoRepository;
import com.rmunhoz.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRpository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
		
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", "2000,00");
		Produto p2 = new Produto(null, "Impressora", "800,00");
		Produto p3 = new Produto(null, "Mouse", "80,00");
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
			
		categoriaRpository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@email.com", "3333333333333", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("333333333333", "33333333333"));
		
		Endereco e1 = new Endereco(null, "rua flores", "300", "Apto 303", "Jardim", "19800-000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centri", "19700-000", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("03/05/2019 12:33"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("05/05/2019 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}

}
 