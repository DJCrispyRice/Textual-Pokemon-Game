package com.pkmn;

import java.util.concurrent.ThreadLocalRandom;

/* 
 * This class will contain all the battle's logic such as attacking, calculating damages, accuracy, etc. All it will contain is two players 
 * since every thing else comes from it.
 */

public class Battle 
{
	Player p1;
	Player p2;
	String s = new String();
	GameData gd;
	
	public Battle(GameData gd)
	{
		this.p1 = new Player("Player");
		this.p2 = new Player("Opponent");
	}
	
	public Pokemon getpPkmn(Player p)
	{
		return p.getCurrentPkmn();
	}
	
	public Attack getpattack(Player p, int i)
	{
		return p.getCurrentPkmn().getAttacks().get(i);
	}
	
	//Shows attacks that can be used by your pokémon
	public String showAttacks()
	{
		this.s = new String();
		if (getpPkmn(p1).getTwoturnstatus() == 0 && getpPkmn(p1).getCountBide() == 0)
		{
			if (this.getpPkmn(p1).getStatus()!=0)
			{
				this.s = this.s + "+++" + this.getpPkmn(p1).getName() + " is ";
				switch (this.getpPkmn(p1).getStatus())
				{
					case 1 :
						this.s = this.s + "paralysed.+++\n";
						break;
					case 2 :
						this.s = this.s + "asleep.+++\n";
						break;
					case 3 :
						this.s = this.s + "poisoned.+++\n";
						break;
					case 4 :
						this.s = this.s + "burning.+++\n";
						break;
					case 5 :
						this.s = this.s + "frozen.+++\n";
						break;
				}
			}
			s = s + "***********************\nWhat should "+this.getpPkmn(p1).getName()+" do ?";
			s = s + "\n 0. Swap Pokémon";
			for (int i=0;i<this.getpPkmn(p1).getAttacks().size();i++)
			{
				s = s + "\n "+Integer.toString(i+1)+". " + this.getpattack(this.p1, i).getType().getName() + " - " + this.getpattack(this.p1,i).getName()+" - "+this.getpattack(this.p1,i).getDescription();
			}
			s = s + "\n***********************";
		}
		else
			s = s + "***********************\nPress enter to proceed to next turn.\n***********************";
		return s;
	}
	
