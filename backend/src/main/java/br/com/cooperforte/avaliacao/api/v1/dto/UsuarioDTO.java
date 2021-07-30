package br.com.cooperforte.avaliacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cooperforte.avaliacao.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO extends ApiDTO {

	private static final long serialVersionUID = -7367152236036144842L;

	private Long id;

	private String nome;

	private String login;

	private Perfil perfil;

}
