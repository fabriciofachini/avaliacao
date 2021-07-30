package br.com.cooperforte.avaliacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDTO extends ApiDTO {

	private static final long serialVersionUID = 1170943034035009909L;

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;

}
