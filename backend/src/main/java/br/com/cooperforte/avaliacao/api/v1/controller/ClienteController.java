package br.com.cooperforte.avaliacao.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperforte.avaliacao.api.ApiConstantes;
import br.com.cooperforte.avaliacao.api.builder.Resposta;
import br.com.cooperforte.avaliacao.api.builder.RespostaBuilder;
import br.com.cooperforte.avaliacao.api.v1.dto.ClienteDTO;
import br.com.cooperforte.avaliacao.service.ClienteService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = ApiConstantes.API_VERSION + "/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController extends AbstractController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Cadastro de cliente", notes = "")
	@PostMapping
	@Secured(ApiConstantes.ROLE_ADMIN)
	@ResponseBody
	public Resposta cadastro(@RequestBody @Valid ClienteDTO clienteDTO) {

		ClienteDTO retorno = clienteService.salvar(clienteDTO, getCurrentUser());

		return RespostaBuilder.getBuilder().resposta(retorno).mensagem("Cliente cadastrado com sucesso.").build();
	}

	@ApiOperation(value = "Atualização de cliente", notes = "")
	@PutMapping("/{id}")
	@Secured(ApiConstantes.ROLE_ADMIN)
	@ResponseBody
	public Resposta atualiza(@PathVariable("id") Long id, @RequestBody @Valid ClienteDTO clienteDTO) {

		clienteDTO.setId(id);

		ClienteDTO retorno = clienteService.salvar(clienteDTO, getCurrentUser());

		return RespostaBuilder.getBuilder().resposta(retorno).mensagem("Cliente atualizado com sucesso.").build();
	}

	@ApiOperation(value = "Exclusão de cliente", notes = "")
	@DeleteMapping("/{id}")
	@Secured(ApiConstantes.ROLE_ADMIN)
	@ResponseBody
	public Resposta apaga(@PathVariable("id") Long id) {

		clienteService.remover(id);

		return RespostaBuilder.getBuilder().mensagem("Cliente excluído com sucesso.").build();
	}

	@ApiOperation(value = "Exclusão de cliente", notes = "")
	@DeleteMapping("/telefone/{id}")
	@Secured(ApiConstantes.ROLE_ADMIN)
	@ResponseBody
	public Resposta apagaTelefone(@PathVariable("id") Long id) {

		clienteService.removerTelefone(id);

		return RespostaBuilder.getBuilder().mensagem("Telefone excluído com sucesso.").build();
	}

	@ApiOperation(value = "Exclusão de cliente", notes = "")
	@DeleteMapping("/email/{id}")
	@Secured(ApiConstantes.ROLE_ADMIN)
	@ResponseBody
	public Resposta apagaEmail(@PathVariable("id") Long id) {

		clienteService.removerEmail(id);

		return RespostaBuilder.getBuilder().mensagem("E-mail excluído com sucesso.").build();
	}

	@ApiOperation(value = "Recupera todos clientes", notes = "")
	@GetMapping
	@ResponseBody
	public Resposta recuperarTodos() {

		return RespostaBuilder.getBuilder().resposta(clienteService.recuperarTodos()).build();
	}

	@ApiOperation(value = "Recupera um cliente", notes = "")
	@GetMapping("/{id}")
	@ResponseBody
	public Resposta recuperarUsuario(@PathVariable("id") Long id) {

		return RespostaBuilder.getBuilder().resposta(clienteService.recuperar(id)).build();
	}

}
