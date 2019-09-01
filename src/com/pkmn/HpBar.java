package com.pkmn;

import java.awt.*;
import javax.swing.*;

public class HpBar extends JLabel
{
	Color c= Color.green;
	private static final long serialVersionUID = 1L;
	public void paint(Graphics g) 
	{
        super.paint(g);
        g.setColor(c);
        g.fillRect(0, 0, (int) 100, (int) 20);
    }
	
	public void changeColor(Color c)
	{
		this.c = c;
	}
}
