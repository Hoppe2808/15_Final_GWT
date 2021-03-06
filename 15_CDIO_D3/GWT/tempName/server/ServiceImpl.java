package tempName.server;

import tempName.client.service.Service;
import tempName.server.data.daoimpl.MYSQLOperatoerDAO;
import tempName.server.data.daoimpl.MYSQLProduktBatchDAO;
import tempName.server.data.daoimpl.MYSQLRaavareBatchDAO;
import tempName.server.data.daoimpl.MYSQLRaavareDAO;
import tempName.server.data.daoimpl.MYSQLReceptDAO;
import tempName.server.data.daointerface.DALException;
import tempName.server.data.database.Connector;
import tempName.shared.dto.OperatoerDTO;
import tempName.shared.dto.ProduktBatchDTO;
import tempName.shared.dto.ProduktBatchKomponentDTO;
import tempName.shared.dto.RaavareBatchDTO;
import tempName.shared.dto.RaavareDTO;
import tempName.shared.dto.ReceptDTO;
import tempName.shared.dto.ReceptKomponentDTO;
import tempName.shared.password.PasswordMethods;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class ServiceImpl extends RemoteServiceServlet implements Service {

	private MYSQLOperatoerDAO operatoerDAO = new MYSQLOperatoerDAO();
	private MYSQLRaavareDAO raavareDAO = new MYSQLRaavareDAO();
	private MYSQLReceptDAO receptDAO = new MYSQLReceptDAO();
	private MYSQLRaavareBatchDAO raavareBatchDAO = new MYSQLRaavareBatchDAO();
	private MYSQLProduktBatchDAO produktBatchDAO = new MYSQLProduktBatchDAO();
	private PasswordMethods passMeth = new PasswordMethods();

	public void connectDatabase(){
		try { 
			new Connector(); 
		} catch (InstantiationException e) {
			e.printStackTrace(); 
		}catch (IllegalAccessException e) {
			e.printStackTrace(); 
		}catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		}catch (SQLException e) {
			e.printStackTrace(); 
		}
	}

	public void createOp(String name, String init, String cpr, String password, int admin) throws DALException{
		try {
			OperatoerDTO opDTO = new OperatoerDTO();
			opDTO.setOprNavn(name);
			opDTO.setIni(init);
			opDTO.setCpr(cpr);
			opDTO.setPassword(password);
			opDTO.setAdminStatus(admin);
			operatoerDAO.createOperatoer(opDTO);
		} catch (DALException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String checkLogin(int id, String pass){
		String check;
		List<OperatoerDTO> rawList = new ArrayList<OperatoerDTO>();
		try {
			rawList = this.operatoerDAO.getOperatoerList();
		} catch (DALException e) {
			e.printStackTrace();
		}
		if (passMeth.correctUserPassword(pass, rawList, id)){
			check = "true";
		} else{
			check = "false";
		}
		return check;
	}

	public ArrayList<OperatoerDTO> getOperators(){
		List<OperatoerDTO> rawList = new ArrayList<OperatoerDTO>();
		try {
			rawList = this.operatoerDAO.getOperatoerList();
		} catch (DALException e) {
			e.printStackTrace();
		}
		return (ArrayList<OperatoerDTO>) rawList;
	}
	@Override
	public ArrayList<RaavareDTO> getRaavare(){
		List<RaavareDTO> rawList = new ArrayList<RaavareDTO>();
		try {
			rawList = this.raavareDAO.getRaavareList();
		} catch (DALException e) {
			e.printStackTrace();
		}

		return (ArrayList<RaavareDTO>) rawList;
	}
	@Override
	public ArrayList<ReceptDTO> getRecept(){
		List<ReceptDTO> rawList = new ArrayList<ReceptDTO>();
		try {
			rawList = this.receptDAO.getReceptList();
		} catch (DALException e) {
			e.printStackTrace();
		}

		return (ArrayList<ReceptDTO>) rawList;
	}

	@Override
	public void updateOp(int id, String name, String ini, String cpr, String password, int admin) throws DALException {
		OperatoerDTO oDTO = new OperatoerDTO();
		oDTO.setOprId(id);
		oDTO.setOprNavn(name);
		oDTO.setIni(ini);
		oDTO.setCpr(cpr);
		oDTO.setPassword(password);
		oDTO.setAdminStatus(admin);
		try {
			this.operatoerDAO.updateOperatoer(oDTO);
		} catch (DALException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int getAdmin(int id) {
		OperatoerDTO oDTO = new OperatoerDTO();

		try {
			oDTO = this.operatoerDAO.getOperatoer(id);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return oDTO.getAdminStatus();
	}
	
	@Override
	public ArrayList<RaavareBatchDTO> getRaavareBatch() {
		List<RaavareBatchDTO> rawList = new ArrayList<RaavareBatchDTO>();
		try {
			rawList = this.raavareBatchDAO.getRaavareBatchList();
		} catch (DALException e) {
			e.printStackTrace();
		}

		return (ArrayList<RaavareBatchDTO>) rawList;
	}
	@Override
	public ArrayList<ProduktBatchDTO> getProduktBatch(){
		List<ProduktBatchDTO> rawList = new ArrayList<ProduktBatchDTO>();
		try {
			rawList = this.produktBatchDAO.getProduktBatchList();
		} catch (DALException e) {
			e.printStackTrace();
		}
		return (ArrayList<ProduktBatchDTO>) rawList;
	}

	@Override
	public void addRaavare(int id, String rName, String deliverer) throws DALException {
		RaavareDTO rDTO = new RaavareDTO();
		rDTO.setrID(id);
		rDTO.setrName(rName);
		rDTO.setDeliverer(deliverer);
		try{
			this.raavareDAO.addRaavare(rDTO);
		} catch (DALException e){
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public void addRecept(int id, String receptName) throws DALException{
		ReceptDTO rDTO = new ReceptDTO();
		rDTO.setReceptId(id);
		rDTO.setReceptName(receptName);
		try{
			this.receptDAO.createRecept(rDTO);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void addRaavareBatch(int id, int raavareID, int maengde) throws DALException{
		RaavareBatchDTO rDTO = new RaavareBatchDTO();
		rDTO.setRbId(id);
		rDTO.setRaavareId(raavareID);
		rDTO.setMaengde(maengde);
		try{
			this.raavareBatchDAO.createRaavareBatch(rDTO);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void addProduktBatch(int status, int receptID) throws DALException {
		ProduktBatchDTO rDTO = new ProduktBatchDTO();
		rDTO.setStatus(status);
		rDTO.setReceptId(receptID);
		try{
			this.produktBatchDAO.createProduktBatch(rDTO);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void updateRaavare(int id, String name, String deliverer){
		try{
			RaavareDTO r = new RaavareDTO();
			r.setrID(id);
			r.setrName(name);
			r.setDeliverer(deliverer);
			this.raavareDAO.updateRaavare(r);
		}catch (DALException e){
			e.printStackTrace();
		}
	}
	@Override
	public void deleteRaavare(int id) throws DALException{
		try{
			this.raavareDAO.deleteRaavare(id);
		}catch (DALException e){
			e.printStackTrace();
			throw e;	
		}
	}

	@Override
	public void updateRecept(int id, String name) {
		try{
			ReceptDTO r = new ReceptDTO();
			r.setReceptId(id);
			r.setReceptName(name);
			this.receptDAO.updateRecept(r);
		}catch (DALException e){
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRecept(int id) throws DALException {
		try{
			this.receptDAO.deleteRecept(id);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void updateRaavareBatch(int id, int rID, double maengde) throws DALException {
		try{
			RaavareBatchDTO r = new RaavareBatchDTO();
			r.setRbId(id);
			r.setRaavareId(rID);
			r.setMaengde(maengde);
			this.raavareBatchDAO.updateRaavareBatch(r);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void deleteRaavareBatch(int id) throws DALException{
		try{
			this.raavareBatchDAO.deleteRaavareBatch(id);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void updateProduktBatch(int id, int status, int rID) throws DALException{
		try{
			ProduktBatchDTO r = new ProduktBatchDTO();
			r.setPbId(id);
			r.setStatus(status);
			r.setReceptId(rID);
			this.produktBatchDAO.updateProduktBatch(r);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public void deleteProduktBatch(int id) throws DALException{
		try{
			this.produktBatchDAO.deleteProduktBatch(id);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ArrayList<ProduktBatchKomponentDTO> getProduktKomp() {
		List<ProduktBatchKomponentDTO> rawList = new ArrayList<ProduktBatchKomponentDTO>();
		try {
			rawList = this.produktBatchDAO.getProduktBatchKomponentList();
		} catch (DALException e) {
			e.printStackTrace();
		}
		return (ArrayList<ProduktBatchKomponentDTO>) rawList;
	}

	@Override
	public ArrayList<ReceptKomponentDTO> getReceptKomp() {
		List<ReceptKomponentDTO> rawList = new ArrayList<ReceptKomponentDTO>();
		try {
			rawList = this.receptDAO.getReceptKompList();
		} catch (DALException e) {
			e.printStackTrace();
		}
		return (ArrayList<ReceptKomponentDTO>) rawList;
	}

	@Override
	public void updateProduktKomp(int pbID, int rbID, double tara, double netto, int oprID) throws DALException {
		try{
			ProduktBatchKomponentDTO r = new ProduktBatchKomponentDTO();
			r.setPbId(pbID);
			r.setRbId(rbID);
			r.setTara(tara);
			r.setNetto(netto);
			r.setOprId(oprID);
			this.produktBatchDAO.updateProduktBatchKomponent(r);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}		
	}

	@Override
	public void updateReceptKomp(int receptID, int raavareID, double nomNetto, double tolerance) throws DALException {
		try{
			ReceptKomponentDTO r = new ReceptKomponentDTO();
			r.setReceptID(receptID);
			r.setRaavareID(raavareID);
			r.setNomNetto(nomNetto);
			r.setTolerance(tolerance);
			this.receptDAO.updateReceptKomp(r);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}		
	}

	@Override
	public void deleteProduktKomp(int pbID, int rbID) throws DALException {
		try{
			this.produktBatchDAO.deleteProduktBatchKomponent(pbID, rbID);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}		
	}

	@Override
	public void deleteReceptKomp(int receptID, int raavareID) throws DALException {
		try{
			this.receptDAO.deleteReceptKomp(receptID, raavareID);
		}catch (DALException e){
			e.printStackTrace();
			throw e;
		}			
	}
}