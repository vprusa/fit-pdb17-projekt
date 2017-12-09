package cz.vutbr.fit.pdb.project01;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Choice;
import java.awt.Color;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTextField statusText;
	private JTextField searchText;
	private JTextField textField;
	private JTextField elementType;
	private JTextField ElementName;
	private JTextField parrentZone;
	private JTextField id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm("TestRealm", "Test");
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
	public MainForm(String url, String login) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
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
		statusText.setText(String.format("Connected to: %s as: %s", url, login));

		JTabbedPane mainPanel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(mainPanel, BorderLayout.CENTER);

		JPanel prostorovaData = new JPanel();
		mainPanel.addTab("Prostorová data", null, prostorovaData, null);
		prostorovaData.setLayout(new BoxLayout(prostorovaData, BoxLayout.X_AXIS));
		
		JPanel canvasPanel = new JPanel();
		canvasPanel.setBackground(Color.WHITE);
		prostorovaData.add(canvasPanel);
		
		Canvas canvas = new Canvas();
		canvas.setSize(400, 400);
		canvasPanel.add(canvas);
		
		JPanel canvasBttonsPanel = new JPanel();
		prostorovaData.add(canvasBttonsPanel);
		canvasBttonsPanel.setLayout(new BoxLayout(canvasBttonsPanel, BoxLayout.Y_AXIS));
		
		Panel addPanel = new Panel();
		canvasBttonsPanel.add(addPanel);
		
		Choice elementChoice = new Choice();
		elementChoice.add("Auto");
		elementChoice.add("Parkovací místo");
		elementChoice.add("Vjezd");
		elementChoice.add("Výjezd");
		addPanel.add(elementChoice);
		
		JButton btnAddElement = new JButton("Přidat");
		addPanel.add(btnAddElement);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		canvasBttonsPanel.add(tabbedPane);
		
		JPanel elementSelectonPanel = new JPanel();
		tabbedPane.addTab("Vlastnosti", null, elementSelectonPanel, null);
		
		JPanel idPanel = new JPanel();
		elementSelectonPanel.add(idPanel);
		
		JLabel lblNewLabel_4 = new JLabel("ID položky");
		idPanel.add(lblNewLabel_4);
		
		id = new JTextField();
		id.setEnabled(false);
		idPanel.add(id);
		id.setColumns(10);
		
		JPanel parrentZonePanel = new JPanel();
		elementSelectonPanel.add(parrentZonePanel);
		
		JLabel lblNewLabel_3 = new JLabel("Mateřská zóna");
		parrentZonePanel.add(lblNewLabel_3);
		
		parrentZone = new JTextField();
		parrentZone.setEditable(false);
		parrentZonePanel.add(parrentZone);
		parrentZone.setColumns(10);
		
		JPanel elementTypePanel = new JPanel();
		elementSelectonPanel.add(elementTypePanel);
		
		JLabel lblNewLabel_1 = new JLabel("Typ položky:");
		elementTypePanel.add(lblNewLabel_1);
		
		elementType = new JTextField();
		elementType.setEditable(false);
		elementTypePanel.add(elementType);
		elementType.setColumns(10);
		
		JPanel elementNamePanel = new JPanel();
		elementSelectonPanel.add(elementNamePanel);
		
		JLabel lblNewLabel_2 = new JLabel("Název položky");
		elementNamePanel.add(lblNewLabel_2);
		
		ElementName = new JTextField();
		ElementName.setEditable(false);
		elementNamePanel.add(ElementName);
		ElementName.setColumns(10);
		
		JButton btnDeleteElement = new JButton("Smazat položku");
		elementSelectonPanel.add(btnDeleteElement);
		
		JPanel distanceMeasuringPanel = new JPanel();
		tabbedPane.addTab("Měření vzdálenosti", null, distanceMeasuringPanel, null);
		
		JButton btnStartDistanceMeasure = new JButton("Zahájit měření");
		distanceMeasuringPanel.add(btnStartDistanceMeasure);
		
		JPanel distanceResultPanel = new JPanel();
		distanceMeasuringPanel.add(distanceResultPanel);
		
		JLabel lblNewLabel = new JLabel("Naměřená vzdálenost:");
		distanceResultPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		distanceResultPanel.add(textField);
		textField.setColumns(10);

		JPanel multimedialniData = new JPanel();
		mainPanel.addTab("Multimediální data", null, multimedialniData, null);
		multimedialniData.setLayout(new BoxLayout(multimedialniData, BoxLayout.X_AXIS));

		JPanel textPanel = new JPanel();
		multimedialniData.add(textPanel);
		textPanel.setLayout(new BorderLayout(0, 0));

		JList list = new JList();
		list.setVisibleRowCount(20);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "test", "test2", "test3", "test4", "test5", "test6", "test7", "test8",
					"test9", "test10", "test11", "test12", "test13", "test14", "test15", "test16", "test17", "test18",
					"test19", "test20", "test21", "test22", "test23", "test24", "test25", "test26", "test27", "test28",
					"test29", "test30", "test31", "test32", "test33", "test34", "test35" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		JScrollPane scrollPane = new JScrollPane(list);
		textPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel filterPanel = new JPanel();
		textPanel.add(filterPanel, BorderLayout.SOUTH);
		filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel searchPanel = new JPanel();
		filterPanel.add(searchPanel);

		searchText = new JTextField();
		searchPanel.add(searchText);
		searchText.setColumns(20);

		JButton btnSearchButton = new JButton("Search");
		searchPanel.add(btnSearchButton);

		JPanel picturePanel = new JPanel();
		multimedialniData.add(picturePanel);
		picturePanel.setLayout(new BoxLayout(picturePanel, BoxLayout.Y_AXIS));

		JPanel pictureItself = new JPanel();
		pictureItself.setBackground(Color.WHITE);
		picturePanel.add(pictureItself);

		JLabel PictureLabel = new JLabel();
		pictureItself.add(PictureLabel);

		JPanel pictureDetail = new JPanel();
		pictureDetail.setMaximumSize(new Dimension(10000,100));
		picturePanel.add(pictureDetail);

		JButton btRotateLeft = new JButton("RotateLeft");
		pictureDetail.add(btRotateLeft);

		JButton btnRotateRight = new JButton("Rotate Right");
		pictureDetail.add(btnRotateRight);

		JPanel temporalniData = new JPanel();
		mainPanel.addTab("Temporální data", null, temporalniData, null);
	}

}
