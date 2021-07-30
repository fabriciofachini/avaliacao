package br.com.cooperforte.avaliacao.mapper;

import br.com.cooperforte.avaliacao.api.v1.dto.EmailDTO;
import br.com.cooperforte.avaliacao.model.Email;

public class EmailMapper {

	public static Email from(EmailDTO dto) {

		Email email = new Email();
		email.setId(dto.getId());
		email.setEmail(dto.getEmail());

		return email;
	}

	public static EmailDTO to(Email email) {

		EmailDTO dto = new EmailDTO();
		dto.setId(email.getId());
		dto.setEmail(email.getEmail());

		return dto;
	}

}
