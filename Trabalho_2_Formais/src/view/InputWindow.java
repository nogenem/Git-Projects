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
import javax.swing.JTextField;
import javax.swing.JTextPane;

import model.exceptions.GrammarException;
import model.exceptions.ParsingException;
import controller.Main;


@SuppressWarnings("serial")
public class InputWindow extends JDialog implements ActionListener {
	
	private Main main;
	
	/* Components */
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public InputWindow(JFrame parent, Main main) {
		this.main = main;
		
		initialize();
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Entre com uma string");
		setSize(451, 144);
		setResizable(false);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		//JScrollPane scrollPane = new JScrollPane();
		//topPanel.add(scrollPane, BorderLayout.CENTER);
		
		JTextPane txtPane = new JTextPane();
		txtPane.setEditable(false);
		txtPane.setText("Por favor lembre-se de separar todos os símbolos terminais e não terminais com um espaço.\r\n"
				+ "Não é preciso por o símbolo de final de palavra na entrada.");
		topPanel.add(txtPane, BorderLayout.CENTER);
		//scrollPane.setViewportView(txtPane);
		
		JLabel label = new JLabel("Entre com a string para análise:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		topPanel.add(label, BorderLayout.SOUTH);
		
		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnIniciar = new JButton("Iniciar análise");
		btnIniciar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIniciar.addActionListener(this);
		getContentPane().add(btnIniciar, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			main.parsing(textField.getText());
			JOptionPane.showMessageDialog(this, "Sucesso! Entrada foi reconhecida pela gramatica.");
			dispose();
		}catch(GrammarException | ParsingException exc){
			System.err.println("ERRO> "+exc.getMessage());
			JOptionPane.showMessageDialog(this, exc.getMessage(),
					"Erro", JOptionPane.WARNING_MESSAGE);
		}catch(Exception exc){
			exc.printStackTrace();
			JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

}
