package br.com.cooperforte.avaliacao.mapper;

import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import br.com.cooperforte.avaliacao.api.v1.dto.ClienteDTO;
import br.com.cooperforte.avaliacao.model.Cliente;
import br.com.cooperforte.avaliacao.util.MaskUtil;

public class ClienteMapper {

	public static Cliente from(ClienteDTO dto) {

		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setNome(dto.getNome());
		cliente.setCpf(MaskUtil.removerMascara(dto.getCpf()));
		cliente.setCep(MaskUtil.removerMascara(dto.getCep()));
		cliente.setLogradouro(dto.getLogradouro());
		cliente.setComplemento(dto.getComplemento());
		cliente.setBairro(dto.getBairro());
		cliente.setCidade(dto.getCidade());
		cliente.setUf(dto.getUf());

		if (!CollectionUtils.isEmpty(dto.getTelefones())) {
			cliente.setTelefones(dto.getTelefones().stream().map(TelefoneMapper::from).collect(Collectors.toList()));
		}

		if (!CollectionUtils.isEmpty(dto.getEmails())) {
			cliente.setEmails(dto.getEmails().stream().map(EmailMapper::from).collect(Collectors.toList()));
		}

		return cliente;
	}

	public static ClienteDTO to(Cliente cliente) {

		ClienteDTO dto = new ClienteDTO();
		dto.setId(cliente.getId());
		dto.setNome(cliente.getNome());
		dto.setCpf(MaskUtil.getCpfWithMask(cliente.getCpf()));
		dto.setCep(MaskUtil.getCepWithMask(cliente.getCep()));
		dto.setLogradouro(cliente.getLogradouro());
		dto.setComplemento(cliente.getComplemento());
		dto.setBairro(cliente.getBairro());
		dto.setCidade(cliente.getCidade());
		dto.setUf(cliente.getUf());

		if (!CollectionUtils.isEmpty(cliente.getTelefones())) {
			dto.setTelefones(cliente.getTelefones().stream().map(TelefoneMapper::to).collect(Collectors.toList()));
		}

		if (!CollectionUtils.isEmpty(cliente.getEmails())) {
			dto.setEmails(cliente.getEmails().stream().map(EmailMapper::to).collect(Collectors.toList()));
		}

		return dto;
	}

}
