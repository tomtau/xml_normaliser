package uk.ac.ed.inf.proj.xmlnormaliser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;

/**
 * Handles button clicks and deletes temporary content on closing the window
 * @author Tomas Tauber
 *
 */
public class ActionsHandler extends WindowAdapter implements ActionListener {
	
	private final MainFrame relatedWindow;
	
	/**
	 * Constructor
	 * @param parent parent window
	 */
	public ActionsHandler(MainFrame parent) {
		relatedWindow = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == relatedWindow.OpenDTD) {
				int returnVal = relatedWindow.FileDialog.showOpenDialog(relatedWindow);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File dtd = relatedWindow.FileDialog.getSelectedFile();
					relatedWindow.DTDText.setText(Utils.readFile(dtd));
					GraphDrawing.generateImage(relatedWindow.DTDText.getText(), "original");
					ImageIcon icon = new ImageIcon(GraphDrawing.TEMP_FOLDER + GraphDrawing.SEPARATOR + "original.gif");
					icon.getImage().flush();
					relatedWindow.OriginalImageHolder.setIcon(icon);
				}
			} else if (e.getSource() == relatedWindow.RunButton) {
				if (!relatedWindow.DTDText.getText().contains("<!DOCTYPE")) {
					String root = (String) JOptionPane.showInputDialog(
							relatedWindow,
		                    "Input the root node:",
		                    "Root node setup",
		                    JOptionPane.PLAIN_MESSAGE);
				}
				relatedWindow.MainPanel.setEnabledAt(1, true);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

	}
	
	@Override
	public void windowClosing(WindowEvent e) {  
		if (GraphDrawing.TEMP_FOLDER.isDirectory()) {
			for (File f : GraphDrawing.TEMP_FOLDER.listFiles()) {
				f.delete();
			}
			GraphDrawing.TEMP_FOLDER.delete();
		}
		super.windowClosing(e);
	}

}
