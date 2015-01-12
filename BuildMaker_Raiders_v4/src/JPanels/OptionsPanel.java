package JPanels;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class OptionsPanel extends JPanel {
	
	private JTabbedPane jtabbed;
	
	public OptionsPanel(){
		
	}
	
	public void setClassesTabs(JTabbedPane jtabbed){
		this.jtabbed = jtabbed;
	}
}
