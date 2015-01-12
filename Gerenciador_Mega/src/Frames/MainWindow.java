package Frames;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import LibMega.MegaHandler2;


//https://mega.co.nz/#!QNQVlbJb!MzrXoXAeoOyMomAw6_BuJytA7GlwzkhSqHGyobskvQ8 [para testes]
@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener {

	private JPanel contentPane, listContainer;
	private JLabel lblDir;
	private JButton btnChooseDir, btnAddLinks, btnBeginDown;
	private JScrollPane scrollPane;
	private MegaHandler2 mh;
	private String dirAtual = System.getProperty("user.home")+"\\Downloads";
	private int downAtual = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Gerenciador de Downloads MEGA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnChooseDir = new JButton("Choose Dir");
		btnChooseDir.setMargin(new Insets(0, 0, 0, 0));
		btnChooseDir.addActionListener(this);
		btnChooseDir.setBounds(10, 299, 89, 23);
		contentPane.add(btnChooseDir);
		
		lblDir = new JLabel(dirAtual);
		lblDir.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblDir.setBounds(109, 299, 312, 23);
		contentPane.add(lblDir);
		
		btnAddLinks = new JButton("Add Links");
		btnAddLinks.setMargin(new Insets(0, 0, 0, 0));
		btnAddLinks.setBounds(10, 11, 89, 23);
		btnAddLinks.addActionListener(this);
		contentPane.add(btnAddLinks);
		
		listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane(listContainer);
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144)));
		scrollPane.setBounds(10, 45, 483, 240);
		contentPane.add(scrollPane);
		
		btnBeginDown = new JButton("Begin Down");
		btnBeginDown.setMargin(new Insets(0, 0, 0, 0));
		btnBeginDown.setBounds(109, 11, 89, 23);
		btnBeginDown.addActionListener(this);
		contentPane.add(btnBeginDown);
	}

	public void getLinks(String[] links){
		linkPanel newPanel;
		for (String string : links) {
			if(!string.contains("mega.co.nz")){continue;}
			newPanel = new linkPanel(string);
			newPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
			listContainer.add(newPanel);
			listContainer.revalidate();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Choose Dir")){
			JFileChooser arquivo = new JFileChooser();  
			arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnVal = arquivo.showDialog(this, null);  
			if (returnVal == JFileChooser.APPROVE_OPTION) { 
				dirAtual = arquivo.getSelectedFile().getAbsolutePath();
				lblDir.setText(dirAtual);
			}
		}else if(e.getActionCommand().equals("Add Links")){
			new AddLinks(this).setVisible(true);
		}else if(e.getActionCommand().equals("Begin Down")){
			executeDownload();
		}
	}
	
	public void executeDownload(){
		if((downAtual == 0 && listContainer.getComponentCount() == 0) || 
			downAtual > (listContainer.getComponentCount()-1)){return;}
		
		final linkPanel panel = (linkPanel) listContainer.getComponent(downAtual++); 
		try {
			mh = new MegaHandler2(panel.getText(), dirAtual);
			mh.addPropertyChangeListener(new PropertyListener(this, panel));
			mh.execute();
		} catch (Exception e1) {
			e1.printStackTrace();
			panel.setText("Fail!");
		}
	}

	
	public class PropertyListener implements PropertyChangeListener {
		
		private linkPanel panel;
		private MainWindow main;
		
		public PropertyListener(MainWindow main, linkPanel panel){
			this.panel = panel;
			this.main = main;
		}
		
		@SuppressWarnings("incomplete-switch")
		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			switch (evt.getPropertyName()) {
				case "progress":
					panel.updateProgress((Integer)evt.getNewValue());
					break;
				case "state":
			          switch ((StateValue) evt.getNewValue()) {
			          	case DONE:
			          		panel.setText("Sucess!");
			          		main.executeDownload();
			          		break;
			          }
			}
		}
	}

}
