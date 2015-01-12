import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import JPanels.ClericPanel;
import JPanels.OptionsPanel;
import JPanels.dAtualLvlPanel;
import JPanels.dNextLvlPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public GUI(){
		//Splits panels
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(420);
		splitPane.setEnabled(false);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setLeftComponent(splitPane_1);
		splitPane_1.setDividerLocation(160);
		splitPane_1.setEnabled(false);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane.setRightComponent(splitPane_2);
		splitPane_2.setDividerLocation(320);
		splitPane_2.setEnabled(false);
		
		//JPanels
		OptionsPanel optionsPanel = new OptionsPanel();
		splitPane_1.setLeftComponent(optionsPanel);

		JPanel TreePanel = new JPanel();
		TreePanel.setLayout(new BorderLayout(0, 0));
		splitPane_1.setRightComponent(TreePanel);

		dAtualLvlPanel dAtual = new dAtualLvlPanel();
		splitPane_2.setLeftComponent(dAtual);

		dNextLvlPanel dNext = new dNextLvlPanel();
		splitPane_2.setRightComponent(dNext);

		//jtabbed
		JTabbedPane classesTabs = new JTabbedPane(JTabbedPane.TOP);
		TreePanel.add(classesTabs, BorderLayout.CENTER);

		classesTabs.addTab("Berserker", null, new JPanel(), "Berserker's build.");
		classesTabs.addTab("Defender", null, new JPanel(), "Defender's build.");
		classesTabs.addTab("Sorcerer", null, new JPanel(), "Sorcerer's build.");
		classesTabs.addTab("Cleric", null, new ClericPanel(optionsPanel, dAtual, dNext), "Cleric's build.");
		classesTabs.addTab("Assassin", null, new JPanel(), "Assassin's build.");

		classesTabs.setSelectedIndex(3);

		optionsPanel.setClassesTabs(classesTabs);

		//add o split
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		//configura a janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(675, 650);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}

}
