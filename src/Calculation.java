import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculation extends JPanel implements ActionListener,Runnable{

	JFrame outside;
	
	JButton ok,languagechanger; Boolean English = false;
	
	JTextField input;
	int answer; int score;
	
	JLabel time; int timer = 0; Thread count;
	
	JLabel remaining; int reminder;
	
	Random random; int result;
	ArrayList<Integer> numbers;
	ArrayList<String> signs;
	JLabel showquestion;	
	
	ArrayList<Integer> scores;
	
	public Calculation() throws IOException
	{
		initialization();
		SetLanguageChanger();
		SetOk();
		SetInput();
		SetTimer();
		SetRemaining();
		PutQuestion();
	}
	
	public void initialization()
	{
		this.setSize(500, 285);
		random = new Random();
		this.setLayout(null);
	}
	
	public void SetLanguageChanger()
	{
		languagechanger = new JButton("更改语言为英文");
		languagechanger.setActionCommand("toEnglish");
		languagechanger.addActionListener(this);
		languagechanger.setBounds(10,10,200,40);
		this.add(languagechanger);
		
	}
	
	public void SetOk()
	{
		ok = new JButton("确认");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		ok.setBounds(210,190,70,30);
		this.add(ok);
	}
	
	public void SetInput() 
	{
		input = new JTextField(15);
		input.setFont(new Font(null,Font.PLAIN,20));
		input.setLocation(210,150);
		input.setSize(70, 30);
		this.add(input);
	}

	public void SetTimer()
	{
		time = new JLabel("已消耗时间 0 秒");
		time.setFont(new Font(null,Font.PLAIN,20));
		timer = 0;
		time.setSize(200,20);
		time.setLocation(280,15);
		this.add(time);
	}

	public void SetRemaining()
	{
		remaining = new JLabel("已完成0/20");
		remaining.setFont(new Font(null,Font.PLAIN,20));
		reminder = 0;
		remaining.setBounds(30,180,200,30);
		this.add(remaining);
	}
	
	public void PutQuestion()
	{
		showquestion = new JLabel();
		showquestion.setBounds(160,80,300,40);
		showquestion.setFont(new Font(null,Font.PLAIN,25));
		scores = new ArrayList<Integer>();
		score = 0;
		this.add(showquestion);	
	}
	
	public void SetQuestion()
	{
		numbers = new ArrayList<Integer>();
		signs = new ArrayList<String>();
		
		result = random.nextInt(100)+1;
		System.out.println("answer is "+ result);
		
		int pick1 = random.nextInt(2);
		System.out.println("Pick1 = " + pick1);
		//第一轮
		if(pick1 == 0)
		{
			int smaller = random.nextInt(100);
			int bigger = result+smaller;
			numbers.add(bigger);
			numbers.add(smaller);
			signs.add("-");
			System.out.println("round1 :" +bigger+"-"+smaller);
		}
		else
		{
			int factor1 = random.nextInt(result);
			int factor2 = result - factor1;
			numbers.add(factor1);
			numbers.add(factor2);
			signs.add("+");
			System.out.println("round1 :" +factor1+"+"+factor2);
		}
		//第二轮
		int pick2_1 = random.nextInt(2);
		System.out.println("Pick2_1 = " + pick2_1);
		if(pick2_1==0) //展开左边那个数字
		{
			int pick2_2 = random.nextInt(2);
			System.out.println("Pick2_2 = " + pick2_2);
			if(pick2_2==0)
			{
				int smaller = random.nextInt(100);
				int bigger = numbers.get(0)+smaller;
				numbers.set(0,bigger);
				numbers.add(1,smaller);
				signs.add(0,"-");
			}
			else
			{
				int factor1 = Math.abs(random.nextInt()%numbers.get(0));
				int factor2 = numbers.get(0) - factor1;
				numbers.set(0, factor1);
				numbers.add(1, factor2);
				signs.add(0,"+");
			}
		}
		else //展开右边那个数字
		{
		    int pick2_2 = random.nextInt(2);
			System.out.println("Pick2_2 = " + pick2_2);
			if(pick2_2==0)//以减法展开
			{
				
				if(signs.get(0).equals("-"))
				{
					int bigger = random.nextInt(numbers.get(0)+1);
					while(bigger<numbers.get(1)) bigger = random.nextInt(numbers.get(0)+1);
					int smaller = bigger - numbers.get(1);
					numbers.set(1,bigger);
					numbers.add(2,smaller);
					signs.add(1,"+");
				}
				else
				{
					int bigger = random.nextInt(100)+numbers.get(1);
					int smaller = bigger - numbers.get(1);
					numbers.set(1,bigger);
					numbers.add(2,smaller);
					signs.add(1,"-");
				}	
			}
			else//以加法展开
			{
				int factor1 = random.nextInt(numbers.get(1));
				int factor2 = numbers.get(1) - factor1;
				numbers.set(1, factor1);
				numbers.add(2, factor2);
				if(signs.get(0).equals("-")) signs.add(1,"-");
				else  signs.add(1,"+");
			}
		}
		//第三轮
		System.out.print("round2 : ");
		for(int i=0;i<signs.size();i++)
		{
			System.out.print(numbers.get(i));
			System.out.print(signs.get(i));
		}
		System.out.println(numbers.get(2));
		//第四轮，以乘法和除法展开;
		//先以除法展开
		int pick3_1 = random.nextInt(3);
		if(pick3_1==0)
		{
			int mf = random.nextInt(5)+1;
			int m = mf*numbers.get(0);
			numbers.set(0, m);
			numbers.add(1,mf);
			signs.add(0,"÷");
		}
		if(pick3_1==1)
		{
			int mf = random.nextInt(5)+1;
			int m = mf*numbers.get(1);
			numbers.set(1, m);
			numbers.add(2,mf);
			signs.add(1,"÷");
		}
		if(pick3_1==2)
		{
			int mf = random.nextInt(5)+1;
			int m = mf*numbers.get(2);
			numbers.set(2, m);
			numbers.add(3,mf);
			signs.add(2,"÷");
		}
		//再以乘法展开
		int pick3_2 = random.nextInt(3);
		while(pick3_2==pick3_1) pick3_2 = random.nextInt(3);
		if(pick3_2==0)
		{
			int i;
			for(i=numbers.get(0)-1;i>=1;i--)
			{
				if(numbers.get(0)%i==0) break;
			}
			numbers.set(0,numbers.get(0)/i);
			numbers.add(1,i);
			signs.add(0,"×");
		}
		
		if(pick3_2==1)
		{
			if(pick3_1==2)
			{
				int i;
				for(i=numbers.get(1)-1;i>=1;i--)
				{
					if(numbers.get(1)%i==0) break;
				}
				numbers.set(1,numbers.get(1)/i);
				numbers.add(2,i);
				signs.add(1,"×");
			}
			if(pick3_1==0)
			{
				int i;
				for(i=numbers.get(2)-1;i>=1;i--)
				{
					if(numbers.get(2)%i==0) break;
				}
				numbers.set(2,numbers.get(2)/i);
				numbers.add(3,i);
				signs.add(2,"×");
			}
		}
		
		if(pick3_2==2)
		{
			int i;
			for(i=numbers.get(3)-1;i>=1;i--)
			{
				if(numbers.get(3)%i==0) break;
			}
			numbers.set(3,numbers.get(3)/i);
			numbers.add(4,i);
			signs.add(3,"×");
		}

		System.out.print("round3 : ");
		for(int i=0;i<signs.size();i++)
		{
			System.out.print(numbers.get(i));
			System.out.print(signs.get(i));
		}
		System.out.println(numbers.get(4));
		
		String info = "";
		for(int i=0;i<signs.size();i++)
		{
			info += numbers.get(i);
			info +=signs.get(i);
		}
		info += numbers.get(numbers.size()-1);
		
		showquestion.setText(info);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comm = e.getActionCommand();
		switch(comm)
		{
			case "toEnglish" :
				languagechanger.setActionCommand("toChinese");
				languagechanger.setText("Setting language to chinese");
				remaining.setText("You've done"+reminder+"/20 Scores:"+score);
				time.setText("It's taken " + timer +" seconds");
				ok.setText("ok");
				English = true;
				break;
				
			case "toChinese" :
				languagechanger.setActionCommand("toEnglish");
				languagechanger.setText("更改语言为英文");	
				ok.setText("确认");
				remaining.setText("已完成"+reminder+"/20,得分"+score);
				time.setText("已消耗时间 " + timer +" 秒");
				English = false;
				break;
				
			case "ok" :	
				String a = input.getText();
				if(a.equals("")==false)
				{
					answer = Integer.parseInt(input.getText());
					reminder++;	
					if(reminder==20)
					{
						reminder=0;
						score=0;
						scores.add(score);
						String address = "C:/test.txt";
						FileWriter fw=null;
						try {
							fw = new FileWriter(address);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							fw.write(""+score);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							fw.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
					System.out.println(answer+";"+result);
					if(answer == result) score += 5;
					if(English==false)remaining.setText("已完成"+reminder+"/20,得分"+score);
					if(English==true)remaining.setText("You've done"+reminder+"/20 Scores:"+score);
					SetQuestion();
				}
				else
				{
					
				}
				input.setText("");
				break;
		}
	}

	public void run()
	{
		while(true)
		{
			timer ++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(English == true) time.setText("It's taken " + timer +" seconds");
			else time.setText("已消耗时间 " + timer +" 秒");
		}
	}
	
	
}
