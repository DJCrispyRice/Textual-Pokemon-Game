package com.pkmn;


/* 
 * This class contains a Pokémon statistic (such as Attack, Defense...). It will contain base stat, current stat and stage. Only HP is not considered as a stat since it works in a different way.
 */

public class Stat 
{
	String Name;
	int base;
	int current;
	int stage = 0;
	
	public Stat(String name, int base)
	{
		this.setName(name);
		this.setBase(base);
		this.setCurrent(base);
	}
	
	//Getters/Setters
	public String getName() 
	{
		return Name;
	}

	public void setName(String name) 
	{
		Name = name;
	}

	public int getBase() 
	{
		return base;
	}

	public void setBase(int base) 
	{
		this.base = base;
	}

	public int getCurrent() 
	{
		return current;
	}

	public void setCurrent(int current) 
	{
		this.current = current;
	}

	public int getStage() 
	{
		return stage;
	}
	
	//Setting a stage will also calculate the right value for the current stat value.
	public void setStage(int stage) 
	{
		this.stage = stage;
		switch (stage)
		{
			case 6 :
				this.setCurrent((int) (this.getBase() * 4));
				break;
			case 5 : 
				this.setCurrent((int) (this.getBase() * 3.5));
				break;
			case 4 : 
				this.setCurrent((int) (this.getBase() * 3));
				break;
			case 3 : 
				this.setCurrent((int) (this.getBase() * 2.5));
				break;
			case 2 : 
				this.setCurrent((int) (this.getBase() * 2));
				break;
			case 1 :
				this.setCurrent((int) (this.getBase() * 1.5));
				break;
			case 0 :
				this.setCurrent(this.getBase());
				break;
			case -1 :
				this.setCurrent((int) (this.getCurrent() * 0.66));
				break;
			case -2 :
				this.setCurrent((int) (this.getCurrent() * 0.5));
				break;
			case -3 :
				this.setCurrent((int) (this.getCurrent() * 0.4));
				break;
			case -4 :
				this.setCurrent((int) (this.getCurrent() * 0.33));
				break;
			case -5 :
				this.setCurrent((int) (this.getCurrent() * 0.28));
				break;
			case -6 :
				this.setCurrent((int) (this.getCurrent() * 0.25));
				break;
		}
	}
}
