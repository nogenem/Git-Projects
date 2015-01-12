package JPanels;

import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class abPanel extends JPanel {
	
	public abPanel(){}
	
	//Funçoes para os paineis das classes
	public void createSeparators(JPanel toPanel, int width, int height){
		JSeparator separator = null;
		for(int i = 1; i<=6; i++){
			separator = new JSeparator();
			separator.setBounds(2, 50*i, width, height);
			toPanel.add(separator);
		}
	}
	public void resetTree(){}
	public void adjustIcons(){}
}

