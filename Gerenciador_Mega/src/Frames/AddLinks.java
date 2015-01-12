package Frames;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class AddLinks extends JFrame implements ActionListener {

	private MainWindow mainWin;
	private JPanel contentPane, panel;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JButton btnAddLinks, btnCancel;

	/**
	 * Create the frame.
	 */
	public AddLinks(MainWindow mainWin) {
		this.mainWin = mainWin;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 414, 222);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		btnAddLinks = new JButton("Add Links");
		btnAddLinks.setMargin(new Insets(0, 0, 0, 0));
		btnAddLinks.setBounds(96, 244, 89, 23);
		btnAddLinks.addActionListener(this);
		contentPane.add(btnAddLinks);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.setBounds(253, 244, 89, 23);
		btnCancel.addActionListener(this);
		contentPane.add(btnCancel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Add Links")){
			String text = textPane.getText();
			if(text.length() > 0){
				text = adjustText(text);
				mainWin.getLinks(text.split("\n"));
			}
			close();
		}else if(arg0.getActionCommand().equals("Cancel")){
			close();
		}
	}
	
	private void close() {  
        this.dispose();  
    }
	
	private String adjustText(String text){
		text = text.replaceAll("\t","");
		text = text.replaceAll(" ", "");
		text = text.replaceAll("\r", "");
		return text;
	}
}
