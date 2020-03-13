package br.com.send.relatorio.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.send.relatorio.model.TrajetoModel;
import br.com.send.relatorio.repository.PontoMonitoradoRepository;
import br.com.send.relatorio.repository.TrajetoRepository;
import br.com.send.relatorio.util.ConverteUtil;
import br.com.send.relatorio.util.DataUtil;
import br.com.send.relatorio.vo.PontoMonitoradoDispositivoVo;
import br.com.send.relatorio.vo.TrajetoVo;


@Service
public class RelatorioTrajetoService {

	private static final Logger logger = LogManager.getLogger(RelatorioTrajetoService.class);
	
	@Autowired
	private TrajetoRepository trajetoRepository;
	
	@Autowired
	private PontoMonitoradoRepository pontoMonitoradoRepository;
	
	@Autowired
	private ConverteUtil converteUtil;
	
	@Autowired
	private EnderecoService enderecoService; 
	
	public byte[] find(String identificadorPontoMonitorado, String dtIniyyyyMMddHHmmss , String dtFinalyyyyMMddHHmmss) throws Exception{

		try {
	
			List<TrajetoVo> listaTrajeto = new ArrayList<TrajetoVo>();
			
			Date startDate = DataUtil.convert_yyyyMMddHHmmss(dtIniyyyyMMddHHmmss);
			Date endDate = DataUtil.convert_yyyyMMddHHmmss(dtFinalyyyyMMddHHmmss);;
			
			List<PontoMonitoradoDispositivoVo> listaPontoMonitoradoDispositivo = findByIdentificadorPontoMonitorado(identificadorPontoMonitorado);
			
			listaPontoMonitoradoDispositivo.stream().forEach( pd-> {
				listaTrajeto.addAll(this.getTrajeto(pd.getIdentificadorDispositivo(), startDate, endDate));
			});
			
			if( listaPontoMonitoradoDispositivo.isEmpty() ) {
				logger.info("Não há registro para relatório");
				return null;
			}
			return this.criaExcel(listaTrajeto, listaPontoMonitoradoDispositivo.get(0), 
					dtIniyyyyMMddHHmmss, dtFinalyyyyMMddHHmmss);
			
		}catch (Exception e) {
			logger.error("{}", e);
			throw e;
		}
	}
	
	private List<TrajetoVo> getTrajeto(String identificadorDispositivo, Date startDate , Date endDate){
		
		return trajetoRepository.findByIdentificadorDispositivoAndDtCriacaoBetween(identificadorDispositivo, 
			startDate, endDate).parallelStream().map( this :: converteTrajeto)
			.collect(Collectors.toList());
	}
	
	public List<PontoMonitoradoDispositivoVo> findByIdentificadorPontoMonitorado(String identificadorPontoMonitorado) throws Exception{
		
		try {
				
			return pontoMonitoradoRepository.findPontoMonitorado(identificadorPontoMonitorado)
				.stream().map( this :: convertePontoMonitoradoDispositivo ).collect(Collectors.toList());
			
		}catch (Exception e) {
			logger.error("{}", e);
			throw e;
		}
	}
	
	private TrajetoVo converteTrajeto( TrajetoModel trajetoModel ) {
		
		TrajetoVo trajetoVo = new TrajetoVo(trajetoModel);
		
		if( trajetoModel.getEndereco() == null ) {
			String endereco = enderecoService.getEndereco(trajetoModel.getId(),
					trajetoModel.getLatitude(), trajetoModel.getLongitude());
			trajetoModel.setEndereco( endereco );
		}
		
		return trajetoVo;
	}
	
	private PontoMonitoradoDispositivoVo convertePontoMonitoradoDispositivo(Object[] obj){
		
		PontoMonitoradoDispositivoVo pontoMonitoradoDispositivoVo = new PontoMonitoradoDispositivoVo();
		
		pontoMonitoradoDispositivoVo.setNomePontoMonitorado(converteUtil.converterString(obj[0]));
		pontoMonitoradoDispositivoVo.setIdentificadorPontoMonitorado(converteUtil.converterString(obj[1]));
		pontoMonitoradoDispositivoVo.setDtCadastroPontoMonitorado(DataUtil.formataData( converteUtil.converterString(obj[2])));
		pontoMonitoradoDispositivoVo.setNomeDispositivo(converteUtil.converterString(obj[3]));
		pontoMonitoradoDispositivoVo.setIdentificadorDispositivo(converteUtil.converterString(obj[4]));
		
		return pontoMonitoradoDispositivoVo;
	}
	
