package com.pkmn;
import java.util.ArrayList;

/* 
 * This class contains all the types that Pokémon or attack can have with its name. It also contains strength and weakness in arrays (since every type may have
 * multiple strength/weakness).
 * It is necessary to create all the types first then assign them their strength/weakness so only one constructor is needed.
 */

public class Type 
{
	String name;
	int id;
	ArrayList <Type> strength;
	ArrayList <Type> weak;
	ArrayList <Type> useless;
	//Single constructor since all types will be created only with their names/id. Then, we will assign strength/weakness/immunity manually.
	public Type(String name, int id)
	{
		this.setName(name);
		this.setId(id);
		this.strength = new ArrayList <Type> ();
		this.weak = new ArrayList <Type>();
		this.useless = new ArrayList <Type> ();
	}
	//Getters/setters, useless ones have been removed.	
	public String getName() 
	{
		return name;
	}
	
	private void setName(String name) 
	{
		this.name = name;
	}
	
	public int getId() 
	{
		return id;
	}
	
	private void setId(int id) 
	{
		this.id = id;
	}
	
	public ArrayList<Type> getStrength() 
	{
		return strength;
	}
	
	public String getStrength(int i)
	{
		return strength.get(i).getName();
	}
	
	public ArrayList<Type> getWeak() 
	{
		return weak;
	}
	
	public String getWeak(int i)
	{
		return weak.get(i).getName();
	}
	
	public ArrayList<Type> getUseless() 
	{
		return useless;
	}
	
	public String getUseless(int i)
	{
		return useless.get(i).getName();
	}
}
