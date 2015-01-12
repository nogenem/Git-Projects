package JPanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import JButtons.abButton;
import Persistencia.ClericDao;

@SuppressWarnings({ "unused", "serial" })
public class ClericPanel extends abPanel implements MouseListener {
	
	private JPanel SkillsPanel, MasteryPanel;
	private OptionsPanel optionsPanel;
	private dAtualLvlPanel dAtual;
	private dNextLvlPanel dNext;	
	private ClericDao dao;
	
	private ArrayList<abButton> skillBtnList, masteryBtnList;
	private String skillsImgsPath, masterysImgsPath;
	private int[] ignoredSkillButtons = {1,6,8,9,13,16,18,20,28,31,32,36,38,39,40};
	private int[] ignoredMasteryButtons = {3,7,11,13};
	
	public ClericPanel(){}	
	public ClericPanel(OptionsPanel optionsPanel, dAtualLvlPanel dAtual, dNextLvlPanel dNext){
		this.optionsPanel = optionsPanel;
		this.dAtual = dAtual;
		this.dNext = dNext;
		
		this.dao = new ClericDao();
		this.skillBtnList = new ArrayList<>();
		this.masteryBtnList = new ArrayList<>();
		this.skillsImgsPath = "src\\JButtons\\Imgs\\Cleric\\Skills\\";
		this.masterysImgsPath = "src\\JButtons\\Imgs\\Cleric\\Mastery\\";
		
		setLayout(null);
		
		//Panels
		SkillsPanel = new JPanel();
		SkillsPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SkillsPanel.setBounds(10, 30, 346, 350);
		SkillsPanel.setLayout(null);
		add(SkillsPanel);
		
		MasteryPanel = new JPanel();
		MasteryPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		MasteryPanel.setBounds(366, 30, 116, 350);
		MasteryPanel.setLayout(null);
		add(MasteryPanel);
		
		//Labels
		JLabel lblActivePassive = new JLabel("Active / Passive");
		lblActivePassive.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblActivePassive.setFont(new Font("Andalus", Font.BOLD, 14));
		lblActivePassive.setHorizontalAlignment(SwingConstants.CENTER);
		lblActivePassive.setBounds(10, 11, 346, 14);
		add(lblActivePassive);

		JLabel lblMastery = new JLabel("Mastery");
		lblMastery.setHorizontalAlignment(SwingConstants.CENTER);
		lblMastery.setFont(new Font("Andalus", Font.BOLD, 14));
		lblMastery.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblMastery.setBounds(366, 11, 116, 14);
		add(lblMastery);
		
		//Separators  
		createSeparators(SkillsPanel, 346, 2);
		createSeparators(MasteryPanel, 132, 2);
		
		//Buttons
		createButtons(SkillsPanel, 45, 6, skillBtnList);
		adjustSkillButtons();
		createButtons(MasteryPanel, 14, 2, masteryBtnList);
		adjustMasteryButtons();
	}
	
	public void createButtons(JPanel toPanel, int qnt, int buttonsPerLine, ArrayList<abButton> list){
		abButton button;
		ArrayList<abButton> tmp;
		String preFix;
		int x = -48, y = 8;
		
		for(int i = 0; i<qnt; i++){
			x += 58; 
			if(i != 0 && i%buttonsPerLine == 0){
				x = 10;
				y += 50;
			}
			
			if(qnt != 14)
				tmp = dao.getSkillBtnById(i);
			else
				tmp = dao.getMasteryBtnById(i);
			
			if(tmp == null || tmp.size() == 0){
				button = new abButton();
				button.setLblText("0/5");
			}else{
				button = tmp.get(0);
				button.addMouseListener(this);
				if(qnt != 14){ //skill
					preFix = button.req == 0 ? "_on" : "_off";
					button.setIcon(new ImageIcon(skillsImgsPath + i + preFix + ".png"));
					button.setLblText("0/"+button.max_pnts);
				}else{
					button.setIcon(new ImageIcon(masterysImgsPath + i + "_off.png"));	
				}
			}
			
			button.setBounds(x, y, 36, 36); 
			button.setMargin(new Insets(0, 0, 0, 0));
			
			toPanel.add(button);
			list.add(button);
		}
	}
	
	public void adjustSkillButtons(){
		for(int i = 0; i<ignoredSkillButtons.length; i++)
			skillBtnList.get(ignoredSkillButtons[i]).setVisible(false);
	}
	
