package JPanels;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import JButtons.sButton;

@SuppressWarnings("serial")
public abstract class abPanel extends JPanel {
	
	protected OptionsPanel optionsPanel;
	protected dAtualLvlPanel dAtual;
	protected dNextLvlPanel dNext;	
	
	protected JPanel SkillsPanel, MasteryPanel;
	protected ArrayList<sButton> skillBtnList, masteryBtnList;
	protected String skillsImgsPath, masterysImgsPath;

	public abPanel(){}
	public abPanel(OptionsPanel optionsPanel, dAtualLvlPanel dAtual, dNextLvlPanel dNext){
		this.optionsPanel = optionsPanel;
		this.dAtual = dAtual;
		this.dNext = dNext;
	}
	
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
