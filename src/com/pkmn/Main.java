/*
 * TO DO :
 * - Finish special attacks > OK
 * - Enable switching in battle > OK
 * - Better pokémon selection > OK
 * - Better code organisation > OK
 * - Add IA > OK
 * - Sound (cries, music) > OK
 * - Graphics (not sure if it suits this project yet)
 */

package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main implements ActionListener, KeyListener
{
	Window win;
	GameData gd;
	String choice;
	Battle b = new Battle(this.gd);
	public Main() throws InterruptedException
	{
		win = new Window();
		gd = new GameData(win);
		win.clear();
		win.jtf.addActionListener(this);
		win.jtf.addKeyListener(this);
		win.jtf.setFocusTraversalKeysEnabled(false);
		win.logTrace("Welcome in the Pokémon Textual Battle Game !\nPress Enter to start.");
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		@SuppressWarnings("unused")
		Main m = new Main();
	}
	
	//Autocompletion using TAB key
	public void keyPressed(KeyEvent event) 
	{
		choice = win.jtf.getText().toUpperCase();
		if (event.getKeyChar() == KeyEvent.VK_TAB && win.whatToChoose.equals("team") && !choice.equals("")) 
		{
			for (int i = 1; i < 152 ; i++)
			{
				if (this.gd.allPkmn[i].getName().startsWith(choice))
				{
					win.jtf.setText(this.gd.allPkmn[i].getName());
					break;
				}
			}
		}
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		choice = win.jtf.getText().toUpperCase();
		win.jtf.setText("");
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try 
		{
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document= builder.parse(new File("src/res/maps/0.xml"));
			final Element racine = document.getDocumentElement();
			final NodeList racineNoeuds = racine.getChildNodes();
			final Element head = (Element) racineNoeuds.item(1);
			final Element name = (Element) head.getElementsByTagName("name").item(0);
			System.out.println("Welcome to "+name.getTextContent());
			final Element texts = (Element) racineNoeuds.item(5);
			final Element speech = (Element) texts.getElementsByTagName("speech").item(0);
			System.out.println(speech.getTextContent());
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
