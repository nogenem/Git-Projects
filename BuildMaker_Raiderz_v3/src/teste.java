import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import javax.swing.JTextPane;


public class teste extends JFrame {
	
	private String begin = "<html>\n<head></head>\n<body>\n";
	private String end = "\n</body>\n</html>";
	
	public teste(){
		
		getContentPane().setLayout(null);
		
		TextPane textPane = new TextPane();
		textPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane.setBounds(10, 38, 234, 188);
		textPane.setMyText("[color=green]");
		getContentPane().add(textPane);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(260, 265);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new teste();
	}
	
	@SuppressWarnings("serial")
	private class TextPane extends JTextPane{
		
		public TextPane(){}
		
		public void setMyText(String txt){
			if(txt != null && txt.matches("[(.+)]")){
				System.out.println(txt);
			}
			super.setText(txt);
		}
	}
}
