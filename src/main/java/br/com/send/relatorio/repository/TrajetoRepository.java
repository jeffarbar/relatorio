package br.com.send.relatorio.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.send.relatorio.model.TrajetoModel;


public interface TrajetoRepository extends MongoRepository<TrajetoModel, String> {

	 List<TrajetoModel> findByIdentificadorDispositivoAndDtCriacaoBetween(String identificadorDispositivo, Date startDate, Date endDate);
}
