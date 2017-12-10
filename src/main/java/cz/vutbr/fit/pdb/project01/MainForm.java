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
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTextField statusText;
	private JTextField searchText;
	private JTextField textField;
	private JTextField elementType;
	private JTextField ElementName;
	private JTextField parrentZone;
	private JTextField id;
	private JTextField lastMonthFrequency;
	private JTextField averageStay;
	private JTextField FrequencyFrom;
	private JTextField FrequencyTo;
	private JTextField AverageStayFrom;
	private JTextField AverageStayTo;
	private JTable EntranceExitToday;
	private JTable CrurrentlyParking;
	private JTextField deleteFrom;
	private JTextField deleteTo;
	private JTable currentResidence;

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
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		prostorovaData.add(horizontalStrut_4);
		
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
		
		Component verticalStrut = Box.createVerticalStrut(20);
		canvasBttonsPanel.add(verticalStrut);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		canvasBttonsPanel.add(tabbedPane);
		
		JPanel elementSelectonPanel = new JPanel();
		elementSelectonPanel.setMinimumSize(new Dimension(0,0));
		tabbedPane.addTab("Vlastnosti", null, elementSelectonPanel, null);
		
		JPanel idPanel = new JPanel();
		elementSelectonPanel.add(idPanel);
		
		JLabel lblNewLabel_4 = new JLabel("ID položky:");
		idPanel.add(lblNewLabel_4);
		
		id = new JTextField();
		id.setEditable(false);
		idPanel.add(id);
		id.setColumns(10);
		
		JPanel parrentZonePanel = new JPanel();
		elementSelectonPanel.add(parrentZonePanel);
		
		JLabel lblNewLabel_3 = new JLabel("Mateřská zóna:");
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
		
		JLabel lblNewLabel_2 = new JLabel("Název položky:");
		elementNamePanel.add(lblNewLabel_2);
		
		ElementName = new JTextField();
		ElementName.setEditable(false);
		elementNamePanel.add(ElementName);
		ElementName.setColumns(10);
		
		JPanel lastMonthFrequencyPanel = new JPanel();
		elementSelectonPanel.add(lastMonthFrequencyPanel);
		
		JLabel lblNewLabel_5 = new JLabel("Frekventovanost od");
		lastMonthFrequencyPanel.add(lblNewLabel_5);
		
		FrequencyFrom = new JTextField();
		lastMonthFrequencyPanel.add(FrequencyFrom);
		FrequencyFrom.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("do");
		lastMonthFrequencyPanel.add(lblNewLabel_7);
		
		FrequencyTo = new JTextField();
		lastMonthFrequencyPanel.add(FrequencyTo);
		FrequencyTo.setColumns(10);
		
		JLabel lblJe = new JLabel("je:");
		lastMonthFrequencyPanel.add(lblJe);
		
		lastMonthFrequency = new JTextField();
		lastMonthFrequency.setEditable(false);
		lastMonthFrequencyPanel.add(lastMonthFrequency);
		lastMonthFrequency.setColumns(10);
		
		JPanel averageStayPanel = new JPanel();
		elementSelectonPanel.add(averageStayPanel);
		
		JLabel lblNewLabel_6 = new JLabel("Průměrná délka stání od");
		averageStayPanel.add(lblNewLabel_6);
		
		AverageStayFrom = new JTextField();
		averageStayPanel.add(AverageStayFrom);
		AverageStayFrom.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("do");
		averageStayPanel.add(lblNewLabel_8);
		
		AverageStayTo = new JTextField();
		averageStayPanel.add(AverageStayTo);
		AverageStayTo.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("je:");
		averageStayPanel.add(lblNewLabel_9);
		
		averageStay = new JTextField();
		averageStay.setEditable(false);
		averageStayPanel.add(averageStay);
		averageStay.setColumns(10);
		
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
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		multimedialniData.add(horizontalStrut_3);

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

		JButton btRotateLeft = new JButton("Rotovat vlevo");
		pictureDetail.add(btRotateLeft);

		JButton btnRotateRight = new JButton("Rotovat vpravo");
		pictureDetail.add(btnRotateRight);
		
		JButton btnSave = new JButton("Uložit");
		pictureDetail.add(btnSave);

		JPanel temporalniData = new JPanel();
		mainPanel.addTab("Temporální data", null, temporalniData, null);
		temporalniData.setLayout(new BoxLayout(temporalniData, BoxLayout.X_AXIS));
		
		JPanel entranceExitTodayPanel = new JPanel();
		temporalniData.add(entranceExitTodayPanel);
		entranceExitTodayPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_10 = new JLabel("Dnešní vjezdy a výjezdy ");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		entranceExitTodayPanel.add(lblNewLabel_10, BorderLayout.NORTH);
		
		EntranceExitToday = new JTable();
		entranceExitTodayPanel.add(EntranceExitToday, BorderLayout.CENTER);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		temporalniData.add(horizontalStrut);
		
		JPanel currentParkingPanel = new JPanel();
		temporalniData.add(currentParkingPanel);
		currentParkingPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_11 = new JLabel("Právě parkují");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		currentParkingPanel.add(lblNewLabel_11, BorderLayout.NORTH);
		
		CrurrentlyParking = new JTable();
		currentParkingPanel.add(CrurrentlyParking, BorderLayout.CENTER);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		temporalniData.add(horizontalStrut_1);
		
		JPanel currentResidencePanel = new JPanel();
		temporalniData.add(currentResidencePanel);
		currentResidencePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_15 = new JLabel("Probíhající pobyty");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		currentResidencePanel.add(lblNewLabel_15, BorderLayout.NORTH);
		
		currentResidence = new JTable();
		currentResidencePanel.add(currentResidence, BorderLayout.CENTER);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		temporalniData.add(horizontalStrut_2);
		
		JPanel deletion = new JPanel();
		temporalniData.add(deletion);
		deletion.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_12 = new JLabel("Mazání dat");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		deletion.add(lblNewLabel_12, BorderLayout.NORTH);
		
		JPanel DeleteButtonsPanel = new JPanel();
		deletion.add(DeleteButtonsPanel, BorderLayout.CENTER);
		
		JPanel FromPanel = new JPanel();
		DeleteButtonsPanel.add(FromPanel);
		
		JLabel lblNewLabel_13 = new JLabel("Smazat data od:");
		FromPanel.add(lblNewLabel_13);
		
		deleteFrom = new JTextField();
		deleteFrom.setText("DD.MM.YYYY");
		FromPanel.add(deleteFrom);
		deleteFrom.setColumns(10);
		deleteFrom.addActionListener(new java.awt.event.ActionListener() 
		{
		    public void actionPerformed(java.awt.event.ActionEvent e) 
		    {
		    	if (deleteFrom.getText().length()==0)
		    	{
		            deleteFrom.setText("DD.MM.YYYY");
		        }       
		    }
		});
		
		
		
		JPanel ToPanel = new JPanel();
		DeleteButtonsPanel.add(ToPanel);
		
		JLabel lblNewLabel_14 = new JLabel("Smazat data do:");
		ToPanel.add(lblNewLabel_14);
		
		deleteTo = new JTextField();
		deleteTo.setText("DD.MM.YYYY");
		ToPanel.add(deleteTo);
		deleteTo.setColumns(10);
		deleteTo.addActionListener(new java.awt.event.ActionListener() 
		{
		    public void actionPerformed(java.awt.event.ActionEvent e) 
		    {
		    	if (deleteTo.getText().length()==0)
		    	{
		    		deleteTo.setText("DD.MM.YYYY");
		        }       
		    }
		});
		
		JButton btnDeleteButton = new JButton("Smazat");
		DeleteButtonsPanel.add(btnDeleteButton);
	}

}
