package br.com.send.relatorio.service;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.send.relatorio.model.TrajetoModel;
import br.com.send.relatorio.repository.TrajetoRepository;
import br.com.send.relatorio.vo.EnderecoVo;

@Service
@EnableAsync
public class EnderecoService {

	@Value("${url.googlemap}") 
	private String url;
	

	private static final Logger logger = LogManager.getLogger(EnderecoService.class);
	
	@Autowired
	private  RestTemplate restTemplate;
	
	@Autowired
	private TrajetoRepository trajetoRepository;
	
	public String getEndereco(String id, double lat, double lon) {
		
		try {
			String posicao = lat +","+ lon;
			String endereco = recuperaEndereco(url.replace("VALOR_LAT_LON", posicao));
			
			this.atualizarEnderecoMongo(id, endereco);
			
			return endereco;
			
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return null;
	}
	
	private String recuperaEndereco(String url) throws Exception {
		
		URI uri = new URI(url);
		
		ResponseEntity<EnderecoVo> result = restTemplate.getForEntity(uri, EnderecoVo.class);
		
		if(result.getStatusCode().is2xxSuccessful() && 
				!result.getBody().getEnderecoCompletos().isEmpty()) {
			return result.getBody().getEnderecoCompletos().get(0).getEnderecoCompleto();
		}
		return null;
	}
	
	@Async
	private void atualizarEnderecoMongo(String id, String endereco ) {
		
		try {
		
			TrajetoModel trajetoModel =	trajetoRepository.findById(id).get();
			trajetoModel.setEndereco(endereco);
			trajetoRepository.save(trajetoModel);
		}catch (Exception e) {
			logger.error("Erro ao atualizar o endereço no mongodb{}", e);
		}
	}
}
