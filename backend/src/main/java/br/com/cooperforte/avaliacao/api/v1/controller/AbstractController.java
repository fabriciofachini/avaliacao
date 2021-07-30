package br.com.cooperforte.avaliacao.api.v1.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.cooperforte.avaliacao.api.v1.dto.LoginDTO;
import br.com.cooperforte.avaliacao.util.ParserUtil;

public class AbstractController {

	private ParserUtil<LoginDTO> loginParser = new ParserUtil<>(LoginDTO.class);

	protected String getCurrentUser() {

		LoginDTO login = getLoginDTO();
		if (login != null) {
			return login.getUsername();
		}

		return null;
	}

	private LoginDTO getLoginDTO() {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

		if (currentUser != null && !StringUtils.equalsIgnoreCase(currentUser.getName(), "anonymousUser")) {
			return loginParser.convertToObject(currentUser.getName());
		}

		return null;
	}

}
