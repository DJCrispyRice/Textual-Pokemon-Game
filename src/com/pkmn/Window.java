package com.pkmn;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

/* 
 * Contains graphics of the game which is basically text. Corresponds to the view in MVC architecture.
 */

public class Window extends JFrame 
{
	private static final long serialVersionUID = 1L;
	public JLabel jl;
	public JTextField jtf;
	public static String choice = "";
	public Window()
	{
		this.setLayout(null);
		this.setJl(new JLabel("<html>Loading...<br>"));
		this.jl.setBounds(15,15,450,600);
		this.setJtf(new JTextField(2));
		this.jtf.setBounds(15,625,450,30);
		this.add(this.jtf);
		this.add(jl);
		jtf.addKeyListener(new KbListener());
		this.setTitle("Textual Pokémon game");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void setJl(JLabel jl)
	{
		this.jl = jl;
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
	
	class KbListener implements KeyListener
	{
		public void keyReleased(KeyEvent event) 
	    {
	    	if (event.getKeyChar()==KeyEvent.VK_ENTER)
	    	{
	    		if (jl.getText().matches("[0-9]*") && jl.getText() != "")
	    			choice = jl.getText();
	    		jtf.setText("");
	    	}
	    }
		public void keyTyped(KeyEvent event) {}
		public void keyPressed(KeyEvent event) {}   
	}
	
	public static void main(String[] args) throws InterruptedException 
	{
		Window win = new Window();
		GameData gd = new GameData(win);
		Thread.sleep(2000);
		win.clear();
		Thread.sleep(500);
		win.setJl("<html>Welcome in the Pokémon Textual Game !<br>Type 1 and Enter to start.");
	}
}




