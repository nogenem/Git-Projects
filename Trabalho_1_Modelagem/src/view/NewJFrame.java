package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

import controller.Simulator;
import model.Statistics;
import model.distribuitions.Distribution;
import model.events.Event;

/**
 * Classe que gera a interface do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
@SuppressWarnings("serial")
public class NewJFrame extends javax.swing.JFrame {
	
	/**
	 * Variável usada para validar as chances, TECs e TS.
	 */
	private boolean[] validate = {
			true,false,false,false
	};
	/**
	 * Variável usada para saber se a simulação 
	 *  atual ja terminou.   
	 */
	private boolean currentSimFinished;
	
    private double chance_C1C1, chance_C1C2, chance_C1FA,
            chance_C2C2, chance_C2C1, chance_C2FA;

    private char c1_TEC_Distribuicao, c2_TEC_Distribuicao,
		duracaoDeChamadas_Distribuicao;
	private double[] distrTEC_C1_values, distrTEC_C2_values,
		distrTS_values;
	
	/**
	 * Modelo customizado usado na JTable.
	 */
	private EventsModel eventsModel;

	/**
	 * Estatística atual sendo mostrada ao usuário.
	 */
    int estatisticaAtual;

    /**
     * Thread da simulação.
     */
    private Simulator sim;

    public NewJFrame() {
        c1_TEC_Distribuicao = 'e';
        c2_TEC_Distribuicao = 'e';
        duracaoDeChamadas_Distribuicao = 'n';
        
        distrTEC_C1_values = new double[1];
        distrTEC_C2_values = new double[1];
        distrTS_values = new double[3];
        
        currentSimFinished = false;
        estatisticaAtual = 0;
        sim = null;
        
        eventsModel = new EventsModel();
       	

        setTitle("Simulador");
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Função responsavel por inicializar todos os componentes da interface.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jSpinner10 = new javax.swing.JSpinner();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbCallDuration = new javax.swing.JComboBox();
        tfCallDuration = new javax.swing.JTextField();
        tfTEC_C2 = new javax.swing.JTextField();
        cbTEC_C2 = new javax.swing.JComboBox();
        tfTEC_C1 = new javax.swing.JTextField();
        cbTEC_C1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        spinnerChannelsC1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        spinnerChannelsC2 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnPlay.setToolTipText("Iniciar/Pausar Simulação");
        btnEndSim = new javax.swing.JButton();
        btnEndSim.setToolTipText("Cancelar simulação");
        btnEndSim.setEnabled(false);
        btnNext = new javax.swing.JButton();
        btnNext.setToolTipText("Avançar estatistica");
        btnNext.setEnabled(false);
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        SpinnerSimTime = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        tfSeed = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel17.setHorizontalAlignment(SwingConstants.CENTER);
        tfC1C1 = new javax.swing.JTextField();
        tfC1C1.setToolTipText("Porcentagem de chamadas do tipo C1C1");
        tfC2C2 = new javax.swing.JTextField();
        tfC2C2.setToolTipText("Porcentagem de chamadas do tipo C2C2");
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel18.setHorizontalAlignment(SwingConstants.CENTER);
        tfC1C2 = new javax.swing.JTextField();
        tfC1C2.setToolTipText("Porcentagem de chamadas do tipo C1C2");
        tfC2C1 = new javax.swing.JTextField();
        tfC2C1.setToolTipText("Porcentagem de chamadas do tipo C2C1");
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel20.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel22.setHorizontalAlignment(SwingConstants.CENTER);
        tfC1FA = new javax.swing.JTextField();
        tfC1FA.setToolTipText("Porcentagem de chamadas do tipo C1FA");
        tfC2FA = new javax.swing.JTextField();
        tfC2FA.setToolTipText("Porcentagem de chamadas do tipo C2FA");
        jLabel10 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel23.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel11 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel24.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lbMinRateC1 = new javax.swing.JLabel();
        lbMinRateC1.setHorizontalAlignment(SwingConstants.LEFT);
        lbAveRateC1 = new javax.swing.JLabel();
        lbAveRateC1.setHorizontalAlignment(SwingConstants.LEFT);
        lbMaxRateC1 = new javax.swing.JLabel();
        lbMaxRateC1.setHorizontalAlignment(SwingConstants.LEFT);
        lbMinRateC2 = new javax.swing.JLabel();
        lbMinRateC2.setHorizontalAlignment(SwingConstants.LEFT);
        lbAveRateC2 = new javax.swing.JLabel();
        lbAveRateC2.setHorizontalAlignment(SwingConstants.LEFT);
        lbMaxRateC2 = new javax.swing.JLabel();
        lbMaxRateC2.setHorizontalAlignment(SwingConstants.LEFT);
        lbMinCallsOnSystem = new javax.swing.JLabel();
        lbMinCallsOnSystem.setHorizontalAlignment(SwingConstants.LEFT);
        lbAveCallsOnSystem = new javax.swing.JLabel();
        lbAveCallsOnSystem.setHorizontalAlignment(SwingConstants.LEFT);
        lbMaxCallsOnSystem = new javax.swing.JLabel();
        lbMaxCallsOnSystem.setHorizontalAlignment(SwingConstants.LEFT);
        lbNumCalls = new javax.swing.JLabel();
        lbNumCalls.setHorizontalAlignment(SwingConstants.RIGHT);
        lbLostCallsC1 = new javax.swing.JLabel();
        lbLostCallsC1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbLostCallsC2 = new javax.swing.JLabel();
        lbLostCallsC2.setHorizontalAlignment(SwingConstants.RIGHT);
        lbCompletedCalls = new javax.swing.JLabel();
        lbCompletedCalls.setHorizontalAlignment(SwingConstants.RIGHT);
        jSeparator6 = new javax.swing.JSeparator();
        btnPrevious = new javax.swing.JButton();
        btnPrevious.setToolTipText("Voltar estatistica");
        btnPrevious.setEnabled(false);
        lbSimTime = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00%"))));
        jFormattedTextField3.setText("0.00%");

        jFormattedTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00%"))));
        jFormattedTextField5.setText("0.00%");

        jFormattedTextField9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00%"))));
        jFormattedTextField9.setText("0.00%");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tempo Entre Chegadas C1");

        jLabel2.setText("Tempo Entre Chegadas C2");

        jLabel3.setText("Duração de Chamadas");

        cbCallDuration.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Normal", "Uniforme", "Exponencial", "Triangular", "Constante"}));
        cbCallDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxCallDurationDistributionActionPerformed(evt);
            }
        });

        tfCallDuration.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfCallDuration.setText("a,b");
        tfCallDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryCallDurationActionPerformed(evt);
            }
        });
        tfCallDuration.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                entryCallDurationActionPerformed(null);
            }
        });

        tfTEC_C2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTEC_C2.setText("λ");
        tfTEC_C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryTEC_C2ActionPerformed(evt);
            }
        });
        tfTEC_C2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryTEC_C2ActionPerformed(null);
            }
        });

        cbTEC_C2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Exponencial", "Constante"}));
        cbTEC_C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxDistribution_C2ActionPerformed(evt);
            }
        });

        tfTEC_C1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTEC_C1.setText("λ");
        tfTEC_C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryTEC_C1ActionPerformed(evt);
            }
        });
        tfTEC_C1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryTEC_C1ActionPerformed(null);
            }
        });

        cbTEC_C1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Exponencial", "Constante"}));
        cbTEC_C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxDistribution_C1ActionPerformed(evt);
            }
        });
        
        tfSeed.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent evt) {
        		entrySeedActionPerformed(null);
        	}
        });
        tfSeed.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		entrySeedActionPerformed(evt);
        	}
        });

        jLabel4.setText("Canais da Célula 1");

        spinnerChannelsC1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel5.setText("Canais da Célula 2");

        spinnerChannelsC2.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel6.setText("%");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel12.setText("Tempo de Simulação:");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel13.setText("Estatísticas:");

        btnPlay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btnPlay.setText(">");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPlayActionPerformed(evt);
            }
        });

        btnEndSim.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btnEndSim.setText("X");
        btnEndSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelSimulationActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btnNext.setText("->");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel15.setText("Tempo Simulação:");

        SpinnerSimTime.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));

        jLabel16.setText("Semente:");

        tfSeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSeed.setText("0");

        jLabel17.setText("C1 -> C1");

        tfC1C1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfC1C1.setText("50.0");
        tfC1C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryC1C1ActionPerformed(evt);
            }
        });
        tfC1C1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryC1C1ActionPerformed(null);
            }
        });

        tfC2C2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfC2C2.setText("50.0");
        tfC2C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryC2C2ActionPerformed(evt);
            }
        });
        tfC2C2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryC2C2ActionPerformed(null);
            }
        });

        jLabel7.setText("%");

        jLabel18.setText("C2 -> C2");

        tfC1C2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfC1C2.setText("30.0");
        tfC1C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryC1C2ActionPerformed(evt);
            }
        });
        tfC1C2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryC1C2ActionPerformed(null);
            }
        });

        tfC2C1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfC2C1.setText("30.0");
        tfC2C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryC2C1ActionPerformed(evt);
            }
        });
        tfC2C1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryC2C1ActionPerformed(null);
            }
        });

        jLabel8.setText("%");

        jLabel20.setText("C2 -> C1");

        jLabel9.setText("%");

        jLabel22.setText("C1 -> C2");

        tfC1FA.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfC1FA.setText("20.0");
        tfC1FA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryC1FAActionPerformed(evt);
            }
        });
        tfC1FA.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryC1FAActionPerformed(null);
            }
        });

        tfC2FA.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfC2FA.setText("20.0");
        tfC2FA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryC2FAActionPerformed(evt);
            }
        });
        tfC2FA.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                entryC2FAActionPerformed(null);
            }
        });

        jLabel10.setText("%");

        jLabel23.setText("C2 -> FA");

        jLabel11.setText("%");

        jLabel24.setText("C1 -> FA");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Ok!");

        jLabel27.setText("Número de Chamadas");

        jLabel28.setText("Taxa de Ocupação C1");

        jLabel29.setText("Duração das Chamadas");

        jLabel30.setText("Chamadas Completadas");

        jLabel31.setText("Chamadas Perdidas C1");

        jLabel32.setText("Chamadas Perdidas C2");

        jLabel33.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel33.setText("mínimo");

        jLabel34.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel34.setText("média");

        jLabel35.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel35.setText("máximo");

        jLabel36.setText("Taxa de Ocupação C2");

        jLabel37.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel37.setText("total");

        lbMinRateC1.setText("--%");

        lbAveRateC1.setText("--%");

        lbMaxRateC1.setText("--%");

        lbMinRateC2.setText("--%");

        lbAveRateC2.setText("--%");

        lbMaxRateC2.setText("--%");

        lbMinCallsOnSystem.setText("--");

        lbAveCallsOnSystem.setText("--");

        lbMaxCallsOnSystem.setText("--");

        lbNumCalls.setText("--");

        lbLostCallsC1.setText("--");

        lbLostCallsC2.setText("--");

        lbCompletedCalls.setText("--");

        btnPrevious.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btnPrevious.setText("<-");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });

        lbSimTime.setHorizontalAlignment(SwingConstants.CENTER);
        lbSimTime.setText("tempoAtual / tempoTotal");

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Ok!");

        JLabel lblSegs = new JLabel("Seg(s)");

        lblChamadasForaDa = new JLabel("Chamadas fora da área C1");

        lbOutOfAreaC1 = new JLabel("--");
        lbOutOfAreaC1.setHorizontalAlignment(SwingConstants.RIGHT);

        lblChamadasForaDa_1 = new JLabel("Chamadas fora da área C2");

        lbOutOfAreaC2 = new JLabel("--");
        lbOutOfAreaC2.setHorizontalAlignment(SwingConstants.RIGHT);

        lblNewLabel = new JLabel("Eventos:");
        lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        scrollPane = new JScrollPane();

        lblChamadasNoSistema = new JLabel("Chamadas no sistema");

        lbMinCallDuration = new JLabel("--");
        lbMinCallDuration.setHorizontalAlignment(SwingConstants.LEFT);

        lbAveCallDuration = new JLabel("--");
        lbAveCallDuration.setHorizontalAlignment(SwingConstants.LEFT);

        lbMaxCallDuration = new JLabel("--");
        lbMaxCallDuration.setHorizontalAlignment(SwingConstants.LEFT);
        
        sliderTick = new JSlider();
        sliderTick.setToolTipText("Velocidade da simulação (ms)");
        sliderTick.setPaintLabels(true);
        sliderTick.setPaintTicks(true);
        sliderTick.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent evt) {
        		sliderTickChanged(evt);
        	}
        });
        sliderTick.setMinorTickSpacing(100);
        sliderTick.setMajorTickSpacing(1000);
        sliderTick.setMaximum(3000);
        sliderTick.setValue(1000);
        
        table = new JTable(eventsModel);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        scrollPane.setViewportView(table);
        
        JLabel lblSegs_1 = new JLabel("Seg(s)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel5)
        								.addComponent(jLabel4))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(spinnerChannelsC1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        								.addComponent(spinnerChannelsC2, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        										.addComponent(jLabel17, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
        										.addComponent(tfC1C1))
        									.addGap(2)
        									.addComponent(jLabel6))
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(tfC1C2, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jLabel9))
        								.addComponent(jLabel22, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel24, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(tfC1FA, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jLabel11))
        								.addComponent(jLabel25, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
        							.addGap(16)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(tfC2FA, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jLabel10))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        										.addComponent(jLabel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        										.addComponent(tfC2C2, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
        									.addGap(2)
        									.addComponent(jLabel7))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        										.addComponent(jLabel23, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        										.addComponent(jLabel20, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        										.addComponent(tfC2C1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jLabel8))
        								.addComponent(jLabel52, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        					.addGap(6)
        					.addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(tfTEC_C1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cbTEC_C1, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jLabel2)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(tfTEC_C2, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cbTEC_C2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jLabel3)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(tfCallDuration, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel16))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(tfSeed)
        						.addComponent(cbCallDuration, 0, 123, Short.MAX_VALUE)))
        				.addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel15)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(SpinnerSimTime, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblSegs))
        				.addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(sliderTick, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        						.addComponent(btnEndSim, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(btnPrevious)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(btnNext))))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(12)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jLabel12)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lbSimTime, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lblSegs_1))
        						.addComponent(jLabel13)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(29)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        										.addComponent(lblChamadasForaDa)
        										.addComponent(lblChamadasForaDa_1))
        									.addGap(17)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(lbOutOfAreaC2, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
        										.addComponent(lbOutOfAreaC1, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(jLabel36)
        										.addComponent(jLabel28)
        										.addComponent(lblChamadasNoSistema)
        										.addComponent(jLabel29)
        										.addComponent(jLabel27)
        										.addComponent(jLabel31)
        										.addComponent(jLabel32)
        										.addComponent(jLabel30))
        									.addGap(30)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(lbCompletedCalls, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
        										.addComponent(lbLostCallsC2, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
        										.addComponent(lbLostCallsC1, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
        										.addGroup(layout.createSequentialGroup()
        											.addGroup(layout.createParallelGroup(Alignment.LEADING)
        												.addComponent(lbMinCallsOnSystem, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        												.addComponent(jLabel33, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        												.addComponent(lbMinRateC1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        												.addComponent(lbMinRateC2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        												.addComponent(lbMinCallDuration, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
        											.addGap(7)
        											.addGroup(layout.createParallelGroup(Alignment.LEADING)
        												.addGroup(layout.createSequentialGroup()
        													.addGroup(layout.createParallelGroup(Alignment.LEADING)
        														.addComponent(jLabel34, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        														.addComponent(lbAveRateC1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        														.addComponent(lbAveRateC2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        														.addComponent(lbAveCallsOnSystem, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
        													.addGap(7)
        													.addGroup(layout.createParallelGroup(Alignment.LEADING)
        														.addComponent(lbMaxRateC2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        														.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        															.addComponent(lbMaxCallsOnSystem, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        															.addComponent(lbMaxRateC1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
        														.addComponent(jLabel35, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
        												.addGroup(layout.createSequentialGroup()
        													.addComponent(lbAveCallDuration, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
        													.addGap(7)
        													.addComponent(lbMaxCallDuration, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))))
        										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        											.addComponent(jLabel37)
        											.addComponent(lbNumCalls, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))))))))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblNewLabel)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(21)
        							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(14)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(spinnerChannelsC1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel4))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(spinnerChannelsC2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel5))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        										.addGroup(layout.createSequentialGroup()
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(jLabel18)
        												.addComponent(jLabel17))
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(tfC2C2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        												.addComponent(jLabel7)))
        										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        											.addComponent(tfC1C1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        											.addComponent(jLabel6)))
        									.addGap(11)
        									.addComponent(jLabel20)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(tfC1C2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel9)
        										.addComponent(tfC2C1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel8))
        									.addPreferredGap(ComponentPlacement.UNRELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel24)
        										.addComponent(jLabel23)
        										.addComponent(jLabel37)))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel12)
        										.addComponent(lbSimTime)
        										.addComponent(lblSegs_1))
        									.addGap(18)
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        										.addGroup(layout.createSequentialGroup()
        											.addComponent(jLabel13)
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addComponent(jLabel28)
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addComponent(jLabel36)
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addComponent(lblChamadasNoSistema))
        										.addGroup(layout.createSequentialGroup()
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(jLabel33)
        												.addComponent(jLabel34)
        												.addComponent(jLabel35))
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(lbMinRateC1)
        												.addComponent(lbAveRateC1)
        												.addComponent(lbMaxRateC1))
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(lbMinRateC2)
        												.addComponent(lbAveRateC2)
        												.addComponent(lbMaxRateC2))
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(lbMinCallsOnSystem)
        												.addComponent(lbAveCallsOnSystem)))
        										.addComponent(lbMaxCallsOnSystem))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel29)
        										.addComponent(lbMinCallDuration)
        										.addComponent(lbAveCallDuration)
        										.addComponent(lbMaxCallDuration))))
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addGroup(layout.createSequentialGroup()
        											.addComponent(jLabel27)
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(jLabel31)
        												.addComponent(lbLostCallsC1)))
        										.addComponent(lbNumCalls))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel32)
        										.addComponent(lbLostCallsC2))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel30)
        										.addComponent(lbCompletedCalls)
        										.addComponent(jLabel1))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addGroup(layout.createSequentialGroup()
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(tfTEC_C1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        												.addComponent(cbTEC_C1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addComponent(jLabel2))
        										.addGroup(layout.createSequentialGroup()
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(lblChamadasForaDa)
        												.addComponent(lbOutOfAreaC1))
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        												.addComponent(lblChamadasForaDa_1)
        												.addComponent(lbOutOfAreaC2)))))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        											.addComponent(tfC1FA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        											.addComponent(jLabel11))
        										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        											.addComponent(tfC2FA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        											.addComponent(jLabel10)))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel52)
        										.addComponent(jLabel25))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(lblNewLabel)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(tfTEC_C2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(cbTEC_C2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jLabel3)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(tfCallDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(cbCallDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel16)
        										.addComponent(tfSeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel15)
        										.addComponent(SpinnerSimTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        										.addComponent(lblSegs))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(btnEndSim)
        										.addComponent(btnPrevious, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
        										.addComponent(btnPlay)
        										.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(sliderTick, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
        					.addGap(6))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(127)
        					.addComponent(jLabel22)))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().setLayout(layout);

        spinnerChannelsC1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Função responsavel por tratar a troca de distribuição para o TEC da C1.
     * 
     * @param evt
     */
    private void comboxDistribution_C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxDistribution_C1ActionPerformed
        // Tempo Entre Chegadas - Célula 1
        if (cbTEC_C1.getSelectedItem().equals("Exponencial")) {
            c1_TEC_Distribuicao = 'e';
            tfTEC_C1.setText("λ");
        } else if (cbTEC_C1.getSelectedItem().equals("Constante")) {
            c1_TEC_Distribuicao = 'c';
            tfTEC_C1.setText("c");
        }
        validate[1] = false;
    }//GEN-LAST:event_comboxDistribution_C1ActionPerformed
    
    /**
     * Função responsavel por tratar a troca de distribuição para o TEC da C2.
     * 
     * @param evt
     */
    private void comboxDistribution_C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxDistribution_C2ActionPerformed
        // Tempo Entre Chegadas - Célula 2 - 
        if (cbTEC_C2.getSelectedItem().equals("Exponencial")) {
            c2_TEC_Distribuicao = 'e';
            tfTEC_C2.setText("λ");
        } else if (cbTEC_C2.getSelectedItem().equals("Constante")) {
            c2_TEC_Distribuicao = 'c';
            tfTEC_C2.setText("c");
        }
        validate[2] = false;
    }//GEN-LAST:event_comboxDistribution_C2ActionPerformed
    
    /**
     * Função responsavel por tratar a troca de distribuição para o TS.
     * 
     * @param evt
     */
    private void comboxCallDurationDistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxCallDurationDistributionActionPerformed
        // Duração de Chamadas - Distribuição
        if (cbCallDuration.getSelectedItem().equals("Normal")) {
            duracaoDeChamadas_Distribuicao = 'n';
            tfCallDuration.setText("a,b");
        } else if (cbCallDuration.getSelectedItem().equals("Uniforme")) {
            duracaoDeChamadas_Distribuicao = 'u';
            tfCallDuration.setText("a,b");
        } else if (cbCallDuration.getSelectedItem().equals("Exponencial")) {
            duracaoDeChamadas_Distribuicao = 'e';
            tfCallDuration.setText("λ");
        } else if (cbCallDuration.getSelectedItem().equals("Triangular")) {
            duracaoDeChamadas_Distribuicao = 't';
            tfCallDuration.setText("a,b,c");
        } else if (cbCallDuration.getSelectedItem().equals("Constante")) {
            duracaoDeChamadas_Distribuicao = 'c';
            tfCallDuration.setText("c");
        } else {
            duracaoDeChamadas_Distribuicao = 'x';
            tfCallDuration.setText("ERRO!");
        }
        validate[3] = false;
    }//GEN-LAST:event_comboxCallDurationDistributionActionPerformed
    
    /**
     * Função responsavel por tratar os eventos de 'Play' e 'Pause' da simulação.
     * 
     * @param evt
     */
    private void buttonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPlayActionPerformed
        // Iniciar, Parar e Continuar Simulação
        if (btnPlay.getText() == ">") {
        	
        	// Se a simulação ja tinha terminado,
        	//  então começa uma nova simulação
        	if(currentSimFinished)
        		buttonCancelSimulationActionPerformed(null);
        	
            // Continuar a simulação
        	if (sim != null) {
                btnPlay.setText("||");
                sim.play();
                return;
            }
            
            // Checagem se os valores da interface estão corretos
    		for(int i = 0; i<validate.length; i++){
    			if(!validate[i]){
    				JOptionPane.showMessageDialog(this, "Algum dos campos contem um valor invalido.", 
    						"Erro", JOptionPane.ERROR_MESSAGE);
    				return;
    			}
    		}
            
    		// Iniciar a simulação
            setConfigsEnabled(false);
            
            btnPlay.setText("||");
            btnEndSim.setEnabled(true);
            btnNext.setEnabled(false);
            btnPrevious.setEnabled(false);
            
            int simTime = (Integer) SpinnerSimTime.getValue();
            int channelsC1 = (Integer) spinnerChannelsC1.getValue();
            int channelsC2 = (Integer) spinnerChannelsC2.getValue();
            long seed = Long.parseLong(tfSeed.getText());
            Distribution TSDistr = Distribution.getDistribution(duracaoDeChamadas_Distribuicao, distrTS_values);
            Distribution TECDistrC1 = Distribution.getDistribution(c1_TEC_Distribuicao, distrTEC_C1_values);
            Distribution TECDistrC2 = Distribution.getDistribution(c2_TEC_Distribuicao, distrTEC_C2_values);
            double[] callsPercentages = {chance_C1C1, chance_C1C2, chance_C1FA,
                chance_C2C2, chance_C2C1, chance_C2FA};
            int tick = sliderTick.getValue();

            sim = new Simulator(this, simTime, tick, channelsC1, channelsC2, seed,
                    TSDistr, TECDistrC1, TECDistrC2, callsPercentages);
            sim.start();

        } else if (btnPlay.getText() == "||") {
        	
        	// Pausar a simulação
            btnPlay.setText(">");
            btnEndSim.setEnabled(true);
            btnNext.setEnabled(true);
            btnPrevious.setEnabled(true);
            
            sim.pause();
        }

    }//GEN-LAST:event_buttonPlayActionPerformed
    
    /**
     * Função responsavel por tratar o evento de cancelar a simulação atual.
     * 
     * @param evt
     */
    private void buttonCancelSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelSimulationActionPerformed
        // Cancelar Simulação
        resetStatistics();

        setConfigsEnabled(true);
        btnPlay.setText(">");
        btnEndSim.setEnabled(false);
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);

        sim.destroy();
        sim = null;
        currentSimFinished = false;
    }//GEN-LAST:event_buttonCancelSimulationActionPerformed
    
    /**
     * Função responsavel por tratar o evento de avançar nas estatisticas da simulação.
     * 
     * @param evt
     */
    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        // Estatísticas Posteriores ou Próxima Estatística
        if(estatisticaAtual != sim.getStatistics().size()-1){
        	estatisticaAtual++;
        	this.loadStatistics(estatisticaAtual);
        }
    }//GEN-LAST:event_buttonNextActionPerformed
    
    /**
     * Função responsavel por tratar o evento de voltar nas estatisticas da simulação.
     * 
     * @param evt
     */
    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        // Estatísticas Anteriores
        if (estatisticaAtual > 0) {
            estatisticaAtual--;
            this.loadStatistics(estatisticaAtual);
        }
    }//GEN-LAST:event_buttonPreviousActionPerformed
    
    /**
     * Função responsável por validar o valor de TEC da C1.
     * 
     * @param evt
     */
    private void entryTEC_C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryTEC_C1ActionPerformed
        // Tempo Entre Chegadas - Célula 1 - Entrada
        try {
            distrTEC_C1_values[0] = Double.parseDouble(tfTEC_C1.getText());
            if(distrTEC_C1_values[0] < 1)
            	throw new NumberFormatException();
            validate[1] = true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor mal formatado da distribuição do TEC da C1.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            validate[1] = false;
        }
    }//GEN-LAST:event_entryTEC_C1ActionPerformed
    
    /**
     * Função responsável por validar o valor de TEC da C2.
     * 
     * @param evt
     */
    private void entryTEC_C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryTEC_C2ActionPerformed
        // Tempo Entre Chegadas - Célula 2 - Entrada
        try {
            distrTEC_C2_values[0] = Double.parseDouble(tfTEC_C2.getText());
            if(distrTEC_C2_values[0] < 1)
            	throw new NumberFormatException();
            validate[2] = true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor mal formatado da distribuição do TEC da C2.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            validate[2] = false;
        }
    }//GEN-LAST:event_entryTEC_C2ActionPerformed
    
    /**
     * Função responsável por validar o valor de TS.
     * 
     * @param evt
     */
    private void entryCallDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryCallDurationActionPerformed
        // Duração de Chamadas - Entrada
        try {
            String value = tfCallDuration.getText();
            if (value.contains(",")) {
                String[] values = value.split(",");
                for (int i = 0; i < values.length; i++) {
                    distrTS_values[i] = Double.parseDouble(values[i]);
                    if(distrTS_values[i] < 1)
                    	throw new NumberFormatException();
                }
            } else {
                distrTS_values[0] = Double.parseDouble(value);
                if(distrTS_values[0] < 1)
                	throw new NumberFormatException();
            }
            validate[3] = true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor mal formatado da distribuição da duração de chamadas.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            validate[3] = false;
        }
    }//GEN-LAST:event_entryCallDurationActionPerformed
    
    /**
     * Função responsável por validar o valor da seed.
     * 
     * @param evt
     */
    private void entrySeedActionPerformed(ActionEvent evt) {
		double seedTmp = 0;
    	try{
			seedTmp = Double.parseDouble(tfSeed.getText());
		}catch(NumberFormatException ex){
			
		}
    	tfSeed.setText(""+(long)seedTmp);
	}
    
    /**
     * Função responsável por validar o valor da porcentagem de 
     *  chamadas do tipo C1C1.
     * 
     * @param evt
     */
    private void entryC1C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryC1C1ActionPerformed
        // C1 -> C1
        getAndDefineChances();
    }//GEN-LAST:event_entryC1C1ActionPerformed
    
    /**
     * Função responsável por validar o valor da porcentagem de 
     *  chamadas do tipo C2C2.
     * 
     * @param evt
     */
    private void entryC2C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryC2C2ActionPerformed
        // C2 -> C2
        getAndDefineChances();
    }//GEN-LAST:event_entryC2C2ActionPerformed
    
    /**
     * Função responsável por validar o valor da porcentagem de 
     *  chamadas do tipo C1C2.
     * 
     * @param evt
     */
    private void entryC1C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryC1C2ActionPerformed
        // C1 -> C2
        getAndDefineChances();
    }//GEN-LAST:event_entryC1C2ActionPerformed
    
    /**
     * Função responsável por validar o valor da porcentagem de 
     *  chamadas do tipo C2C1.
     * 
     * @param evt
     */
    private void entryC2C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryC2C1ActionPerformed
        // C2 -> C1
        getAndDefineChances();
    }//GEN-LAST:event_entryC2C1ActionPerformed
    
    /**
     * Função responsável por validar o valor da porcentagem de 
     *  chamadas do tipo C1FA.
     * 
     * @param evt
     */
    private void entryC1FAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryC1FAActionPerformed
        // C1 -> FA
        getAndDefineChances();
    }//GEN-LAST:event_entryC1FAActionPerformed
    
    /**
     * Função responsável por validar o valor da porcentagem de 
     *  chamadas do tipo C2FA.
     * 
     * @param evt
     */
    private void entryC2FAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryC2FAActionPerformed
        // C2 -> FA
        getAndDefineChances();
    }//GEN-LAST:event_entryC2FAActionPerformed
    
    /**
     * Função responsável por modificar o valor de 'tick' da simulação. 
     * 
     * @param evt
     */
    private void sliderTickChanged(ChangeEvent evt) {
    	if(sim == null)
    		return;
    	
		JSlider source = (JSlider) evt.getSource();
		if(!source.getValueIsAdjusting()){
			int value = (int) source.getValue();
			sim.setTick(value);
		}
	}
    
    /**
     * Função 'wrapper' que chama outras duas funções.
     */
    private void getAndDefineChances() {
        getChances();
        defineChances();
    }
    
    /**
     * Função responsavel por converter os valores das porcentagens
     *  dos tipos de chamadas e fazer uma validação básica.
     */
    private void getChances() {
        try {
            chance_C1C1 = Double.parseDouble(tfC1C1.getText());
            chance_C1C1 = chance_C1C1 >= 0 ? chance_C1C1 : 0;
        } catch (NumberFormatException e) {
        }
        try{
            chance_C2C2 = Double.parseDouble(tfC2C2.getText());
            chance_C2C2 = chance_C2C2 >= 0 ? chance_C2C2 : 0;
        } catch (NumberFormatException e) {
        }
        try{
            chance_C1C2 = Double.parseDouble(tfC1C2.getText());
            chance_C1C2 = chance_C1C2 >= 0 ? chance_C1C2 : 0;
        } catch (NumberFormatException e) {
        }
        try{
            chance_C2C1 = Double.parseDouble(tfC2C1.getText());
            chance_C2C1 = chance_C2C1 >= 0 ? chance_C2C1 : 0;
        } catch (NumberFormatException e) {
        }
        try{
            chance_C1FA = Double.parseDouble(tfC1FA.getText());
            chance_C1FA = chance_C1FA >= 0 ? chance_C1FA : 0;
        } catch (NumberFormatException e) {
        }
        try{
            chance_C2FA = Double.parseDouble(tfC2FA.getText());
            chance_C2FA = chance_C2FA >= 0 ? chance_C2FA : 0;
        } catch (NumberFormatException e) {
        }
    }
    
    /**
     * Função responsavel por validar as porcentagens dos tipos
     *  de chamadas.
     */
    private void defineChances() {
        tfC1C1.setText("" + chance_C1C1);
        tfC2C2.setText("" + chance_C2C2);
        tfC1C2.setText("" + chance_C1C2);
        tfC2C1.setText("" + chance_C2C1);
        tfC1FA.setText("" + chance_C1FA);
        tfC2FA.setText("" + chance_C2FA);
        
        double soma_C1 = chance_C1C1 + chance_C1C2 + chance_C1FA;
        if (soma_C1 == 100) {
            jLabel25.setText("Ok!");
            validate[0] = true;
        } else {
            jLabel25.setText("" + ((int) soma_C1) + " != 100");
            validate[0] = false;
        }
        
        double soma_C2 = chance_C2C2 + chance_C2C1 + chance_C2FA;
        if (soma_C2 == 100) {
            jLabel52.setText("Ok!");
            validate[0] &= true;
        } else {
            jLabel52.setText("" + ((int) soma_C2) + " != 100");
            validate[0] = false;
        }
    }
    
    /**
     * Função responsavel por limpar os dados de todas 
     *  as 'Labels' da interface e também remover
     *  todos os eventos da 'JTable' da interface.
     */
    public void resetStatistics() {
        // Tempo de Simulação
        lbSimTime.setText("- / -");
        // Taxa de Ocupação C1
        lbMinRateC1.setText("--%");
        lbAveRateC1.setText("--%");
        lbMaxRateC1.setText("--%");
        // Taxa de Ocupação C2
        lbMinRateC2.setText("--%");
        lbAveRateC2.setText("--%");
        lbMaxRateC2.setText("--%");
        // Duração das Chamadas
        lbMinCallDuration.setText("--");
        lbAveCallDuration.setText("--");
        lbMaxCallDuration.setText("--");
        // Chamadas no sistema
        lbMinCallsOnSystem.setText("--");
        lbAveCallsOnSystem.setText("--");
        lbMaxCallsOnSystem.setText("--");
        // Número de Chamadas
        lbNumCalls.setText("--");
        // Chamadas Completadas
        lbCompletedCalls.setText("--");
        // Chamadas Perdidas C1
        lbLostCallsC1.setText("--");
        // Chamadas Perdidas C2
        lbLostCallsC2.setText("--");
        // Chamadas fora da area C1
        lbOutOfAreaC1.setText("--");
        // Chamadas fora da area C2
        lbOutOfAreaC2.setText("--");
        // Limpar Calendário de Eventos
        eventsModel.resetTable();
    }
    
    /**
     * Função responsavel por carregar os dados de uma estatística 
     *  nas 'Labels' da interface.
     * 
     * @param id		Qual das estatisticas da lista de estatísticas
     * 					 deve ser carregada.
     */
    public void loadStatistics(int id) {
        if (id < 0 || id >= sim.getStatistics().size()) {
            resetStatistics();
        } else {
            Statistics s = sim.getStatistics().get(id);
            // Tempo
            updateClock(s.tempo);
            // Taxa de Ocupação C1
            lbMinRateC1.setText(String.format("%.1f", s.ocupacao_min_C1) + "%");
            lbAveRateC1.setText(String.format("%.1f", s.ocupacao_med_C1) + "%");
            lbMaxRateC1.setText(String.format("%.1f", s.ocupacao_max_C1) + "%");
            // Taxa de Ocupação C2
            lbMinRateC2.setText(String.format("%.1f", s.ocupacao_min_C2) + "%");
            lbAveRateC2.setText(String.format("%.1f", s.ocupacao_med_C2) + "%");
            lbMaxRateC2.setText(String.format("%.1f", s.ocupacao_max_C2) + "%");
            // Duração das Chamadas
            lbMinCallDuration.setText(String.format("%.1f", s.duracao_min));
            lbAveCallDuration.setText(String.format("%.1f", s.duracao_med));
            lbMaxCallDuration.setText(String.format("%.1f", s.duracao_max));
            // Chamadas no sistema
            lbMinCallsOnSystem.setText("" +s.chamadasNoSistema_min);
            lbAveCallsOnSystem.setText(String.format("%.1f", s.chamadasNoSistema_med));
            lbMaxCallsOnSystem.setText("" +s.chamadasNoSistema_max);
            // Número de Chamadas
            lbNumCalls.setText("" + s.chamadasTotais);
            // Chamadas Completadas
            lbCompletedCalls.setText("" + s.chamadasCompletadas);
            // Chamadas Perdidas C1
            lbLostCallsC1.setText("" + s.chamadasPerdidas_C1);
            // Chamadas Perdidas C2
            lbLostCallsC2.setText("" + s.chamadasPerdidas_C2);
            // Chamadas Fora de Área C1
            lbOutOfAreaC1.setText("" + s.chamadasForaDeArea_C1);
            // Chamadas Fora de Área C2
            lbOutOfAreaC2.setText("" + s.chamadasForaDeArea_C2);
        }
    }
    
    /**
     * Função responsavel por habilitar/desabilitar todos os
     *  campos de entrada de dados da interface.
     * 
     * @param enabled		TRUE caso deseja-se habilitar os campos,
     * 						FALSE caso deseja-se desabilitar os campos.
     */
    private void setConfigsEnabled(boolean enabled) {
        spinnerChannelsC1.setEnabled(enabled);
        spinnerChannelsC2.setEnabled(enabled);
        tfC1C1.setEnabled(enabled);
        tfC1C2.setEnabled(enabled);
        tfC1FA.setEnabled(enabled);
        tfC2C2.setEnabled(enabled);
        tfC2C1.setEnabled(enabled);
        tfC2FA.setEnabled(enabled);
        tfTEC_C1.setEnabled(enabled);
        cbTEC_C1.setEnabled(enabled);
        tfTEC_C2.setEnabled(enabled);
        cbTEC_C2.setEnabled(enabled);
        tfCallDuration.setEnabled(enabled);
        cbCallDuration.setEnabled(enabled);
        tfSeed.setEnabled(enabled);
        SpinnerSimTime.setEnabled(enabled);
    }
    
    /**
     * Função que deve ser chamada ao término de uma simulação.
     */
    public void endOfSimulation(){
    	// Fazer alguma coisa...
    	btnPlay.setText(">");
    	btnNext.setEnabled(true);
    	btnPrevious.setEnabled(true);
    	currentSimFinished = true;
    	
    	JOptionPane.showMessageDialog(this, "Simulação terminada.");
    }
    
    /**
     * Carrega a ultima estatística na interface.
     */
    public void loadLatestStatistics(){
    	estatisticaAtual = sim.getStatistics().size()-1;
    	loadStatistics(estatisticaAtual);
    }
    
    /**
     * Atualiza a 'Label' do relógio da interface.
     * 
     * @param currentTime		Tempo atual do relógio.
     */
    private void updateClock(double currentTime){
    	String cTime = String.format("%.3f", currentTime);
    	lbSimTime.setText(cTime +" / "+ SpinnerSimTime.getValue());
    }
    
    /**
     * Adiciona um novo evento a 'JTable' da interface.
     * 	
     * @param evt		Evento a ser adicionado.
     */
    public void addEvent(Event evt){
    	this.eventsModel.addEvent(evt);
    	
    	// Códigos para ir dando scroll pro fim -nenhum funciona 100%...-
    	
    	/*Rectangle cellRect = table.getCellRect(table.getRowCount()-1, 0, true);
    	table.scrollRectToVisible(cellRect);*/
    	//table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, table.getColumnCount(), true));
    	/*int lastIndex = table.getRowCount()-1;
        table.changeSelection(lastIndex, 0,false,false);*/
    	/*JScrollBar scroll = scrollPane.getVerticalScrollBar();
    	scroll.setValue(table.getHeight());*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnEndSim;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JComboBox cbCallDuration;
    private javax.swing.JComboBox cbTEC_C2;
    private javax.swing.JComboBox cbTEC_C1;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel lbMinRateC1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbAveRateC1;
    private javax.swing.JLabel lbMaxRateC1;
    private javax.swing.JLabel lbMinRateC2;
    private javax.swing.JLabel lbAveRateC2;
    private javax.swing.JLabel lbMaxRateC2;
    private javax.swing.JLabel lbMinCallsOnSystem;
    private javax.swing.JLabel lbAveCallsOnSystem;
    private javax.swing.JLabel lbMaxCallsOnSystem;
    private javax.swing.JLabel lbNumCalls;
    private javax.swing.JLabel lbLostCallsC1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbLostCallsC2;
    private javax.swing.JLabel lbCompletedCalls;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel lbSimTime;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSpinner spinnerChannelsC1;
    private javax.swing.JSpinner jSpinner10;
    private javax.swing.JSpinner spinnerChannelsC2;
    private javax.swing.JSpinner SpinnerSimTime;
    private javax.swing.JTextField tfSeed;
    private javax.swing.JTextField tfC1C1;
    private javax.swing.JTextField tfC2C2;
    private javax.swing.JTextField tfC1C2;
    private javax.swing.JTextField tfC2C1;
    private javax.swing.JTextField tfC1FA;
    private javax.swing.JTextField tfC2FA;
    private javax.swing.JTextField tfCallDuration;
    private javax.swing.JTextField tfTEC_C2;
    private javax.swing.JTextField tfTEC_C1;
    private JLabel lblChamadasForaDa;
    private JLabel lbOutOfAreaC1;
    private JLabel lblChamadasForaDa_1;
    private JLabel lbOutOfAreaC2;
    private JLabel lblNewLabel;
    private JLabel lblChamadasNoSistema;
    private JLabel lbMinCallDuration;
    private JLabel lbAveCallDuration;
    private JLabel lbMaxCallDuration;
    private JTable table;
    private JSlider sliderTick;
    private JScrollPane scrollPane;
}
