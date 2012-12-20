package uk.ac.ed.inf.proj.xmlnormaliser.gui;

/**
 * The Swing GUI for controlling the algorithm
 * @author Tomas Tauber
 */
public class MainFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 4738967437899732366L;
	/**
     * Creates new form
     */
    public MainFrame() {
        initComponents();
        MainPanel.setEnabledAt(1, false);
    }

    private void initComponents() {

        FileDialog = new javax.swing.JFileChooser();
        XQueryDialog = new javax.swing.JDialog();
        XQueryPanel = new javax.swing.JPanel();
        XQueryButtons = new javax.swing.JToolBar();
        SaveXQuery = new javax.swing.JButton();
        XQueryTextWrap = new javax.swing.JScrollPane();
        XQueryText = new javax.swing.JTextArea();
        MainPanel = new javax.swing.JTabbedPane();
        InputScreen = new javax.swing.JPanel();
        InputButtons = new javax.swing.JToolBar();
        OpenDTD = new javax.swing.JButton();
        OpenXFD = new javax.swing.JButton();
        RunButton = new javax.swing.JButton();
        InputSplit = new javax.swing.JSplitPane();
        InputTexts = new javax.swing.JPanel();
        DTDTextWrap = new javax.swing.JScrollPane();
        DTDText = new javax.swing.JTextArea();
        XFDTextWrap = new javax.swing.JScrollPane();
        XFDText = new javax.swing.JTextArea();
        InputImagePanel = new javax.swing.JPanel();
        OriginalImageHolder = new javax.swing.JLabel();
        OutputScreen = new javax.swing.JPanel();
        OutputButtons = new javax.swing.JToolBar();
        SaveDTD = new javax.swing.JButton();
        SaveXFD = new javax.swing.JButton();
        GenerateXQuery = new javax.swing.JButton();
        OutputSplit = new javax.swing.JSplitPane();
        OutputTexts = new javax.swing.JPanel();
        NewDTDTextWrap = new javax.swing.JScrollPane();
        NewDTDText = new javax.swing.JTextArea();
        NewXFDTextWrap = new javax.swing.JScrollPane();
        NewXFDText = new javax.swing.JTextArea();
        OutputImagePanel = new javax.swing.JPanel();
        NewImageHolder = new javax.swing.JLabel();

        XQueryDialog.setTitle("Generated XQuery");
        XQueryDialog.setModal(true);
 
        XQueryButtons.setFloatable(false);
        XQueryButtons.setRollover(true);

        SaveXQuery.setText("Save");
        SaveXQuery.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        SaveXQuery.setFocusable(false);
        SaveXQuery.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SaveXQuery.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        XQueryButtons.add(SaveXQuery);

        XQueryText.setColumns(20);
        XQueryText.setRows(5);
        XQueryTextWrap.setViewportView(XQueryText);

        javax.swing.GroupLayout XQueryPanelLayout = new javax.swing.GroupLayout(XQueryPanel);
        XQueryPanel.setLayout(XQueryPanelLayout);
        XQueryPanelLayout.setHorizontalGroup(
            XQueryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(XQueryButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(XQueryTextWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        XQueryPanelLayout.setVerticalGroup(
            XQueryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, XQueryPanelLayout.createSequentialGroup()
                .addComponent(XQueryTextWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(XQueryButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout XQueryDialogLayout = new javax.swing.GroupLayout(XQueryDialog.getContentPane());
        XQueryDialog.getContentPane().setLayout(XQueryDialogLayout);
        XQueryDialogLayout.setHorizontalGroup(
            XQueryDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(XQueryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        XQueryDialogLayout.setVerticalGroup(
            XQueryDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(XQueryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("XML Normalisation Algorithm - s0943263");

        MainPanel.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        InputButtons.setFloatable(false);
        InputButtons.setRollover(true);

        OpenDTD.setText("Load DTD");
        OpenDTD.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        OpenDTD.setFocusable(false);
        OpenDTD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        OpenDTD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        OpenDTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenDTDActionPerformed(evt);
            }
        });
        InputButtons.add(OpenDTD);

        OpenXFD.setText("Load XFDs");
        OpenXFD.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        OpenXFD.setFocusable(false);
        OpenXFD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        OpenXFD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        InputButtons.add(OpenXFD);

        RunButton.setText("Verify XNF and Transform");
        RunButton.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        RunButton.setFocusable(false);
        RunButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RunButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        RunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunButtonActionPerformed(evt);
            }
        });
        InputButtons.add(RunButton);

        InputSplit.setDividerLocation(350);

        DTDText.setColumns(20);
        DTDText.setRows(5);
        DTDText.setText("DTD Input");
        DTDTextWrap.setViewportView(DTDText);

        XFDText.setColumns(20);
        XFDText.setRows(5);
        XFDText.setText("XFD Input");
        XFDTextWrap.setViewportView(XFDText);

        javax.swing.GroupLayout InputTextsLayout = new javax.swing.GroupLayout(InputTexts);
        InputTexts.setLayout(InputTextsLayout);
        InputTextsLayout.setHorizontalGroup(
            InputTextsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DTDTextWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
            .addComponent(XFDTextWrap)
        );
        InputTextsLayout.setVerticalGroup(
            InputTextsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputTextsLayout.createSequentialGroup()
                .addComponent(DTDTextWrap, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(XFDTextWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
        );

        InputSplit.setLeftComponent(InputTexts);

        javax.swing.GroupLayout InputImagePanelLayout = new javax.swing.GroupLayout(InputImagePanel);
        InputImagePanel.setLayout(InputImagePanelLayout);
        InputImagePanelLayout.setHorizontalGroup(
            InputImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OriginalImageHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        InputImagePanelLayout.setVerticalGroup(
            InputImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OriginalImageHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        InputSplit.setRightComponent(InputImagePanel);

        javax.swing.GroupLayout InputScreenLayout = new javax.swing.GroupLayout(InputScreen);
        InputScreen.setLayout(InputScreenLayout);
        InputScreenLayout.setHorizontalGroup(
            InputScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InputButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(InputSplit)
        );
        InputScreenLayout.setVerticalGroup(
            InputScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InputScreenLayout.createSequentialGroup()
                .addComponent(InputSplit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.addTab("Input", InputScreen);

        OutputButtons.setFloatable(false);
        OutputButtons.setRollover(true);

        SaveDTD.setText("Save new DTD");
        SaveDTD.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        SaveDTD.setFocusable(false);
        SaveDTD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SaveDTD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        OutputButtons.add(SaveDTD);

        SaveXFD.setText("Save new XFDs");
        SaveXFD.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        SaveXFD.setFocusable(false);
        SaveXFD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SaveXFD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        OutputButtons.add(SaveXFD);

        GenerateXQuery.setText("Generate XQuery to transform data");
        GenerateXQuery.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        GenerateXQuery.setFocusable(false);
        GenerateXQuery.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        GenerateXQuery.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        GenerateXQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateXQueryActionPerformed(evt);
            }
        });
        OutputButtons.add(GenerateXQuery);

        OutputSplit.setDividerLocation(350);

        NewDTDText.setColumns(20);
        NewDTDText.setRows(5);
        NewDTDTextWrap.setViewportView(NewDTDText);

        NewXFDText.setColumns(20);
        NewXFDText.setRows(5);
        NewXFDTextWrap.setViewportView(NewXFDText);

        javax.swing.GroupLayout OutputTextsLayout = new javax.swing.GroupLayout(OutputTexts);
        OutputTexts.setLayout(OutputTextsLayout);
        OutputTextsLayout.setHorizontalGroup(
            OutputTextsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NewDTDTextWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
            .addComponent(NewXFDTextWrap)
        );
        OutputTextsLayout.setVerticalGroup(
            OutputTextsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutputTextsLayout.createSequentialGroup()
                .addComponent(NewDTDTextWrap, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NewXFDTextWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
        );

        OutputSplit.setLeftComponent(OutputTexts);

        javax.swing.GroupLayout OutputImagePanelLayout = new javax.swing.GroupLayout(OutputImagePanel);
        OutputImagePanel.setLayout(OutputImagePanelLayout);
        OutputImagePanelLayout.setHorizontalGroup(
            OutputImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewImageHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        OutputImagePanelLayout.setVerticalGroup(
            OutputImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewImageHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        OutputSplit.setRightComponent(OutputImagePanel);

        javax.swing.GroupLayout OutputScreenLayout = new javax.swing.GroupLayout(OutputScreen);
        OutputScreen.setLayout(OutputScreenLayout);
        OutputScreenLayout.setHorizontalGroup(
            OutputScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OutputButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(OutputSplit)
        );
        OutputScreenLayout.setVerticalGroup(
            OutputScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OutputScreenLayout.createSequentialGroup()
                .addComponent(OutputSplit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OutputButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.addTab("Result", OutputScreen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel)
        );

        MainPanel.getAccessibleContext().setAccessibleName("MainPanel");

        pack();
    }

    private void RunButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
        MainPanel.setEnabledAt(1, true);
    }                                         

    private void OpenDTDActionPerformed(java.awt.event.ActionEvent evt) {                                        
        FileDialog.showOpenDialog(MainFrame.this);
    }                                       

    private void GenerateXQueryActionPerformed(java.awt.event.ActionEvent evt) {
        XQueryDialog.pack();
        XQueryDialog.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JTextArea DTDText;
    private javax.swing.JScrollPane DTDTextWrap;
    private javax.swing.JFileChooser FileDialog;
    private javax.swing.JButton GenerateXQuery;
    private javax.swing.JToolBar InputButtons;
    private javax.swing.JPanel InputImagePanel;
    private javax.swing.JPanel InputScreen;
    private javax.swing.JSplitPane InputSplit;
    private javax.swing.JPanel InputTexts;
    private javax.swing.JTabbedPane MainPanel;
    private javax.swing.JTextArea NewDTDText;
    private javax.swing.JScrollPane NewDTDTextWrap;
    private javax.swing.JLabel NewImageHolder;
    private javax.swing.JTextArea NewXFDText;
    private javax.swing.JScrollPane NewXFDTextWrap;
    private javax.swing.JButton OpenDTD;
    private javax.swing.JButton OpenXFD;
    private javax.swing.JLabel OriginalImageHolder;
    private javax.swing.JToolBar OutputButtons;
    private javax.swing.JPanel OutputImagePanel;
    private javax.swing.JPanel OutputScreen;
    private javax.swing.JSplitPane OutputSplit;
    private javax.swing.JPanel OutputTexts;
    private javax.swing.JButton RunButton;
    private javax.swing.JButton SaveDTD;
    private javax.swing.JButton SaveXFD;
    private javax.swing.JButton SaveXQuery;
    private javax.swing.JTextArea XFDText;
    private javax.swing.JScrollPane XFDTextWrap;
    private javax.swing.JToolBar XQueryButtons;
    private javax.swing.JDialog XQueryDialog;
    private javax.swing.JPanel XQueryPanel;
    private javax.swing.JTextArea XQueryText;
    private javax.swing.JScrollPane XQueryTextWrap;
    // End of variables declaration
}
