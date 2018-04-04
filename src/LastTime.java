import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LastTime extends JPanel implements ActionListener{

	JLabel Info;
	JButton GoOn;
	JPanel outside;
	Calculation cc;
	int lt;
	
	public LastTime(JPanel jf,Calculation cc) throws IOException
	{
		outside = jf; this.cc = cc;
		SetGoOn();
		ReadLastTime();
		ShowTheLastTime();
	}
	
	public void SetGoOn()
	{
		GoOn = new JButton("开始新一轮");
		GoOn.addActionListener(this);
		GoOn.setActionCommand("goon");
		GoOn.setBounds(200,200,100,30);
		this.setSize(500, 285);
		this.add(GoOn);
	}

	public void ReadLastTime() throws IOException
	{
		String a = "C:/test.txt";
		FileInputStream fis = new FileInputStream(a);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		lt = Integer.parseInt(s);
		System.out.println(lt);
	}
	
	public void ShowTheLastTime()
	{
		Info = new JLabel();
		Info.setFont(new Font(null,Font.PLAIN,25));
		this.add(Info);
		Info.setBounds(200,300,200,20);
		Info.setText("最近一次的成绩是"+lt+",要再来一次吗");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("goon"))
		{
			cc.SetQuestion();
			((CardLayout)outside.getLayout()).show(outside,"calculation");
			Thread start = new Thread(cc);
			start.start();
		}
	}
	
}
