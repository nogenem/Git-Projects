package JPanels;

import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class dNextLvlPanel extends abPanel {
	
	private JLabel lblName;
	private JEditorPane editorPane;
	
	public dNextLvlPanel(){
		setLayout(null);

		//JLabels
		JLabel lblSkillName = new JLabel("Skill Name: ");
		lblSkillName.setBounds(10, 11, 75, 14);
		add(lblSkillName);

		lblName = new JLabel("");
		lblName.setBounds(78, 7, 230, 24);
		lblName.setFont(new Font("Andalus", Font.BOLD, 14));
		add(lblName);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o: ");
		lblDescrio.setBounds(10, 42, 89, 14);
		add(lblDescrio);
		
		//Panes
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 298, 114);
		add(scrollPane);
		
		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
	}
	
	public JEditorPane getEditorPane(){
		return editorPane;
	}
	
	public JLabel getLblName(){
		return lblName;
	}
}
