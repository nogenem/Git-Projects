package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.FlowLayout;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import model.cfg.ContextFreeGrammar;
import model.exceptions.GrammarException;
import model.exceptions.ParsingException;
import controller.Main;

@SuppressWarnings({"rawtypes","unchecked"})
public class UserInterface {

	private JFrame frame;
	private Main main;
	
	// Variavel usada para o usuario não poder 
	// executar duas operações ao mesmo tempo
	private boolean isDoingSomething;
	
	// Valores da ComboBox do programa
	private final String[] cbModel = 
		{"G.L.C", "Verificações", "First", "FirstNT", "Follow", "Parser"};
	
	/* Components */
	private JPanel rightPanel;
	private JPanel subMenu1;

	private DefaultListModel<String> listModel;
	private JComboBox<String> comboBox;
	
	private JLabel lblTitulo;

	/**
	 * Create the application.
	 */
	public UserInterface(Main main) {
		this.main = main;
		this.isDoingSomething = false;
		initialize();
		this.frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Metodos extras
	 */
	public JFrame getFrame(){
		return this.frame;
	}
	
	/**
	 * Adiciona um titulo na lista do painel esquerdo da UI.
	 * 
	 * @param titulo		Titulo de uma GLC do programa.
	 */
	public void addInTheList(String titulo){
		listModel.addElement(titulo);
	}
	
	/**
	 * Remove um titulo da lista do painel esquerdo da UI.
	 * 
	 * @param titulo		Titulo de uma GLC do programa.
	 */
	public void removeOfTheList(String titulo){
		listModel.removeElement(titulo);
		setEnabledSubMenu1(false);
	}
	
	/**
	 * Muda o valor da comboBox para a 'key' passada.
	 * 
	 * @param key		'Key' para a qual se quer mudar a comboBox.
	 * @return			TRUE caso a comboBox ainda não esteja na 'key'
	 * 					passada.
	 */
	public boolean setComboBoxSelectedItem(String key){
		if(comboBox.getSelectedItem().equals(key))
			return false;
		comboBox.setSelectedItem(key);
		return true;
	}
	
	private void refresh(){
		rightPanel.setVisible(false);
		rightPanel.setVisible(true);
	}
	
	/**
	 * Habilita ou desabilita a comboBox e os botões
	 * de Edit e Del do painel direito da UI.
	 * 
	 * @param enabled		Habilita ou desabilita?
	 */
	private void setEnabledSubMenu1(boolean enabled){
		for(Component c : subMenu1.getComponents())
			c.setEnabled(enabled);
	}
	
	/**
	 * Muda o conteudo central do painel direito da UI.
	 * 
	 * @param panel			Panel que sera colocado na 
	 * 						parte central  do painel direito
	 * 						da UI.
	 */
	public void setRightContent(RightContent panel){
		if(rightPanel.getComponentCount() >= 2)
			rightPanel.remove(1);
		
		if(panel != null){
			setEnabledSubMenu1(true);
			rightPanel.add(panel, BorderLayout.CENTER);
		}else
			setEnabledSubMenu1(false);
		refresh();
	}
	
	public void setTitulo(String titulo){
		lblTitulo.setText(titulo);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setType(Type.POPUP);
		frame.setTitle("Trabalho 2 Formais");
		frame.setBounds(100, 100, 563, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		/* Menus */
		MenuListener mListener = new MenuListener();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNovo = new JMenu("Novo");
		menuBar.add(mnNovo);
		
		JMenuItem itemGlc = new JMenuItem("G.L.C");
		itemGlc.addActionListener(mListener);
		mnNovo.add(itemGlc);
		
		JMenu mnVerificao = new JMenu("Verifica\u00E7\u00E3o");
		menuBar.add(mnVerificao);
		
		JMenuItem itemLL1 = new JMenuItem("LL(1)");
		itemLL1.addActionListener(mListener);
		mnVerificao.add(itemLL1);
		
		JMenu mnParser = new JMenu("Parser");
		menuBar.add(mnParser);
		
		JMenuItem itemIniciar = new JMenuItem("Iniciar");
		itemIniciar.addActionListener(mListener);
		mnParser.add(itemIniciar);
		
		/* Spliter */
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(204);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		/* Left Panel */
		JPanel leftPanel = new JPanel();
		splitPane.setLeftComponent(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		leftPanel.add(scrollPane, BorderLayout.CENTER);
		
		listModel = new DefaultListModel<>();
		JList<String> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList) evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	main.showRightContent((String)list.getSelectedValue());
		        }
		    }
		});
		scrollPane.setViewportView(list);
		
		/* Right Panel */
		RightPanelListener rpListener = new RightPanelListener();
		
		rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel menuPanel = new JPanel();
		rightPanel.add(menuPanel, BorderLayout.NORTH);
		menuPanel.setLayout(new BorderLayout(0, 0));
		
		subMenu1 = new JPanel();
		menuPanel.add(subMenu1, BorderLayout.NORTH);
		subMenu1.setLayout(new BoxLayout(subMenu1, BoxLayout.X_AXIS));
		
		comboBox = new JComboBox<>();
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel(cbModel));
		comboBox.addItemListener(rpListener);
		subMenu1.add(comboBox);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(rpListener);
		subMenu1.add(btnEdit);
		
		JButton btnDel = new JButton("Del");
		btnDel.setEnabled(false);
		btnDel.addActionListener(rpListener);
		subMenu1.add(btnDel);
		
		JPanel subMenu2 = new JPanel();
		menuPanel.add(subMenu2, BorderLayout.SOUTH);
		subMenu2.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		lblTitulo = new JLabel("Sem conteudo...");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		subMenu2.add(lblTitulo);
	}
	
	/**
	 * Classe responsavel por cuidar dos eventos
	 * gerados pelo menu do programa.
	 * 
	 * @author Gilney
	 *
	 */
	private class MenuListener implements ActionListener {

		public MenuListener() {
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if(isDoingSomething)
				return;
			
			isDoingSomething = true;
			
			try{
				switch(cmd){
					case "G.L.C":
						new GrammarWindow(frame, main);
						break;
					case "LL(1)":
						if(!main.hasGrammarOnPanel())
							JOptionPane.showMessageDialog(frame, "Escolha uma gramatica primeiro.");
						else
							main.checkIfIsLL1();
						break;
					case "Iniciar":
						if(!main.hasGrammarOnPanel())
							JOptionPane.showMessageDialog(frame, "Escolha uma gramatica primeiro.");
						else{
							if(!main.isGrammarLL1())
								throw new GrammarException("A gramatica não é LL(1)!");
							else{
								main.generateParser();
								new InputWindow(frame, main);
							}
						}
						break;
				}
			}catch(GrammarException | ParsingException exc){
				System.err.println("ERRO> "+exc.getMessage());
				JOptionPane.showMessageDialog(frame, exc.getMessage(),
						"Erro", JOptionPane.WARNING_MESSAGE);
			}catch(Exception exc){
				exc.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Ocorreu um erro inesperado no programa.",
						"Erro", JOptionPane.ERROR_MESSAGE);
			}finally{
				isDoingSomething = false;
			}
		}
	}
	
	/**
	 * Classe responsavel por cuidar dos eventos
	 * gerados pelos botoes de Edit, Del e a ComboBox
	 * dos paineis da interface.
	 * 
	 * @author Gilney
	 *
	 */
	private class RightPanelListener implements ActionListener, ItemListener {
		
		public RightPanelListener() {
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if(isDoingSomething)
				return;
			
			isDoingSomething = true;
			
			try{
				switch(cmd){
					case "Edit":
						ContextFreeGrammar cfg = main.getCurrentCFG();
						new GrammarWindow(frame, main, cfg.getTitulo(), cfg.toString());
						break;
					case "Del":
						int op = JOptionPane.showConfirmDialog(frame, "Você tem certeza que deseja deletar esta gramatica?");
						if(op==0)
							main.deleteGrammar();
						break;
				}
			}catch(Exception exc){
				exc.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Ocorreu um erro inesperado no programa.");
			}finally{
				isDoingSomething = false;
			}
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				main.showRightContent(e.getItem().toString());
			}
		}
	}

}
