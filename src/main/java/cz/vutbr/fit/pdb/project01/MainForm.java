package cz.vutbr.fit.pdb.project01;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTextField statusText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
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
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new CardLayout(0, 0));
		
		statusText = new JTextField();
		statusText.setEditable(false);
		southPanel.add(statusText, "name_21676688515623");
		statusText.setColumns(10);
		
		JTabbedPane mainPanel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		
		JPanel prostorovaData = new JPanel();
		mainPanel.addTab("Prostorová data", null, prostorovaData, null);
		
		JPanel multimedialniData = new JPanel();
		mainPanel.addTab("Multimediální data", null, multimedialniData, null);
		
		JPanel temporalniData = new JPanel();
		mainPanel.addTab("Temporální data", null, temporalniData, null);
	}

}
