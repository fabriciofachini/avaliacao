package br.com.cooperforte.avaliacao.api.security.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.cooperforte.avaliacao.api.v1.dto.LoginDTO;

public class AuthUserResult extends User {

    private static final long serialVersionUID = -756855576553260228L;

    private LoginDTO login;

    public AuthUserResult(LoginDTO login, String username, String password, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);
        this.login = login;
    }

    public LoginDTO getLogin() {
        login.setSenha(null);
        return login;
    }

}