	/*
	public  byte[] teste() throws Exception {
		RelatorioTrajetoService r = new RelatorioTrajetoService();
		
		List<TrajetoVo> listaTrajeto = new ArrayList<TrajetoVo>();
		
		TrajetoVo trajetoVo_1 = new TrajetoVo();
		trajetoVo_1.setDtCriacao("2020-02-0510:00:00");
		trajetoVo_1.setEndereco("rua ola");
		trajetoVo_1.setLatitude(Double.valueOf("8.033334"));
		trajetoVo_1.setLongitude(Double.valueOf("1.22321334"));
		trajetoVo_1.setIdentificadorDispositivo("TYDASDASD");
		
		TrajetoVo trajetoVo_2 = new TrajetoVo();
		trajetoVo_2.setDtCriacao("2020-02-0510:00:00");
		trajetoVo_2.setEndereco("rua ola wqw rua ola wqw v rua ola wqw rua ola wqw rua ola wqw rua ola wqw");
		trajetoVo_2.setLatitude(Double.valueOf("8.033334"));
		trajetoVo_2.setLongitude(Double.valueOf("1.22321334"));
		trajetoVo_2.setIdentificadorDispositivo("TYDASDASD");
		
		listaTrajeto.add(trajetoVo_1);
		listaTrajeto.add(trajetoVo_2);
		
		PontoMonitoradoDispositivoVo pontoMonitoradoDispositivo = new PontoMonitoradoDispositivoVo();
		pontoMonitoradoDispositivo.setDtCadastroPontoMonitorado("2020-02-0500:00:00");
		pontoMonitoradoDispositivo.setIdentificadorDispositivo("UDHHDKSGDAADAS");
		pontoMonitoradoDispositivo.setNomePontoMonitorado("YSDASGDASDASD");
		
		return this.criaExcel(listaTrajeto, pontoMonitoradoDispositivo, "2020-02-0500:00:00" , "2020-02-0520:15:56");
	}
	*/
	
