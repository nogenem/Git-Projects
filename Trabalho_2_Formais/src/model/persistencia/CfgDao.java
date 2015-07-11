package model.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import model.cfg.CfgCtrl;
import model.cfg.ContextFreeGrammar;

public class CfgDao extends Dao {
	
	public CfgDao() {
		super();
	}
	
	public void addGrammar(ContextFreeGrammar cfg) throws Exception {
		HashMap<String, String> dados = new HashMap<>();
		dados.put("titulo", cfg.getTitulo());
		dados.put("grammar", cfg.toString());
		
		this.insert(dados);
	}
	
	public void editGrammar(ContextFreeGrammar cfg) throws Exception {
		HashMap<String, String> dados = new HashMap<>();
		dados.put("grammar", cfg.toString());;
		dados.put("parser", "NULL");
		
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", cfg.getTitulo());
		
		this.update(dados, where);
	}
	
	public void setExtras(ContextFreeGrammar cfg) throws Exception {
		HashMap<String, String> dados = new HashMap<>();
		dados.put("extras", "1");
		
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", cfg.getTitulo());
		
		this.update(dados, where);
	}
	
	public void setParser(ContextFreeGrammar cfg) throws Exception {
		HashMap<String, String> dados = new HashMap<>();
		dados.put("parser", cfg.getParser());
		
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", cfg.getTitulo());
		
		this.update(dados, where);
	}
	
	public void removeGrammar(ContextFreeGrammar cfg) throws Exception {
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", cfg.getTitulo());
		
		this.delete(where);
	}
	
	public ArrayList<ContextFreeGrammar> getAll() throws Exception {
		this.select("");
		ResultSet result = this.getResultSet();
		ArrayList<ContextFreeGrammar> cfgs = new ArrayList<>();
		ContextFreeGrammar tmp = null;
		
		while(result != null && result.next()){
			tmp = CfgCtrl.createGrammar(result.getString("grammar"));
			tmp.setTitulo(result.getString("titulo"));
			tmp.setParser(result.getString("parser"));
			if(result.getBoolean("extras"))
				tmp.setExtras(true);
			
			cfgs.add(tmp);
		}
		
		return cfgs;
	}
}
