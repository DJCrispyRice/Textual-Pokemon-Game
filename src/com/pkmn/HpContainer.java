package com.pkmn;

import java.awt.*;
import javax.swing.*;


public class HpContainer extends JLabel
{
	private static final long serialVersionUID = 1L;
	public void paint(Graphics g) 
	{
        super.paint(g);
        /*double width = this.getSize().getWidth();
        double height = this.getSize().getHeight();*/
        g.setColor(Color.black);
        g.drawRect(0, 0, (int) 100, (int) 20);
     }             
}

