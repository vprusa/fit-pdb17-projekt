package cz.vutbr.fit.pdb.project01;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cz.vutbr.fit.pdb.project.model.PobytModel;
import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.Pobyt;
import cz.vutbr.fit.pdb.project.tables.Vozidlo;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Choice;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionEvent;

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
	private JTextField SPZ;
	public Vozidlo myCar = null;
	private JTextField SPZpath;
	private JTextField PhotoPath;
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.print("MainForm about to close.");
		    	TableBase.closeManager();
				dispose();
		        System.exit(0);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		// create form for spatial data
		SpatialDataPanelForm prostorovaData = new SpatialDataPanelForm();
		mainPanel.addTab("Prostorová data", null, prostorovaData, null);

		JPanel multimedialniData = new JPanel();
		mainPanel.addTab("Multimediální data", null, multimedialniData, null);
		multimedialniData.setLayout(new BoxLayout(multimedialniData, BoxLayout.X_AXIS));

		JPanel textPanel = new JPanel();
		multimedialniData.add(textPanel);
		textPanel.setLayout(new BorderLayout(0, 0));
		String[] originalList = originalListRefresh();
		JList<String> list = new JList<String>();
		
		
		list.setVisibleRowCount(20);
		list.setModel(new AbstractListModel() {
			String[] values = originalList;

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
		
		JButton btnRefresh = new JButton("Obnovit");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				originalListRefresh();
				list.setModel(new AbstractListModel() {
					String[] values = originalList;

					public int getSize() {
						return values.length;
					}

					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		});
		filterPanel.add(btnRefresh);

		JPanel searchPanel = new JPanel();
		filterPanel.add(searchPanel);
		
		JButton btnClear = new JButton("Vyčistit");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setModel(new AbstractListModel() {
				String[] values = originalList;

				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});
				btnClear.setEnabled(false);
			}
		});
		btnClear.setEnabled(false);
		searchPanel.add(btnClear);

		searchText = new JTextField();
		searchPanel.add(searchText);
		searchText.setColumns(20);

		JButton btnSearchButton = new JButton("Hledat");
		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> newList = new ArrayList<>();
				for (String string : originalList)
				{
					if (string.matches(String.format(".*%s.*", searchText.getText())))
					{
						newList.add(string);
					}
				}
				list.setModel(new AbstractListModel() {
					String[] stockArr = new String[newList.size()];
					String[] values = newList.toArray(stockArr);
					
					public int getSize() {
						return values.length;
					}

					public Object getElementAt(int index) {
						return values[index];
					}
				});
				btnClear.setEnabled(true);
			}
		});
		searchPanel.add(btnSearchButton);
		
		JButton btnNewEntry = new JButton("Nový záznam");
		btnNewEntry.setEnabled(false);
		filterPanel.add(btnNewEntry);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		multimedialniData.add(horizontalStrut_3);
		
		JPanel picturePanel = new JPanel();
		multimedialniData.add(picturePanel);
		
		picturePanel.setLayout(new BoxLayout(picturePanel, BoxLayout.Y_AXIS));
		
				JPanel pictureItself = new JPanel();
				pictureItself.setBackground(Color.WHITE);
				picturePanel.add(pictureItself);
				pictureItself.setLayout(new BoxLayout(pictureItself, BoxLayout.Y_AXIS));
				
				JPanel panel_2 = new JPanel();
				pictureItself.add(panel_2);
				
						JLabel SPZLabel = new JLabel();
						panel_2.add(SPZLabel);
						SPZLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						
						JPanel picPanel = new JPanel();
						//picPanel.setMaximumSize(new Dimension(10000, 300));
						pictureItself.add(picPanel);
						
						JButton btRotateLeft1 = new JButton("Rotovat vlevo");
						btRotateLeft1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								Image src = iconToImage(SPZLabel.getIcon());
								int w = src.getHeight(null);
								int h = src.getWidth(null);
								BufferedImage dest = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
								Graphics2D g2 = dest.createGraphics();
								g2.rotate(Math.toRadians(270));
								g2.drawImage(src, 0, 0, null);
								g2.dispose();
								SPZLabel.setIcon(new ImageIcon(dest));
							}
						});
						picPanel.add(btRotateLeft1);
						
						JButton btRotateRight1 = new JButton("Rotovat vpravo");
						picPanel.add(btRotateRight1);
						
						JPanel panel_4 = new JPanel();
						picPanel.add(panel_4);
						
						JLabel label = new JLabel("Cesta:");
						panel_4.add(label);
						
						PhotoPath = new JTextField();
						PhotoPath.setText("");
						PhotoPath.setColumns(30);
						panel_4.add(PhotoPath);
						
						JButton btnPhotoLoad = new JButton("Načíst");
						panel_4.add(btnPhotoLoad);
						
						Component verticalStrut = Box.createVerticalStrut(20);
						pictureItself.add(verticalStrut);
						
						JPanel panel_1 = new JPanel();
						pictureItself.add(panel_1);
						
						JLabel CarLabel = new JLabel();
						panel_1.add(CarLabel);
						CarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						
						Component verticalStrut_1 = Box.createVerticalStrut(20);
						pictureItself.add(verticalStrut_1);
						
								JPanel SPZDetail = new JPanel();
								pictureItself.add(SPZDetail);
								//SPZDetail.setMaximumSize(new Dimension(10000,300));
								
										JButton btRotateLeft = new JButton("Rotovat vlevo");
										SPZDetail.add(btRotateLeft);
										
												JButton btnRotateRight = new JButton("Rotovat vpravo");
												SPZDetail.add(btnRotateRight);
												
												JPanel panel_3 = new JPanel();
												SPZDetail.add(panel_3);
												
												JLabel lblNewLabel_1 = new JLabel("Cesta:");
												panel_3.add(lblNewLabel_1);
												
												SPZpath = new JTextField();
												SPZpath.setText("");
												panel_3.add(SPZpath);
												SPZpath.setColumns(30);
												
												JButton btnSPZLoad = new JButton("Načíst");
												panel_3.add(btnSPZLoad);
												
												JPanel panel = new JPanel();
												panel.setMaximumSize(new Dimension(1000,100));
												pictureItself.add(panel);
												
												JLabel lblNewLabel = new JLabel("SPZ:");
												panel.add(lblNewLabel);
												lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
												
												SPZ = new JTextField();
												SPZ.setEditable(false);
												panel.add(SPZ);
												SPZ.setColumns(20);
												
												JButton btnSave = new JButton("Uložit");
												btnSave.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) {
														myCar.update(myCar.getSpz(), iconToImage(CarLabel.getIcon()), iconToImage(SPZLabel.getIcon()), myCar.getPobyts());
													}
												});
												btnSave.setEnabled(false);
												panel.add(btnSave);

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
		
		JButton btnTempRefresh = new JButton("Obnovit");
		btnTempRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshTemp();
			}
		});
		DeleteButtonsPanel.add(btnTempRefresh);
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				myCar = Vozidlo.selectById(list.getSelectedValue());
				SPZ.setText(myCar.getSpz());
				SPZLabel.setIcon(new ImageIcon(myCar.getSpzFoto()));
				CarLabel.setIcon(new ImageIcon(myCar.getFoto()));
				btnSave.setEnabled(true);
			}
		});
	}

	protected void RefreshTemp() {
			List<Pobyt> pobyty = Pobyt.getCurrentResidence();
			List<String[]> arrayList = new ArrayList();
			for (Pobyt pob : pobyty)
			{
				
			}
		//	currentResidence.setModel(new PobytModel(pobyty.ar));
	}

	private String[] originalListRefresh() {
		List<String> SPZlist = new ArrayList<String>();
		List<Vozidlo> v2 = Vozidlo.list();
		for (Vozidlo v : Vozidlo.list())
		{
			
		SPZlist.add(v.getSpz());
		}
		String[] stockArr = new String[SPZlist.size()];
		return SPZlist.toArray(stockArr);
	}
	
	static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon)icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = 
              GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
	
	private static BufferedImage toBufferedImage(Image src) {
		int w = src.getWidth(null);
		int h = src.getHeight(null);
		int type = BufferedImage.TYPE_INT_RGB; // other options
		BufferedImage dest = new BufferedImage(w, h, type);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(src, 0, 0, null);
		g2.dispose();
		return dest;
	}
	
}
