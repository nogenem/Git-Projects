package JButtons;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class sButton extends JButton {
	
	public int id, pntsReq, maxPnts, skillIdReq, 
			   enCost, staCost, cd, modHPmax, modSTAmax;
	public String name, description, effects;
	public boolean isMastery, isSkill;
	private JLabel myText;
	
	//Hidden Buttons
	public sButton(){}
	
	//Mastery Buttons
	public sButton(int id, String name, String description, int pntsReq){
		this.id = id;
		this.name = name;
		this.description = description;
		this.pntsReq = pntsReq;
		this.isMastery = true;
	}
	
	//Skill Buttons
	public sButton(int id, String name, String description, int pntsReq, int maxPnts, int skillIdReq, 
				   int enCost, int staCost, int cd, String effects, int modHPmax, int modSTAmax){
		this.id = id;
		this.name = name;
		this.description = description;
		this.pntsReq = pntsReq;
		this.maxPnts = maxPnts;
		this.skillIdReq = skillIdReq;
		this.enCost = enCost;
		this.staCost = staCost;
		this.cd = cd;
		this.effects = effects;
		this.modHPmax = modHPmax;
		this.modSTAmax = modSTAmax;
		this.isSkill = true;
	}
	
	public void copyInfo(sButton but){
		this.id = but.id;
		this.name = but.name;
		this.description = but.description;
		this.pntsReq = but.pntsReq;
		if(but.isSkill){
			this.maxPnts = but.maxPnts;
			this.skillIdReq = but.skillIdReq;
			this.enCost = but.enCost;
			this.staCost = but.staCost;
			this.cd = but.cd;
			this.effects = but.effects;
			this.modHPmax = but.modHPmax;
			this.modSTAmax = but.modSTAmax;
		}
	}
	
	public void initLabel(){
		this.setLayout(null);
		this.myText = new JLabel();
		this.myText.setBounds(20,23,46,14);
		this.myText.setOpaque(true);
		this.myText.setBackground(Color.BLACK);
		this.myText.setForeground(Color.GREEN);
		this.myText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.add(myText);
	}
	
	public String getLblText(){
		if(myText == null){initLabel();}
		return myText.getText();
	}
	
	public void setLblText(String text){
		if(myText == null){initLabel();}
		myText.setText(text);
	}
	
	public int getCurrentPnts(){
		return Integer.parseInt(this.getLblText().substring(0, 1));
	}
}
