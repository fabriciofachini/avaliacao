package br.com.cooperforte.avaliacao.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.cooperforte.avaliacao.api.v1.dto.LoginDTO;
import lombok.Getter;
import lombok.Setter;

@Component
@Qualifier("tokenUtil")
public class TokenUtil {

    public static final String GOVBR = "GOV.BR";
    public static final String SUPERAPP = "SUPERAPP";
    public static final String APPLE = "APPLE";
    public static final String REDES_SOCIAIS = "REDES_SOCIAIS";

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private LoginDTO login;

}
