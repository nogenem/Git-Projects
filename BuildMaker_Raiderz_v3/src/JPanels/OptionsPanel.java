package JPanels;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class OptionsPanel extends abPanel implements ActionListener {
	
	private JTabbedPane classesTabs;
	private JLabel SpentPoints, RemainingPoints;
	private int MaxPoints;
	
	public OptionsPanel(){
		MaxPoints = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual sera o level maximo da calculadora?"));//40
		
		setLayout(null);
		
		//JLabels
		JLabel lblTotal = new JLabel("Total points spent:");
		lblTotal.setBounds(10, 144, 111, 14);
		add(lblTotal);
		
		JLabel lblRemaining = new JLabel("Points remaining:");
		lblRemaining.setBounds(10, 169, 111, 14);
		add(lblRemaining);
		
		SpentPoints = new JLabel("0");
		SpentPoints.setBounds(126, 144, 46, 14);
		add(SpentPoints);
		
		RemainingPoints = new JLabel(""+MaxPoints);
		RemainingPoints.setBounds(126, 169, 46, 14);
		add(RemainingPoints);
		
		//JSeparators
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 131, 190, 2);
		add(separator);
		
		separator = new JSeparator();
		separator.setBounds(0, 194, 190, 2);
		add(separator);
		
		//JButtons
		JButton btnResetTree = new JButton("Reset Tree");
		btnResetTree.setMargin(new Insets(0, 0, 0, 0));
		btnResetTree.setBounds(32, 207, 89, 23);
		btnResetTree.addActionListener(this);
		add(btnResetTree);
		
		JButton btnResetAll = new JButton("Reset All");
		btnResetAll.setMargin(new Insets(0, 0, 0, 0));
		btnResetAll.setBounds(32, 241, 89, 23);
		btnResetAll.addActionListener(this);
		add(btnResetAll);
	}	
	
	public void setClassesTabs(JTabbedPane c){
		this.classesTabs = c;
	}
	
	public int getSpentPoints(){
		return Integer.parseInt(SpentPoints.getText());
	}
	
	public boolean addPnt(int amount){
		int current = Integer.parseInt(SpentPoints.getText());
		int remaining = Integer.parseInt(RemainingPoints.getText());
		
		remaining -= amount;
		current += amount;
		if(remaining >= 0){
			SpentPoints.setText( ""+ current );
			RemainingPoints.setText( ""+ remaining );
			return true;
		}
		return false;
	}
	
	public boolean removePnt(int amount){
		int current = Integer.parseInt(SpentPoints.getText());
		int remaining = Integer.parseInt(RemainingPoints.getText());
		
		remaining += amount;
		current -= amount;
		if(remaining <= MaxPoints){
			SpentPoints.setText( ""+ current );
			RemainingPoints.setText( ""+ remaining );
			return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getActionCommand().equals("Reset Tree")){
			abPanel panel = (abPanel) classesTabs.getSelectedComponent();
			panel.resetTree();
		}else if( e.getActionCommand().equals("Reset All")){
			/*Component[] allPanels = classesTabs.getComponents();
			for( int i = 0; i<allPanels.length; i++){
				abPanel panel = (abPanel) allPanels[i];
				if(panel != null)
					panel.resetTree();
			}*/
		}
	}
}