	//Is used whatever an attack is launched by a pokemon. att is the attacker, def is the defender
	//i is used to see if it's the first or the second attack in this turn
	public String useAttack (int iAtt, Player att, Player def, int i)
	{
		//By default, the attack should occur
		Boolean atkok = true;
		this.s = new String();
		//If the pokemon is paralysed, it has 25% chance not to attack.
		if (this.getpPkmn(att).getStatus()==1)
		{
			int ckatk = ThreadLocalRandom.current().nextInt(0,100);
			if (ckatk<25)
			{
				atkok = false;
				this.s = this.getpPkmn(att).getName() + " is fully paralysed !";
			}
		}
		//If the pokemon is asleep, it can't attack except if it wakes up
		else if (this.getpPkmn(att).getStatus()==2)
		{
			this.getpPkmn(att).setCountSleep(this.getpPkmn(att).getCountSleep() - 1);
			if (this.getpPkmn(att).getCountSleep()>0)
			{
				atkok = false;
				this.s = this.getpPkmn(att).getName() + " is fast asleep !";
			}
			else
			{
				this.s = this.getpPkmn(att).getName() + " woke up !\n";
				getpPkmn(att).setStatus(0);
			}
		}
		//If the pokemon is frozen, it can't attack except if it unfrozes
		else if (this.getpPkmn(att).getStatus()==5)
		{
			int rngfrz = ThreadLocalRandom.current().nextInt(0,100);
			if (rngfrz>90)
			{
				this.getpPkmn(att).setStatus(0);
				this.s = this.getpPkmn(att).getName() + " unfroze !\n";
			}
			else
				this.s = this.getpPkmn(att).getName() + " is frozen solid !";
			atkok = false;
		}
		//Confusion check
		if (this.getpPkmn(att).getCountConfusion()>0)
		{
			this.getpPkmn(att).setCountConfusion(this.getpPkmn(att).getCountConfusion() - 1);
			if (this.getpPkmn(att).getCountConfusion()>0)
			{
				if (att.getName().equals("Opponent"))
					this.s = this.s + "Enemy ";
				this.s = this.s + this.getpPkmn(att).getName() + " is confused...\n";
				int ckcon = ThreadLocalRandom.current().nextInt(0,100);
				if (ckcon > 50)
				{
					int damage = ((2*20)/2 + 2)*40;
					damage = damage * (this.getpPkmn(att).getAttack("current")/this.getpPkmn(att).getDefense("current"));
					damage = damage/50 + 2;
					if (att.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					this.s = this.s + this.getpPkmn(att).getName() + " hurts himself in confusion and lost "+damage+" HP.";
					this.getpPkmn(att).setCurrentHp(this.getpPkmn(att).getCurrentHp() - damage);
					this.checkHpLeft(att);
					atkok = false;
				}
			}
			else
			{
				if (att.getName().equals("Opponent"))
					this.s = this.s + "Enemy ";
				this.s = this.s + this.getpPkmn(att).getName() + " snapped out of confusion !\n";
			}
		}
		//If the attack can occur (no status avoiding it to happen), it does...
		if (atkok)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + this.getpPkmn(att).getName()+" used "+this.getpattack(att,iAtt).getName()+". ";
			if (checkHit(this.getpattack(att,iAtt),this.getpPkmn(att),this.getpPkmn(def)))
			{
				if (this.getpattack(att,iAtt).getStatus() == 54)
					this.s = this.doSpecialAttack(iAtt, att, def, i);
				else
					this.s = this.doDamages(iAtt, att, def, i);
			}
			else
			{
				if (att.getName().equals("Opponent"))
					this.s = this.s + "Enemy ";
				this.s = this.s + this.getpPkmn(att).getName() + " missed !";
			}
		}
		//If the attack didn't occur, reset the two turn status
		else
			getpPkmn(att).setTwoturnstatus(0);
		//After the attack occurs, applies whatever damage it should if there is a status
		//If the pokémon is poisoned, lose 1/16 of its HP
		if (this.getpPkmn(att).getStatus()==3)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + "\n"+this.getpPkmn(att).getName() + " suffers "+Integer.toString(this.getpPkmn(att).getBaseHp()/16) + " HP due to poison.";
			this.getpPkmn(att).setCurrentHp(this.getpPkmn(att).getCurrentHp() - this.getpPkmn(att).getBaseHp()/16);
			this.checkHpLeft(att);
		}
		//If the pokémon is burn, lose 1/16 of its HP.
		else if (this.getpPkmn(att).getStatus()==4)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + "\n"+this.getpPkmn(att).getName() + " suffers "+Integer.toString(this.getpPkmn(att).getBaseHp()/16) + " HP due to burn.";
			this.getpPkmn(att).setCurrentHp(this.getpPkmn(att).getCurrentHp() - this.getpPkmn(att).getBaseHp()/16);
			this.checkHpLeft(att);
		}
		return s;
	}
	
	//Will calculate what happens if the attack hits 
	//Return s which will be printed as a log trace.
	//i is used to see if it's the first or the second attack in this turn
	private String doDamages(int iAtt, Player att, Player def, int i)
	{
		//Checking the status of the attack to see what will happen next
		//If move's power is higher than 0, do damages. Then check status change
		//Also checks if the attack is a multi-hit move
		if (this.getpattack(att, iAtt).getStatus() == 46)
		{
			int hits = ThreadLocalRandom.current().nextInt(2,5);
			int nb = 0;
			int j;
			if (getpattack(att,iAtt).getId() == 13 || getpattack(att,iAtt).getId() == 29 || getpattack(att,iAtt).getId() == 149)
				hits = 2;
			for (j = 1; j <= hits; j++)
			{
				nb++;
				deal(att,def,iAtt);
				this.s = this.s + "\n";
				if (getpPkmn(def).getCurrentHp()==0)
					break;
			}
			if (getpPkmn(def).getCurrentHp()>0)
				this.s = this.s + "Hit " + getpPkmn(def).getName() + " " + nb + " times.";
		}
		//Checks if it's the second part of a two-turn attack situation. If so, do the damages and sets the variable to 0
		else if (getpPkmn(att).getTwoturnstatus() != 0)
		{
			deal(att,def,iAtt);
			getpPkmn(att).setTwoturnstatus(0);
		}
		//Checks if the attack is a two-turn attack. If so, sets twoturnstatus to the id of this attack
		else if (this.getpattack(att,iAtt).getStatus()==48)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + getpPkmn(att).getName();
			//Switch to trace the attack
			switch (getpattack(att,iAtt).getId())
			{
				case 26 :
					this.s = this.s + " dug a hole !";
					break;
				case 45 : 
					this.s = this.s + " flew up high !";
					break;
				case 111 :
					this.s = this.s + " lowered its head !";
					break;
				case 120 : 
					this.s = this.s + " is shining !";
					break;
			}
			getpPkmn(att).setTwoturnstatus(getpattack(att,iAtt).getId());
		}
		//If the power is higher than 0 and it's not a multi-hit move, deals damages to opponent
		else if (this.getpattack(att,iAtt).getPower()>0 && this.getpattack(att, iAtt).getStatus() != 46)
			deal(att, def, iAtt);
		//Checking if the attack does anything but statut alteration
		//Checks if the status alteration hits using the accu_status.
		int randomstat1 = ThreadLocalRandom.current().nextInt(0,100);
		if (randomstat1 >= 100 - this.getpattack(att, iAtt).getAccu_status())
		{
			switch (this.getpattack(att, iAtt).getStatus())
			{
				//Attack drop for opponent
				case 8 : 
					statModifier(def,"attack",-1);
					break;
				//Attack boost for user
				case 9 :
					statModifier(att,"attack",1);
					break;
				//Attack drop for user
				case 10 :
					statModifier(def,"attack",-1);
					break;
				//Double attack boost for opponent
				case 11 :
					statModifier(def,"attack",2);
					break;
				//Double attack drop for opponent
				case 12 :
					statModifier(def,"attack",-2);
					break;
				//Double attack boost for user
				case 13 :
					statModifier(att,"attack",2);
					break;
				//Defense boost for opponent
				case 15:
					statModifier(def,"defense",1);
					break;
				//Defense drop for opponent
				case 16:
					statModifier(def,"defense",-1);
					break;
				//Defense boost for user
				case 17:
					statModifier(att,"defense",1);
					break;
				//Defense drop for user
				case 18:
					statModifier(att,"defense",-1);
					break;
				//Double defense boost for opponent
				case 19:
					statModifier(def,"defense",2);
					break;
				//Double defense drop for opponent
				case 20:
					statModifier(def,"defense",-2);
					break;
				//Double defense drop for user
				case 21:
					statModifier(att,"defense",2);
					break;
				//Double defense drop for user
				case 22:
					statModifier(att,"defense",-2);
					break;
				//speed boost for opponent
				case 23:
					statModifier(def,"speed",1);
					break;
				//speed drop for opponent
				case 24:
					statModifier(def,"speed",-1);
					break;
				//speed boost for user
				case 25:
					statModifier(att,"speed",1);
					break;
				//speed drop for user
				case 26:
					statModifier(att,"speed",-1);
					break;
				//Double speed boost for opponent
				case 27:
					statModifier(def,"speed",2);
					break;
				//Double speed drop for opponent
				case 28:
					statModifier(def,"speed",-2);
					break;
				//Double speed drop for user
				case 29:
					statModifier(att,"speed",2);
					break;
				//Double speed drop for user
				case 30:
					statModifier(att,"speed",-2);
					break;
					//speed boost for opponent
				case 31:
					statModifier(def,"special",1);
					break;
				//special drop for opponent
				case 32:
					statModifier(def,"special",-1);
					break;
				//special boost for user
				case 33:
					statModifier(att,"special",1);
					break;
				//special drop for user
				case 34:
					statModifier(att,"special",-1);
					break;
				//Double special boost for opponent
				case 35:
					statModifier(def,"special",2);
					break;
				//Double special drop for opponent
				case 36:
					statModifier(def,"special",-2);
					break;
				//Double special drop for user
				case 37:
					statModifier(att,"special",2);
					break;
				//Double special drop for user
				case 38:
					statModifier(att,"special",-2);
					break;
				//Accuracy boost for opponent
				case 39:
					statModifier(def,"accuracy",1);
					break;
				//Accuracy drop for opponent
				case 40:
					statModifier(def,"accuracy",-1);
					break;
				//Evasion boost for user
				case 41:
					statModifier(att,"evasion",1);
					break;
				//Evasion drop for user
				case 42:
					statModifier(att,"evasion",-1);
					break;
				//Cause flinching
				case 43:
					//Should occur only if the attack was faster or attacked first
					if (i==0)
					{
						if (att.getName().equals("Opponent"))
							this.s = this.s + "Enemy ";
						this.s = this.s + this.getpPkmn(def).getName() + " flinched !";
						def.getCurrentPkmn().setCanAttack(false);
					}
					break;
				//Healing mechanic
				case 45:
					if (this.getpattack(att, iAtt).getId() == 97 || this.getpattack(att, iAtt).getId() == 119)
					{
						int heal = att.getCurrentPkmn().getBaseHp() / 2;
						att.getCurrentPkmn().setCurrentHp(att.getCurrentPkmn().getCurrentHp() + heal);
						if (att.getName().equals("Opponent"))
							this.s = this.s + "\nEnemy ";
						else
							this.s = this.s + "\n";
						this.s = this.s + this.getpPkmn(att).getName() + " healed "+ heal +" HP and now has "+att.getCurrentPkmn().getCurrentHp() +"/" + att.getCurrentPkmn().getBaseHp()+".";
					}
					else
					{
						att.setCurrentStats(false);
						def.setCurrentStats(false);
						this.s = this.s + "All status changes were eliminated !";
					}
					break;
				//Walls
				case 52:
					if (att.getWall() == 0)
						statModifier(att,"wall",this.getpattack(att,iAtt).getId());
					else
					{
						if (att.getName().equals("Opponent"))
							this.s = this.s + "Enemy ";
						this.s = this.s + this.getpPkmn(att).getName() + " missed !";
					}
			}
		}
		//Status change can only occur if the Pokémon is not already altered by another status.
		if (this.getpPkmn(def).getStatus()==0 && (this.getpattack(att,iAtt).getStatus()>=1 && this.getpattack(att,iAtt).getStatus() < 6))
		{
			//Checks if the status alteration hits using the accu_status.
			int randomstat2 = ThreadLocalRandom.current().nextInt(0,100);
			if (randomstat2 >= 100 - this.getpattack(att, iAtt).getAccu_status())
				statusModifier(def,this.getpattack(att,iAtt).getStatus());
		}
		//Shows the "avoid attack" message if the purpose of the move is only status alteration. Exception with healing move that does not hit opponent
		else if (this.getpattack(att,iAtt).getPower()==0 && this.getpattack(att,iAtt).getStatus() < 6)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + this.getpPkmn(def).getName()+" avoid the attack !";
		}
		return s;
	}
	
	//This function will be used to simplify the usage of status 54 attacks which contains every move that is "unique".
	private String doSpecialAttack(int iAtt, Player att, Player def, int i)
	{
		switch (this.getpattack(att, iAtt).getId())
		{
			case 8 : 
				if (getpPkmn(att).getCountBide() == 0)
				{
					getpPkmn(att).setCountBide(2);
					if (att.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					this.s = this.s + getpPkmn(att).getName() + " is storing energy !";
				}
				else
				{
					getpPkmn(att).setCountBide(getpPkmn(att).getCountBide()-1);
					if (att.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					if (getpPkmn(att).getCountBide() == 0)
					{
						this.s = this.s + getpPkmn(att).getName() + " unleashed energy !\n";
						//Damage calculation here
						int damage = getpPkmn(att).getTotalBideDmg() * 2;
						getpPkmn(def).setCurrentHp(getpPkmn(def).getCurrentHp() - damage );
						if (def.getName().equals("Opponent"))
							this.s = this.s + "Enemy ";
						this.s = this.s + getpPkmn(def).getName() + " lost " + damage + " HP. ";
						getpPkmn(att).setTotalBideDmg(0);
						this.checkHpLeft(def);
					}
					else
						this.s = this.s + getpPkmn(att).getName() + " is still storing energy !";
				}
				break;
			case 16 : 
				break;
			case 21 : 
				break;
			case 22 : 
				break;
			case 27 : 
				break;
			case 34 : 
				break;
			case 42 : 
				break;
			case 55 : 
				break;
			case 58 : 
				break;
			case 63 : 
				break;
			case 67 : 
				break;
			case 77 : 
				break;
			case 78 : 
				break;
			case 80 : 
				break;
			case 84 : 
				break;
			case 96 : 
				break;
			case 99 : 
				break;
			case 100 : 
				break;
			case 112 : 
				break;
			case 128 : 
				break;
			case 131 : 
				break;
			case 132 : 
				break;
			case 140 : 
				break;
			case 147 : 
				break;
			case 148 : 
				break;
			case 154 : 
				break;
			case 157 : 
				break;
		}
		
		return this.s;
	}
	
	private boolean checkHit(Attack atk, Pokemon att, Pokemon def)
	{
		int accurate = 100;
		//Swift can't fail whatever the case, if it's currently biding it can't also fail
		if (atk.getName().equals("Swift") || att.getCountBide() > 0)
			return true;
		//Will automatically fail if the user is in the air or underground except if earthquake + underground
		//... Unless the used attack is two-turn move (first turn using it)
		if (atk.getStatus() == 48 && att.getTwoturnstatus() == 0)
			return true;
		//If the opponent is in the sky or underground, will automatically fail unless earthquake + underground
		//But if the move is a status boost for the user, it shouldn't fail
		else if (((def.getTwoturnstatus() == 26 && atk.getId() != 36) || def.getTwoturnstatus() == 45) && !atk.getSelf())
		{
			if (att.getTwoturnstatus() != 0)
				att.setTwoturnstatus(0);
			return false;
		}
		accurate = accurate - (100 - atk.getAccuracy()) - (100 - att.getAccuracy("current") - (def.getEvasion("current") - 100));
		if (accurate < 0)
			accurate = 0;
		int random = ThreadLocalRandom.current().nextInt(0,100);
		if (random > accurate)
		{
			if (att.getTwoturnstatus() != 0)
				att.setTwoturnstatus(0);
			return false;
		}
		else
			return true;
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
			}
		}
		//Checking weakness then
		for (int i = 0;i<att.getType().getWeak().size();i++)
		{
			if (att.getType().getWeak().get(i).equals(pk.getType1()))
			{
				type = 2;
				rt = dmg/2;
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
			}
		}
		//Checking uselessness
		for (int i = 0;i<att.getType().getUseless().size();i++)
		{
			if (att.getType().getUseless().get(i).equals(pk.getType1()))
			{
				rt = 0;
				type = 3;
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
			}
		}
		switch (type)
		{
			case 1 : 
				this.s = this.s + "It's super effective !\n";
				break;
			case 2 : 
				this.s = this.s + "It's not very effective...\n";
				break;
			case 3 :
				this.s = this.s + "It did nothing at all !\n";
				break;
		}
		return rt;
	}
	
	//To write how many hp left the pokémon has. Def is the player who is being checked.
	public void checkHpLeft(Player def)
	{
		//Checks if the pokemon is dead.
		if (def.getCurrentPkmn().getStatus() == 9)
		{
			this.s = this.s + "\n***********************\n"+def.getCurrentPkmn().getName()+" fainted !";
			if (def.getName().equals("Player"))
			{
				this.p1.getTeam().remove(def.getCurrentPkmn());
				this.s = this.s + "\nYou have "+this.p1.getTeam().size()+" pokémon left.";
			}
			else
			{
				this.p2.getTeam().remove(def.getCurrentPkmn());
				this.s = this.s + "\nYour opponent has "+this.p2.getTeam().size()+" pokémon left.";
			}
		}
		else
		{
			if (def.getName().equals("Opponent"))
				this.s = this.s + "\nEnemy ";
			else
				this.s = this.s + "\n";
			this.s = this.s + def.getCurrentPkmn().getName()+" has "+def.getCurrentPkmn().getCurrentHp()+"/"+def.getCurrentPkmn().getBaseHp()+" HP. ";
		}
	}
		
	private boolean checkCrit(Attack att, Pokemon pk)
	{
		//Check if the attack has high critical ratio
		if (att.getStatus() == 47)
		{
			int T = pk.getSpeed("base") * 4;
			int P = ThreadLocalRandom.current().nextInt(0,256);
			if (T > P)
			{
				this.s = this.s + "\nA critical hit ! ";
				return true;
			}
			else
				return false;
		}
		else
		{
			int T = pk.getSpeed("base") / 2;
			int P = ThreadLocalRandom.current().nextInt(0,256);
			if (T > P)
			{
				this.s = this.s + "\nA critical hit ! ";
				return true;
			}
			else
				return false;
		}
	}
	
	//Dealing damages
	//atk is attacker, def is defender
	private void deal(Player atk, Player def, int iAtt)
	{
		int damage = 0;
		int power;
		power = this.getpattack(atk,iAtt).getPower();
		//Verify if STAB
		if (this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType1()) || this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType2()))
			power = (int) (power * 1.5);
		//If the attack is Earthquake and the defender is underground, doubles the power of the attack
		if (getpPkmn(def).getTwoturnstatus() == 26 && getpattack(atk,iAtt).getId() == 36)
			power = power * 2;
		//Mathematical calculation for damages. Level is 50
		damage = ((2*50)/5 + 2)*power;
		//Checking crit to see if we use base or current stats
		//Checks if the attack inflict fixed damages
		if (this.getpattack(atk, iAtt).getStatus() == 53)
			damage = this.getpattack(atk, iAtt).getPower();
		else 
		{
			if (checkCrit(this.getpattack(atk,iAtt), atk.getCurrentPkmn()))
			{
				if (this.getpattack(atk,iAtt).getPhy())
					damage = damage * (atk.getCurrentPkmn().getAttack("base")/def.getCurrentPkmn().getDefense("base"));
				else
					damage = damage * (atk.getCurrentPkmn().getSpecial("base")/def.getCurrentPkmn().getSpecial("base"));
				damage = damage/50 + 2;
				damage = (int) (damage * 1.5);
			}
			else
			{
				if (this.getpattack(atk,iAtt).getPhy())
				{
					//If protect is on
					if (def.getWall() == 98)
						damage = damage * (atk.getCurrentPkmn().getAttack("current")/(def.getCurrentPkmn().getDefense("current") * 2));
					else	
						damage = damage * (atk.getCurrentPkmn().getAttack("current")/def.getCurrentPkmn().getDefense("current"));
				}
				else
				{
					//If light screen is on
					if (def.getWall() == 70)
						damage = damage * (atk.getCurrentPkmn().getSpecial("current")/(def.getCurrentPkmn().getSpecial("current") * 2));
					else
						damage = damage * (atk.getCurrentPkmn().getSpecial("current")/def.getCurrentPkmn().getSpecial("current"));
				}
				damage = damage/50 + 2;
			}
			//Checks strength/weakness
			damage = this.checkStrWeak(damage, this.getpattack(atk,iAtt), def.getCurrentPkmn());
		}
		if (damage != 0)
		{
			if (def.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP. ";
			//Checking if the attack should steal HP
			if (this.getpattack(atk, iAtt).getStatus()==44)
			{
				int heal = 0;
				if (def.getCurrentPkmn().getCurrentHp() - damage < 0)
					heal = def.getCurrentPkmn().getCurrentHp()/2;
				else
					heal = damage/2;
				atk.getCurrentPkmn().setCurrentHp(atk.getCurrentPkmn().getCurrentHp()+heal);
				if (atk.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + atk.getCurrentPkmn().getName()+" stole "+heal+" HP and now has "+atk.getCurrentPkmn().getCurrentHp()+"/"+atk.getCurrentPkmn().getBaseHp()+".";
			}
			if (getpPkmn(def).getCountBide() > 0)
				getpPkmn(def).setTotalBideDmg(getpPkmn(def).getTotalBideDmg() + damage);
		}
		def.getCurrentPkmn().setCurrentHp(def.getCurrentPkmn().getCurrentHp() - damage);
		this.checkHpLeft(def);
		//Checks if the attack should inflict recoil damages
		if (this.getpattack(atk, iAtt).getStatus() == 49)
		{
			int recoil = damage/4;
			if (recoil == 0)
				recoil = 1;
			getpPkmn(atk).setCurrentHp(getpPkmn(atk).getCurrentHp() - (recoil));
			this.s = this.s + "\n";
			if (atk.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + getpPkmn(atk).getName() + "'s hit with recoil ! It lost "+ recoil +" HP. ";
			this.checkHpLeft(atk);
		}
		//Checks if the attack should kill its user (explosion/self-destruct)
		else if (this.getpattack(atk, iAtt).getStatus() == 50)
		{
			getpPkmn(atk).setCurrentHp(0);
			this.checkHpLeft(atk);
		}
	}
	
	//Applies status alteration and prints the result
	private void statusModifier(Player p, int status)
	{
		getpPkmn(p).setStatus(status);
		if (p.getName().equals("Opponent"))
			this.s = this.s + "\nEnemy ";
		else
			this.s = this.s + "\n";
		switch (status)
		{
			case 1 :
				this.s = this.s + getpPkmn(p).getName() + " is paralysed ! It may not be able to attack !";
				getpPkmn(p).getSpeed().setCurrent((int) (getpPkmn(p).getSpeed("base")*0.25));
				break;
			case 2 :
				this.s = this.s + getpPkmn(p).getName() + " felt asleep !";
				getpPkmn(p).setCountSleep(ThreadLocalRandom.current().nextInt(1,7));
				break;
			case 3 :
				//Type poison pokemons cannot be poisonned
				if (getpPkmn(p).getType1().getName() != "Poison")
				{
					this.s = this.s + getpPkmn(p).getName() + " is poisonned !";
				}
				else if (getpPkmn(p).getType2() != null)
				{
					if (getpPkmn(p).getType2().getName() != "Poison")
						this.s = this.s + getpPkmn(p).getName() + " is poisonned !";
				}
				else
				{
					this.s = this.s + getpPkmn(p).getName() + " avoid the attack !";
					getpPkmn(p).setStatus(0);
				}
				break;
			case 4 :
				this.s = this.s + getpPkmn(p).getName() + " got burnt !";
				break;
			case 5 :
				this.s = this.s + getpPkmn(p).getName() + " was frozen solid !";
				break;
			case 6 :
				this.s = this.s + getpPkmn(p).getName() + " became confused !";
				getpPkmn(p).setCountConfusion(ThreadLocalRandom.current().nextInt(1,4));
				getpPkmn(p).setStatus(0);
				break;
		}
	}
	
	//Applies stats alteration and prints the result
	private void statModifier(Player p, String stat, int modifier)
	{
		boolean min = false;
		boolean max = false;
		switch (stat)
		{
			case "attack" :
				if (getpPkmn(p).getAttack("stage") >= 6)
				{
					max = true;
					break;
				}
				else if (getpPkmn(p).getAttack("stage") <= -6)
				{
					min = true;
					break;
				}
				getpPkmn(p).getAttack().setStage(getpPkmn(p).getAttack("stage") + modifier);
				break;
			case "defense" :
				if (getpPkmn(p).getDefense("stage")>=6)
				{
					max = true;
					break;
				}
				else if (getpPkmn(p).getDefense("stage")<=-6)
				{
					min = true;
					break;
				}
				getpPkmn(p).getDefense().setStage(getpPkmn(p).getDefense("stage") + modifier);
				break;
			case "speed" :
				if (getpPkmn(p).getSpeed("stage")>=6)
				{
					max = true;
					break;
				}
				else if (getpPkmn(p).getSpeed("stage")<=-6)
				{
					min = true;
					break;
				}
				getpPkmn(p).getSpeed().setStage(getpPkmn(p).getSpeed("stage") + modifier);
				break;
			case "special" :
				if (getpPkmn(p).getSpecial("stage")>=6)
				{
					max = true;
					break;
				}
				else if (getpPkmn(p).getSpecial("stage")<=-6)
				{
					min = true;
					break;
				}
				getpPkmn(p).getSpecial().setStage(getpPkmn(p).getSpecial("current") + modifier);
				break;
			case "accuracy" :
				if (getpPkmn(p).getAccuracy("stage")>=6)
				{
					max = true;
					break;
				}
				else if (getpPkmn(p).getAccuracy("stage")<=-6)
				{
					min = true;
					break;
				}
				getpPkmn(p).getAccuracy().setStage(getpPkmn(p).getAccuracy("stage") + modifier);
				break;
			case "evasion" :
				if (getpPkmn(p).getEvasion("stage")>=6)
				{
					max = true;
					break;
				}
				else if (getpPkmn(p).getEvasion("stage")<=-6)
				{
					min = true;
					break;
				}
				getpPkmn(p).getEvasion().setStage(getpPkmn(p).getEvasion("stage") + modifier);
				break;
			case "wall" :
				p.setWall(modifier);
				p.setCountWall(5);
			
		}
		if (p.getName().equals("Opponent"))
			this.s = this.s + "Enemy ";
		if (!stat.equals("wall"))
			this.s = this.s + getpPkmn(p).getName()+"'s " + stat;
		if (max)
			this.s = this.s + " won't go higher !";
		else if (min)
			this.s = this.s + " won't go lower !";
		else
		{
			//Should not occur if a wall was used
			if (modifier > 0 && !stat.equals("wall"))
				this.s = this.s + " rose !";
			else if (modifier < 0 && !stat.equals("wall"))
				this.s = this.s + " fell !";
			//If a wall was used - light screen
			else if (modifier == 70)
				this.s = this.s + getpPkmn(p).getName() + "'s protected against special attacks !";
			//If a wall was used - reflect
			else if (modifier == 98)
				this.s = this.s + getpPkmn(p).getName() + " gained armor !";
		}
	}
	
	public boolean checkDead(Player p, Window win)
	{
		if (p.getCurrentPkmn().getStatus()==9)
		{
			if (p.getName().equals("Opponent"))
			{				
				if (p.getTeam().size()>0)
				{
					if (p.getTeam().size()==1)
						p.setCurrentPkmn(p.getTeam().get(0));
					p.setCurrentPkmn(p.getTeam().get(ThreadLocalRandom.current().nextInt(0, p.getTeam().size() - 1)));
					p.setCurrentStats(true);
					win.logTrace("Your opponent sent "+getpPkmn(p).getName()+" !");
					getpPkmn(p).setCanAttack(false);
				}
				else
				{
					win.logTrace("You won ! :-D");
					win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
					win.whatToChoose = "continue";
				}
			}
			else
			{
				if (p.getTeam().size()>0)
				{
					win.logTrace("Please choose another Pokémon from your team.");
					for (int i = 0; i < p.getTeam().size(); i++)
					{
						win.logTrace(Integer.toString(i+1)+" - "+p.getTeam().get(i).getName());
					}
					win.logTrace("*********************** ");
					win.whatToChoose = "swap";
					getpPkmn(p).setCanAttack(false);
				}
				else
				{
					win.logTrace("You lost the battle, all of your pokémons fainted. :-(");
					win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
					win.whatToChoose = "continue";
				}
			}
			return true;
		}
		else
			return false;
	}

	public void reinitPrio() 
	{
		getpPkmn(p1).setPrio(false);
		getpPkmn(p2).setPrio(false);
	}
}
