package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JOptionPane;

import model.cfg.CfgCtrl;
import model.cfg.ContextFreeGrammar;
import model.cfg.ParsingCtrl;
import model.exceptions.GrammarException;
import model.exceptions.ParsingException;
import model.exceptions.SucessException;
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
	 * 
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
	
	/**
	 * Função que verifica se o usuario ja escolheu
	 * uma gramatica para fazer as operações do programa.
	 * 
	 * @return		TRUE caso tenha uma gramatica no painel do programa.
	 */
	public boolean hasGrammarOnPanel(){
		return currentCFG != null;
	}
	
	/**
	 * Verifica se a gramatica atualmente sendo usada pelo programa
	 * é LL(1).
	 * 
	 * @return						TRUE caso a gramatica corrente seja LL(1).
	 * 
	 * @throws GrammarException
	 * @throws Exception
	 */
	public boolean isGrammarLL1() throws GrammarException, Exception {
		if(!currentCFG.isLL1Checked())
			checkIfIsLL1(true);
		return currentCFG.isLL1();
	}
	
	/**
	 * Retorna a gramatica corrente sendo usada pelo programa.
	 * 
	 * @return			Gramatica corrente do programa.
	 */
	public ContextFreeGrammar getCurrentCFG(){
		return currentCFG;
	}
	
	/**
	 * Função usada para iniciar as Keys do HashMap que 
	 * vai guardar os paineis do programa.
	 */
	private void initHash(){
		panels.put("G.L.C", new RightContent());
		panels.put("Verificações", new RightContent());
		panels.put("First", new RightContent());
		panels.put("FirstNT", new RightContent());
		panels.put("Follow", new RightContent());
		panels.put("Parser", new RightContent());
	}
	
	/**
	 * Função usada para pegar as gramaticas salvas no Banco de Dados
	 * e adiciona-las ao programa.
	 */
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

	/**
	 * Função usada para limpar o conteudo de todos os paineis
	 * do programa.
	 */
	private void cleanPanels(){
		ui.setTitulo("Sem conteudo...");
		for(RightContent c : panels.values())
			c.cleanContent();
	}
	
	/**
	 * Função usada para mudar o conteudo do painel 
	 * atualmente visivel para o usuario.
	 * Ela é chamada tanto por uma mudança na ComboBox
	 * do programa ou por um clique duplo em uma gramatica
	 * na lista do lado esquerdo do programa.
	 * 
	 * @param key		'Chave' contendo o titulo da gramatica ou 
	 * 					o elemento da ComboBox que ira ser
	 * 					mostrada no painel. 
	 */
	public void showRightContent(String key) {
		if(grammars.containsKey(key)){//left list [key == titulo]
			ContextFreeGrammar cfg = grammars.get(key);
			setRightContent("G.L.C", cfg, true);
		}else if(panels.containsKey(key)){//change on comboBox
			ui.setRightContent(panels.get(key));
		}
	}
	
	/**
	 * Função usada para inicializar os valores de First, FirstNT, Follow,
	 * Parser e Verificações do programa.
	 */
	private void setExtras() {
		panels.get("First").setContent(currentCFG.printSet("first"));
		panels.get("FirstNT").setContent(currentCFG.printSet("firstNT"));
		panels.get("Follow").setContent(currentCFG.printSet("follow"));
		panels.get("Parser").setContent(currentCFG.getParser());
		if(currentCFG.getVerificacoes() == null){
			try{
				checkIfIsLL1(true);
			}catch(Exception e){
				
			}
		}
		panels.get("Verificações").setContent(currentCFG.getVerificacoes());
	}
	
	/**
	 * Função usada para alterar a gramatica corrente do programa
	 * e tambem para mudar o conteudo do painel principal do programa.
	 * 
	 * @param key			'Chave' da ComboBox para qual sera mudado o conteudo
	 * 						do painel.
	 * @param cfg			A nova gramatica que sera usada no programa.
	 * @param clean			É para limpar os paineis?
	 */
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
		
		if(!ui.setComboBoxSelectedItem(key))// Caso ja esteja no panel do 'key', então da refresh no panel
			ui.setRightContent(cfg==null?null:panels.get(key));
	}
	
	/**
	 * Função usada para alterar o conteudo de um painel especifico 
	 * e mudar o painel visivel para o usuario.
	 * 
	 * @param key			'Chave' da ComboBox para qual sera mudado o conteudo
	 * 						do painel.
	 * @param content		Conteudo que o painel correspondente a 'chave' ira receber.
	 */
	private void setRightContent(String key, String content){
		if(content != null)
			panels.get(key).setContent(content);
		
		if(!ui.setComboBoxSelectedItem(key))// Caso ja esteja no panel do 'key', então da refresh no panel
			ui.setRightContent(panels.get(key));
	}
	
	/**
	 * Função usada internamente para adicionar uma gramatica a lista
	 * de gramaticas do programa sem adicionar ela ao painel.
	 * 
	 * @param cfg		Gramatica a ser adicionada.
	 */
	private void internalAddGrammar(ContextFreeGrammar cfg) {
		grammars.put(cfg.getTitulo(), cfg);
		ui.addInTheList(cfg.getTitulo());		
	}
	
	/**
	 * Função usada para adicionar uma gramatica ao programa.
	 * A gramatica é adicionada a lista de gramaticas, ao Banco de Dados,
	 * a lista do lado esquerdo do programa, ao painel principal do programa  
	 * e é 'setada' como a gramatica corrente do programa.
	 * 
	 * @param titulo				Titulo da nova gramatica.
	 * @param grammar				Conteudo da nova gramatica.
	 * 
	 * @throws GrammarException
	 * @throws Exception
	 */
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
	
	/**
	 * Função usada para editar o conteudo da gramatica 
	 * corrente do programa.
	 * 
	 * @param titulo				Titulo da gramatica.
	 * @param grammar				Novo conteudo da gramatica.
	 * 
	 * @throws GrammarException
	 * @throws Exception
	 */
	public void editGrammar(String titulo, String grammar) throws GrammarException, Exception {
		ContextFreeGrammar cfg = CfgCtrl.createGrammar(grammar);
		cfg.setTitulo(titulo);
		cfg.setExtras(grammars.get(titulo).hasExtras());
		
		grammars.put(titulo, cfg);
		dao.editGrammar(cfg);
		
		setRightContent("G.L.C", cfg, true);
	}
	
	/**
	 * Função usada para deletar a gramatica corrente do programa.
	 * 
	 * @throws GrammarException
	 * @throws Exception
	 */
	public void deleteGrammar() throws GrammarException, Exception {
		grammars.remove(currentCFG.getTitulo());
		ui.removeOfTheList(currentCFG.getTitulo());
		setRightContent("G.L.C", null, true);
		dao.removeGrammar(currentCFG);
		currentCFG = null;
	}
	
	/**
	 * Função usada para verificar se a gramatica corrente do programa
	 * é LL(1).
	 * O resultado da verificação é colocado na 'chave' 'Vericações' da ComboBox
	 * do programa.
	 * 
	 * @throws GrammarException
	 * @throws Exception
	 */
	public void checkIfIsLL1() throws GrammarException, Exception {
		checkIfIsLL1(false);
	}
	
	/**
	 * Função usada para verificar se a gramatica corrente do programa
	 * é LL(1). 
	 * O resultado da verificação é colocado na 'chave' 'Vericações' da ComboBox
	 * do programa.
	 * 
	 * @param internal				É uma chamada interna da função?
	 * 
	 * @throws GrammarException
	 * @throws Exception
	 */
	private void checkIfIsLL1(boolean internal) throws GrammarException, Exception {
		if(currentCFG == null)
			throw new GrammarException("Escolha uma gramatica primeiro.");
		if(currentCFG.isLL1Checked()){
			setRightContent("Verificações", null);
			return;
		}
			
		
		ArrayList<String> result = CfgCtrl.checkIfIsLL1(currentCFG);
		StringBuilder txt = new StringBuilder("Verificações: \n");
		Set<String> tmp = null;
		
		txt.append("    -Recursão a esquerda:\n");
		txt.append("       -"+result.get(0)+"\n");
		tmp = currentCFG.getRecSymbols();
		if(tmp.size() > 0){
			txt.append("       -Símbolos recursivos:\n");
			txt.append("          -"+tmp+"\n");
		}
		txt.append("    -Fatoração:\n");
		txt.append("       -"+result.get(1)+"\n");
		tmp = currentCFG.getNonFatSymbols();
		if(tmp.size() > 0){
			txt.append("       -Símbolos não fatorados:\n");
			txt.append("          -"+tmp+"\n");
		}
		txt.append("    -Terceira condição:\n");
		txt.append("       -"+result.get(2)+"\n");
		tmp = currentCFG.getNonEmptyIntersection();
		if(tmp.size() > 0){
			txt.append("       -Símbolos com intersecção não vazia:\n");
			txt.append("          -"+tmp+"\n");
		}
		txt.append("\nResultado:\n");
		txt.append("   -"+result.get(3));
		
		currentCFG.setVerificacoes(txt.toString());
		
		// seta first, firstNT, follow e verificacoes
		setExtras();
		if(!currentCFG.hasExtras()){
			currentCFG.setExtras(true);
			dao.setExtras(currentCFG);
		}

		setRightContent("Verificações", null);
		if(!internal)
			JOptionPane.showMessageDialog(ui.getFrame(), result.get(3));
	}
	
	/**
	 * Função usada para criar o Parser da gramatica corrente do programa,
	 * caso esta ainda não o possua.
	 * 
	 * @throws Exception		
	 */
	public void generateParser() throws Exception {
		if(currentCFG.getParser() == null){
			ParsingCtrl.generateParser(currentCFG);
			dao.setParser(currentCFG);
			setRightContent("Parser", currentCFG.getParser());
		}else
			setRightContent("Parser", null);
	}
	
	/**
	 * Função usada para realizar o Parsing da entrada fornecida
	 * pelo usuario utilizando o Parser da gramatica corrente
	 * do programa.
	 * 
	 * @param input					Entrada dada pelo usuario.
	 * 
	 * @throws GrammarException
	 * @throws ParsingException
	 * @throws SucessException
	 * @throws Exception
	 */
	public void parsing(String input) throws GrammarException, ParsingException, SucessException, Exception {
		if(currentCFG == null)
			throw new GrammarException("Escolha uma gramatica primeiro.");
		else if(!currentCFG.isLL1Checked())
			checkIfIsLL1();
		
		if(!currentCFG.isLL1())
			throw new GrammarException("A gramatica não é LL(1)!");
			
		// Check input
		if(input.matches("(.+)\\$") && !currentCFG.getVt().contains("$"))
			throw new ParsingException("Por favor remova o símbolo $ do final da entrada.\r\n");
		if(input.contains("Z'"))
			throw new ParsingException("Por favor remova o símbolo Z' da entrada.\r\n");
		
		input = input.replaceAll("[\t\r\n]+", " "); // Troca tabulação e quebra de linhas por espaços
													// para garantir o funcionamento do Parser
		input = input.trim();
		input += " Z'"; // Adiciona o simbolo de final de sentença a entrada
		
		ParsingCtrl.parsing(input, currentCFG);
	}

}
