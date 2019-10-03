package com.pkmn;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

/* 
 * Contains graphics of the game which is basically text. Corresponds to the view in MVC architecture.
 */

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;
	public JTextArea jl;
	public JTextField jtf;
	public JScrollPane sp;
	public Sound music;
	public Sound se;
	public JLabel player = new JLabel(new ImageIcon());
	public JLabel ia = new JLabel(new ImageIcon());
	public JLabel pika = new JLabel(new ImageIcon());
	public HpContainer playerContainer;
	public HpContainer iaContainer;
	public HpBar playerBar;
	public HpBar iaBar = new HpBar();
	public JLabel hpCounter = new JLabel("");
	public JLabel playerStatus = new JLabel("");
	public JLabel iaStatus = new JLabel("");
	public JLabel date = new JLabel("Coucou");
	String whatToChoose = "level";
	public Window() throws IOException
	{
		this.setLayout(null);
		this.setJl(new JTextArea("Loading...\n"));
		this.sp = new JScrollPane(jl);
		this.sp.setBounds(15,15,450,600);
		this.setJtf(new JTextField(2));
		this.jtf.setBounds(15,625,450,30);
		this.add(this.jtf);
		this.add(sp);
		this.pika = new JLabel(new ImageIcon("src/res/sprites/front/25.png"));
		this.pika.setBounds(650,645,56,56);
		this.add(pika);
		this.hpCounter.setBounds(545,520,100,100);
		this.add(hpCounter);
		this.add(playerStatus);
		this.add(iaStatus);
		this.date.setBounds(500,600,100,100);
		this.add(date);
		this.setTitle("Textual Pokémon game");
		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		SwingWorker tDate = new SwingWorker()
		{
			protected Object doInBackground() throws Exception 
			{
				while (true)
				{
					Date d = new Date();
					DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
					String t = dateFormat2.format(d);
					date.setText("It is : "+t);
					Thread.sleep(1000);
				}
			}
		};
		tDate.execute();
		this.music = new Sound(this.getClass().getResource("/res/audio/menu.wav"));
		this.music.playLoop();
	}
	
	private void setJl(JTextArea jl)
	{
		this.jl = jl;
		this.jl.setLineWrap(true);
		this.jl.setWrapStyleWord(true);
		this.jl.setEditable(false);
	}
	
	public void setJl(String s)
	{
		this.jl.setText(s);
	}
	
	private void setJtf(JTextField jtf)
	{
		this.jtf = jtf;
	}
	
	public void clear()
	{
		this.setJl("");
	}
	
	public void logTrace(String s)
	{
		if (s != null)
		{
			this.jl.setText(this.jl.getText()+s+"\n");
			this.jl.setCaretPosition(this.jl.getDocument().getLength());
		}
	}
	
	public void drawPlayerSprite(String path)
	{
		this.removePlayerSprite();
		ImageIcon poke = new ImageIcon(path);
		Image image = poke.getImage();
		Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
		poke = new ImageIcon(newimg);
		this.player = new JLabel(poke);
		this.player.setBounds(525,400,120,120);
		this.add(this.player);
		this.repaint();
	}
	
	public void removePlayerSprite()
	{
		this.player.setIcon(null);
	}
	
	public void drawIaSprite(int number)
	{
		this.removeIaSprite();
		ImageIcon poke = new ImageIcon("src/res/sprites/front/"+number+".png");
		Image image = poke.getImage();
		Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
		poke = new ImageIcon(newimg);
		this.ia = new JLabel(poke);
		this.ia.setBounds(525,100,120,120);
		this.add(this.ia);
		this.repaint();
	}
	
	public void removeIaSprite()
	{
		this.ia.setIcon(null);
	}
	
	public void drawPlayerHp(Pokemon p)
	{
		float hpLeft = ((float) p.getCurrentHp()/p.getBaseHp()) * 100;
		if (playerBar == null)
		{
			this.playerBar = new HpBar();
			this.playerContainer = new HpContainer();
			this.playerContainer.setBounds(535,530,110,30);
			this.playerBar.setBounds(535,530,100,25);
			this.add(playerContainer);
			this.add(playerBar);
			this.playerStatus = new JLabel ("");
			this.add(playerStatus);
			this.playerStatus.setBounds(575,493,50,50);
		}
		this.hpCounter.setText("HP : "+p.getCurrentHp()+"/"+p.getBaseHp());
		if (hpLeft <= 20)
			this.playerBar.changeColor(Color.red);
		else if (hpLeft <= 50)
			this.playerBar.changeColor(Color.orange);
		else
			this.playerBar.changeColor(Color.green);
		@SuppressWarnings("rawtypes")
		SwingWorker sw = new SwingWorker()
		{
			protected Object doInBackground() throws Exception 
			{
				while (playerBar.getWidth() != (int) hpLeft)
				{
					System.out.println("Coucou");
					if (playerBar.getWidth() > hpLeft)
						playerBar.setBounds(535, 530, playerBar.getWidth() - 1, 25);
					else
						playerBar.setBounds(535, 530, playerBar.getWidth() + 1, 25);
					
					Thread.sleep(100);
				}
				if (hpLeft == 0)
					playerBar = null;
				return null;
			}
			
			public void done() {}
		};
		sw.execute();
		if (p.getStatus()!=0)
		{
			switch (p.getStatus())
			{
				case 1 : 
					this.playerStatus.setText("PAR");
					break;
				case 2 : 
					this.playerStatus.setText("SLP");
					break;
				case 3 : 
					this.playerStatus.setText("PSN");
					break;
				case 4 : 
					this.playerStatus.setText("BRN");
					break;
				case 5 : 
					this.playerStatus.setText("FRN");
					break;
			}
		}
		else
			this.playerStatus.setText("");
		this.repaint();
	}
		
	public void drawIaHp(Pokemon p)
	{
		this.remove(iaBar);
		this.iaContainer = new HpContainer();
		this.iaContainer.setBounds(535,240,110,30);
		this.add(iaContainer);
		this.iaBar = new HpBar();
		float hpLeft = ((float) p.getCurrentHp()/p.getBaseHp()) * 100;
		if (hpLeft <= 20)
			this.iaBar.changeColor(Color.red);
		else if (hpLeft <= 50)
			this.iaBar.changeColor(Color.orange);
		this.iaBar.setBounds(535,240,(int)hpLeft,25);
		this.add(iaBar);
		this.remove(iaStatus);
		if (p.getStatus()!=0)
		{
			switch (p.getStatus())
			{
				case 1 : 
					this.iaStatus = new JLabel("PAR");
					break;
				case 2 : 
					this.iaStatus = new JLabel("SLP");
					break;
				case 3 : 
					this.iaStatus = new JLabel("PSN");
					break;
				case 4 : 
					this.iaStatus = new JLabel("BRN");
					break;
				case 5 : 
					this.iaStatus = new JLabel("FRN");
					break;
			}
			this.iaStatus.setBounds(575,203,50,50);
			this.add(iaStatus);
		}
		else
		{
			this.iaStatus = new JLabel ("");
			this.add(iaStatus);
		}
		this.repaint();
	}
}