package cz.vutbr.fit.pdb.project01;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Hibernate;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.Zona;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Component;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JTextField password;
	private JTextField url;
	private JPanel userPanel;
	private JPanel passwordPanel;
	private JPanel urlPanel;
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JLabel urlLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
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
	public LoginForm() {
		setResizable(false);
		setTitle("Login Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 400, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		userPanel = new JPanel();
		contentPane.add(userPanel);

		userLabel = new JLabel("Username:");
		userPanel.add(userLabel);

		user = new JTextField();
		userPanel.add(user);
		user.setColumns(20);

		passwordPanel = new JPanel();
		contentPane.add(passwordPanel);

		passwordLabel = new JLabel("Password:");
		passwordPanel.add(passwordLabel);

		password = new JTextField();
		passwordPanel.add(password);
		password.setColumns(20);

		urlPanel = new JPanel();
		contentPane.add(urlPanel);

		urlLabel = new JLabel("URL:");
		urlPanel.add(urlLabel);

		url = new JTextField();

		url.setText("jdbc:oracle:thin:@//gort.fit.vutbr.cz:1521/gort.fit.vutbr.cz");
		urlPanel.add(url);
		url.setColumns(30);
		

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TableBase.login(url.getText(), "xkrbec02", "hjdjob9b");
				//MainForm myMainForm = new MainForm(url.getText(), user.getText());
				//myMainForm.setVisible(true);
				//dispose();
				if (TableBase.login(url.getText(), user.getText(), password.getText())) {
					MainForm myMainForm = new MainForm(url.getText(), user.getText());
					myMainForm.setVisible(true);
					dispose();
					// test - shoudl not fail
					Zona.list();
				} else {
					user.setText("");
					password.setText("");
				}
			}
		});
		contentPane.add(btnNewButton);

		String Dusername = System.getProperty("username");
		String Dpassword = System.getProperty("password");
		String Durl = System.getProperty("url");
		if (Durl != null) {
			url.setText(Durl);
		}
		if (Dpassword != null) {
			password.setText(Dpassword);
		}
		if (Dusername != null) {
			user.setText(Dusername);
		}
	}

}
