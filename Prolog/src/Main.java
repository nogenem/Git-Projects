import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Main implements ActionListener {

	private JFrame frame;
	private prologHandler ph;
	private JLabel lblPerg;

	private String pergAtual = "Seu pais pertence a America do Sul?";//pergunta inicial
	private String plFile = "src/pl/Main.pl";
	private String bdFile = "src/pl/bd.txt";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		ph = new prologHandler(plFile, bdFile);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 384, 179);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblEnun = new JLabel("Pense em um pa\u00EDs e resposda a pergunta:");
		lblEnun.setBounds(10, 11, 277, 14);
		frame.getContentPane().add(lblEnun);

		lblPerg = new JLabel(pergAtual);
		lblPerg.setVerticalAlignment(SwingConstants.TOP);
		lblPerg.setBounds(21, 32, 337, 46);
		frame.getContentPane().add(lblPerg);

		JButton btnSim = new JButton("Sim");
		btnSim.setBounds(86, 89, 72, 23);
		btnSim.addActionListener(this);
		frame.getContentPane().add(btnSim);

		JButton btnNo = new JButton("N\u00E3o");
		btnNo.setBounds(215, 89, 72, 23);
		btnNo.addActionListener(this);
		frame.getContentPane().add(btnNo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String next = ph.getNextQuestion(e.getActionCommand().substring(0, 1).toLowerCase(),
				 						 pergAtual);

		if(next.equals("Done")){
			JOptionPane.showMessageDialog(null, "Obrigado por jogar comigo, boa sorte na proxima.");
			System.exit(0);
		}else if(next.equals("N sei")){
			JOptionPane.showMessageDialog(null, "Ent\u00E3o eu n\u00E3o sei a resposta, quem sabe " +
												"na proxima eu acerte...");
			System.exit(0);
		}

		pergAtual = next;
		lblPerg.setText(pergAtual);
	}

}