	public void adjustIcons(){
		abButton but;
		String preFix;
		int atualPnts = optionsPanel.getSpentPoints();
		
		for(int i = 0; i<skillBtnList.size(); i++){
			but = skillBtnList.get(i);
			if(but.isVisible()){
				preFix = but.req <= atualPnts ? "_on" : "_off";
				but.setIcon(new ImageIcon(skillsImgsPath + i + preFix + ".png"));
			}
		}
		for(int i = 0; i<masteryBtnList.size(); i++){
			but = masteryBtnList.get(i);
			if(but.isVisible()){
				preFix = but.req <= atualPnts ? "_on" : "_off";
				but.setIcon(new ImageIcon(masterysImgsPath + i + preFix + ".png"));
			}
		}
	}
	
	public void adjustMasteryButtons(){
		for(int i = 0; i<ignoredMasteryButtons.length; i++){
			masteryBtnList.get(ignoredMasteryButtons[i]).setVisible(false);
			JButton button = masteryBtnList.get(ignoredMasteryButtons[i]-1);
			button.setBounds(button.getX()+30, button.getY(), button.getWidth(), button.getHeight());
		}
	}
	
	public void resetTree(){
		int count = 0;
		ArrayList<abButton> tmp;
		abButton but;
		
		for( int i = 0; i<skillBtnList.size(); i++ ){
			but = skillBtnList.get(i);
			count += but.getCurrentPnts();//pega qnts pnts foram gastos na skill 'i'
			tmp = dao.getSkillBtnById(i);//pega o botao 'i'
			
			if(tmp != null && tmp.size() > 0){
				but.copyInfo(tmp.get(0));
				but.setLblText("0/"+ tmp.get(0).max_pnts);//zera os pnts gastos
			}else
				skillBtnList.get(i).setLblText("0/5");//zera os pnts gastos
		}
		
		optionsPanel.removePnt(count);
	}
	
	public boolean canRemovePoint(abButton but){
		int atual = optionsPanel.getSpentPoints()-1;
		
		if (atual == 0)
			return true;
		
		for (abButton button : skillBtnList){
			if(button.isVisible() && button.req == atual && !button.name.equals(but.name))
				if(button.getCurrentPnts() > 0)
					return false;
		}
		return true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		abButton button = (abButton) e.getSource();
		
		if(button.isMastery)
			return;
		
		int current = button.getCurrentPnts();
		int max = button.max_pnts;
		
		ArrayList<abButton> list = dao.getSkillBtnById(button.id);
		
		if(e.getButton() == e.BUTTON1){
			if(current < list.size() && optionsPanel.getSpentPoints() < list.get(current).req)
				return;
			
			++current;
			if(current <= max && optionsPanel.addPnt(1)){
				if(current <= list.size())
					button.copyInfo(list.get(current-1));
				button.setLblText(current +"/"+ max);
				if(optionsPanel.getSpentPoints() % 5 == 0)
					adjustIcons();
			}
		}else if(e.getButton() == e.BUTTON3){
			if(current == 0)
				return;
			
			--current;
			if(current >= 0 && canRemovePoint(button) && optionsPanel.removePnt(1)){
				if(current <= list.size())
					button.copyInfo(list.get(current-1 >= 0 ? current-1 : current));
				button.setLblText(current +"/"+ max);
				if( (optionsPanel.getSpentPoints()+1) % 5 == 0)
					adjustIcons();
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		abButton button = (abButton) e.getSource();
		
		ArrayList<abButton> list;
		
		if(button.isSkill)
			list = dao.getSkillBtnById(button.id);
		else
			list = dao.getMasteryBtnById(button.id);
		
		if(button != null){
			dAtual.getLblName().setText(button.name);
			dAtual.getEditorPane().setText("Requires: " + button.req +" pnts.");
			
			if(button.isSkill){
				int current = Integer.parseInt(button.getLblText().substring(0, 1))-1;
				if(current != -1 && current+1 < list.size()){
					dNext.getLblName().setText(list.get(current+1).name);
					dNext.getEditorPane().setText("Requires: " + list.get(current+1).req +" pnts.");
				}
			}
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		abButton button = (abButton) e.getSource();
		
		if(button != null){
			dAtual.getLblName().setText("");
			dAtual.getEditorPane().setText("");
			
			dNext.getLblName().setText("");
			dNext.getEditorPane().setText("");
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
}
