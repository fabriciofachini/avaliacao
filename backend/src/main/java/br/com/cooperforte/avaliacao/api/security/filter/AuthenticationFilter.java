package br.com.cooperforte.avaliacao.api.security.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;

import br.com.cooperforte.avaliacao.api.security.auth.AuthUserResult;
import br.com.cooperforte.avaliacao.api.v1.dto.LoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public static final String AUTH_SECRET = "AUTH_SECRET";

	public AuthenticationFilter(AuthenticationManager authenticationManager) {

		this.setUsernameParameter("login");
		this.setPasswordParameter("senha");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginDTO user = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
			Gson gson = new Gson();

			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(gson.toJson(user), user.getSenha()));
		} catch (AuthenticationException e) {
			System.out.println(e);

			String mensagem = "Login e/ou senha inválidos.";
			if (e.getCause() instanceof BadCredentialsException) {
				mensagem = e.getCause().getMessage();
			}

			throw new BadCredentialsException(mensagem);
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		LoginDTO login = ((AuthUserResult) authResult.getPrincipal()).getLogin();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		Date expTime = calendar.getTime();

		String token = Jwts.builder().setSubject(mapper.writeValueAsString(login))
				.signWith(SignatureAlgorithm.HS512, AUTH_SECRET).setExpiration(expTime).compact();

		login.setAccessToken(token);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		response.getWriter().write(mapper.writeValueAsString(login));
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {

		String error = "{ \"mensagem\": \"" + failed.getMessage() + "\",  \"status\": \""
				+ HttpServletResponse.SC_UNAUTHORIZED + "\" }";
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(error);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
