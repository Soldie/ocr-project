/**
 * 
 */
package br.com.deployxtech.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.deployxtech.ocr.OCRProcessing;

/**
 * @author francisco
 *
 */
public class OCRView extends JDialog {

	private JButton btnTest = new JButton("Testar");
	private JButton btnLearm = new JButton("Treinar");
	private JPanel pnlControl = new JPanel();

	private JLabel lblImage = new JLabel();
	private JButton btnSearchImage = new JButton("Selecionar...");
	private JPanel pnlImage = new JPanel();
	private JTextField txtResult = new JTextField();
	private JTextArea txtConsole = new JTextArea();
	private JPanel pnlProcessing = new JPanel(new BorderLayout(10,10));

	private JFileChooser fc = new JFileChooser(".");

	private OCRProcessing processing = new OCRProcessing(txtConsole);

	private File fileImage;

	public OCRView() {
		setTitle("OCR-Project(Reconhecimento ótico de caracteres)");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		init();
	}

	private void init() {
		setLayout(new BorderLayout(10,10));
		pnlControl.add(btnSearchImage);
		pnlControl.add(btnTest);
		pnlControl.add(btnLearm);
		getContentPane().add(pnlControl, BorderLayout.NORTH);
		pnlProcessing.setLayout(new GridLayout(2, 1));
		JScrollPane imageScroll = new JScrollPane(lblImage);
		imageScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
		pnlProcessing.add(imageScroll, BorderLayout.NORTH);
		pnlProcessing.add(txtResult, BorderLayout.CENTER);
		getContentPane().add(pnlProcessing, BorderLayout.CENTER);
		txtConsole.setEditable(false);
		JScrollPane consoleScroll = new JScrollPane(txtConsole);
		consoleScroll.setPreferredSize(new Dimension(800, 300));
		getContentPane().add(consoleScroll, BorderLayout.SOUTH);

		btnSearchImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
						"Image files", ImageIO.getReaderFileSuffixes());
				fc.setFileFilter(imageFilter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(OCRView.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileImage = fc.getSelectedFile();
					lblImage.setIcon(new ImageIcon(fileImage.getAbsolutePath()));

					Pattern pattern = Pattern.compile("(\\()(.*?)(\\))");
					Matcher matcher = pattern.matcher(fileImage.getName());
					if (matcher.find()) {
						String letras = matcher.group(2);
						txtResult.setText(letras);
					}
					else {
						txtResult.setText("");
					}
				}
			}
		});

		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (fileImage == null) {
						JOptionPane.showMessageDialog(OCRView.this, "É preciso selecionar uma imagem.");
					}
					else if (!txtResult.getText().equals("")) {
						JOptionPane.showMessageDialog(OCRView.this, "É preciso apagar a caixa de texto para realizar o teste.");
					}
					else {
						String result = processing.recogner(ImageIO.read(fileImage));
						txtResult.setText(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnLearm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (fileImage == null) {
						JOptionPane.showMessageDialog(OCRView.this, "É preciso selecionar uma imagem.");
					}
					else if (txtResult.getText().equals("")) {
						JOptionPane.showMessageDialog(OCRView.this, "É preciso escrever as letras exatamente como esta na imagem.");
					}
					else {
						processing.learm(txtResult.getText(), ImageIO.read(fileImage));
						txtResult.setText("");
						lblImage.setIcon(null);
						JOptionPane.showMessageDialog(OCRView.this, "Letras aprendidas...");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OCRView view = new OCRView();
		view.setSize(400, 600);
		view.setLocationByPlatform(true);
		view.setVisible(true);
	}

}
