package br.com.send.relatorio.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.send.relatorio.service.VersaoService;




@RestController
@RequestMapping(path = "/versao")
public class VersaoController extends Controller{

	@Autowired
	private VersaoService versaoService;
	
	@GetMapping(path="/", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> versao(){
		try {
			return ResponseEntity.ok().body(versaoService.getVersao()); 
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
    }
}
