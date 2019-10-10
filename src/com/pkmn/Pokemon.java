package com.pkmn;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/* 
 * This class contains our favorite little monsters. They have a name, an Id (basically number in Pokédex), at least one type (the second may be null), attacks
 * (at least 1, max 4). Every stat is covered by "base" and "current" since it can drop or be buffed. Accuracy is 100 by default but can be lowered
 * by sand attack for example. 
 * Status 0 : Normal (no status)
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
	int hpSubstitute = 0;
	boolean sub = false;
	int countSleep = 0;
	int countConfusion = 0;
	int countTrap = 0; 
	int countBide = 0;
	int countDisable = 0;
	int countThrash = 0;
	int countToxic = 0;
	int totalBideDmg = 0;
	boolean canAttack = true;
	boolean prio = false;
	boolean seeded = false;
	int twoturnstatus = 0; //Used for two-turn attacks. Will contain the ID of the attack
	Attack lastattacksuffered = new Attack(); // Only used for counter and disable
	int lastdamagesuffered = 0; // Only used for counter
	
	//Empty constructor only for the fake index 0 Pokémon
	public Pokemon()
	{
		setName("MissingNo");
		setId(0);
	}
	//Constructor to instantiate a pokémon through the GameData class. Since current stats are the same as base's initially and status is always 0 at the beginning, those variables are not in the constructor.
	//Also Accuracy is 100 by default as stated in the descriptor.
	public Pokemon(String name, int id, Type type1, Type type2, int baseHp, int baseAtk,  int baseDef,  int baseSpd, int baseSpe)
	{
		setName(name.toUpperCase());
		setId(id);
		setType1(type1);
		setType2(type2);
		this.attacks = new ArrayList<Attack>(); // We will add attacks when the Pokémon is created
		setBaseHp(baseHp);
		setCurrentHp(baseHp);
		setAttack(new Stat("Attack",baseAtk));
		setDefense(new Stat("Defense",baseDef));
		setSpeed(new Stat("Speed",baseSpd));
		setSpecial(new Stat("Special",baseSpe));
	}
	
	public String toString()
	{
		return Integer.toString(this.getId()) + " - " + this.getName() + ". Type : " +this.getTypes();
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
	
	//This one is public for Conversion move reason
	public void setType1(Type type1) 
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

	public void setAttacks(ArrayList<Attack> attacks) 
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

	public void setCurrentHp(int damage) 
	{
		if (this.getHpSubstitute() > 0)
		{
			if (damage < 0)
				setHpSubstitute(damage);
		}
		else
		{
			this.currentHp = this.currentHp + damage;
			if (currentHp > 0)
			{
				if  (currentHp > this.getBaseHp())
					this.currentHp = this.getBaseHp();
			}
			else
			{
				this.currentHp = 0;
				setStatus(9);
			}
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
		return speed;
	}
	
	public int getSpeed(String s)
	{
		if (s.equals("current"))
			return speed.getCurrent();
		else if (s.equals("base"))
			return speed.getBase();
		else if (s.equals("stage"))
			return speed.getStage();
		else
			return 0;
	}
	
	public void setSpeed(Stat st)
	{
		speed = st;
	}
	
	public Stat getSpecial()
	{
		return special;
	}
	
	public int getSpecial(String s)
	{
		if (s.equals("current"))
			return special.getCurrent();
		else if (s.equals("base"))
			return special.getBase();
		else if (s.equals("stage"))
			return special.getStage();
		else
			return 0;
	}
	
	public void setSpecial(Stat st)
	{
		special = st;
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
	
	public int getHpSubstitute() 
	{
		return hpSubstitute;
	}
	
	public void setHpSubstitute(int hp) 
	{
		this.hpSubstitute = this.hpSubstitute + hp;
		if (this.hpSubstitute < 0)
			this.hpSubstitute = 0;
	}
	
	public boolean getSub() 
	{
		return sub;
	}
	
	public void setSub(boolean sub) 
	{
		this.sub = sub;
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
	
	public int getCountTrap() 
	{
		return countTrap;
	}
	
	public void setCountTrap(int countTrap) 
	{
		this.countTrap = countTrap;
	}
	
	public int getCountBide() 
	{
		return this.countBide;
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
	
	public int getCountThrash() 
	{
		return countThrash;
	}
	public void setCountThrash(int countThrash) 
	{
		this.countThrash = countThrash;
	}
	
	public int getCountDisable() 
	{
		return countDisable;
	}

	public void setCountDisable(int countDisable) 
	{
		this.countDisable = countDisable;
	}
	
	public int getCountToxic() 
	{
		return countToxic;
	}
	public void setCountToxic(int countToxic) 
	{
		this.countToxic = countToxic;
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
	
	public boolean getSeeded() 
	{
		return seeded;
	}
	
	public void setSeeded(boolean seeded) 
	{
		this.seeded = seeded;
	}
	
	public int getTwoturnstatus() 
	{
		return twoturnstatus;
	}
	
	public void setTwoturnstatus(int twoturnstatus) 
	{
		this.twoturnstatus = twoturnstatus;
	}
	
	public Attack getLastattacksuffered() 
	{
		return lastattacksuffered;
	}
	
	public void setLastattacksuffered(Attack lastattacksuffered) 
	{
		this.lastattacksuffered = lastattacksuffered;
	}
	
	public int getLastdamagesuffered() 
	{
		return lastdamagesuffered;
	}
	
	public void setLastdamagesuffered(int lastdamagesuffered) 
	{
		this.lastdamagesuffered = lastdamagesuffered;
	}
	
	public Pokemon clone() throws CloneNotSupportedException
	{
		return (Pokemon) super.clone();
	}
	
	public String getTypes()
	{
		String s = this.getType1().getName();
		if (this.getType2()!=null)
		{
			s = s + "/" + this.getType2().getName();
		}
		return s;
	}
	
	//status is used because haze should not reset status.
	public void setCurrentStats(boolean status)
	{
		setCurrentHp(this.getCurrentHp());
		this.getAttack().setStage(0);
		this.getDefense().setStage(0);
		this.getSpecial().setStage(0);
		this.getSpeed().setStage(0);
		this.getAccuracy().setStage(0);
		this.getEvasion().setStage(0);
		this.setCountToxic(0);
		setSeeded(false);
		if (status)
			setStatus(0);
		setTwoturnstatus(0);
		setSeeded(false);
		setCanAttack(true);
		setCountTrap(0);
	}
	
	//Shows attacks that can be used by your pokémon
	public String showAttacks()
	{
		String s = new String();
		if (this.getTwoturnstatus() == 0 && this.getCountBide() == 0 && this.getCountThrash() == 0)
		{
			if (this.getStatus()!=0)
			{
				s = s + "+++" + this.getName() + " is ";
				switch (this.getStatus())
				{
					case 1 :
						s = s + "paralysed.+++\n";
						break;
					case 2 :
						s = s + "asleep.+++\n";
						break;
					case 3 :
						s = s + "poisoned.+++\n";
						break;
					case 4 :
						s = s + "burning.+++\n";
						break;
					case 5 :
						s = s + "frozen.+++\n";
						break;
				}
			}
			s = s + "What should "+this.getName()+" do ?";
			s = s + "\n 0. Swap Pokémon";
			for (int i=0;i<this.getAttacks().size();i++)
			{
				if (this.getAttacks().get(i).getEnabled())
					s = s + "\n "+Integer.toString(i+1)+". ";  
				else
					s = s + "\n DISABLED. ";
				s = s + this.getAttacks().get(i).getType().getName() + " - " + getAttacks().get(i).getName()+" - "+getAttacks().get(i).getDescription();
			}
			s = s + "\n***********************";
		}
		else
			s = s + "\nPress enter to proceed to next turn.\n***********************";
		return s;
	}
	
	public int checkDisabledAttack()
	{
		for (int i = 0;i < this.getAttacks().size();i++)
		{
			if (!this.getAttacks().get(i).getEnabled())
				return i;
		}	
		return 5;
	}
	
	//Applies status alteration and prints the result
	public String statusModifier(int status)
	{
		String s = new String();
		if (!this.getSub() && this.getStatus() != 9)
		{
			s = s + "\n";
			switch (status)
			{
				case 1 :
					s = s + this.getName() + " is paralysed ! It may not be able to attack !";
					this.getSpeed().setCurrent((int) (this.getSpeed("base")*0.25));
					setStatus(status);
					break;
				case 2 :
					s = s + this.getName() + " felt asleep !";
					setCountSleep(ThreadLocalRandom.current().nextInt(2,7));
					setStatus(status);
					break;
				case 3 :
					//Type poison pokemons cannot be poisonned
					if (this.getType1().getName() != "Poison")
					{
						s = s + this.getName() + " is poisonned !";
						setStatus(status);
					}
					else if (this.getType2() != null)
					{
						if (this.getType2().getName() != "Poison")
						{
							s = s + this.getName() + " is poisonned !";
							setStatus(status);
						}
					}
					else
						s = s + this.getName() + " avoid the attack !";
					break;
				case 4 :
					s = s + this.getName() + " got burnt !";
					setStatus(status);
					break;
				case 5 :
					s = s + this.getName() + " was frozen solid !";
					setStatus(status);
					break;
				case 6 :
					s = s + this.getName() + " became confused !";
					setCountConfusion(ThreadLocalRandom.current().nextInt(1,4));
					break;
			}
		}
		return s;
	}
	
	public String checkTrap(Player p)
	{
		String s = new String();
		if (this.getCountTrap() > 0)
		{
			setCountTrap(this.getCountTrap() - 1);
			if (this.getCountTrap() == 0)
			{
				s = s + this.getName() + " was released from TRAP !";
			}
			else
			{
				int dmg = this.getCurrentHp()/16;
				setCurrentHp(-dmg);
				s = s + this.getName() + " lost " + dmg + " HP due to TRAP.";
				s = s + p.checkHpLeft();
			}
			s = s + "\n***********************";
			return s;
		}
		return null;
	}
	
	//Applies stats alteration and prints the result
	public String statModifier(Player p, String stat, int modifier)
	{
		//To avoid the "drop status" message if the Pokemon is already dead
		if (this.getStatus() != 9)
		{
			String s = new String();
			boolean min = false;
			boolean max = false;
			switch (stat)
			{
				case "attack" :
					if (this.getAttack("stage") >= 6)
					{
						max = true;
						break;
					}
					else if (this.getAttack("stage") <= -6)
					{
						min = true;
						break;
					}
					this.getAttack().setStage(this.getAttack("stage") + modifier);
					break;
				case "defense" :
					if (this.getDefense("stage")>=6)
					{
						max = true;
						break;
					}
					else if (this.getDefense("stage")<=-6)
					{
						min = true;
						break;
					}
					this.getDefense().setStage(this.getDefense("stage") + modifier);
					break;
				case "speed" :
					if (this.getSpeed("stage")>=6)
					{
						max = true;
						break;
					}
					else if (this.getSpeed("stage")<=-6)
					{
						min = true;
						break;
					}
					this.getSpeed().setStage(this.getSpeed("stage") + modifier);
					break;
				case "special" :
					if (this.getSpecial("stage")>=6)
					{
						max = true;
						break;
					}
					else if (this.getSpecial("stage")<=-6)
					{
						min = true;
						break;
					}
					this.getSpecial().setStage(this.getSpecial("stage") + modifier);
					break;
				case "accuracy" :
					if (this.getAccuracy("stage")>=6)
					{
						max = true;
						break;
					}
					else if (this.getAccuracy("stage")<=-6)
					{
						min = true;
						break;
					}
					this.getAccuracy().setStage(this.getAccuracy("stage") + modifier);
					break;
				case "evasion" :
					if (this.getEvasion("stage")>=6)
					{
						max = true;
						break;
					}
					else if (this.getEvasion("stage")<=-6)
					{
						min = true;
						break;
					}
					this.getEvasion().setStage(this.getEvasion("stage") + modifier);
					break;
				case "wall" :
					p.setWall(modifier);
					p.setCountWall(5);
			}
			if (p.getName().equals("Opponent"))
				s = s + "Enemy ";
			if (!stat.equals("wall"))
				s = s + this.getName()+"'s " + stat;
			if (max)
				s = s + " won't go higher !";
			else if (min)
				s = s + " won't go lower !";
			else
			{
				//Should not occur if a wall was used
				if (modifier > 0 && !stat.equals("wall"))
					s = s + " rose !";
				else if (modifier < 0 && !stat.equals("wall"))
					s = s + " fell !";
				//If a wall was used - light screen
				else if (modifier == 70)
					s = s + this.getName() + "'s protected against special attacks !";
				//If a wall was used - reflect
				else if (modifier == 98)
					s = s + this.getName() + " gained armor !";
			}
			return s;
		}
		else
			return "";
	}
}
