package uk.ac.ed.inf.proj.xmlnormaliser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.proj.xmlnormaliser.Main;
import uk.ac.ed.inf.proj.xmlnormaliser.Utils;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;

/**
 * Handles button clicks and deletes temporary content on closing the window
 * @author Tomas Tauber
 *
 */
public class ActionsHandler extends WindowAdapter implements ActionListener {

	private static final Logger LOGGER = Logger.getLogger(ActionsHandler.class.getName());	
	
	private final MainFrame relatedWindow;
	
	private List<TransformAction> actions;
	
	private DTD parsedDTD;
	
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
			} else if (e.getSource() == relatedWindow.OpenXFD) {
				int returnVal = relatedWindow.FileDialog.showOpenDialog(relatedWindow);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File xfds = relatedWindow.FileDialog.getSelectedFile();
					relatedWindow.XFDText.setText(Utils.readFile(xfds));
				}
			} else if (e.getSource() == relatedWindow.RunButton) {
				if (!relatedWindow.DTDText.getText().contains("<!DOCTYPE")) {
					String root = (String) JOptionPane.showInputDialog(
							relatedWindow,
		                    "Input the root node:",
		                    "Root node setup",
		                    JOptionPane.PLAIN_MESSAGE);
					parsedDTD = DTDParser.parse(relatedWindow.DTDText.getText(), root);
				} else {
					parsedDTD = DTDParser.parse(relatedWindow.DTDText.getText());
				}
				Map<FDPath, FDPath> xfds = FDParser.parse(relatedWindow.XFDText.getText());
				actions = Main.checkAndGenerateActions(parsedDTD, xfds);
				relatedWindow.NewDTDText.setText(TransformAction.applyActions(relatedWindow.DTDText.getText(), actions,
						parsedDTD));
				relatedWindow.NewXFDText.setText("");
				for (Entry<FDPath, FDPath> xfd : xfds.entrySet()) {
					relatedWindow.NewXFDText.append(xfd.getKey() + " -> " + xfd.getValue());
					relatedWindow.NewXFDText.append("\n");
				}
				GraphDrawing.generateImage(relatedWindow.NewDTDText.getText(), "transformed");
				ImageIcon icon = new ImageIcon(GraphDrawing.TEMP_FOLDER + GraphDrawing.SEPARATOR + "transformed.gif");
				icon.getImage().flush();
				relatedWindow.NewImageHolder.setIcon(icon);				
				relatedWindow.MainPanel.setEnabledAt(1, true);
			} else if (e.getSource() == relatedWindow.SaveDTD || e.getSource() == relatedWindow.SaveXFD) {
				int returnVal = relatedWindow.FileDialog.showSaveDialog(relatedWindow);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File output = relatedWindow.FileDialog.getSelectedFile();
					if (e.getSource() == relatedWindow.SaveDTD) {
						Utils.writeFile(output.toString(), relatedWindow.NewDTDText.getText());
					} else {
						Utils.writeFile(output.toString(), relatedWindow.NewXFDText.getText());
					}
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			LOGGER.error(ex.getMessage(), ex);
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
