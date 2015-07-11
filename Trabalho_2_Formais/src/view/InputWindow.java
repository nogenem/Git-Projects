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
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import model.exceptions.GrammarException;
import model.exceptions.ParsingException;
import model.exceptions.SucessException;
import controller.Main;

/**
 * Interface usada para verificação
 * de entradas do usuario.
 * 
 * @author Gilney
 *
 */
@SuppressWarnings("serial")
public class InputWindow extends JDialog implements ActionListener {
	
	private Main main;
	
	/* Compoenents */
	private JTextArea taInput;
	private JTextArea taResult;
	
	/**
	 * Create the application.
	 */
	public InputWindow(JFrame parent, Main main) {
		this.main = main;
		
		initialize();
		setLocationRelativeTo(parent);
		taInput.requestFocusInWindow();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Analise");
		setSize(563, 387);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(300);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel leftPanel = new JPanel();
		splitPane.setLeftComponent(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		leftPanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		JTextPane txtInfo = new JTextPane();
		txtInfo.setFocusable(false);
		txtInfo.setEditable(false);
		txtInfo.setText("-Por favor lembre-se de separar todos os símbolos terminais com um espaço.\r\n"
				+ "-Não é preciso por o símbolo de final de sentença na entrada.\r\n"
				+ "-Caso queira testar &, simplesmente deixe a entrada vazia.");
		topPanel.add(txtInfo, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		leftPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInput = new JLabel("Entre com a sentença para análise:");
		lblInput.setFont(new Font("Tahoma", Font.BOLD, 12));
		centerPanel.add(lblInput, BorderLayout.NORTH);
		
		JButton btnIniciar = new JButton("Iniciar Análise");
		btnIniciar.addActionListener(this);
		centerPanel.add(btnIniciar, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		taInput = new JTextArea();
		scrollPane.setViewportView(taInput);
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblResult = new JLabel("Resultado da análise:");
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 12));
		rightPanel.add(lblResult, BorderLayout.NORTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		rightPanel.add(scrollPane_1, BorderLayout.CENTER);
		
		taResult = new JTextArea();
		taResult.setEditable(false);
		scrollPane_1.setViewportView(taResult);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			main.parsing(taInput.getText());
		}catch(GrammarException exc){
			System.err.println("ERRO> "+exc.getMessage());
			JOptionPane.showMessageDialog(this, exc.getMessage(),
					"Erro", JOptionPane.WARNING_MESSAGE);
		}catch(SucessException |  ParsingException exc){
			System.out.println("Result> "+exc.getMessage());
			taResult.setText(exc.getMessage());
		}catch(Exception exc){
			exc.printStackTrace();
			JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

}
