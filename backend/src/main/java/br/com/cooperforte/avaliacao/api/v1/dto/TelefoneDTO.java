package br.com.cooperforte.avaliacao.api.v1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cooperforte.avaliacao.util.MaskFormatUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelefoneDTO extends ApiDTO {

	private static final long serialVersionUID = -7472362783080367966L;

	private Long id;

	@NotBlank(message = "O número do telefone é de preenchimento obrigatório")
	@Pattern(regexp = MaskFormatUtil.TELEFONE_REGEX_PATTERN, message = "Telefone informado é inválido.")
	private String telefone;

	@NotBlank(message = "O tipo do telefone é de preenchimento obrigatório")
	private String tipo;

}
