package br.com.cooperforte.avaliacao.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cooperforte.avaliacao.api.security.auth.AuthUserResult;
import br.com.cooperforte.avaliacao.api.v1.dto.LoginDTO;
import br.com.cooperforte.avaliacao.model.Usuario;
import br.com.cooperforte.avaliacao.repository.UsuarioRepository;
import br.com.cooperforte.avaliacao.util.ParserUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private ParserUtil<LoginDTO> loginParser = new ParserUtil<>(LoginDTO.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	public CustomUserDetailsService() {
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LoginDTO login = loginParser.convertToObject(username);

		if (username == null || username.length() == 0) {
			throw new BadCredentialsException("Usuário não encontrado");
		}

		return this.getUsuario(login);
	}

	private UserDetails getUsuario(LoginDTO login) {

		Usuario usuario = usuarioRepository.findByLogin(login.getUsername());
		if (usuario == null) {
			throw new BadCredentialsException("Usuário não encontrado");
		}

		login.setId(usuario.getId());
		login.setNome(usuario.getNome());
		login.setPerfil(usuario.getPerfil());

		return new AuthUserResult(login, login.getUsername(), usuario.getSenha(),
				CustomUserDetailsService.getAuthorities(usuario.getPerfil().toString()));
	}

	public static Collection<? extends GrantedAuthority> getAuthorities(String... roles) {

		List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}

		return authorities;
	}

}
