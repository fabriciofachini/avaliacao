package br.com.cooperforte.avaliacao.api.builder;

import java.util.List;

public class Resposta {

    private Object resposta;
    private String mensagem;
    private List<String> mensagens;
    private String excecao;
    private boolean contemErro;

    public String getExcecao() {
        return excecao;
    }

    public void setExcecao(String excecao) {
        this.excecao = excecao;
    }

    public Object getResposta() {
        return resposta;
    }

    public void setResposta(Object resposta) {
        this.resposta = resposta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }

    public boolean isContemErro() {
        return contemErro;
    }

    public void setContemErro(boolean contemErro) {
        this.contemErro = contemErro;
    }

}
