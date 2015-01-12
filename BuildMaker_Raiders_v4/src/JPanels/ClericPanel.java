package JPanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import JButtons.sButton;
import Persistencia.ClericDao;

@SuppressWarnings({ "unused", "serial" })
public class ClericPanel extends abPanel implements MouseListener {
	
	private ClericDao dao;
	private int[] ignoredSkillButtons = {1,6,8,9,13,16,18,20,28,31,32,36,38,39,40};
	private int[] ignoredMasteryButtons = {3,7,11,13};
	
	//o resto dos atributos vem da super classe, abPanel...
	
	public ClericPanel(){}
	public ClericPanel(OptionsPanel optionsPanel, dAtualLvlPanel dAtual, dNextLvlPanel dNext){
		super(optionsPanel, dAtual, dNext);
		
		this.dao = new ClericDao();
		this.skillBtnList = new ArrayList<>();
		this.masteryBtnList = new ArrayList<>();
		this.skillsImgsPath = "Data\\Imgs\\Cleric\\Skills\\";
		this.masterysImgsPath = "Data\\Imgs\\Cleric\\Mastery\\";
		
		setLayout(null);
		
		//Panels
		this.SkillsPanel = new JPanel();
		this.SkillsPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.SkillsPanel.setBounds(10, 30, 346, 350);
		this.SkillsPanel.setLayout(null);
		add(SkillsPanel);

		this.MasteryPanel = new JPanel();
		this.MasteryPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.MasteryPanel.setBounds(366, 30, 116, 350);
		this.MasteryPanel.setLayout(null);
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
	
	public void createButtons(JPanel toPanel, int qnt, int buttonsPerLine, ArrayList<sButton> list){
		sButton button;
		ArrayList<sButton> tmp;
		String preFix;
		int x = -48, y = 8;
		
		for(int i = 0; i<qnt; i++){
			x += 58; 
			if(i != 0 && i%buttonsPerLine == 0){
				x = 10;
				y += 50;
			}
			
			button = new sButton();
			button.setLblText("0/5");
			button.addMouseListener(this);
			
			
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
	
	public void adjustMasteryButtons(){
		for(int i = 0; i<ignoredMasteryButtons.length; i++){
			masteryBtnList.get(ignoredMasteryButtons[i]).setVisible(false);
			JButton button = masteryBtnList.get(ignoredMasteryButtons[i]-1);
			button.setBounds(button.getX()+30, button.getY(), button.getWidth(), button.getHeight());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
}
