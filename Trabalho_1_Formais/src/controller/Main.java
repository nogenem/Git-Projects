package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Regular;
import model.WarningException;
import model.af.Automaton;
import model.af.AutomatonCtrl;
import model.er.RegExpressionCtrl;
import model.gr.RegGrammar;
import model.gr.RegGrammarCtrl;
import model.persistencia.RegularDao;
import view.RightContent;
import view.ShowAF;
import view.UserInterface;

/**
 * Classe responsavel pelo controle de dados do programa.
 * 
 * @author Gilney
 */
public class Main {
	
	// interface grafica do programa
	private UserInterface ui;
	// os 2 paineis contendo as Grs/Ers/Afs do programa
	private ArrayList<HashMap<String, RightContent>> panels;
	// 'cache' contendo as Grs/Ers altualmente no programa
	private HashMap<String, Regular> regulares; 
	// classe para interação com banco de dados
	private RegularDao dao;
	
	/*
	 * Coisas que deveria ter feito:
	 * 		-Poder ver e utilizar os AFs intermediarios, como uniao, diferença, complemento...
	 * 			-No meu soh da pra fazer isso com a intersecção...
	 * 		-Tirar o alwaysOnTop das janelas
	 * 		-Na parte de busca, usar um JTextEdit em vez de um input normal e
	 * 		 nao feixar a aba logo apos a 1* busca
	 * 		-Fazer um tratamento para expressoes da forma: a, (a), durante o script De Simone
	 * 		-Permitir entrar com ERs do tpw:
	 * 			a***, a?+ ...
	 */
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		this.ui = new UserInterface(this);
		this.panels = new ArrayList<>(); 
		this.regulares = new HashMap<>();
		this.dao = new RegularDao();
		initHashs();
		initList();
		ui.getFrame().setVisible(true);
	}
	
	/**
	 * Inicializa a lista de Grs/Ers pegando 
	 * as informações do banco de dados.
	 */
	private void initList(){
		ArrayList<Regular> regs = null;
		try {
			regs = dao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(regs != null && regs.size() > 0){
			for(Regular r : regs)
				internalAddGrEr(r);
		}
	}
	
	/**
	 * Inicializa os HashMaps dos dois paines do programa.
	 */
	private void initHashs(){
		HashMap<String, RightContent> l1 = new HashMap<>();
		l1.put("GR/ER", new RightContent());
		l1.put("AF", new RightContent());
		l1.put("AFD", new RightContent());
		l1.put("AFD_Min", new RightContent());
		l1.put("AFD_Comp", new RightContent());
		
		panels.add(l1);

		HashMap<String, RightContent> l2 = new HashMap<>();
		l2.put("GR/ER", new RightContent());
		l2.put("AF", new RightContent());
		l2.put("AFD", new RightContent());
		l2.put("AFD_Min", new RightContent());
		l2.put("AFD_Comp", new RightContent());
		
		panels.add(l2);
	}
	
	/**
	 * Retorna o 'Conjunto' Regular dependendo do lado e da chave.
	 * 
	 * @param side		Qual lado? 1 ou 2.
	 * @param key		Qual 'key'? GR/ER, AF, AFD, AFD_Min, AFD_Comp.
	 * @return			Retorna o 'conjunto' regular.
	 */
	public Regular getRegular(int side, String key){
		return panels.get(side-1).get(key).getRegular();
	}
	
	/**
	 * Limpa todos os paineis de um dos lados.
	 * 
	 * @param side		Qual lado? 1 ou 2.
	 */
	private void cleanExtraPanels(int side){
		HashMap<String, RightContent> panel = panels.get(side-1);
		panel.get("GR/ER").setRegular(null);
		panel.get("AF").setRegular(null);
		panel.get("AFD").setRegular(null);
		panel.get("AFD_Min").setRegular(null);
		panel.get("AFD_Comp").setRegular(null);
	}
	
	/**
	 * Adiciona um novo 'Conjunto' Regular ao 'cache' do programa
	 * e a lista.
	 * 
	 * @param r		'Conjunto' Regular a ser adicionado.
	 */
	private void internalAddGrEr(Regular r){
		regulares.put(r.getTitulo(), r); 
		ui.addInTheList(r.getTitulo());
	}
	
	/**
	 * Adicionada uma nova GR/ER passada pelo usuario.
	 * 
	 * @param type			Qual o tipo do 'Conjunto' Regular? 0 = GR, 1 = ER.
	 * @param side			Qual lado? 1 ou 2.
	 * @param titulo		Titulo do novo 'Conjunto' Regular.
	 * @param reg			Gramatica ou Expressão regular a ser adicionada.
	 * 
	 * @throws WarningException		Caso ja exista uma Gr/Er com mesmo titulo.
	 * @throws WarningException		Caso haja um erro com a Gr/Er entrada pelo usuario.
	 * @throws Exception			Caso haja um erro vindo do banco de dados.
	 */
	public void addGrEr(int type, int side, String titulo, String reg) throws Exception {
		Regular regular = null;
		
		if(titulo == null || titulo.equals(""))
			throw new WarningException("Por favor entre com um titulo.");
		
		titulo = (type==0?"GR: ":"ER: ") +titulo;
		
		if(regulares.containsKey(titulo)) //deixar substituir?
			throw new WarningException("Ja existe uma "+
					(type==0? "gramatica":"express\u00E3o") +
					" com este titulo, por favor escolha outro.");
		
		if(type == 0){
			regular = RegGrammarCtrl.createRegGrammar(titulo, reg);
		}else if(type == 1){
			regular = RegExpressionCtrl.createRegExpression(titulo, reg);
		}
		
		if(regular == null){
			throw new WarningException("Parece haver algum erro com sua "+ 
					(type==0? "gramatica":"express\u00E3o") + 
					". Por favor reanalize-a e tente novamente.");
		}
		
		regulares.put(titulo, regular); 
		ui.addInTheList(titulo);
		
		dao.addRegular(regular);
		
		setRightContent(side, "GR/ER", regular, true);
		createExtras(side);
	}
	
	/**
	 * Edita uma Gr/Er ja adicionada pelo usuario.
	 * 
	 * @param type		Qual o tipo do 'Conjunto' Regular? 0 = GR, 1 = ER.
	 * @param side		Qual lado? 1 ou 2.
	 * @param titulo	Titulo da Gr/Er a ser editada.
	 * @param reg		Gramatica ou Expressão regular a ser editada.
	 * 
	 * @throws WarningException		Caso haja um erro com a Gr/Er entrada pelo usuario.
	 * @throws Exception 			Caso haja um erro vindo do banco de dados.
	 */
	public void editGrEr(int type, int side, String titulo, String reg) throws Exception {
		Regular regular = null;
		
		if(type == 0){
			regular = RegGrammarCtrl.createRegGrammar(titulo, reg);
		}else if(type == 1){
			regular = RegExpressionCtrl.createRegExpression(titulo, reg);
		}
		
		if(regular == null){
			throw new WarningException("Parece haver algum erro com sua "+ 
					(type==0? "gramatica":"express\u00E3o") + 
					". Por favor reanalize-a e tente novamente.");
		}
		
		regular.setExtras(regulares.get(titulo).getExtras());
		regulares.put(titulo, regular);
		
		dao.editRegular(regular);
		
		updateRightContentPanel(side, "GR/ER", regular, true);
	}
	
	/**
	 * Deleta uma Gr/Er do 'cache', da lista e do banco de dados.
	 * 
	 * @param side			Qual lado? 1 ou 2.	
	 * @throws Exception 	Caso haja um erro vindo do banco de dados.
	 */
	public void deleteGrEr(int side) throws Exception{
		Regular reg = getRegular(side, "GR/ER");
		if(reg != null){
			regulares.remove(reg.getTitulo());
			setRightContent(side, "GR/ER", null, true);
			ui.removeOfTheList(side, reg.getTitulo());
			dao.removeRegular(reg);
		}
	}
	
	/**
	 * Cria os AFs extras de uma Gr/Er ou de um AF Intersecção.
	 * 
	 * @param side	Qual lado? 1 ou 2.
	 */
	private void createExtras(int side){
		Regular reg = getRegular(side, "GR/ER");
		String extras = reg.getExtras();
		
		if(!reg.isDumbGrEr() && (extras.equals("") || extras.contains("AF")))
			panels.get(side-1).get("AF").
				setRegular(AutomatonCtrl.createAutomaton(reg));
		
		Automaton af = (Automaton) getRegular(side, "AF");
		if(extras.contains("AFD"))
			panels.get(side-1).get("AFD").
				setRegular(AutomatonCtrl.determinize(af));
		
		af = (Automaton) getRegular(side, "AFD");
		if(extras.contains("AFD_Comp"))
			panels.get(side-1).get("AFD_Comp").
				setRegular(AutomatonCtrl.complement(af));
		
		if(extras.contains("AFD_Min"))
			panels.get(side-1).get("AFD_Min").
				setRegular(AutomatonCtrl.minimize(af));
	}
	
	/**
	 * Função utilizada para mudar o conteudo de um painel.
	 * Pode tanto ser chamada pelo usuario clicando 2x num elemento da lista,
	 * como pelo usuario mudando o valor de um dos comboBox.
	 * 
	 * @param side		Qual lado? 1 ou 2.
	 * @param key		Qual 'key'? GR/ER, AF, AFD, AFD_Min, AFD_Comp.
	 */
	public void showRightContent(int side, String key){
		if(regulares.containsKey(key)){//left list [key == titulo]
			Regular reg = regulares.get(key);
			if(regulares.get(key).isAutomation()){
				RegGrammar dumb = new RegGrammar(reg.getTitulo(), "");
					dumb.setExtras(reg.getExtras());
				setRightContent(side, "GR/ER", dumb, true);
				setRightContent(side, "AF", reg, false);
			}else
				setRightContent(side, "GR/ER", reg, true);

			createExtras(side);
		}else if(panels.get(side-1).containsKey(key)){//change on comboBox
			RightContent panel = panels.get(side-1).get(key);
			ui.setRightContent(side, panel);
		}
	}
	
	/**
	 * Função utilizada internamente para mudar o conteudo de um 
	 * dos paineis.
	 * 
	 * @param side		Qual lado? 1 ou 2.
	 * @param key		Qual 'key'? GR/ER, AF, AFD, AFD_Min, AFD_Comp.
	 * @param reg		'Conjunto' Regular que ira ser adicionado ao painel.
	 * @param clean		Devesse limpar os outros paineis?
	 */
	private void setRightContent(int side, String key, Regular reg, boolean clean){
		RightContent panel = panels.get(side-1).get(key);
		
		if(clean)
			cleanExtraPanels(side);
		if(reg != null)
			panel.setRegular(reg);
		
		if(!ui.setComboBoxSelectedItem(side, key))//caso ja esteja no panel do 'key', entao da refresh no panel
			ui.setRightContent(side, panel);
	}
	
	/**
	 * Função utilizada para mudar o conteudo de um 
	 * dos paineis. Caso os 2 paineis sejam iguais, 
	 * atualiza o outro painel tambem.
	 * 
	 * @param side			Qual lado? 1 ou 2.
	 * @param key			Qual 'key'? GR/ER, AF, AFD, AFD_Min, AFD_Comp.
	 * @param regular		'Conjunto' Regular que ira ser adicionado ao painel.
	 * @param clean			Devesse limpar os outros paineis?
	 */
	private void updateRightContentPanel(int side, String key, Regular regular, boolean clean){
		if(isSameGrErInBothPanels()){//ms GR/ER nos 2 lados
			setRightContent((side%2)+1, key, regular, clean);
			createExtras((side%2)+1);
		}
		setRightContent(side, key, regular, clean);
		createExtras(side);
	}
	
	/**
	 * Determiniza o AF de um dos lados do programa.
	 * Caso os dois lados sejam iguais, atualiza os dois.
	 * Esta função atualiza o valor de 'Extras' da Gr/Er.
	 * 
	 * @param side			Qual lado? 1 ou 2.
	 * @throws Exception	Caso haja um erro vindo do banco de dados.
	 */
	public void determinize(int side) throws Exception{
		determinize(side, true);
	}
	
	/**
	 * Determiniza o AF de um dos lados do programa.
	 * Caso os dois lados sejam iguais, atualiza os dois.
	 * 
	 * @param side				Qual lado? 1 ou 2.
	 * @param updateExtras		Devesse atualizar o valor de 'Extras' da Gr/Er?
	 * @throws Exception		Caso haja um erro vindo do banco de dados.
	 */
	private void determinize(int side, boolean updateExtras) throws Exception{
		if(getRegular(side, "GR/ER") == null)//ainda n tem GR/ER
			return;
		else if(getRegular(side, "AFD") != null){//AFD ja esta criado, entao soh mude para o panel dele
			setRightContent(side, "AFD", null, false);
			return;
		}
		
		Automaton afnd = (Automaton) getRegular(side, "AF");
		setRightContent(side, "AFD", AutomatonCtrl.determinize(afnd), false);
		
		if(updateExtras){
			addExtra(side, "AFD");
			dao.setExtras(getRegular(side, "GR/ER"));
		}

		if(isSameGrErInBothPanels())
			determinize((side%2)+1, false);
	}
	
	/**
	 * Determiniza, caso necessario, e complementa o AF de um dos lados.
	 * Caso os dois lados sejam iguais, atualiza os dois.
	 * Esta função atualiza o valor de 'Extras' da Gr/Er.
	 *
	 * @param side			Qual lado? 1 ou 2.
	 * @throws Exception	Caso haja um erro vindo do banco de dados.
	 */
	public void complement(int side) throws Exception{
		complement(side, true);
	}
	
	/**
	 * Determiniza, caso necessario, e complementa o AF de um dos lados.
	 * Caso os dois lados sejam iguais, atualiza os dois.
	 *
	 * @param side				Qual lado? 1 ou 2.
	 * @param updateExtras		Devesse atualizar o valor de 'Extras' da Gr/Er?
	 * @throws Exception		Caso haja um erro vindo do banco de dados.
	 */
	private void complement(int side, boolean updateExtras) throws Exception{
		if(getRegular(side, "GR/ER") == null)//ainda n tem GR/ER
			return;
		else if(getRegular(side, "AFD_Comp") != null){//AFD_Comp ja esta criado, entao soh mude para o panel dele
			setRightContent(side, "AFD_Comp", null, false);
			return;
		}
		
		if(getRegular(side, "AFD") == null)
			determinize(side,false);
		
		Automaton afd = (Automaton) getRegular(side, "AFD");
		setRightContent(side, "AFD_Comp", AutomatonCtrl.complement(afd), false);
		
		if(updateExtras){
			addExtra(side, "AFD|AFD_Comp");
			dao.setExtras(getRegular(side, "GR/ER"));
		}
		
		if(isSameGrErInBothPanels())
			complement((side%2)+1, false);
	}
	
	/**
	 * Realiza uma busca no texto passado.
	 * Caso o usuario esteja visualizando o AFD Complementar, ele que sera
	 * utilizado na busca, caso contrario sera utilizado o AFD Minimo.
	 * 
	 * @param side			Qual lado? 1 ou 2.
	 * @param txt			Texto usado para a busca.
	 * @return				Todas as strings do texto aceitas pelo AFD_Min.
	 * @throws Exception	Caso haja um erro vindo do banco de dados.
	 */
	public ArrayList<String> search(int side, String txt) throws Exception{
		if(getRegular(side, "GR/ER") == null && 
				getRegular(side, "AF") == null)//ainda n tem GR/ER
			return null;
		
		Automaton afd;
		if(ui.getComboBoxSelectedItem(side).equals("AFD_Comp")){//usuario quer usar o complemento
			if(getRegular(side, "AFD_Comp") == null)
				complement(side,true);
			afd = (Automaton) getRegular(side, "AFD_Comp");
		}else{
			if(getRegular(side, "AFD_Min") == null)
				minimize(side,true);
			afd = (Automaton) getRegular(side, "AFD_Min");
		}
		
		return AutomatonCtrl.search(txt, afd, true);
	}
	
	/**
	 * Determiniza, caso necessario, e Miniminiza o AF de um dos lados do programa.
	 * Caso os dois lados sejam iguais, atualiza os dois.
	 * Esta função atualiza o valor de 'Extras' da Gr/Er.
	 *
	 * @param side			Qual lado? 1 ou 2.
	 * @throws Exception	Caso haja um erro vindo do banco de dados.
	 */
	public void minimize(int side) throws Exception{
		minimize(side, true);
	}
	
	/**
	 * Determiniza, caso necessario, e Miniminiza o AF de um dos lados do programa.
	 * Caso os dois lados sejam iguais, atualiza os dois.
	 *
	 * @param side				Qual lado? 1 ou 2.
	 * @param updateExtras		Devesse atualizar o valor de 'Extras' da Gr/Er?
	 * @throws Exception		Caso haja um erro vindo do banco de dados.
	 */
	private void minimize(int side, boolean updateExtras) throws Exception{
		if(getRegular(side, "GR/ER") == null)//ainda n tem GR/ER
			return;
		else if(getRegular(side, "AFD_Min") != null){//AFD_Min ja esta criado, entao soh mude para o panel dele
			setRightContent(side, "AFD_Min", null, false);
			return;
		}
		
		if(getRegular(side, "AFD") == null)//Minimizacao precisa de um AFD
			determinize(side,false);
		
		Automaton afd = (Automaton) getRegular(side, "AFD");
		setRightContent(side, "AFD_Min", AutomatonCtrl.minimize(afd), false);
		
		if(updateExtras){
			addExtra(side, "AFD|AFD_Min");
			dao.setExtras(getRegular(side, "GR/ER"));
		}
		
		if(isSameGrErInBothPanels())
			minimize((side%2)+1,false);
	}
	
	/**
	 * Miniminiza, caso necessario, os dois lados e Compara suas Grs/Ers.
	 * 
	 * @return				TRUE caso lado1 == lado2, FALSE caso contrario.
	 * @throws Exception	Caso haja um erro vindo do banco de dados casada pela minimização.
	 */
	public boolean compare() throws Exception{
		if(getRegular(1, "AFD_Min") == null)//melhor usar os AFDs minimos para comparar...
			minimize(1,true);
		if(!isSameGrErInBothPanels() && getRegular(2, "AFD_Min") == null)
			minimize(2,true);
		
		Automaton afd1 = (Automaton) getRegular(1, "AFD_Min");
		Automaton afd2 = (Automaton) getRegular(2, "AFD_Min");
		
		return AutomatonCtrl.compare(afd1, afd2);
	}
	
	/**
	 * Miniminiza, caso necessario, e faz a intersecção dos dois lados.
	 * 
	 * @throws Exception	Caso haja um erro vindo do banco de dados.
	 */
	public void intersection() throws Exception{
		if(getRegular(1, "AFD_Min") == null)//melhor usar os AFDs minimos para comparar...
			minimize(1,true);
		if(!isSameGrErInBothPanels() && getRegular(2, "AFD_Min") == null)
			minimize(2,true);
		
		Automaton afd1 = (Automaton) getRegular(1, "AFD_Min");
		Automaton afd2 = (Automaton) getRegular(2, "AFD_Min");
		
		Automaton inter = AutomatonCtrl.intersection(afd1, afd2);
		RightContent content = new RightContent(inter);
		
		inter.setTitulo("AF: "+afd1.getTitulo()+" /\\ "+afd2.getTitulo());
		regulares.put(inter.getTitulo(), inter); 
		ui.addInTheList(inter.getTitulo());
		
		new ShowAF(ui.getFrame(), content);
	}
	
	/**
	 * Verifica se os dois lados tem a mesma Gr/Er
	 * 
	 * @return
	 */
	private boolean isSameGrErInBothPanels(){
		Regular r1 = getRegular(1, "GR/ER");
		Regular r2 = getRegular(2, "GR/ER");
		
		if(r1 != null && r2 != null && 
				r2.getTitulo().equals(r1.getTitulo())){
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica se a Gr/Er de um dos lados 
	 * esta vazia, isto é utilizado para a intersecção.
	 * 
	 * @param side		Qual lado? 1 ou 2.
	 * @return			TRUE caso a Gr/Er esta vazia.
	 */
	public boolean isDumbGrEr(int side){
		Regular reg = getRegular(side, "GR/ER");
		return reg==null?false:reg.isDumbGrEr();
	}
	
	/**
	 * Verifica se os dois lados tem AFs.
	 * 
	 * @return		TRUE caso os dois lados contenham AFs.
	 */
	public boolean haveTwoAFs(){
		return getRegular(1, "AF") != null &&
			   getRegular(2, "AF") != null;	
	}
	
	/**
	 * Verifica se a Gr/Er de um dos lados é uma ER.
	 * 
	 * @param side	Qual lado? 1 ou 2.
	 * @return		TRUE caso seja uma ER.
	 */
	public boolean isRegExpression(int side){
		return getRegular(side, "GR/ER").isExpression();
	}
	
	/**
	 * Adiciona um valor 'extra' a Gr/Er do lado expecificado.
	 * 
	 * @param side		Qual lado? 1 ou 2.
	 * @param extra		Valor 'extra' a ser adicionado.
	 */
	private void addExtra(int side, String extra) {
		Regular reg = getRegular(side, "GR/ER");
		
		reg.addExtra(extra);
	}
}
