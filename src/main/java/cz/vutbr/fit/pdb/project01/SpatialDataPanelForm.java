package cz.vutbr.fit.pdb.project01;


import javax.swing.*;
import java.awt.*;

public class SpatialDataPanelForm extends JPanel {

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
	 * Create the panel.
	 */
	public SpatialDataPanelForm() {
		//JPanel prostorovaData = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
	    JPanel canvasPanelWrap = new JPanel();
	    canvasPanelWrap.setLayout(new GridLayout());
	    canvasPanelWrap.setMinimumSize(new Dimension(400, getHeight()));
	    SpatialDataCanvasPanelForm canvasPanel = new SpatialDataCanvasPanelForm();
	    canvasPanelWrap.add(canvasPanel);
		
		add(canvasPanelWrap);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		add(horizontalStrut_4);
		
		JPanel canvasBttonsPanel = new JPanel();
		add(canvasBttonsPanel);
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
		
		setVisible(true);
	}

}
