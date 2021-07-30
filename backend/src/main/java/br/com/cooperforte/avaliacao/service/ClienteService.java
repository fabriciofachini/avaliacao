package br.com.cooperforte.avaliacao.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.cooperforte.avaliacao.api.v1.dto.ClienteDTO;
import br.com.cooperforte.avaliacao.exception.ServiceException;
import br.com.cooperforte.avaliacao.mapper.ClienteMapper;
import br.com.cooperforte.avaliacao.model.Cliente;
import br.com.cooperforte.avaliacao.model.Email;
import br.com.cooperforte.avaliacao.model.Telefone;
import br.com.cooperforte.avaliacao.model.Usuario;
import br.com.cooperforte.avaliacao.repository.ClienteRepository;
import br.com.cooperforte.avaliacao.repository.EmailRepository;
import br.com.cooperforte.avaliacao.repository.TelefoneRepository;
import br.com.cooperforte.avaliacao.repository.UsuarioRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Transactional(rollbackFor = Exception.class)
	public ClienteDTO salvar(ClienteDTO clienteDTO, String usuarioLogado) {

		Cliente cliente = ClienteMapper.from(clienteDTO);

		if (CollectionUtils.isEmpty(cliente.getTelefones())) {
			throw new ServiceException("Deve ser informado pelo menos 1 telefone.");
		}

		if (CollectionUtils.isEmpty(cliente.getEmails())) {
			throw new ServiceException("Deve ser informado pelo menos 1 email.");
		}

		Usuario usuario = usuarioRepository.findByLogin(usuarioLogado);
		LocalDateTime dataHora = LocalDateTime.now();

		cliente.setUsuario(usuario);
		cliente.setDataHora(dataHora);

		clienteRepository.save(cliente);

		cliente.getTelefones().forEach(telefone -> {
			telefone.setUsuario(usuario);
			telefone.setDataHora(dataHora);
			telefone.setCliente(cliente);

			telefoneRepository.save(telefone);
		});

		cliente.getEmails().forEach(email -> {
			email.setUsuario(usuario);
			email.setDataHora(dataHora);
			email.setCliente(cliente);

			emailRepository.save(email);
		});

		return ClienteMapper.to(cliente);
	}

	@Transactional(rollbackFor = Exception.class)
	public void remover(Long idCliente) {

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ServiceException("ID informado é inválido."));

		clienteRepository.delete(cliente);
	}

	@Transactional(rollbackFor = Exception.class)
	public void removerTelefone(Long idTelefone) {

		Telefone telefone = telefoneRepository.findById(idTelefone)
				.orElseThrow(() -> new ServiceException("ID informado é inválido."));

		telefoneRepository.delete(telefone);
	}

	@Transactional(rollbackFor = Exception.class)
	public void removerEmail(Long idEmail) {

		Email email = emailRepository.findById(idEmail)
				.orElseThrow(() -> new ServiceException("ID informado é inválido."));

		emailRepository.delete(email);
	}

	@Transactional(readOnly = true)
	public List<ClienteDTO> recuperarTodos() {

		List<Cliente> clientes = clienteRepository.findAll();

		return clientes.stream().map(ClienteMapper::to).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClienteDTO recuperar(Long idCliente) {

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ServiceException("ID informado é inválido."));

		return ClienteMapper.to(cliente);
	}

}
