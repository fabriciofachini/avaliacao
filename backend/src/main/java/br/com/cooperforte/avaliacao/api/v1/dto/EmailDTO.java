package br.com.cooperforte.avaliacao.api.v1.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO extends ApiDTO {

	private static final long serialVersionUID = -4926554810967573924L;

	private Long id;

	@NotBlank(message = "O e-mail é de preenchimento obrigatório")
	@javax.validation.constraints.Email(message = "O e-mail informado é inválido")
	private String email;

}
