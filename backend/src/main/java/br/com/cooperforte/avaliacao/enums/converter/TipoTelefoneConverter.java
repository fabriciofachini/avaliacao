package br.com.cooperforte.avaliacao.enums.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.cooperforte.avaliacao.enums.TipoTelefone;

@Converter(autoApply = true)
public class TipoTelefoneConverter implements AttributeConverter<TipoTelefone, String> {

	@Override
	public String convertToDatabaseColumn(TipoTelefone tipoTelefone) {

		if (tipoTelefone == null) {
			return null;
		}
		return tipoTelefone.getCodigo();
	}

	@Override
	public TipoTelefone convertToEntityAttribute(String codigo) {

		if (codigo == null) {
			return null;
		}

		return Stream.of(TipoTelefone.values()).filter(c -> c.getCodigo().equals(codigo)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
