package br.com.cooperforte.avaliacao.enums;

import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoTelefone {

	RESIDENCIAL("RE", "Residencial"), //
	COMERCIAL("CO", "Comercial"), //
	CELULAR("CE", "Celular");

	private String codigo;
	private String descricao;

	private TipoTelefone(String codigo, String descricao) {

		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoTelefone getByCodigo(String codigo) {

		if (StringUtils.isEmpty(codigo)) {
			return null;
		}

		return Stream.of(TipoTelefone.values()).filter(tipo -> tipo.codigo.equalsIgnoreCase(codigo)).findFirst()
				.orElse(null);
	}

	@JsonCreator
	public static TipoTelefone forValue(Map<String, String> value) {

		return TipoTelefone.getByCodigo(value.get("codigo"));
	}

}