	private byte[] criaExcel(List<TrajetoVo> listaTrajeto , 
			PontoMonitoradoDispositivoVo pontoMonitoradoDispositivo,
			String dtIniyyyyMMddHHmmss , String dtFinalyyyyMMddHHmmss) throws Exception {
		
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 ByteArrayOutputStream out = new ByteArrayOutputStream();

		 AtomicInteger rownum = new AtomicInteger(1);
		 
		 try{
	         HSSFSheet sheetTrajetos = workbook.createSheet("Trajetos");
	        
	         
	         // título
        	 Row rowTitle = sheetTrajetos.createRow(rownum.getAndIncrement());
        	 int cellnumTitle = 0; 
        	 HSSFCellStyle styleTitle =  this.criaStyleTitle(workbook);
        	 
        	 Cell cellNomePontoMonitorado = rowTitle.createCell(cellnumTitle++);   
 	         cellNomePontoMonitorado.setCellValue("Nome Ponto Monitorado");
 	         cellNomePontoMonitorado.setCellStyle(styleTitle); 
 	         Cell cellNomePontoMonitoradoV = rowTitle.createCell(cellnumTitle++);
 	         cellNomePontoMonitoradoV.setCellValue(pontoMonitoradoDispositivo.getNomePontoMonitorado());
 	         cellNomePontoMonitoradoV.setCellStyle(styleTitle); 
 	          
 	         Cell cellIdentPontoMonitorado = rowTitle.createCell(cellnumTitle++);   
 	         cellIdentPontoMonitorado.setCellValue("Identificador Ponto Monitorado");
 	         cellIdentPontoMonitorado.setCellStyle(styleTitle); 
 	         Cell cellIdentPontoMonitoradoV = rowTitle.createCell(cellnumTitle++);
 	         cellIdentPontoMonitoradoV.setCellValue(pontoMonitoradoDispositivo.getIdentificadorPontoMonitorado());
 	         cellIdentPontoMonitoradoV.setCellStyle(styleTitle);  
 	         
 	         Cell cellDataPontoMonitorado = rowTitle.createCell(cellnumTitle++);   
 	         cellDataPontoMonitorado.setCellValue("Data: " + dtIniyyyyMMddHHmmss + " à " + dtFinalyyyyMMddHHmmss );
 	         cellDataPontoMonitorado.setCellStyle(styleTitle); 
 	         
 	         // Cabeçalho
	         Row rowCabe = sheetTrajetos.createRow(rownum.incrementAndGet());
	         int cellnumCabe = 0;
	         HSSFCellStyle styleCabe =  this.criaStyleCabecalho(workbook);
	         
	         Cell cellIdenCabe = rowCabe.createCell(cellnumCabe++);            
             cellIdenCabe.setCellValue("Identificador Dispositivo");
             cellIdenCabe.setCellStyle(styleCabe);  
             
             Cell cellDtCabe = rowCabe.createCell(cellnumCabe++);
             cellDtCabe.setCellValue("Data/Hora");
             cellDtCabe.setCellStyle(styleCabe);  
             
             Cell cellLatCabe = rowCabe.createCell(cellnumCabe++);
             cellLatCabe.setCellValue("Latitude");
             cellLatCabe.setCellStyle(styleCabe);  
             
             Cell cellLonCabe = rowCabe.createCell(cellnumCabe++);
             cellLonCabe.setCellValue("Longitude");
             cellLonCabe.setCellStyle(styleCabe);  
             
             Cell cellEndCabe = rowCabe.createCell(cellnumCabe++);
             cellEndCabe.setCellValue("Endereço");
             cellEndCabe.setCellStyle(styleCabe); 
        
             // conteúdo
             listaTrajeto.stream().forEach( trajetoVo ->{
            	 
            	 int cellnum = 0;
	             
	        	 Row row = sheetTrajetos.createRow(rownum.incrementAndGet());
	         
	             Cell cellIden = row.createCell(cellnum++);            
	             cellIden.setCellValue(trajetoVo.getIdentificadorDispositivo());
	             
	             Cell cellDt = row.createCell(cellnum++);
	             cellDt.setCellValue(trajetoVo.getDtCriacao() );
	             
	             Cell cellLat = row.createCell(cellnum++);
	             cellLat.setCellValue(trajetoVo.getLatitude() );
	             
	             Cell cellLon = row.createCell(cellnum++);
	             cellLon.setCellValue(trajetoVo.getLongitude());
	             
	             Cell cellEnd = row.createCell(cellnum++);
	             cellEnd.setCellValue(trajetoVo.getEndereco());
            
             });
	         
             sheetTrajetos.autoSizeColumn(0);
             sheetTrajetos.autoSizeColumn(1);
             sheetTrajetos.autoSizeColumn(2);
             sheetTrajetos.autoSizeColumn(3);
             sheetTrajetos.autoSizeColumn(4);
             
	         workbook.write(out);
	         
	         logger.info("Arquivo Excel criado com sucesso!");
	         return out.toByteArray();
		 }finally {
			 workbook.close();
			 out.close();   
		}
	}
	
	private HSSFCellStyle criaStyleCabecalho(HSSFWorkbook workbook) {
		
		 HSSFCellStyle style = workbook.createCellStyle();
		 
		 style.setAlignment(HorizontalAlignment.CENTER);
		 style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());  
         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
         return style;
	}
	
	private HSSFCellStyle criaStyleTitle(HSSFWorkbook workbook) {
		
		 HSSFCellStyle style = workbook.createCellStyle();
		 
		 HSSFFont font = workbook.createFont();
		 font.setBold(true);
		 
		 style.setFont(font);
		 style.setFillForegroundColor(IndexedColors.RED1.getIndex());  
         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
         return style;
	}
}
