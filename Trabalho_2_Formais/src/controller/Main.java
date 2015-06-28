package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import model.cfg.CfgCtrl;
import model.cfg.ContextFreeGrammar;
import model.exceptions.GrammarException;
import model.persistencia.CfgDao;
import view.RightContent;
import view.UserInterface;

public class Main {
	
	private UserInterface ui;
	private HashMap<String, ContextFreeGrammar> grammars;
	private HashMap<String, RightContent> panels;
	private ContextFreeGrammar currentCFG;
	
	private CfgDao dao;
	
	/*
	 * To do:
	 * 		Arrumar problema no First
	 */
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		this.dao = new CfgDao();
		this.grammars = new HashMap<>();
		this.panels = new HashMap<>();
		this.ui = new UserInterface(this);
		
		initHash();
		initList();
		
		this.ui.getFrame().setVisible(true);
	}
	
	private void initHash(){
		panels.put("G.L.C", new RightContent());
		panels.put("Verificações", new RightContent());
		panels.put("First", new RightContent());
		panels.put("FirstNT", new RightContent());
		panels.put("Follow", new RightContent());
		panels.put("Parser", new RightContent());
	}
	
	private void initList() {
		ArrayList<ContextFreeGrammar> cfgs = null;
		try{
			cfgs = dao.getAll();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(ui.getFrame(), "Ocorreu um erro inesperado com o banco de dados.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		if(cfgs != null && cfgs.size() > 0)
			for(ContextFreeGrammar cfg : cfgs)
				internalAddGrammar(cfg);
	}

	private void cleanPanels(){
		ui.setTitulo("Sem conteudo...");
		for(RightContent c : panels.values())
			c.cleanContent();
	}
	
	public void showRightContent(String key) {
		if(grammars.containsKey(key)){//left list [key == titulo]
			ContextFreeGrammar cfg = grammars.get(key);
			setRightContent("G.L.C", cfg, true);
		}else if(panels.containsKey(key)){//change on comboBox
			ui.setRightContent(panels.get(key));
		}
	}
	
	private void setExtras() {
		panels.get("First").setContent(currentCFG.printSet("first"));
		panels.get("FirstNT").setContent(currentCFG.printSet("firstNT"));
		panels.get("Follow").setContent(currentCFG.printSet("follow"));
	}
	
	private void setRightContent(String key, ContextFreeGrammar cfg,
			boolean clean){
		if(clean)
			cleanPanels();
		if(cfg != null){
			currentCFG = cfg;
			panels.get(key).setContent(currentCFG.toString());
			ui.setTitulo(currentCFG.getTitulo());
			if(currentCFG.hasExtras())
				setExtras();
		}
		
		if(!ui.setComboBoxSelectedItem(key))//caso ja esteja no panel do 'key', entao da refresh no panel
			ui.setRightContent(panels.get(key));
	}
	
	private void setRightContent(String key, String content){
		if(content != null)
			panels.get(key).setContent(content);
		
		if(!ui.setComboBoxSelectedItem(key))//caso ja esteja no panel do 'key', entao da refresh no panel
			ui.setRightContent(panels.get(key));
	}
	
	private void internalAddGrammar(ContextFreeGrammar cfg) {
		grammars.put(cfg.getTitulo(), cfg);
		ui.addInTheList(cfg.getTitulo());		
	}
	
	public void addGrammar(String titulo, String grammar) throws GrammarException, Exception {
		if(grammars.containsKey(titulo))
			throw new GrammarException("Ja existe uma gramatica com este titulo.");
		
		ContextFreeGrammar cfg = CfgCtrl.createGrammar(grammar);
		cfg.setTitulo(titulo);
		
		grammars.put(titulo, cfg);
		ui.addInTheList(titulo);
		
		dao.addGrammar(cfg);
		
		setRightContent("G.L.C", cfg, true);
	}

	public void isLL1() throws GrammarException, Exception {
		if(currentCFG == null)
			return;
		
		ArrayList<String> result = CfgCtrl.checkIfIsLL1(currentCFG);
		StringBuilder txt = new StringBuilder("Verificações: \n");
		
		txt.append("    -Recursão a esquerda:\n");
		txt.append("       -"+result.get(0)+"\n");
		txt.append("    -Fatoração:\n");
		txt.append("       -"+result.get(1)+"\n");
		txt.append("    -Terceira condição:\n");
		txt.append("       -"+result.get(2)+"\n");
		txt.append("\n\nResultado:\n");
		txt.append("   -"+result.get(3));
		
		// seta first, firstNT e follow
		setExtras();
		if(!currentCFG.hasExtras()){
			currentCFG.setExtras(true);
			dao.setExtras(currentCFG);
		}
		
		setRightContent("Verificações", txt.toString());
		JOptionPane.showMessageDialog(ui.getFrame(), result.get(3));
	}

}
