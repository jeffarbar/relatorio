package br.com.send.relatorio.model;


import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trajeto")
public class TrajetoModel {

	public TrajetoModel() {}

	
	 public TrajetoModel(String id, Date dtCriacao, String identificadorDispositivo, 
			 Double latitude, Double longitude) {
		super();
		this.id = id;
		this.dtCriacao = dtCriacao;
		this.identificadorDispositivo = identificadorDispositivo;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Id
	private String id;
	 
	@Indexed
	private Date dtCriacao;
	 
	@Indexed
	private String identificadorDispositivo;
	 
	private Double latitude;
	 
	private Double longitude;
	
	private String endereco;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getIdentificadorDispositivo() {
		return identificadorDispositivo;
	}

	public void setIdentificadorDispositivo(String identificadorDispositivo) {
		this.identificadorDispositivo = identificadorDispositivo;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	 
}