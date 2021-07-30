package br.com.cooperforte.avaliacao.api.builder;

import java.util.List;

public class RespostaBuilder {

    private Resposta resposta;

    private RespostaBuilder() {
        this.resposta = new Resposta();
    }

    public static RespostaBuilder getBuilder() {
        return new RespostaBuilder();
    }

    public Resposta build() {
        return this.resposta;
    }

    public RespostaBuilder resposta(Object resposta) {
        this.resposta.setResposta(resposta);
        return this;
    }

    public RespostaBuilder mensagem(String mensagem) {
        this.resposta.setMensagem(mensagem);
        return this;
    }

    public RespostaBuilder mensagens(List<String> mensagens) {
        this.resposta.setMensagens(mensagens);
        return this;
    }

    public RespostaBuilder contemErro(Boolean contemErro) {
        this.resposta.setContemErro(contemErro);
        return this;
    }

    public RespostaBuilder excecao(String excecao) {
        this.resposta.setExcecao(excecao);
        return this;
    }

    public RespostaBuilder criaErro(Exception ex) {
        this.contemErro(true);
        this.excecao(ex.getLocalizedMessage());
        return this;
    }

    public RespostaBuilder criaMensagem(Exception ex) {
        this.contemErro(true);
        this.mensagem(ex.getLocalizedMessage());
        return this;
    }

}
