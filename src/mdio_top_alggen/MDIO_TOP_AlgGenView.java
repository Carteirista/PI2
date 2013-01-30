/*
 * MDIO_TOP_AlgGenView.java
 */

package mdio_top_alggen;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;

/**
 * The application's main frame.
 */
public class MDIO_TOP_AlgGenView extends FrameView {
	   Universo uni;
	   Thread t;
	   File file = new File("instancia.txt");
	   LeitorInstancia li;

    public MDIO_TOP_AlgGenView(SingleFrameApplication app) {
        super(app);
        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

	JFormattedTextField tf =  ((javax.swing.JSpinner.DefaultEditor)this.jSpinner1.getEditor()).getTextField();
	tf.setEditable(false);
	tf.setBackground(Color.white);
	tf =  ((javax.swing.JSpinner.DefaultEditor)this.jSpinner2.getEditor()).getTextField();
	tf.setEditable(false);
	tf.setBackground(Color.white);
	tf =  ((javax.swing.JSpinner.DefaultEditor)this.jSpinner3.getEditor()).getTextField();
	tf.setEditable(false);
	tf.setBackground(Color.white);
	tf =  ((javax.swing.JSpinner.DefaultEditor)this.jSpinner4.getEditor()).getTextField();
	tf.setEditable(false);
	tf.setBackground(Color.white);
	li = new LeitorInstancia(file);
		try {
			li.ler();
		} catch (IOException ex) {
			Logger.getLogger(MDIO_TOP_AlgGenView.class.getName()).log(Level.SEVERE, null, ex);
		}
	this.uni = new Universo(li);	
	
	//Mutaçoes
	SpinnerNumberModel spm = (SpinnerNumberModel) jSpinner3.getModel();
	spm.setMaximum((Comparable)uni.getNumNodos());
	//elite
	spm = (SpinnerNumberModel) jSpinner1.getModel();
	spm.setMaximum((Comparable)uni.getNumPopulacao());
	//subelite
	spm = (SpinnerNumberModel) jSpinner4.getModel();
	spm.setMaximum((Comparable)uni.getElite());
	
	this.jSpinner4.setValue(uni.getSubelite());
	this.jSpinner3.setValue(uni.getNumMuta());
	this.jSpinner2.setValue(uni.getGeracao().getPopulacao().size());
	this.jSpinner1.setValue(uni.getElite());
	this.jLabel4.setText("("+uni.getGeracaoActual()+")");
	
	this.updateGeracaoText();
	this.updateMelhorFitText();
	System.out.println(file.canRead());
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) { 
            JFrame mainFrame = MDIO_TOP_AlgGenApp.getApplication().getMainFrame();
            aboutBox = new MDIO_TOP_AlgGenAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MDIO_TOP_AlgGenApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jSpinner4 = new javax.swing.JSpinner();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mdio_top_alggen.MDIO_TOP_AlgGenApp.class).getContext().getResourceMap(MDIO_TOP_AlgGenView.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setRows(5);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane2.setViewportView(jTextArea2);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mdio_top_alggen.MDIO_TOP_AlgGenApp.class).getContext().getActionMap(MDIO_TOP_AlgGenView.class, this);
        jButton1.setAction(actionMap.get("quit")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("novaGer")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton4.setAction(actionMap.get("comecar")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jToggleButton1.setAction(actionMap.get("exec")); // NOI18N
        jToggleButton1.setText(resourceMap.getString("jToggleButton1.text")); // NOI18N
        jToggleButton1.setName("jToggleButton1"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(2)));
        jSpinner1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSpinner1.setName("jSpinner1"); // NOI18N
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(2)));
        jSpinner2.setName("jSpinner2"); // NOI18N
        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jSpinner3.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        jSpinner3.setName("jSpinner3"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(2)));
        jSpinner4.setName("jSpinner4"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSpinner1)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner4))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jToggleButton1)
                            .addComponent(jButton4))
                        .addGap(33, 33, 33)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setAction(actionMap.get("batchTest")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        aboutMenuItem1.setAction(actionMap.get("showFileChooser")); // NOI18N
        aboutMenuItem1.setText(resourceMap.getString("aboutMenuItem1.text")); // NOI18N
        aboutMenuItem1.setName("aboutMenuItem1"); // NOI18N
        fileMenu.add(aboutMenuItem1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 595, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

	private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
		// TODO add your handling code here:
		Integer s2 = (Integer)jSpinner2.getValue();
		SpinnerNumberModel spm = (SpinnerNumberModel) jSpinner1.getModel();
		spm.setMaximum((Comparable)s2);
		if ( s2 < (Integer)jSpinner1.getValue()){
			jSpinner1.setValue(s2);
		}
		if (s2 < (Integer)jSpinner1.getValue()+(Integer)jSpinner4.getValue()){
			jSpinner4.setValue(s2-(Integer)jSpinner1.getValue());
			spm = (SpinnerNumberModel) jSpinner4.getModel();
			spm.setMaximum((Comparable)(s2-(Integer)jSpinner1.getValue()));
		}
	}//GEN-LAST:event_jSpinner2StateChanged

	private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
		// TODO add your handling code here:
		Integer s1 = (Integer)jSpinner1.getValue();
		SpinnerNumberModel spm = (SpinnerNumberModel) jSpinner4.getModel();
		spm.setMaximum((Comparable)s1);
		if ( s1 < (Integer)jSpinner4.getValue()){
			jSpinner4.setValue(s1);
		}
		
	}//GEN-LAST:event_jSpinner1StateChanged

	@Action
	public void comecar() {
		li.file = file;
		try {
			li.ler();
		} catch (IOException ex) {
			Logger.getLogger(MDIO_TOP_AlgGenView.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.uni = new Universo(li, (Integer)this.jSpinner2.getValue(), (Integer)this.jSpinner1.getValue());
		this.uni.setSubelite((Integer)this.jSpinner4.getValue());
		this.uni.setNumMuta((Integer)this.jSpinner3.getValue());
		//this.jLabel4.setText(""+uni.getGeracoes().get(uni.getGeracaoActual()).getPopulacao().size());
		//this.uni.setElite((Integer)this.jSpinner2.getValue());
		//this.uni.setNumPopulacao((Integer)this.jSpinner1.getValue());
		this.updateGeracaoText();
		this.updateMelhorFitText();
	}
	public void updateGeracaoText(){
		//Geracao ger = uni.getGeracoes().get(uni.getGeracaoActual());
		Geracao ger = uni.getGeracao();
		ArrayList<Cromossoma> pop = ger.getPopulacao();
		StringBuilder sb = new StringBuilder();
		int popSize = pop.size();
		for(int i = 0; i<popSize; i++){
			sb.append("[").append(i).append("]").append(pop.get(i).toString());
		}
		this.jTextArea1.setText(sb.toString());
	}
	
	public void updateMelhorFitText(){
		//Geracao ger = uni.getGeracoes().get(uni.getGeracaoActual());
		Geracao ger = uni.getGeracao();
		ArrayList<Cromossoma> pop = ger.getPopulacao();
		StringBuilder sb = new StringBuilder();
		sb.append(this.jTextArea2.getText()); 
		Cromossoma cr = pop.get(pop.size()-1);
		sb.append("Geração: ");
		sb.append(uni.getGeracaoActual());
		sb.append(" -> ");
		sb.append(cr.toString());
		this.jTextArea2.setText(sb.toString());
	}

	@Action
	public void novaGer() {
		uni.nextGen();
		this.jLabel4.setText("("+uni.getGeracaoActual()+")");
		this.updateGeracaoText();
		this.updateMelhorFitText();
	}
	
	public boolean novaGerB() {
		uni.nextGen();
		this.updateGeracaoText();
		this.updateMelhorFitText();
		this.jLabel4.setText("("+uni.getGeracaoActual()+")");
		return this.jToggleButton1.isSelected();
	}

	@Action
	public void exec() {
		if(this.jToggleButton1.isSelected()){
			this.jToggleButton1.setText("Parar");
			AutoGen exec = new AutoGen(this);
			t = new Thread(exec);
			t.start();
		}
		else this.jToggleButton1.setText("Executar");
	}

	@Action
	public void showFileChooser() {
	    JFileChooser chooser = new JFileChooser();
	    int result = chooser.showOpenDialog(null);
	    switch (result) {
			case JFileChooser.APPROVE_OPTION:
				file = chooser.getSelectedFile();
				break;
			case JFileChooser.CANCEL_OPTION:
				break;
			case JFileChooser.ERROR_OPTION:
				System.out.println("Error");
				break;
		}
	}

	@Action
	public void batchTest() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
	     int result = chooser.showOpenDialog(null);
	     switch (result) {
			case JFileChooser.APPROVE_OPTION:
				file = chooser.getSelectedFile();
				break;
			case JFileChooser.CANCEL_OPTION:
				break;
			case JFileChooser.ERROR_OPTION:
				System.out.println("Error");
				break;
		}
		File[] files = file.listFiles();
		BatchTest bt = new BatchTest(files, 10000, 10, 220, 10, 10, 1, "teste");
		bt.runTests();
		showDone();
	}
	
	public void showDone() {
        if (done == null) { 
            JFrame mainFrame = MDIO_TOP_AlgGenApp.getApplication().getMainFrame();
            done = new Done(new javax.swing.JFrame(), true);
            done.setLocationRelativeTo(mainFrame);
        }
        MDIO_TOP_AlgGenApp.getApplication().show(done);
    }



	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private JDialog done;	
    private JDialog fileChooser;
}
