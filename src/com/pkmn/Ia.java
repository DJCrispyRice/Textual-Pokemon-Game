package com.pkmn;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * This class will contain everything related to an IA player such as level, choosing attacks, etc.
 */

public class Ia extends Player
{
	int level = 3; // 0 = easy, 1 = normal, 2 = hard
	public Ia() 
	{
		super("Opponent");
	}
	
	public void createTeam(GameData gd, Window win)
	{
		try
		{
			for (int j = 1 ; j <= 6; j++)
			{
				this.setTeam((Pokemon) gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)].clone());
				win.logTrace("Pokémon "+j+" is... "+this.getTeam().get(j-1).getName()+" !");
			}
		}
		catch (CloneNotSupportedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		win.logTrace("Your opponent chose his team, now let's the battle begin !");
	}
	
	public int chooseAttack(Player def)
	{
		int rdatt = -1;
		int dmg = 0;
		/*
		 * IA is "dumb" and will only choose randomly
		 */
		if (level == 0)
		{
			if (this.getCurrentPkmn().getAttacks().size() == 1)
				rdatt = 0;
			else
			{
				Random r = new Random();
				rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
				while (rdatt == this.getCurrentPkmn().checkDisabledAttack())
					rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
			}
			return rdatt;
		}
		/*
		 * IA is "good IA" style in standard Pokémon.
		 * Will only choose attack by type, but it must deal damages
		 * If no type is good against the type, will choose randomly
		 */
		else if (level == 1)
		{
			for (int i = 0; i < this.getCurrentPkmn().getAttacks().size(); i++)
			{
				//Checks if the attack is super effective
				if ((checkStrWeak(1,this.getCurrentPkmn().getAttacks().get(i),def.getCurrentPkmn()) == 2 || checkStrWeak(1,this.getCurrentPkmn().getAttacks().get(i),def.getCurrentPkmn()) == 4) && this.getCurrentPkmn().checkDisabledAttack() != i)
				{
					rdatt = i;
					//Checks if the attack is the most powerfull available
					if (calculateDamages(def,this.getCurrentPkmn().getAttacks().get(i)) > dmg && this.getCurrentPkmn().getAttacks().get(i).getStatus() != 50)
					{
						dmg = calculateDamages(def,this.getCurrentPkmn().getAttacks().get(i));
						rdatt = i;
					}
				}	
			}
			//Checks if no attack were chosen (i.e. no super effective attack). If so, chooses a random attack
			if (rdatt < 0)
			{
				Random r = new Random();
				rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
				while (rdatt == this.getCurrentPkmn().checkDisabledAttack())
					rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
			}
			return rdatt;
		}
		
		/*
		 * IA is "smart" and will really try to win.
		 * Will try to use a wall : OK
		 * Will try to use Leech Seed : OK
		 * Will try to put the player in a status if possible : OK
		 * Will check stats and try to be quicker than the player : OK
		 * Will choose the attack that inflicts the most damage : OK
		 * Will heal if possible (if HP are below 30%) : OK
		 * Will explode/self-destructs if HP are below 30% : OK
		*/
		else if (level == 2)
		{
			int wtc = 0;
			if (this.getWall()==0)
				wtc = 5;
			else if (!this.getSeeded())
				wtc = 4;
			else if (def.getCurrentPkmn().getStatus() == 0)
				wtc = 3;
			else if (def.getCurrentPkmn().getSpeed("current") < this.getCurrentPkmn().getSpeed("current"))
				wtc = 2;
			else if (def.getCurrentPkmn().getCurrentHp() <= def.getCurrentPkmn().getBaseHp()*0.33)
				wtc = 1;
			for (int i = 0; i < this.getCurrentPkmn().getAttacks().size(); i++)
			{
				if (this.getCurrentPkmn().checkDisabledAttack() != i)
				{
					switch (wtc)
					{
						case 0 :
							if (calculateDamages(def,this.getCurrentPkmn().getAttacks().get(i)) > dmg && this.getCurrentPkmn().getAttacks().get(i).getStatus() != 50)
							{
								dmg = calculateDamages(def,this.getCurrentPkmn().getAttacks().get(i));
								rdatt = i;
								break;
							}
						case 5 :
							if (this.getWall()==0 && this.getCurrentPkmn().getAttacks().get(i).getStatus() == 52)
							{
								rdatt = i;
								break;
							}
						case 4 :
							if (!this.getSeeded() && this.getCurrentPkmn().getAttacks().get(i).getId() == 67)
							{
								rdatt = i;
								break;
							}
						case 3 :
							if (def.getCurrentPkmn().getStatus() == 0 && this.getCurrentPkmn().getAttacks().get(i).getStatus() <= 5
							&& this.getCurrentPkmn().getAttacks().get(i).getStatus() != 0
							&& this.checkStrWeak(1, this.getCurrentPkmn().getAttacks().get(i), def.getCurrentPkmn()) >=1)
							{
								rdatt = i;
								break;
							}
						case 2 : 
							if ((this.getCurrentPkmn().getAttacks().get(i).getStatus() >= 23 && this.getCurrentPkmn().getAttacks().get(i).getStatus() <= 30) && def.getCurrentPkmn().getSpeed("current") < this.getCurrentPkmn().getSpeed("current"))
							{
								rdatt = i;
								break;
							}
						case 1 :
							if (this.getCurrentPkmn().getAttacks().get(i).getStatus() == 44 || this.getCurrentPkmn().getAttacks().get(i).getStatus() == 45 || this.getCurrentPkmn().getAttacks().get(i).getStatus() == 50)
							{
								rdatt = i;
								break;
							}
					}
				}
				if (wtc > 0 && rdatt >= 0)
					break;
				if (i == this.getCurrentPkmn().getAttacks().size() - 1 && rdatt < 0)
				{
					i = -1;
					wtc--;
				}
			}
		}
		return rdatt;
	}
	
	public String checkHpLeft()
	{
		String s = new String();
		//Checks if the pokemon is dead.
		if (this.getCurrentPkmn().getStatus() == 9)
		{
			s = s + "\n***********************\n"+this.getCurrentPkmn().getName()+" fainted !";
			this.getTeam().remove(this.getCurrentPkmn());
			s = s + "\nYour opponent has "+this.getTeam().size()+" pokémon left.";
		}
		else
		{
			if (this.getCurrentPkmn().getSub())
			{
				if (this.getCurrentPkmn().getHpSubstitute() <= 0)
				{
					this.getCurrentPkmn().setSub(false);
					s = s + "\n Enemy" +this.getCurrentPkmn().getName()+"'s substitute broke.";
				}
			}
			else
				s = s + "\nEnemy " + this.getCurrentPkmn().getName()+" has "+this.getCurrentPkmn().getCurrentHp()+"/"+this.getCurrentPkmn().getBaseHp()+" HP.";
		}
		return s;
	}
	
	public boolean checkDead(Window win)
	{
		if (this.getCurrentPkmn().getStatus()==9)
		{
			
			if (this.getTeam().size()>0)
			{
				if (this.getTeam().size()==1)
					this.setCurrentPkmn(this.getTeam().get(0));
				else
					this.setCurrentPkmn(this.getTeam().get(ThreadLocalRandom.current().nextInt(0, this.getTeam().size() - 1)));
				this.getCurrentPkmn().setCurrentStats(false);
				win.music.stop();
				win.se = new Sound("res/cries/"+this.getCurrentPkmn().getId()+".wav");
				win.se.play();
				win.logTrace("Your opponent sent "+this.getCurrentPkmn().getName()+" !");
				win.music.playLoop();
				this.getCurrentPkmn().setCanAttack(false);
			}
			else
			{
				win.logTrace("You won ! :-D");
				win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
				win.whatToChoose = "continue";
			}
			return true;
		}
		else
			return false;
	}
	
	private int checkStrWeak(int dmg, Attack att, Pokemon pk)
	{
		int rt=dmg;
		int type=0; // 0 = neutral, 1 = super effective, 2 = not very effective, 3 = useless
		//Checking Strength first
		for (int i = 0;i<att.getType().getStrength().size();i++)
		{
			if (att.getType().getStrength().get(i).equals(pk.getType1()))
			{
				type = 1;
				rt = dmg*2;
				break;
			}
		}
		//Checking Strength for type 2
		for (int i = 0;i<att.getType().getStrength().size();i++)
		{
			if (att.getType().getStrength().get(i).equals(pk.getType2()))
			{
				if (type == 1)
					rt = dmg*4;
				else
				{
					type = 1;
					rt = dmg*2;
				}
				break;
			}
		}
		//Checking weakness then
		for (int i = 0;i<att.getType().getWeak().size();i++)
		{
			if (att.getType().getWeak().get(i).equals(pk.getType1()))
			{
				type = 2;
				rt = dmg/2;
				break;
			}
		}
		//Checking weakness for type 2
		for (int i = 0;i<att.getType().getWeak().size();i++)
		{
			if (att.getType().getWeak().get(i).equals(pk.getType2()))
			{
				if (type == 2)
					rt = dmg/4;
				else if (type == 1)
					rt = dmg;
				else
				{
					type = 2;
					rt = dmg/2;
				}
				break;
			}
		}
		//Checking uselessness
		for (int i = 0;i<att.getType().getUseless().size();i++)
		{
			if (att.getType().getUseless().get(i).equals(pk.getType1()))
			{
				rt = 0;
				type = 3;
				break;
			}
		}
		//Checking uselessness for type 2
		for (int i = 0;i<att.getType().getUseless().size();i++)
		{
			if (att.getType().getUseless().get(i).equals(pk.getType2()))
			{
				if (type == 0)
				{
					rt = dmg/2;
					type = 2;
				}
				else if (type == 1)
				{
					rt = dmg;
					type = 0;
				}
				else if (type == 2)
				{
					rt = dmg/4;
					type = 2;
				}
				else
				{
					type = 3;
					rt = 0;
				}
				break;
			}
		}
		return rt;
	}
	
	private int calculateDamages(Player def, Attack iAtt)
	{
		int damage = 0;
		int power;
		power = iAtt.getPower();
		//Verify if STAB
		if (iAtt.getType().equals(this.getCurrentPkmn().getType1()) || iAtt.getType().equals(this.getCurrentPkmn().getType2()))
			power = (int) (power * 1.5);
		//If the attack is Earthquake and the defender is underground, doubles the power of the attack
		if (def.getCurrentPkmn().getTwoturnstatus() == 26 && iAtt.getId() == 36)
			power = power * 2;
		//Mathematical calculation for damages. Level is 50
		damage = ((2*50)/5 + 2)*power;
		//Checking crit to see if we use base or current stats
		//Checks if the attack inflict fixed damages
		if (iAtt.getStatus() == 53)
			damage = iAtt.getPower();
		else 
		{
			if (iAtt.getPhy())
			{
				//If protect is on
				if (def.getWall() == 98)
					damage = damage * (this.getCurrentPkmn().getAttack("current")/(def.getCurrentPkmn().getDefense("current") * 2));
				else	
					damage = damage * (this.getCurrentPkmn().getAttack("current")/def.getCurrentPkmn().getDefense("current"));
			}
			else
			{
				//If light screen is on
				if (def.getWall() == 70)
					damage = damage * (this.getCurrentPkmn().getSpecial("current")/(def.getCurrentPkmn().getSpecial("current") * 2));
				else
					damage = damage * (this.getCurrentPkmn().getSpecial("current")/def.getCurrentPkmn().getSpecial("current"));
			}
			damage = damage/50 + 2;
		}
		//Checks strength/weakness
		damage = this.checkStrWeak(damage, iAtt, def.getCurrentPkmn());
		return damage;
	}

	public int getLevel() 
	{
		return level;
	}

	public void setLevel(int level) 
	{
		this.level = level;
	}
}
