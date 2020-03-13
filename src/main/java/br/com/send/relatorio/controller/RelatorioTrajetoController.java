package br.com.send.relatorio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.send.relatorio.service.RelatorioTrajetoService;
import br.com.send.relatorio.vo.TrajetoVo;


@RestController
@RequestMapping(path = "/trajeto")
public class RelatorioTrajetoController extends Controller {

	@Autowired
	private RelatorioTrajetoService relatorioTrajetoService;

	@GetMapping(path="/{identificadorPontoMonitorado}/{dtIniyyyyMMddHHmmss}/{dtFinalyyyyMMddHHmmss}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE} )
    public ResponseEntity<ByteArrayResource> get(@PathVariable("identificadorPontoMonitorado") final String identificadorPontoMonitorado,
    		@PathVariable("dtIniyyyyMMddHHmmss") final String dtIniyyyyMMddHHmmss, 
    		@PathVariable("dtFinalyyyyMMddHHmmss") final String dtFinalyyyyMMddHHmmss){
		try {
			
			  HttpHeaders header = new HttpHeaders();
		      header.setContentType(new MediaType("application", "force-download"));
		      header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=trajeto.xlsx");

		      return new ResponseEntity<>(new ByteArrayResource(relatorioTrajetoService.find(identificadorPontoMonitorado, dtIniyyyyMMddHHmmss , dtFinalyyyyMMddHHmmss )),
	                    header, HttpStatus.CREATED);
		      
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
    }
}
