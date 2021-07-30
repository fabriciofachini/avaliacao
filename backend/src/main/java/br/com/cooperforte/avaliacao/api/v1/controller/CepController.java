package br.com.cooperforte.avaliacao.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperforte.avaliacao.api.ApiConstantes;
import br.com.cooperforte.avaliacao.api.builder.Resposta;
import br.com.cooperforte.avaliacao.api.builder.RespostaBuilder;
import br.com.cooperforte.avaliacao.service.EnderecoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = ApiConstantes.API_VERSION + "/cep", produces = MediaType.APPLICATION_JSON_VALUE)
public class CepController extends AbstractController {

	@Autowired
	private EnderecoService enderecoService;

	@ApiOperation(value = "Recupera todos clientes", notes = "")
	@GetMapping("/{cep}")
	@ResponseBody
	public Resposta recuperarEndereco(@PathVariable("cep") String cep) {

		return RespostaBuilder.getBuilder().resposta(enderecoService.recuperarEndereco(cep)).build();
	}

}
