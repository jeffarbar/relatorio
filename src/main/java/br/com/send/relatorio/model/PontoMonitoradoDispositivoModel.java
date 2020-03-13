package br.com.send.relatorio.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "ponto_monitorado_dispositivo" )
public class PontoMonitoradoDispositivoModel {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ponto_monitorado_dispositivo", nullable = false )
	private Long idPontoMonitoradoDispositivo;
	
	@Column(name = "dt_cadastro", nullable = false )
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCadastro;
	
	@Column(name = "dt_desativada")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtDesativada;
	
	@Column(name = "fg_ativo", columnDefinition = "boolean default true" , nullable = false )
	private Boolean ativo = Boolean.TRUE;


	public Long getIdPontoMonitoradoDispositivo() {
		return idPontoMonitoradoDispositivo;
	}


	public void setIdPontoMonitoradoDispositivo(Long idPontoMonitoradoDispositivo) {
		this.idPontoMonitoradoDispositivo = idPontoMonitoradoDispositivo;
	}


	public Date getDtCadastro() {
		return dtCadastro;
	}


	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}


	public Date getDtDesativada() {
		return dtDesativada;
	}


	public void setDtDesativada(Date dtDesativada) {
		this.dtDesativada = dtDesativada;
	}


	public Boolean getAtivo() {
		return ativo;
	}


	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
