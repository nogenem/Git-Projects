package model.persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import model.Regular;
import model.er.RegExpression;
import model.er.RegExpressionCtrl;
import model.gr.RegGrammar;
import model.gr.RegGrammarCtrl;

public class RegularDao extends Dao {
	public RegularDao() {
		super();
	}
	
	public void addRegular(Regular r) throws Exception {
		if(r.isAutomation()){
			System.out.println("RegularDao > Soh eh possivel adicionar GRs ou ERs!");
			return;
		}else if(r.isDumbGrEr())//intersecções não vão ser salvas no DB
			return;
		
		HashMap<String, String> dados = new HashMap<>();
		dados.put("titulo", r.getTitulo());
		if(r.isGrammar())
			dados.put("gr_er", ((RegGrammar)r).getGrammar());
		else
			dados.put("gr_er", ((RegExpression)r).getRegEx());
		dados.put("extras", r.getExtras());
		
		this.insert(dados);
	}
	
	public void editRegular(Regular r) throws Exception {
		if(r.isAutomation()){
			System.out.println("RegularDao > Soh eh possivel adicionar GRs ou ERs!");
			return;
		}else if(r.isDumbGrEr())//intersecções não vão ser salvas no DB
			return;
		
		HashMap<String, String> dados = new HashMap<>();
		if(r.isGrammar())
			dados.put("gr_er", ((RegGrammar)r).getGrammar());
		else
			dados.put("gr_er", ((RegExpression)r).getRegEx());
		dados.put("extras", r.getExtras());
		
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", r.getTitulo());
		
		this.update(dados, where);
	}
	
	public void setExtras(Regular r) throws Exception {
		if(r.isAutomation()){
			System.out.println("RegularDao > Soh eh possivel adicionar GRs ou ERs!");
			return;
		}else if(r.isDumbGrEr())//intersecções não vão ser salvas no DB
			return;
		
		HashMap<String, String> dados = new HashMap<>();
		dados.put("extras", r.getExtras());
		
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", r.getTitulo());
		
		this.update(dados, where);
	}
	
	public void removeRegular(Regular r) throws Exception {
		if(r.isAutomation()){
			System.out.println("RegularDao > Soh eh possivel adicionar GRs ou ERs!");
			return;
		}else if(r.isDumbGrEr())//intersecções não vão ser salvas no DB
			return;
		
		HashMap<String, String> where = new HashMap<>();
		where.put("titulo", r.getTitulo());
		
		this.delete(where);
	}
	
	public ArrayList<Regular> getAll() throws Exception {
		this.select("");
		ResultSet result = this.getResultSet();
		ArrayList<Regular> regResult = new ArrayList<>();
		String tmpTitulo;
		Regular tmpReg;
		
		while(result != null && result.next()){
			tmpTitulo = result.getString("titulo");
			if(tmpTitulo.contains("GR:")){
				tmpReg = RegGrammarCtrl.createRegGrammar(tmpTitulo, result.getString("gr_er"));
				tmpReg.setExtras(result.getString("extras"));
				regResult.add(tmpReg);
			}else{
				tmpReg = RegExpressionCtrl.createRegExpression(tmpTitulo, result.getString("gr_er"));
				tmpReg.setExtras(result.getString("extras"));
				regResult.add(tmpReg);
			}
		}
		
		return regResult;
	}
}
