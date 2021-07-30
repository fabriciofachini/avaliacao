package br.com.cooperforte.avaliacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cooperforte.avaliacao.enums.Perfil;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDTO {

	private Long id;

	private String username;

	private String senha = "senha";

	private String nome;

	private Perfil perfil;

}
