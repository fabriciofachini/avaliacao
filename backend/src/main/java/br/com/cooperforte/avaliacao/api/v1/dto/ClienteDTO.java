package br.com.cooperforte.avaliacao.api.v1.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO extends ApiDTO {

	private static final long serialVersionUID = -8540033392989341103L;

	private Long id;

	@NotBlank(message = "O nome é de preenchimento obrigatório")
	@Size(min = 3, max = 100, message = "O nome deve conter de 3 até 100 caracteres.")
	private String nome;

	@NotBlank(message = "O CPF é de preenchimento obrigatório")
	@CPF
	private String cpf;

	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String cep;

	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String logradouro;

	private String complemento;

	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String bairro;

	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String cidade;

	@NotBlank(message = "O CEP é de preenchimento obrigatório")
	private String uf;

	private List<TelefoneDTO> telefones;

	private List<EmailDTO> emails;

}
