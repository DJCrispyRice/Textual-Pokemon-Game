package com.pkmn;
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
	String whatToChoose = "start";
	public Window()
	{
		this.setLayout(null);
		this.setJl(new JTextArea("Loading...\n"));
		this.sp = new JScrollPane(jl);
		this.sp.setBounds(15,15,450,600);
		this.setJtf(new JTextField(2));
		this.jtf.setBounds(15,625,450,30);
		this.add(this.jtf);
		this.add(sp);
		this.setTitle("Textual Pokémon game");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
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
}