package com.pkmn;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Map 
{
	String name;
	int id;
	ArrayList <Pokemon> encounters;
	ArrayList <String[][]> texts; // First dimension : text. Second dimension : Flag
	ArrayList <String[][]> links; // First dimension : id of the map. Second dimension : how the map is accessible (walk, surf...)
	Shop shop;
	public Map (int i, GameData gd) throws NumberFormatException, CloneNotSupportedException
	{
		this.setId(i);
		Document doc = null;
		Element racine;
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			doc = sxb.build(new File("src/res/maps/"+this.getId()+".xml"));
		}
		catch (Exception e) {}
		racine = doc.getRootElement();
		this.name = racine.getChildren("head").get(0).getChildText("name");
		this.loadEncounters(gd, racine);
		this.loadLinks(racine);
		this.loadTexts(racine);
		this.loadShop(gd, racine);
	}
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public ArrayList<Pokemon> getEncounters() 
	{
		return encounters;
	}
	
	public void setEncounters(ArrayList<Pokemon> encounters) 
	{
		this.encounters = encounters;
	}
	
	public ArrayList<String[][]> getTexts() 
	{
		return texts;
	}
	
	public void setTexts(ArrayList<String[][]> texts) 
	{
		this.texts = texts;
	}
	
	public ArrayList<String[][]> getLinks() 
	{
		return links;
	}
	
	public void setLinks(ArrayList<String[][]> links) 
	{
		this.links = links;
	}
	
	public void loadEncounters(GameData gd, Element racine) throws NumberFormatException, CloneNotSupportedException
	{
		List<Element> listEnc = racine.getChildren("encounters");
		Iterator<Element> ite = listEnc.iterator();
		while (ite.hasNext())
		{
			this.encounters = new ArrayList<Pokemon>();
			Element courant = (Element)ite.next();
			List<Element> listEnc2 = courant.getChildren("encounter");
			Iterator<Element> ite2 = listEnc2.iterator();
			while (ite2.hasNext())
			{
				Element courant2 = (Element)ite2.next();
				this.encounters.add(gd.allPkmn[Integer.parseInt(courant2.getChildText("poke"))].clone());
				this.encounters.get(Integer.parseInt(courant2.getChildText("id"))).setLevel(Integer.parseInt(courant2.getChildText("level")));
			}
		}
	}
	
	public void loadLinks(Element racine)
	{
		List<Element> listLink = racine.getChildren("links");
		Iterator<Element> ite = listLink.iterator();
		while (ite.hasNext())
		{
			this.links = new ArrayList<String[][]>();
			Element courant = (Element)ite.next();
			List<Element> listLink2 = courant.getChildren("link");
			Iterator<Element> ite2 = listLink2.iterator();
			while (ite2.hasNext())
			{
				Element courant2 = (Element)ite2.next();
				String[][] link = new String[1][2];
				link[0][0] = courant2.getChildText("area");
				link[0][1] = courant2.getChildText("way");
				this.links.add(link);
			}
		}
	}
	
	public void loadTexts(Element racine)
	{
		List<Element> listTexts = racine.getChildren("texts");
		Iterator<Element> ite = listTexts.iterator();
		while (ite.hasNext())
		{
			this.texts = new ArrayList<String[][]>();
			Element courant = (Element)ite.next();
			List<Element> listTexts2 = courant.getChildren("text");
			Iterator<Element> ite2 = listTexts2.iterator();
			while (ite2.hasNext())
			{
				Element courant2 = (Element)ite2.next();
				String[][] text = new String[1][2];
				text[0][0] = courant2.getChildText("speech");
				text[0][1] = courant2.getChildText("flag");
				this.texts.add(text);
			}
		}
	}
	
	public void loadShop(GameData gd, Element racine)
	{
		List<Element> listShop = racine.getChildren("shop");
		if (!listShop.isEmpty())
		{
			/*Iterator<Element> ite = listShop.iterator();
			while (ite.hasNext())
			{
				
			}*/
		}
		else
			this.shop = null;
	}

}
