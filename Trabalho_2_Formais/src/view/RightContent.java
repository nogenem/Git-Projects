package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Interface dos paineis do lado direito da UI.
 * 
 * @author Gilney
 *
 */
@SuppressWarnings("serial")
public class RightContent extends JPanel {
	
	private String content;
	
	/* Components */
	private JTextArea textArea;
	
	/**
	 * Create the application.
	 */
	public RightContent() {
		initialize();
	}
	
	/**
	 * Metodos extras
	 */
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
		refresh();
	}
	
	public void cleanContent(){
		this.content = null;
		refresh();
	}
	
	private void refresh(){
		removeAll();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if(content != null)
			initTextArea();
		else
			initDefault();
	}
	
	private void initDefault(){
		setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("Opera\u00E7\u00E3o ainda n\u00E3o executada...");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);
	}
	
	private void initTextArea(){
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setText(content);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
	}

}
