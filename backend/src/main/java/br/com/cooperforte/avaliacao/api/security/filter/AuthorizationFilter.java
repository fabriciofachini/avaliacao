package br.com.cooperforte.avaliacao.api.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.cooperforte.avaliacao.api.security.CustomUserDetailsService;
import br.com.cooperforte.avaliacao.api.v1.dto.LoginDTO;
import br.com.cooperforte.avaliacao.util.ParserUtil;
import br.com.cooperforte.avaliacao.util.TokenUtil;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private ParserUtil<LoginDTO> loginParser = new ParserUtil<>(LoginDTO.class);

	@Autowired
	private TokenUtil tokenUtil;

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = request;
		if (this.tokenUtil == null) {
			ServletContext context = httpRequest.getSession().getServletContext();
			SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, context);
		}

		UsernamePasswordAuthenticationToken authenticationToken = validateToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		if (authenticationToken == null) {
			String error = "{ \"message\": \"Sessão expirada\", \"codeError\": \"" + HttpServletResponse.SC_UNAUTHORIZED
					+ "\" }";
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(error);
			response.getWriter().flush();
			response.getWriter().close();
			return;
		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken validateToken(HttpServletRequest request) {

		try {
			String authHeader = request.getHeader("Authorization");
			if (authHeader == null) {
				return null;
			}
			String user = Jwts.parser().setSigningKey(AuthenticationFilter.AUTH_SECRET)
					.parseClaimsJws(authHeader.replace("Bearer ", "")).getBody().getSubject();
			if (user == null) {
				return null;
			}

			LoginDTO login = loginParser.convertToObject(user);

			tokenUtil.setLogin(login);

			return new UsernamePasswordAuthenticationToken(user, null,
					CustomUserDetailsService.getAuthorities(login.getPerfil().toString()));
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
