import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JFrame{

	LastTime lt;
	Calculation cc;
	JPanel panels;
	
	public Frame() throws IOException
	{
		this.setSize(500, 285);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		SetPanels();
		this.add(panels,BorderLayout.CENTER);
		((CardLayout)panels.getLayout()).show(panels,"lastime");
		this.setVisible(true);
	}
	
	public void SetPanels() throws IOException
	{
		panels = new JPanel();
		panels.setLayout(new CardLayout());
		cc = new Calculation();
		lt = new LastTime(panels,cc);
		panels.add(lt,"lastime");
		panels.add(cc,"calculation");
	}
	
}
