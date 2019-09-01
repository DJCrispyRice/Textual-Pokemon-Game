package com.pkmn;
import java.awt.*;
import java.io.IOException;
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
	public JLabel pika;
	public HpContainer playerContainer;
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
		this.setTitle("Textual Pokémon game");
		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
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
	
	public void drawFrontPlayerSprite(int number)
	{
		this.removePlayerSprite();
		ImageIcon poke = new ImageIcon("src/res/sprites/front/"+number+".png");
		Image image = poke.getImage();
		Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
		poke = new ImageIcon(newimg);
		this.player = new JLabel(poke);
		this.player.setBounds(525,400,120,120);
		this.add(this.player);
		this.repaint();
	}
	
	public void drawBackPlayerSprite(int number)
	{
		this.removePlayerSprite();
		ImageIcon poke = new ImageIcon("src/res/sprites/back/"+number+".png");
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
	
	public void drawPlayerContainer()
	{
		this.playerContainer = new HpContainer();
		this.playerContainer.setBounds(535,530,110,30);
		this.add(playerContainer);
		this.repaint();
	}
	
	public void drawPlayerHp(Pokemon p)
	{
		
	}
	
	public void drawIaContainer()
	{
		this.playerContainer = new HpContainer();
		this.playerContainer.setBounds(535,230,110,30);
		this.add(playerContainer);
		this.repaint();
	}
	
	public void drawIaHp(Pokemon p)
	{
		
	}
}