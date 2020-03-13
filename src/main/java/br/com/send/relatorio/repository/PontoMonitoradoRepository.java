package br.com.send.relatorio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.send.relatorio.model.PontoMonitoradoDispositivoModel;



@Repository
public interface PontoMonitoradoRepository extends JpaRepository<PontoMonitoradoDispositivoModel, Long> {

	@Query(value = "SELECT p.nome as nome_ponto_monitorado , p.identificador as identificador_ponto_monitorado , p.dt_cadastro as dt_cat_ponto_monitorado , " + 
			" d.nome as nome_dispositivo , d.identificador as identificador_dispositivo " + 
			" FROM ponto_monitorado p " + 
			" INNER JOIN ponto_monitorado_dispositivo pd ON ( p.id_ponto_monitorado = pd.id_ponto_monitorado ) " + 
			" INNER JOIN dispositivo d ON ( d.id_dispositivo = pd.id_dispositivo ) " + 
			" WHERE p.fg_ativo = true and p.identificador = ?1 " , nativeQuery = true)
	List<Object[]> findPontoMonitorado(String identificadorPontoMonitorado);
}
