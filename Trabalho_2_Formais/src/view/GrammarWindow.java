package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import model.exceptions.GrammarException;
import controller.Main;

/**
 * Interface para adicionar e editar gramaticas. 
 * 
 * @author Gilney
 *
 */
@SuppressWarnings("serial")
public class GrammarWindow extends JDialog implements ActionListener {

	private Main main;
	private boolean isEdit;
	
	/* Components */
	private JTextField tfTitulo;
	private JTextArea taGrammar;
	private JLabel lblGrammar;
	
	/**
	 * Create the application.
	 * @wbp.parser.constructor
	 */
	public GrammarWindow(JFrame parent, Main main){
		this(parent, main, null, null);
	}
	
	public GrammarWindow(JFrame parent, Main main, String titulo, String grammar) {
		this.main = main;
		this.isEdit = (titulo != null && grammar != null);
		
		initialize(titulo, grammar);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String titulo, String grammar) {
		setTitle("Adicionar G.L.C");
		setType(Type.UTILITY);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//setAlwaysOnTop(true);
		setSize(368,438);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel examplePanel = new JPanel();
		getContentPane().add(examplePanel, BorderLayout.NORTH);
		examplePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		examplePanel.add(scrollPane, BorderLayout.NORTH);
		
		JTextPane txtExample = new JTextPane();
		txtExample.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtExample.setEditable(false);
		txtExample.setText("Siga o padrão do exemplo abaixo:\r\n      E -> E + T | E - T | T\r\n"
				+ "      T -> T * F | T / F | F\r\n      F -> ( E ) | id");
		scrollPane.setViewportView(txtExample);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel titlePanel = new JPanel();
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitulo = new JLabel("Entre com o titulo:");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		titlePanel.add(lblTitulo, BorderLayout.NORTH);
		
		tfTitulo = new JTextField();
		titlePanel.add(tfTitulo, BorderLayout.SOUTH);
		tfTitulo.setColumns(10);
		
		JPanel grammarPanel = new JPanel();
		mainPanel.add(grammarPanel, BorderLayout.CENTER);
		grammarPanel.setLayout(new BorderLayout(0, 0));
		
		lblGrammar = new JLabel("Entre com a gramatica:");
		lblGrammar.setFont(new Font("Tahoma", Font.BOLD, 12));
		grammarPanel.add(lblGrammar, BorderLayout.NORTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		grammarPanel.add(scrollPane_1, BorderLayout.CENTER);
		
		taGrammar = new JTextArea();
		scrollPane_1.setViewportView(taGrammar);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(this);
		getContentPane().add(btnAdicionar, BorderLayout.SOUTH);
		
		if(isEdit){
			setTitle("Editar G.L.C");
			taGrammar.setText(grammar);
			tfTitulo.setText(titulo);
			tfTitulo.setEnabled(false);
			btnAdicionar.setText("Salvar");
			lblGrammar.setText("Edite sua gramatica:");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(tfTitulo.getText().equals("")){
			JOptionPane.showMessageDialog(this, "Entre com um titulo!");
			return;
		}else if(taGrammar.getText().equals("")){
			JOptionPane.showMessageDialog(this, "Entre com uma gramatica!");
			return;
		}
		try{
			if(isEdit)
				main.editGrammar(tfTitulo.getText(), taGrammar.getText());
			else
				main.addGrammar(tfTitulo.getText(), taGrammar.getText());
			dispose();
		}catch (GrammarException exc){
			System.err.println("ERRO> "+exc.getMessage());
			JOptionPane.showMessageDialog(this, 
					exc.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
		}catch(Exception exc){
			exc.printStackTrace();
			JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

}
