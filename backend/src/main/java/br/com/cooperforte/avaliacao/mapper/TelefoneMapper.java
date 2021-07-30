package br.com.cooperforte.avaliacao.mapper;

import br.com.cooperforte.avaliacao.api.v1.dto.TelefoneDTO;
import br.com.cooperforte.avaliacao.enums.TipoTelefone;
import br.com.cooperforte.avaliacao.model.Telefone;
import br.com.cooperforte.avaliacao.util.MaskUtil;

public class TelefoneMapper {

	public static Telefone from(TelefoneDTO dto) {

		Telefone telefone = new Telefone();
		telefone.setId(dto.getId());
		telefone.setTelefone(MaskUtil.removerMascara(dto.getTelefone()));
		telefone.setTipo(TipoTelefone.getByCodigo(dto.getTipo()));

		return telefone;
	}

	public static TelefoneDTO to(Telefone telefone) {

		TelefoneDTO dto = new TelefoneDTO();
		dto.setId(telefone.getId());
		dto.setTelefone(MaskUtil.getTelWithMask(telefone.getTelefone()));
		dto.setTipo(telefone.getTipo().getCodigo());

		return dto;
	}

}
