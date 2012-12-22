package uk.ac.ed.inf.proj.xmlnormaliser.gui;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 * The Swing GUI for controlling the algorithm
 * @author Tomas Tauber
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4738967437899732366L;
	/**
     * Creates new form
     */
    public MainFrame() {
        initComponents();
        MainPanel.setEnabledAt(1, false);
        addWindowListener(listener);
    }

    private void initComponents() {

    	listener = new ActionsHandler(this);
        FileDialog = new JFileChooser(System.getProperties().getProperty("user.dir"));
        XQueryDialog = new JDialog();
        XQueryPanel = new JPanel();
        XQueryButtons = new JToolBar();
        SaveXQuery = new JButton();
        XQueryTextWrap = new JScrollPane();
        XQueryText = new JTextArea();
        MainPanel = new JTabbedPane();
        InputScreen = new JPanel();
        InputButtons = new JToolBar();
        OpenDTD = new JButton();
        OpenXFD = new JButton();
        RunButton = new JButton();
        InputSplit = new JSplitPane();
        InputTexts = new JPanel();
        DTDTextWrap = new JScrollPane();
        DTDText = new JTextArea();
        XFDTextWrap = new JScrollPane();
        XFDText = new JTextArea();
        InputImagePanel = new JPanel();
        OriginalImageHolder = new JLabel();
        OutputScreen = new JPanel();
        OutputButtons = new JToolBar();
        SaveDTD = new JButton();
        SaveXFD = new JButton();
        GenerateXQuery = new JButton();
        OutputSplit = new JSplitPane();
        OutputTexts = new JPanel();
        NewDTDTextWrap = new JScrollPane();
        NewDTDText = new JTextArea();
        NewXFDTextWrap = new JScrollPane();
        NewXFDText = new JTextArea();
        OutputImagePanel = new JPanel();
        NewImageHolder = new JLabel();

        XQueryDialog.setTitle("Generated XQuery");
        XQueryDialog.setModal(true);
 
        XQueryButtons.setFloatable(false);
        XQueryButtons.setRollover(true);

        SaveXQuery.setText("Save");
        SaveXQuery.setBorder(BorderFactory.createTitledBorder(""));
        SaveXQuery.setFocusable(false);
        SaveXQuery.setHorizontalTextPosition(SwingConstants.CENTER);
        SaveXQuery.setVerticalTextPosition(SwingConstants.BOTTOM);
        XQueryButtons.add(SaveXQuery);

        XQueryText.setColumns(20);
        XQueryText.setRows(5);
        XQueryTextWrap.setViewportView(XQueryText);

        GroupLayout XQueryPanelLayout = new GroupLayout(XQueryPanel);
        XQueryPanel.setLayout(XQueryPanelLayout);
        XQueryPanelLayout.setHorizontalGroup(
            XQueryPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(XQueryButtons, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(XQueryTextWrap, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        XQueryPanelLayout.setVerticalGroup(
            XQueryPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, XQueryPanelLayout.createSequentialGroup()
                .addComponent(XQueryTextWrap, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(XQueryButtons, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout XQueryDialogLayout = new GroupLayout(XQueryDialog.getContentPane());
        XQueryDialog.getContentPane().setLayout(XQueryDialogLayout);
        XQueryDialogLayout.setHorizontalGroup(
            XQueryDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(XQueryPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        XQueryDialogLayout.setVerticalGroup(
            XQueryDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(XQueryPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("XML Normalisation Algorithm - Tomas Tauber");

        MainPanel.setTabPlacement(JTabbedPane.BOTTOM);

        InputButtons.setFloatable(false);
        InputButtons.setRollover(true);

        OpenDTD.setText("Load DTD");
        OpenDTD.setBorder(BorderFactory.createTitledBorder(""));
        OpenDTD.setFocusable(false);
        OpenDTD.setHorizontalTextPosition(SwingConstants.CENTER);
        OpenDTD.setVerticalTextPosition(SwingConstants.BOTTOM);
        OpenDTD.addActionListener(listener);
        InputButtons.add(OpenDTD);

        OpenXFD.setText("Load XFDs");
        OpenXFD.setBorder(BorderFactory.createTitledBorder(""));
        OpenXFD.setFocusable(false);
        OpenXFD.setHorizontalTextPosition(SwingConstants.CENTER);
        OpenXFD.setVerticalTextPosition(SwingConstants.BOTTOM);
        OpenXFD.addActionListener(listener);
        InputButtons.add(OpenXFD);

        RunButton.setText("Verify XNF and Transform");
        RunButton.setBorder(BorderFactory.createTitledBorder(""));
        RunButton.setFocusable(false);
        RunButton.setHorizontalTextPosition(SwingConstants.CENTER);
        RunButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        RunButton.addActionListener(listener);
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

        GroupLayout InputTextsLayout = new GroupLayout(InputTexts);
        InputTexts.setLayout(InputTextsLayout);
        InputTextsLayout.setHorizontalGroup(
            InputTextsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(DTDTextWrap, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
            .addComponent(XFDTextWrap)
        );
        InputTextsLayout.setVerticalGroup(
            InputTextsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(InputTextsLayout.createSequentialGroup()
                .addComponent(DTDTextWrap, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(XFDTextWrap, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
        );

        InputSplit.setLeftComponent(InputTexts);

        GroupLayout InputImagePanelLayout = new GroupLayout(InputImagePanel);
        InputImagePanel.setLayout(InputImagePanelLayout);
        InputImagePanelLayout.setHorizontalGroup(
            InputImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(InputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OriginalImageHolder, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        InputImagePanelLayout.setVerticalGroup(
            InputImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(InputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OriginalImageHolder, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        InputSplit.setRightComponent(InputImagePanel);

        GroupLayout InputScreenLayout = new GroupLayout(InputScreen);
        InputScreen.setLayout(InputScreenLayout);
        InputScreenLayout.setHorizontalGroup(
            InputScreenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(InputButtons, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(InputSplit)
        );
        InputScreenLayout.setVerticalGroup(
            InputScreenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, InputScreenLayout.createSequentialGroup()
                .addComponent(InputSplit)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputButtons, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.addTab("Input", InputScreen);

        OutputButtons.setFloatable(false);
        OutputButtons.setRollover(true);

        SaveDTD.setText("Save new DTD");
        SaveDTD.setBorder(BorderFactory.createTitledBorder(""));
        SaveDTD.setFocusable(false);
        SaveDTD.setHorizontalTextPosition(SwingConstants.CENTER);
        SaveDTD.setVerticalTextPosition(SwingConstants.BOTTOM);
        OutputButtons.add(SaveDTD);

        SaveXFD.setText("Save new XFDs");
        SaveXFD.setBorder(BorderFactory.createTitledBorder(""));
        SaveXFD.setFocusable(false);
        SaveXFD.setHorizontalTextPosition(SwingConstants.CENTER);
        SaveXFD.setVerticalTextPosition(SwingConstants.BOTTOM);
        OutputButtons.add(SaveXFD);

        GenerateXQuery.setText("Generate XQuery to transform data");
        GenerateXQuery.setBorder(BorderFactory.createTitledBorder(""));
        GenerateXQuery.setFocusable(false);
        GenerateXQuery.setHorizontalTextPosition(SwingConstants.CENTER);
        GenerateXQuery.setVerticalTextPosition(SwingConstants.BOTTOM);
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

        GroupLayout OutputTextsLayout = new GroupLayout(OutputTexts);
        OutputTexts.setLayout(OutputTextsLayout);
        OutputTextsLayout.setHorizontalGroup(
            OutputTextsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(NewDTDTextWrap, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
            .addComponent(NewXFDTextWrap)
        );
        OutputTextsLayout.setVerticalGroup(
            OutputTextsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(OutputTextsLayout.createSequentialGroup()
                .addComponent(NewDTDTextWrap, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NewXFDTextWrap, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
        );

        OutputSplit.setLeftComponent(OutputTexts);

        GroupLayout OutputImagePanelLayout = new GroupLayout(OutputImagePanel);
        OutputImagePanel.setLayout(OutputImagePanelLayout);
        OutputImagePanelLayout.setHorizontalGroup(
            OutputImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(OutputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewImageHolder, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        OutputImagePanelLayout.setVerticalGroup(
            OutputImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(OutputImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewImageHolder, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        OutputSplit.setRightComponent(OutputImagePanel);

        GroupLayout OutputScreenLayout = new GroupLayout(OutputScreen);
        OutputScreen.setLayout(OutputScreenLayout);
        OutputScreenLayout.setHorizontalGroup(
            OutputScreenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(OutputButtons, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(OutputSplit)
        );
        OutputScreenLayout.setVerticalGroup(
            OutputScreenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, OutputScreenLayout.createSequentialGroup()
                .addComponent(OutputSplit)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OutputButtons, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.addTab("Result", OutputScreen);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel)
        );

        MainPanel.getAccessibleContext().setAccessibleName("MainPanel");

        pack();
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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
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
    ActionsHandler listener;
    JTextArea DTDText;
    JScrollPane DTDTextWrap;
    JFileChooser FileDialog;
    JButton GenerateXQuery;
    JToolBar InputButtons;
    JPanel InputImagePanel;
    JPanel InputScreen;
    JSplitPane InputSplit;
    JPanel InputTexts;
    JTabbedPane MainPanel;
    JTextArea NewDTDText;
    JScrollPane NewDTDTextWrap;
    JLabel NewImageHolder;
    JTextArea NewXFDText;
    JScrollPane NewXFDTextWrap;
    JButton OpenDTD;
    JButton OpenXFD;
    JLabel OriginalImageHolder;
    JToolBar OutputButtons;
    JPanel OutputImagePanel;
    JPanel OutputScreen;
    JSplitPane OutputSplit;
    JPanel OutputTexts;
    JButton RunButton;
    JButton SaveDTD;
    JButton SaveXFD;
    JButton SaveXQuery;
    JTextArea XFDText;
    JScrollPane XFDTextWrap;
    JToolBar XQueryButtons;
    JDialog XQueryDialog;
    JPanel XQueryPanel;
    JTextArea XQueryText;
    JScrollPane XQueryTextWrap;
    // End of variables declaration
}
