package Persistencia;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import JButtons.MasteryButton;
import JButtons.SkillButton;
import JButtons.abButton;

public class ClericDao extends Dao {
	
	public ClericDao(){
		super(new ConexaoBanco(), "ClericSkills");
	}
	public ClericDao(ConexaoBanco c, String tabela) {
		super(c, tabela);
	}
	
	public ArrayList<abButton> getSkillBtnById(int id){
		try{
			this.tabela = "ClericSkills";
			HashMap<String, String> cond = new HashMap<>();
			cond.put("ID", ""+id);
			
			this.select(cond);
			
			abButton but;
			ResultSet result = this.getResultSet();
			ArrayList<abButton> ret = new ArrayList<>();
			
			if(result == null){return null;}
			
			while(result.next()){
				but = new abButton(result.getInt("ID"), result.getString("NAME"), result.getString("DESCRICAO"),
									  result.getInt("MAX_PNTS"), result.getInt("REQ"));
				ret.add(but);
			}
			return ret;			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<abButton> getMasteryBtnById(int id){
		try{
			this.tabela = "ClericPassives";
			HashMap<String, String> cond = new HashMap<>();
			cond.put("ID", ""+id);
			
			this.select(cond);
			
			abButton but;
			ResultSet result = this.getResultSet();
			ArrayList<abButton> ret = new ArrayList<>();
			
			if(result == null) return null;
			
			while(result.next()){
				but = new abButton(result.getInt("ID"), result.getString("NAME"), result.getString("DESCRICAO"),
									  result.getInt("REQ"));
				ret.add(but);
			}
			return ret;			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
