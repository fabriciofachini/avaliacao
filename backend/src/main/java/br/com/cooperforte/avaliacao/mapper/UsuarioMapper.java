package br.com.cooperforte.avaliacao.mapper;

import br.com.cooperforte.avaliacao.api.v1.dto.UsuarioDTO;
import br.com.cooperforte.avaliacao.model.Usuario;

public class UsuarioMapper {

	public static Usuario from(UsuarioDTO dto) {

		Usuario usuario = new Usuario();
		usuario.setLogin(dto.getLogin());
		usuario.setNome(dto.getNome());
		usuario.setPerfil(dto.getPerfil());

		return usuario;
	}

	public static UsuarioDTO to(Usuario usuario) {

		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setLogin(usuario.getLogin());
		dto.setPerfil(usuario.getPerfil());

		return dto;
	}

}
