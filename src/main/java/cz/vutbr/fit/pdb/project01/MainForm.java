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

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTextField statusText;
	private JTextField searchText;

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
		setBounds(100, 100, 700, 500);
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
		picturePanel.add(pictureItself);

		JLabel PictureLabel = new JLabel();
		pictureItself.add(PictureLabel);

		JPanel pictureDetail = new JPanel();
		picturePanel.add(pictureDetail);

		JButton btRotateLeft = new JButton("RotateLeft");
		pictureDetail.add(btRotateLeft);

		JButton btnRotateRight = new JButton("Rotate Right");
		pictureDetail.add(btnRotateRight);

		JPanel temporalniData = new JPanel();
		mainPanel.addTab("Temporální data", null, temporalniData, null);
	}

}
