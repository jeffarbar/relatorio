package br.com.send.relatorio.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class EnderecoVo {

	@JsonIgnoreProperties("plus_code")
	private String plus_code;
	
	@JsonIgnoreProperties("status")
	private String status;
	
	@JsonProperty("results")
	private List<EnderecoComplemento> enderecoCompletos;

	public List<EnderecoComplemento> getEnderecoCompletos() {
		return enderecoCompletos;
	}

	public void setEnderecoCompletos(List<EnderecoComplemento> enderecoCompletos) {
		this.enderecoCompletos = enderecoCompletos;
	}

	public static class EnderecoComplemento{
		
		@JsonIgnoreProperties("address_components")
		private String address_components;
		
		@JsonIgnoreProperties("plus_code")
		private String plus_code;
		
		@JsonIgnoreProperties("types")
		private String types;
		
		@JsonIgnoreProperties("place_id")
		private String place_id;
		
		@JsonIgnoreProperties("geometry")
		private String geometry;
		
		@JsonProperty("formatted_address")
		private String enderecoCompleto;

		public String getEnderecoCompleto() {
			return enderecoCompleto;
		}

		public void setEnderecoCompleto(String enderecoCompleto) {
			this.enderecoCompleto = enderecoCompleto;
		}
	
	}
}



