package br.com.cooperforte.avaliacao.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cooperforte.avaliacao.api.v1.dto.EnderecoDTO;
import br.com.cooperforte.avaliacao.exception.ServiceException;
import br.com.cooperforte.avaliacao.util.MaskUtil;
import br.com.cooperforte.avaliacao.util.RestTemplate;

@Service
public class EnderecoService {

	private RestTemplate restTemplate = new RestTemplate();

	public EnderecoDTO recuperarEndereco(String cep) {

		String url = String.format("https://viacep.com.br/ws/%s/json/", MaskUtil.removerMascara(cep));

		try {
			ResponseEntity<String> response = restTemplate.getRestTemplate().getForEntity(url, String.class);

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response.getBody());

			EnderecoDTO dto = new EnderecoDTO();
			dto.setCep(json.get("cep").toString());
			dto.setLogradouro(json.get("logradouro").toString());
			dto.setComplemento(json.get("complemento").toString());
			dto.setBairro(json.get("bairro").toString());
			dto.setCidade(json.get("localidade").toString());
			dto.setUf(json.get("uf").toString());

			return dto;
		} catch (Exception e) {
			throw new ServiceException("Não foi possível recuperar o endereço", e);
		}
	}

}
