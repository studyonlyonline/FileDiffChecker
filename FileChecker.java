import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

class Design
{
	JFrame f;
	String fileName1=null,fileName2=null;

	Design()
	{
		f=new JFrame("File Difference Checker");
		
		//Button 1 for file 1
		JButton button1=new JButton("Browse");
		button1.setBounds(50,10,100,30);

		JTextField textField1=new JTextField();
		textField1.setBounds(25,50,200,30);
		JTextArea fileData1=new JTextArea();
		JScrollPane sp1=new JScrollPane(fileData1);
		sp1.setBounds(25,100,300,300);	
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fileName1=getFileName();
				textField1.setText(fileName1);
				fillData(fileName1,fileData1);
			}

		});

		//adding for 1st
		f.add(button1);
		f.add(textField1);
		f.add(sp1);

		//Button 2 for file 2
		JButton button2=new JButton("Browse");
		button2.setBounds(600,10,100,30);
		JTextField textField2=new JTextField();
		textField2.setBounds(500,50,200,30);
		JTextArea fileData2=new JTextArea();
		JScrollPane sp2=new JScrollPane(fileData2);
		sp2.setBounds(400,100,300,300);
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fileName2=getFileName();
				textField2.setText(fileName2);
				fillData(fileName2,fileData2);
			}

		});
		
		//add button1 and button 2
		f.add(button2);
		f.add(textField2);
		f.add(sp2);

		//compare files 
		JLabel label=new JLabel("Logs");
		label.setBounds(25,425,30,15);
		JTextArea logs=new JTextArea();
		JScrollPane sp3=new JScrollPane(logs);
		sp3.setBounds(25,450,600,100);
		JButton compareButton=new JButton("compare");
		compareButton.setBounds(650,450,100,50);

		//add the actionListener to the compare button
		compareButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				compareFiles(fileName1,fileName2,logs,fileData1,fileData2);
			}
		});

		f.add(label);
		f.add(sp3);
		f.add(compareButton);
		//frame size
		f.setSize(800,700);
		f.setLayout(null);
		f.setVisible(true);

		//close the frame
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	String getFileName()
	{
		String temp="F:\\competetive coding\\java programming\\FileDiffChecker";
		String userDir=System.getProperty("user.home");
		// JFileChooser fileChooser=new JFileChooser(userDir+"/Dextop");
		JFileChooser fileChooser=new JFileChooser(temp);
		int returnValue=fileChooser.showOpenDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION)
		{
			return fileChooser.getSelectedFile().getPath();
		}
		else
		{
			return null;
		}
	}

	void fillData(String fileName1,JTextArea textArea)
	{
		FileReader fr=null;
		BufferedReader br=null;

		try
		{
			fr=new FileReader(fileName1);
			br=new BufferedReader(fr);
			String line;
			StringBuilder sb=new StringBuilder();
			while((line=br.readLine())!=null)
			{
				sb.append(line);
				sb.append("\n");
			}
			textArea.setText(sb.toString());
		}
		catch (Exception e) 
		{
			e.printStackTrace();	
		}
	}

	void compareFiles(String fileName1,String fileName2,JTextArea logs,JTextArea fileData1,JTextArea fileData2)
	{
		FileReader fileReader1=null;
		FileReader fileReader2=null;
		BufferedReader br1=null;
		BufferedReader br2=null;
		Highlighter highlighter1 =fileData1.getHighlighter();
		HighlightPainter painter1=new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
		Highlighter highlighter2 =fileData2.getHighlighter();
		HighlightPainter painter2=new DefaultHighlighter.DefaultHighlightPainter(Color.green);
		fileData1.setText(null);
		fileData2.setText(null);
		try
		{
			StringBuilder sb=new StringBuilder("");
			fileReader1=new FileReader(fileName1);
			br1=new BufferedReader(fileReader1);
			fileReader2=new FileReader(fileName2);
			br2=new BufferedReader(fileReader2);
			
			String line1=null,line2=null;
			int noOfLines1=0,noOfLines2=0,start1=0,start2=0;
			while(((line1=br1.readLine())!=null) && ((line2=br2.readLine())!=null))
			{
				noOfLines1++;
				noOfLines2++;				
				fileData1.append(line1+"\n");
				fileData2.append(line2+"\n");
				
				if(!line1.equals(line2))
				{
					highlighter1.addHighlight(start1,start1+line1.length(),painter1);
					highlighter2.addHighlight(start2,start2+line2.length(),painter2);
					sb.append("Line no "+noOfLines1+"\n");
				}
				start1+=line1.length()+1;
				start2+=line2.length()+1;
			}
			logs.setText(sb.toString());
		}
		catch(FileNotFoundException ex)
		{
			logs.setText("COuld not load the files");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}

class FileChecker
{
	public static void main(String[] args) 
	{
		Design panel=new Design();	
	}
}
