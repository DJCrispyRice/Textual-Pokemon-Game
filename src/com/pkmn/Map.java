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
	ArrayList <String> texts;
	ArrayList <Map> links;
	public Map (int i, GameData gd)
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
				this.encounters.add(gd.allPkmn[Integer.parseInt(courant2.getChildText("poke"))]);
			}
		}
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
	
	public ArrayList<String> getTexts() 
	{
		return texts;
	}
	
	public void setTexts(ArrayList<String> texts) 
	{
		this.texts = texts;
	}
	
	public ArrayList<Map> getLinks() 
	{
		return links;
	}
	
	public void setLinks(ArrayList<Map> links) 
	{
		this.links = links;
	}

}
