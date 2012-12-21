package uk.ac.ed.inf.proj.xmlnormaliser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;

public class ActionsHandler implements ActionListener {
	
	private final MainFrame relatedWindow;
	
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
				}
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

	}

}
