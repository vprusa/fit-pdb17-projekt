package cz.vutbr.fit.pdb.project01;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JTextField password;
	private JPanel userPanel;
	private JPanel passwordPanel;
	private JLabel userLabel;
	private JLabel passwordLabel;

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
		setBounds(300, 300, 235, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		userPanel = new JPanel();
		contentPane.add(userPanel);
		
		userLabel = new JLabel("Username:");
		userPanel.add(userLabel);
		
		user = new JTextField();
		userPanel.add(user);
		user.setColumns(10);
		
		passwordPanel = new JPanel();
		contentPane.add(passwordPanel);
		
		passwordLabel = new JLabel("Password:");
		passwordPanel.add(passwordLabel);
		
		password = new JTextField();
		passwordPanel.add(password);
		password.setColumns(10);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(user.getText().toLowerCase().equals("admin") && password.getText().toLowerCase().equals("admin"))
				{
				MainForm myMainForm = new MainForm ();
				myMainForm.setVisible(true);
				dispose();
				}
				else
				{
				user.setText("");
				password.setText("");
				}
			}
		});
		contentPane.add(btnNewButton);
	}

}
