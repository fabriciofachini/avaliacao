package br.com.cooperforte.avaliacao.enums.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.cooperforte.avaliacao.enums.Perfil;

@Converter(autoApply = true)
public class PerfilConverter implements AttributeConverter<Perfil, String> {

    @Override
    public String convertToDatabaseColumn(Perfil perfil) {

        if (perfil == null) {
            return null;
        }
        return perfil.getCodigo();
    }

    @Override
    public Perfil convertToEntityAttribute(String codigo) {

        if (codigo == null) {
            return null;
        }

        return Stream.of(Perfil.values()).filter(c -> c.getCodigo().equals(codigo)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
