package br.com.cooperforte.avaliacao.enums;

import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Perfil {

	ADMIN("AD", "Admin"), //
	COMUM("CO", "Comum");

	private String codigo;
	private String descricao;

	private Perfil(String codigo, String descricao) {

		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil getByCodigo(String codigo) {

		if (StringUtils.isEmpty(codigo)) {
			return null;
		}

		return Stream.of(Perfil.values()).filter(perfil -> perfil.codigo.equalsIgnoreCase(codigo)).findFirst()
				.orElse(null);
	}

	@JsonCreator
	public static Perfil forValue(Map<String, String> value) {

		return Perfil.getByCodigo(value.get("codigo"));
	}

}
