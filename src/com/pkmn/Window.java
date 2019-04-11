package com.pkmn;
import javax.swing.*;

/* 
 * Contains graphics of the game which is basically text. Corresponds to the view in MVC architecture.
 */

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;
	public JLabel jl;
	public JTextField jtf;
	String whatToChoose = "start";
	public Window()
	{
		this.setLayout(null);
		this.setJl(new JLabel("<html>Loading...<br>"));
		this.jl.setBounds(15,15,450,600);
		this.setJtf(new JTextField(2));
		this.jtf.setBounds(15,625,450,30);
		this.add(this.jtf);
		this.add(jl);
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
}




