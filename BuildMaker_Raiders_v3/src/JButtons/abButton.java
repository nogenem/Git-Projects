package JButtons;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class abButton extends JButton {
	
	public int id, max_pnts, req;
	public String name, descricao;
	public boolean isMastery, isSkill;
	protected JLabel myText;
	
	public abButton(){}
	public abButton(int id, String name, String descricao, int req){ //mastery
		this.id = id;
		this.name = name;
		this.descricao = descricao;
		this.req = req;
		this.isMastery = true;
	}
	public abButton(int id, String name, String descricao, int max_pnts, int req){ //skill
		this.id = id;
		this.name = name;
		this.descricao = descricao;
		this.max_pnts = max_pnts;
		this.req = req;
		this.isSkill = true;
	}
	
	public void copyInfo(abButton but){
		this.id = but.id;
		this.name = but.name;
		this.descricao = but.descricao;
		if(but.isSkill)
			this.max_pnts = but.max_pnts;
		this.req = but.req;
		//this.myText = but.myText;
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
