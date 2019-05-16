package com.pkmn;

import java.util.ArrayList;

/* 
 * This class contains our favorite little monsters. They have a name, an Id (basically number in Pokédex), at least one type (the second may be null), attacks
 * (at least 1, max 4). Every stat is covered by "base" and "current" since attacks may occur that alter stats. Accuracy is 100 by default but can be lowered
 * by sand attack for example. 
 * Status 0 : Normal (no status) - simply deals damages
 * Status 1 : Paralysis
 * Status 2 : Sleep
 * Status 3 : Poison
 * Status 4 : Burn
 * Status 5 : Frozen
 * Status 9 : Dead
 */

public class Pokemon implements Cloneable
{
	String name;
	int id;
	int level = 50; //Since it's battling only, all Pokémon will be level 50. Useful only for damage calculation.
	Type type1;
	Type type2;
	ArrayList <Attack> attacks;
	int status = 0;
	int baseHp;
	int currentHp;
	Stat attack;
	Stat defense;
	Stat speed;
	Stat special;
	Stat accuracy = new Stat("Accuracy", 100);
	Stat evasion = new Stat("Evasion", 100);
	int countSleep = 0;
	int countConfusion = 0;
	int countDot = 0; //Count Damage Over Time
	int countBide = 0;
	int totalBideDmg = 0;
	boolean canAttack = true; //Used for flinching
	boolean prio = false;
	int twoturnstatus = 0; //Used for two-turn attacks. Will contain the ID of the attack
	
	//Empty constructor only for the fake index 0 Pokémon
	public Pokemon()
	{
		this.setName("MissingNo");
		this.setId(0);
	}
	//Constructor to instantiate a pokémon through the GameData class. Since current stats are the same as base's initially and status is always 0 at the beginning, those variables are not in the constructor.
	//Also Accuracy is 100 by default as stated in the descriptor.
	public Pokemon(String name, int id, Type type1, Type type2, int baseHp, int baseAtk,  int baseDef,  int baseSpd, int baseSpe)
	{
		this.setName(name);
		this.setId(id);
		this.setType1(type1);
		this.setType2(type2);
		this.attacks = new ArrayList<Attack>(); // We will add attacks after the Pokémon is created
		this.setBaseHp(baseHp);
		this.setCurrentHp(baseHp);
		this.setAttack(new Stat("Attack",baseAtk));
		this.setDefense(new Stat("Defense",baseDef));
		this.setSpeed(new Stat("Speed",baseSpd));
		this.setSpecial(new Stat("Special",baseSpe));
	}
	
	//Getters/Setters
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
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

	public int getLevel() 
	{
		return level;
	}

	public Type getType1() 
	{
		return type1;
	}

	private void setType1(Type type1) 
	{
		this.type1 = type1;
	}

	public Type getType2() 
	{
		return type2;
	}

	private void setType2(Type type2) 
	{
		this.type2 = type2;
	}

	public ArrayList<Attack> getAttacks() 
	{
		return attacks;
	}

	private void setAttacks(ArrayList<Attack> attacks) 
	{
		this.attacks = attacks;
	}

	public int getStatus() 
	{
		return status;
	}

	public void setStatus(int status) 
	{
		this.status = status;
	}

	public int getBaseHp() 
	{
		return baseHp;
	}

	private void setBaseHp(int baseHp) 
	{
		this.baseHp = baseHp;
	}

	public int getCurrentHp() 
	{
		return currentHp;
	}

	public void setCurrentHp(int currentHp) 
	{
		if (currentHp > 0)
		{
			if  (currentHp > this.getBaseHp())
				this.currentHp = this.getBaseHp();
			else
				this.currentHp = currentHp;
		}
		else
		{
			this.currentHp = 0;
			this.setStatus(9);
		}
	}
	
	public Stat getAttack()
	{
		return this.attack;
	}
	
