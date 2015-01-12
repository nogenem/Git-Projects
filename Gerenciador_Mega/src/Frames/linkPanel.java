package Frames;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class linkPanel extends JPanel {
	private JLabel label;
	private JProgressBar progress;
	
	public linkPanel(String text){
		label = new JLabel(text);
		label.setToolTipText(text);
		label.setBounds(10, 3, 270, 14);
		
		progress = new JProgressBar(0,100);
		progress.setBounds(290, 3, 146, 14);
		progress.setToolTipText("0%");
		
		setLayout(null);
		add(label);
		add(progress);
	}
	
	public String getText(){
		return label.getText();
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public void updateProgress(int value){
		progress.setValue(value);
		progress.setToolTipText(value+"%");
	}
}