	public int getAttack(String s)
	{
		if (s.equals("current"))
			return this.attack.getCurrent();
		else if (s.equals("base"))
			return this.attack.getBase();
		else if (s.equals("stage"))
			return this.attack.getStage();
		else
			return 0;
	}
	
	public void setAttack(Stat st)
	{
		this.attack = st;
	}

	public Stat getDefense()
	{
		return this.defense;
	}
	
	public int getDefense(String s)
	{
		if (s.equals("current"))
			return this.defense.getCurrent();
		else if (s.equals("base"))
			return this.defense.getBase();
		else if (s.equals("stage"))
			return this.defense.getStage();
		else
			return 0;
	}
	
	public void setDefense(Stat st)
	{
		this.defense = st;
	}
	
	public Stat getSpeed()
	{
		return this.speed;
	}
	
	public int getSpeed(String s)
	{
		if (s.equals("current"))
			return this.speed.getCurrent();
		else if (s.equals("base"))
			return this.speed.getBase();
		else if (s.equals("stage"))
			return this.speed.getStage();
		else
			return 0;
	}
	
	public void setSpeed(Stat st)
	{
		this.speed = st;
	}
	
	public Stat getSpecial()
	{
		return this.special;
	}
	
	public int getSpecial(String s)
	{
		if (s.equals("current"))
			return this.special.getCurrent();
		else if (s.equals("base"))
			return this.special.getBase();
		else if (s.equals("stage"))
			return this.special.getStage();
		else
			return 0;
	}
	
	public void setSpecial(Stat st)
	{
		this.special = st;
	}
	
	public Stat getAccuracy()
	{
		return this.accuracy;
	}
	
	public int getAccuracy(String s)
	{
		if (s.equals("current"))
			return this.accuracy.getCurrent();
		else if (s.equals("base"))
			return this.accuracy.getBase();
		else if (s.equals("stage"))
			return this.accuracy.getStage();
		else
			return 0;
	}
	
	public void setAccuracy(Stat st)
	{
		this.accuracy = st;
	}
	
	public Stat getEvasion()
	{
		return this.evasion;
	}
	
	public int getEvasion(String s)
	{
		if (s.equals("current"))
			return this.evasion.getCurrent();
		else if (s.equals("base"))
			return this.evasion.getBase();
		else if (s.equals("stage"))
			return this.evasion.getStage();
		else
			return 0;
	}
	
	public void setEvasion(Stat st)
	{
		this.evasion = st;
	}
	
	public int getCountConfusion() 
	{
		return countConfusion;
	}
	
	public void setCountConfusion(int countConfusion) 
	{
		this.countConfusion = countConfusion;
	}	
	
	public int getCountSleep() 
	{
		return countSleep;
	}
	
	public void setCountSleep(int countStatus) 
	{
		this.countSleep = countStatus;
	}
	
	public int getCountDot() 
	{
		return countDot;
	}
	
	public void setCountDot(int countDot) 
	{
		this.countDot = countDot;
	}
	
	public int getCountBide() 
	{
		return countBide;
	}
	
	public void setCountBide(int countBide) 
	{
		this.countBide = countBide;
	}
	
	public int getTotalBideDmg() 
	{
		return totalBideDmg;
	}
	
	public void setTotalBideDmg(int totalBideDmg) 
	{
		this.totalBideDmg = totalBideDmg;
	}
	
	public boolean getCanAttack() 
	{
		return this.canAttack;
	}
	
	public void setCanAttack(boolean canAttack) 
	{
		this.canAttack = canAttack;
	}
	
	public boolean getPrio() 
	{
		return prio;
	}
	
	public void setPrio(boolean prio) 
	{
		this.prio = prio;
	}
	
	public int getTwoturnstatus() 
	{
		return twoturnstatus;
	}
	public void setTwoturnstatus(int twoturnstatus) 
	{
		this.twoturnstatus = twoturnstatus;
	}
	
	public Pokemon clone() throws CloneNotSupportedException
	{
		return (Pokemon) super.clone();
	}
}
