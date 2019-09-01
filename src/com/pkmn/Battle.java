package com.pkmn;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* 
 * This class will contain all the battle's logic such as attacking, calculating damages, accuracy, etc. All it will contain is two players 
 * since every thing else comes from it.
 */

public class Battle 
{
	Player p1;
	Ia p2;
	String s = new String();
	GameData gd;
	
	public Battle(GameData gd)
	{
		this.p1 = new Player("Player");
		this.p2 = new Ia();
		this.gd = new GameData();
	}
	
	public Pokemon getpPkmn(Player p)
	{
		return p.getCurrentPkmn();
	}
	
	public Attack getpattack(Player p, int i)
	{
		return p.getCurrentPkmn().getAttacks().get(i);
	}
	
	public void checkPriority(Player p, Player ia, int rdatt, String choice)
	{
		if (getpPkmn(p).getSpeed("current") >= getpPkmn(ia).getSpeed("current"))
		{
			if (getpattack(ia, rdatt).getStatus() == 51)
				getpPkmn(p).setPrio(false);
			else if (getpPkmn(p).getTwoturnstatus() != 0)
				getpPkmn(p).setPrio(true);
			else if (getpattack(p, Integer.parseInt(choice)-1).getId() == 22)
				getpPkmn(p).setPrio(false);
			else if (getpattack(p, Integer.parseInt(choice)-1).getId() == 80)
				getpPkmn(p).setPrio(false);
			else
				getpPkmn(p).setPrio(true);
		}
		else
		{
			if (!choice.equals("") && getpattack(p, Integer.parseInt(choice)-1).getStatus() == 51)
				getpPkmn(p).setPrio(true);
			else if (getpattack(ia, rdatt).getId() == 22)
				getpPkmn(p).setPrio(true);
			else if (getpattack(ia, rdatt).getId() == 80)
				getpPkmn(p).setPrio(false);
			else
				getpPkmn(p).setPrio(false);
		}
	}
	
	public void aTurn (Player p1, Player p2, Window win, String a1, String a2, GameData g)
	{
		//Calls the useAttack function for index 0 which is the player. Checks if there is a twoturnstatus going on
		if (getpPkmn(p1).getTwoturnstatus() != 0)
			win.logTrace(useAttack(g.allAtks[getpPkmn(p1).getTwoturnstatus()],p1,p2,0));
		else
		{
			int att1 = Integer.parseInt(a1) - 1;
			win.logTrace(useAttack(getpPkmn(p1).getAttacks().get(att1),p1,p2,0));
		}
		//Checks if the opponent fainted
		p2.checkDead(win);
		//If the attack deals to user, checks if it's not dead
		if (p1.checkDead(win))
			getpPkmn(p2).setCanAttack(false);
		if (getpPkmn(p2).getStatus() != 9)
		{
			//Checks if the pokémon didn't flinched between turns
			if (getpPkmn(p2).getCanAttack())
			{
				if (getpPkmn(p2).getTwoturnstatus() != 0)
					win.logTrace(useAttack(g.allAtks[getpPkmn(p2).getTwoturnstatus()],p2,p1,1));
				else
				{
					int att2 = Integer.parseInt(a2) - 1;
					win.logTrace(useAttack(getpPkmn(p2).getAttacks().get(att2),p2,p1,1));
				}
				//Checks if the player fainted
				p1.checkDead(win);
				p2.checkDead(win);
			}
			//... if it flinched, reset canAttack
			else
				getpPkmn(p2).setCanAttack(true);
		}
	}
	
	//Is used whatever an attack is launched by a pokemon. att is the attacker, def is the defender
	//i is used to see if it's the first or the second attack in this turn
	public String useAttack (Attack iAtt, Player att, Player def, int i)
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
					this.getpPkmn(att).setCurrentHp(-damage);
					this.s = this.s + att.checkHpLeft();
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
			//Hyperbeam situation
			if (getpPkmn(att).getTwoturnstatus() == 58)
				this.s = this.doSpecialAttack(iAtt, att, def, i);
			else
			{
				if (getpPkmn(att).getCountThrash() == 0)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					this.s = this.s + this.getpPkmn(att).getName()+" used "+iAtt.getName()+". ";
				}
					
				if (checkHit(iAtt,this.getpPkmn(att),this.getpPkmn(def)))
				{
					if (iAtt.getStatus() == 54 && iAtt.getId() != 55 && iAtt.getId() != 63)
						this.s = this.doSpecialAttack(iAtt, att, def, i);
					else
						this.s = this.doDamages(iAtt, att, def, i);
				}
				else
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					//Handle crashing attacks
					if (iAtt.getId() == 55 || iAtt.getId() == 63)
					{
						int damage = getpPkmn(att).getBaseHp()/8;
						this.s = this.s + getpPkmn(att).getName() + " crashed and lost " + damage + " HP.";
						getpPkmn(att).setCurrentHp(-damage);
						this.s = this.s + att.checkHpLeft();
					}
					else
					{
						this.s = this.s + this.getpPkmn(att).getName() + " missed !";
						if (iAtt.getStatus() == 50)
						{
							getpPkmn(att).setCurrentHp(getpPkmn(att).getBaseHp());
							this.s = this.s + att.checkHpLeft();
						}
					}
				}
			}
			getpPkmn(def).setLastattacksuffered(iAtt);
			//If a move is disabled, lowers the disable counter. Should occur only if an attack is used
			if (this.getpPkmn(att).getCountDisable() > 0)
			{
				if (this.getpPkmn(att).getCountDisable()==1)
				{
					this.getpPkmn(att).getAttacks().get(getpPkmn(att).checkDisabledAttack()).setEnabled(true);
					this.s = this.s + "\nThe effect of DISABLE whore off !";
				}
				this.getpPkmn(att).setCountDisable(this.getpPkmn(att).getCountDisable() - 1);
			}
		}
		//If the attack didn't occur, reset the two turn status
		else
			getpPkmn(att).setTwoturnstatus(0);
		//After the attack occurs, applies whatever damage it should if there is a status
		//If the pokémon is poisoned, lose 1/16 of its HP
		if (this.getpPkmn(att).getStatus()==3 && iAtt.getId() != 77)
		{
			int dmgpn = this.getpPkmn(att).getBaseHp()/16;
			if (getpPkmn(att).getCountToxic() > 0)
				dmgpn = dmgpn * getpPkmn(att).getCountToxic();
			this.s = this.s + "\n";
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s +this.getpPkmn(att).getName() + " suffers "+Integer.toString(dmgpn) + " HP due to poison.";
			this.getpPkmn(att).setCurrentHp(-dmgpn);
			this.s = this.s + att.checkHpLeft();
			if (getpPkmn(att).getCountToxic()>0)
				getpPkmn(att).setCountToxic(getpPkmn(att).getCountToxic() + 1);
		}
		//If the pokémon is burn, lose 1/16 of its HP.
		else if (this.getpPkmn(att).getStatus()==4 && iAtt.getId() != 77)
		{
			this.s = this.s + "\n";
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + this.getpPkmn(att).getName() + " suffers "+Integer.toString(this.getpPkmn(att).getBaseHp()/16) + " HP due to burn.";
			this.getpPkmn(att).setCurrentHp(-this.getpPkmn(att).getBaseHp()/16);
			this.s = this.s + att.checkHpLeft();
		}
		//To avoid *** being shown multiple times when Metronome is used
		if (iAtt.getId() != 77)
			s = s + "\n***********************";
		return s;
	}
	
	//Will calculate what happens if the attack hits 
	//Return s which will be printed as a log trace.
	//i is used to see if it's the first or the second attack in this turn
	private String doDamages(Attack iAtt, Player att, Player def, int i)
	{
		//Checking the status of the attack to see what will happen next
		//If move's power is higher than 0, do damages. Then check status change
		//Also checks if the attack is a multi-hit move
		if (iAtt.getStatus() == 46)
		{
			int hits = ThreadLocalRandom.current().nextInt(2,5);
			int nb = 0;
			int j;
			if (iAtt.getId() == 13 || iAtt.getId() == 29 || iAtt.getId() == 149)
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
			if (iAtt.getId() == 112)
			{
				if (i==0)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					this.s = this.s + this.getpPkmn(def).getName() + " flinched !";
					def.getCurrentPkmn().setCanAttack(false);
				}
			}
			getpPkmn(att).setTwoturnstatus(0);
		}
		//Checks if the attack is a two-turn attack. If so, sets twoturnstatus to the id of this attack
		else if (iAtt.getStatus()==48)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + getpPkmn(att).getName();
			//Switch to trace the attack
			switch (iAtt.getId())
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
				case 112 :
					this.s = this.s + " is glowing !";
					break;
				case 120 : 
					this.s = this.s + " is shining !";
					break;
			}
			getpPkmn(att).setTwoturnstatus(iAtt.getId());
		}
		//If the power is higher than 0 and it's not a multi-hit move, deals damages to opponent
		else if (iAtt.getPower()>0 && iAtt.getStatus() != 46)
			deal(att, def, iAtt);
		//Checking if the attack does anything but statut alteration
		//Checks if the status alteration hits using the accu_status.
		int randomstat1 = ThreadLocalRandom.current().nextInt(0,100);
		if (randomstat1 >= 100 - iAtt.getAccu_status())
		{	
			switch (iAtt.getStatus())
			{
				//Attack drop for opponent
				case 8 : 
					this.s = this.s + getpPkmn(def).statModifier(def,"attack",-1);
					break;
				//Attack boost for user
				case 9 :
					this.s = this.s + getpPkmn(att).statModifier(att,"attack",1);
					break;
				//Attack drop for user
				case 10 :
					this.s = this.s + getpPkmn(def).statModifier(def,"attack",-1);
					break;
				//Double attack boost for opponent
				case 11 :
					this.s = this.s + getpPkmn(def).statModifier(def,"attack",2);
					break;
				//Double attack drop for opponent
				case 12 :
					this.s = this.s + getpPkmn(def).statModifier(def,"attack",-2);
					break;
				//Double attack boost for user
				case 13 :
					this.s = this.s + getpPkmn(att).statModifier(att,"attack",2);
					break;
				//Defense boost for opponent
				case 15:
					this.s = this.s + getpPkmn(def).statModifier(def,"defense",1);
					break;
				//Defense drop for opponent
				case 16:
					this.s = this.s + getpPkmn(def).statModifier(def,"defense",-1);
					break;
				//Defense boost for user
				case 17:
					this.s = this.s + getpPkmn(att).statModifier(att,"defense",1);
					break;
				//Defense drop for user
				case 18:
					this.s = this.s + getpPkmn(att).statModifier(att,"defense",-1);
					break;
				//Double defense boost for opponent
				case 19:
					this.s = this.s + getpPkmn(def).statModifier(def,"defense",2);
					break;
				//Double defense drop for opponent
				case 20:
					this.s = this.s + getpPkmn(def).statModifier(def,"defense",-2);
					break;
				//Double defense drop for user
				case 21:
					this.s = this.s + getpPkmn(att).statModifier(att,"defense",2);
					break;
				//Double defense drop for user
				case 22:
					this.s = this.s + getpPkmn(att).statModifier(att,"defense",-2);
					break;
				//speed boost for opponent
				case 23:
					this.s = this.s + getpPkmn(def).statModifier(def,"speed",1);
					break;
				//speed drop for opponent
				case 24:
					this.s = this.s + getpPkmn(def).statModifier(def,"speed",-1);
					break;
				//speed boost for user
				case 25:
					this.s = this.s + getpPkmn(att).statModifier(att,"speed",1);
					break;
				//speed drop for user
				case 26:
					this.s = this.s + getpPkmn(att).statModifier(att,"speed",-1);
					break;
				//Double speed boost for opponent
				case 27:
					this.s = this.s + getpPkmn(def).statModifier(def,"speed",2);
					break;
				//Double speed drop for opponent
				case 28:
					this.s = this.s + getpPkmn(def).statModifier(def,"speed",-2);
					break;
				//Double speed drop for user
				case 29:
					this.s = this.s + getpPkmn(att).statModifier(att,"speed",2);
					break;
				//Double speed drop for user
				case 30:
					this.s = this.s + getpPkmn(att).statModifier(att,"speed",-2);
					break;
					//speed boost for opponent
				case 31:
					this.s = this.s + getpPkmn(def).statModifier(def,"special",1);
					break;
				//special drop for opponent
				case 32:
					this.s = this.s + getpPkmn(def).statModifier(def,"special",-1);
					break;
				//special boost for user
				case 33:
					this.s = this.s + getpPkmn(att).statModifier(att,"special",1);
					break;
				//special drop for user
				case 34:
					this.s = this.s + getpPkmn(att).statModifier(att,"special",-1);
					break;
				//Double special boost for opponent
				case 35:
					this.s = this.s + getpPkmn(def).statModifier(def,"special",2);
					break;
				//Double special drop for opponent
				case 36:
					this.s = this.s + getpPkmn(def).statModifier(def,"special",-2);
					break;
				//Double special drop for user
				case 37:
					this.s = this.s + getpPkmn(att).statModifier(att,"special",2);
					break;
				//Double special drop for user
				case 38:
					this.s = this.s + getpPkmn(att).statModifier(att,"special",-2);
					break;
				//Accuracy boost for opponent
				case 39:
					this.s = this.s + getpPkmn(def).statModifier(def,"accuracy",1);
					break;
				//Accuracy drop for opponent
				case 40:
					this.s = this.s + getpPkmn(def).statModifier(def,"accuracy",-1);
					break;
				//Evasion boost for user
				case 41:
					this.s = this.s + getpPkmn(att).statModifier(att,"evasion",1);
					break;
				//Evasion drop for user
				case 42:
					this.s = this.s + getpPkmn(att).statModifier(att,"evasion",-1);
					break;
				//Cause flinching
				case 43:
					//Should occur only if the attack was faster or attacked first
					if (i==0)
					{
						if (def.getCurrentPkmn().getStatus()!=9)
						{
							if (att.getName().equals("Opponent"))
								this.s = this.s + "Enemy ";
							this.s = this.s + this.getpPkmn(def).getName() + " flinched !";
							def.getCurrentPkmn().setCanAttack(false);
						}
					}
					break;
				//Healing mechanic
				case 45:
					if (iAtt.getId() == 97 || iAtt.getId() == 119)
					{
						int heal = att.getCurrentPkmn().getBaseHp() / 2;
						att.getCurrentPkmn().setCurrentHp(heal);
						if (att.getName().equals("Opponent"))
							this.s = this.s + "\nEnemy ";
						else
							this.s = this.s + "\n";
						this.s = this.s + this.getpPkmn(att).getName() + " healed "+ heal +" HP and now has "+att.getCurrentPkmn().getCurrentHp() +"/" + att.getCurrentPkmn().getBaseHp()+".";
					}
					else
					{
						getpPkmn(att).setCurrentStats(false);
						getpPkmn(def).setCurrentStats(false);
						this.s = this.s + "All status changes were eliminated !";
					}
					break;
				//Walls
				case 52:
					if (att.getWall() == 0)
						this.s = this.s + getpPkmn(att).statModifier(att,"wall",iAtt.getId());
					else
					{
						if (att.getName().equals("Opponent"))
							this.s = this.s + "Enemy ";
						this.s = this.s + this.getpPkmn(att).getName() + " missed !";
					}
			}
		}
		//Status change can only occur if the Pokémon is not already altered by another status.
		if (this.getpPkmn(def).getStatus()==0 && (iAtt.getStatus()>=1 && iAtt.getStatus() <= 6))
		{
			//Checks if the status alteration hits using the accu_status.
			int randomstat2 = ThreadLocalRandom.current().nextInt(0,100);
			if (randomstat2 >= 100 - iAtt.getAccu_status() && !getpPkmn(def).getSub())
				this.s = this.s + getpPkmn(def).statusModifier(iAtt.getStatus());
		}
		//Shows the "avoid attack" message if the purpose of the move is only status alteration. Exception with healing move that does not hit opponent
		else if (iAtt.getPower()==0 && iAtt.getStatus() <= 6)
		{
			if (att.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + this.getpPkmn(def).getName()+" avoid the attack !";
		}
		return s;
	}
	
	//This function will be used to simplify the usage of status 54 attacks which contains every move that is "unique".
	private String doSpecialAttack(Attack iAtt, Player att, Player def, int i)
	{
		int damage;
		switch (iAtt.getId())
		{
			//Bide
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
						damage = getpPkmn(att).getTotalBideDmg() * 2;
						getpPkmn(def).setCurrentHp(-damage);
						if (def.getName().equals("Opponent"))
							this.s = this.s + "Enemy ";
						this.s = this.s + getpPkmn(def).getName() + " lost " + damage + " HP. ";
						getpPkmn(att).setTotalBideDmg(0);
						this.s = this.s + def.checkHpLeft();
					}
					else
						this.s = this.s + getpPkmn(att).getName() + " is still storing energy !";
				}
				break;
			//Clamp
			case 16 : 
				deal(att,def,iAtt);
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + getpPkmn(def).getName() + " was CLAMPED by " + getpPkmn(att).getName() + " !";
				getpPkmn(def).setCountTrap(ThreadLocalRandom.current().nextInt(3,6));
				break;
			//Conversion
			case 21 : 
				this.s = this.s + "Converted type to ";
				if (def.getName().equals("Opponent"))
					this.s = this.s + "Enemy";
				this.s = this.s + "'s " + getpPkmn(def).getName() + " !";
				getpPkmn(att).setType1(getpPkmn(def).getType1());
				break;
			//Counter
			case 22 : 
				if (getpPkmn(att).getLastattacksuffered().getPhy() && getpPkmn(att).getLastdamagesuffered() > 0)
				{
					damage = getpPkmn(att).getLastdamagesuffered() * 2;
					getpPkmn(def).setCurrentHp(-damage);
					if (def.getName().equals("Opponent"))
						this.s = this.s + "Enemy ";
					this.s = this.s + getpPkmn(def).getName() + " lost " + damage + " HP. ";
					this.s = this.s + def.checkHpLeft();
				}
				else
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				}
				break;
			//Disable	
			case 27 :
				if (getpPkmn(def).getCountDisable() > 0 || getpPkmn(def).getAttacks().size() == 1)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				}
				else
				{
					//Choosing the attack that will be disabled
					Random r = new Random();
					int rdatt = r.nextInt(getpPkmn(def).getAttacks().size());
					getpPkmn(def).getAttacks().get(rdatt).setEnabled(false);
					getpPkmn(def).setCountDisable(ThreadLocalRandom.current().nextInt(2,8));
					if (def.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s  + getpPkmn(def).getName() + "'s " + getpPkmn(def).getAttacks().get(rdatt).getName() + " was disabled !";
				}
				break;
			//Dream Eater
			case 34 : 
				if (att.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				if (getpPkmn(def).getStatus() == 2)
				{
					deal(att,def,iAtt);
					getpPkmn(att).setCurrentHp(getpPkmn(def).getLastdamagesuffered()/2);
					this.s = this.s + this.getpPkmn(att).getName() + " healed "+ getpPkmn(def).getLastdamagesuffered()/2 +" HP and now has "+att.getCurrentPkmn().getCurrentHp() +"/" + att.getCurrentPkmn().getBaseHp()+".";
				}
				else
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				break;
			//Fire spin
			case 42 : 
				deal(att,def,iAtt);
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + getpPkmn(def).getName() + " was trapped !";
				getpPkmn(def).setCountTrap(ThreadLocalRandom.current().nextInt(3,6));
				break;
			//Hyper Beam
			case 58 : 
				if (getpPkmn(att).getTwoturnstatus() == 58)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " must recharge !";
					getpPkmn(att).setTwoturnstatus(0);
				}
				else
				{
					deal(att,def,iAtt);
					getpPkmn(att).setTwoturnstatus(58);
				}
				break;
			//Leech Seed
			case 67 : 
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				if (getpPkmn(def).getSeeded())
					this.s = this.s + getpPkmn(def).getName() + " is already seeded !";
				else
				{
					getpPkmn(def).setSeeded(true);
					this.s = this.s + getpPkmn(def).getName() + " was seeded !";
				}
				break;
			//Metronome
			case 77 : 
				Random r = new Random();
				int rdatt = r.nextInt(156);
				gd.randomAttack(rdatt);
				this.useAttack(gd.randomAttack(rdatt), att, def, i);
				break;
			//Mimic
			case 78 : 
				if (getpPkmn(att).getLastattacksuffered().getName() != null)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " learned " + getpPkmn(att).getLastattacksuffered().getName() + " !";
					getpPkmn(att).getAttacks().remove(iAtt);
					getpPkmn(att).getAttacks().add(getpPkmn(att).getLastattacksuffered());
				}
				else
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				}
				break;
			//Mirror Move
			case 80 :
				this.useAttack(getpPkmn(att).getLastattacksuffered(), att, def, i);
				break;
			//Petal Dance
			case 84 : 
				if (getpPkmn(att).getCountThrash() > 0)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + "'s thrashing about !\n";
					getpPkmn(att).setCountThrash(getpPkmn(att).getCountThrash() - 1);
				}
				else
				{
					getpPkmn(att).setCountThrash(ThreadLocalRandom.current().nextInt(2,3));
					getpPkmn(att).setTwoturnstatus(84);
				}
				deal(att,def,iAtt);
				if (getpPkmn(att).getCountThrash() == 0)
				{
					getpPkmn(att).setTwoturnstatus(0);
					getpPkmn(att).setCountConfusion(ThreadLocalRandom.current().nextInt(1,4));
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " became confused !";
				}
				break;
			//Rest
			case 99 : 
				if (getpPkmn(att).getCurrentHp() != getpPkmn(att).getBaseHp())
				{
					getpPkmn(att).setStatus(2);
					getpPkmn(att).setCountSleep(2);
					getpPkmn(att).setCountToxic(0);
					getpPkmn(att).setCurrentHp(getpPkmn(att).getBaseHp());
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " started sleeping and regained health !";
				}
				else
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				}
				break;
			//Roar
			case 100 :
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + getpPkmn(def).getName() + " got scared  and ran away.";
				def.setCurrentPkmn(def.getTeam().get(ThreadLocalRandom.current().nextInt(0,5)));
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nYour opponent sent ";
				else
					this.s = this.s + "\n You sent ";
				this.s = this.s + getpPkmn(def).getName() + " !";
				break;
			//Struggle
			case 128 :
				if (checkHit(iAtt,getpPkmn(att),getpPkmn(def)))
				{
					deal(att,def,iAtt);
					getpPkmn(att).setCurrentHp(-(getpPkmn(att).getLastdamagesuffered()/4));
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " lost " + (getpPkmn(att).getLastdamagesuffered()/4) + " HP due to recoil.";
					this.s = this.s + att.checkHpLeft();
				}
				break;
			//Substitute
			case 131 :
				if (att.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				if (getpPkmn(att).getCurrentHp() > (getpPkmn(att).getBaseHp()/4) && !getpPkmn(att).getSub())
				{
					damage = getpPkmn(att).getBaseHp()/4;
					getpPkmn(att).setCurrentHp(-damage);
					getpPkmn(att).setHpSubstitute(damage + 1);
					getpPkmn(att).setSub(true);
					this.s = this.s + getpPkmn(att).getName() + " lost " + damage + " HP to create a substitue !";
					this.s = this.s + att.checkHpLeft();
				}
				else
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				break;
			//Super Fang	
			case 132 :
				damage = getpPkmn(def).getCurrentHp()/2;
				getpPkmn(def).setCurrentHp(-damage );
				if (def.getName().equals("Opponent"))
					this.s = this.s + "Enemy ";
				this.s = this.s + getpPkmn(def).getName() + " lost " + damage + " HP. ";
				this.s = this.s + def.checkHpLeft();
				break;
			//Thrash
			case 140 :
				if (getpPkmn(att).getCountThrash() > 0)
				{
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + "'s thrashing about !\n";
					getpPkmn(att).setCountThrash(getpPkmn(att).getCountThrash() - 1);
				}
				else
				{
					getpPkmn(att).setCountThrash(ThreadLocalRandom.current().nextInt(2,3));
					getpPkmn(att).setTwoturnstatus(140);
				}
				deal(att,def,iAtt);
				if (getpPkmn(att).getCountThrash() == 0)
				{
					getpPkmn(att).setTwoturnstatus(0);
					getpPkmn(att).setCountConfusion(ThreadLocalRandom.current().nextInt(1,4));
					if (att.getName().equals("Opponent"))
						this.s = this.s + "\nEnemy ";
					else
						this.s = this.s + "\n";
					this.s = this.s + getpPkmn(att).getName() + " became confused !";
				}
				break;
			//Toxic
			case 146 :
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				if (getpPkmn(def).getStatus() == 0)
				{
					getpPkmn(def).setStatus(3);
					getpPkmn(def).setCountToxic(1);
					this.s = this.s + getpPkmn(def).getName() + " is badly poisoned !";
				}
				else
					this.s = this.s + getpPkmn(att).getName() + " missed !";
				break;
			//Transform
			case 147 :
				if (att.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + getpPkmn(att).getName() + " transformed into " + getpPkmn(def).getName() + " !";
				getpPkmn(att).setName(getpPkmn(def).getName() + " (" + getpPkmn(att).getName() + ")");
				getpPkmn(att).setAttack(getpPkmn(def).getAttack());
				getpPkmn(att).setDefense(getpPkmn(def).getDefense());
				getpPkmn(att).setSpecial(getpPkmn(def).getSpecial());
				getpPkmn(att).setSpeed(getpPkmn(def).getSpeed());
				getpPkmn(att).setAttacks(getpPkmn(def).getAttacks());
				break;
			//Tri-Attack
			case 148 : 
				deal(att,def,iAtt);
				int random = ThreadLocalRandom.current().nextInt(0,100);
				if (random > 80 && getpPkmn(def).getStatus() != 0)
				{
					int status;
					random = ThreadLocalRandom.current().nextInt(0,100);
					if (random > 66)
						status = 1;
					else if (random > 33)
						status = 4;
					else
						status = 5;
					this.s = this.s + getpPkmn(def).statusModifier(status);
				}
				break;
			//Whirlwind
			case 154 :
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + getpPkmn(def).getName() + " was blown away.";
				def.setCurrentPkmn(def.getTeam().get(ThreadLocalRandom.current().nextInt(0,5)));
				if (def.getName().equals("Opponent"))
					this.s = this.s + "\nYour opponent sent ";
				else
					this.s = this.s + "\n You sent ";
				this.s = this.s + getpPkmn(def).getName() + " !";
				break;
			//Wrap
			case 157 : 
				deal(att,def,iAtt);
				if (att.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + getpPkmn(def).getName() + " was WRAPPED by " + getpPkmn(att).getName() + " !";
				getpPkmn(def).setCountTrap(ThreadLocalRandom.current().nextInt(3,6));
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
		//Will automatically succeed if it's the first part of a two-turn move
		if (atk.getStatus() == 48 && att.getTwoturnstatus() == 0)
			return true;
		//If the defender is already trapped, the attack will miss
		if (def.getCountTrap() != 0)
			return false;
		//If the attack is self-applied, it will succeed no matter what.
		if (atk.getSelf())
			return true;
		//If the opponent is in the sky or underground, will automatically fail unless earthquake + underground
		else if (((def.getTwoturnstatus() == 26 && atk.getId() != 36) || def.getTwoturnstatus() == 45))
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
		
	private boolean checkCrit(Attack att, Pokemon pk)
	{
		int rdc = ThreadLocalRandom.current().nextInt(0,100);
		//Check if the attack has high critical ratio
		if (att.getStatus() == 47 || att.getId() == 96)
		{
			if (rdc >= 85)
			{
				this.s = this.s + "\nA critical hit ! ";
				return true;
			}
			else
				return false;
		}
		else
		{
			if (rdc >= 92)
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
	private int deal(Player atk, Player def, Attack iAtt)
	{
		int damage = 0;
		int power;
		float a;
		float d;
		int level = 50;
		if (checkCrit(iAtt, atk.getCurrentPkmn()))
		{
			if (iAtt.getPhy())
			{
				a = atk.getCurrentPkmn().getAttack("base");
				d = def.getCurrentPkmn().getDefense("base");
			}
			else
			{
				a = atk.getCurrentPkmn().getSpecial("base");
				d = def.getCurrentPkmn().getSpecial("base");
			}
			level = level * 2;
		}
		else
		{
			if (iAtt.getPhy())
			{
				a = atk.getCurrentPkmn().getAttack("current");
				if (def.getWall() == 98)
					d = def.getCurrentPkmn().getDefense("current") * 2;
				else
					d = def.getCurrentPkmn().getDefense("current");
			}
			else
			{
				a = atk.getCurrentPkmn().getSpecial("current");
				if (def.getWall() == 70)
					d = def.getCurrentPkmn().getSpecial("current") * 2;
				else
					d = def.getCurrentPkmn().getSpecial("current");
			}
		}
		float ad = a/d;
		power = iAtt.getPower();
		//Verify if STAB
		if (iAtt.getType().equals(atk.getCurrentPkmn().getType1()) || iAtt.getType().equals(atk.getCurrentPkmn().getType2()))
			power = (int) (power * 1.5);
		//If the attack is Earthquake and the defender is underground, doubles the power of the attack
		if (getpPkmn(def).getTwoturnstatus() == 26 && iAtt.getId() == 36)
			power = power * 2;
		//Damage calculation
		damage = (int) (((( ((2*level)/5)+2) * power * (ad))/50) + 2);
		//Check strength/weakness
		damage = this.checkStrWeak(damage, iAtt, def.getCurrentPkmn());
		//If the attack should inflict fixed damages
		if (iAtt.getStatus() == 53)
			damage = iAtt.getPower();
		
		if (damage != 0)
		{
			if (getpPkmn(def).getHpSubstitute()>0)
			{
				if (def.getName().equals("Opponent"))
					this.s = this.s + "Enemy's ";
				this.s = this.s + "Substitue lost " + Integer.toString(damage)+" HP. ";
			}
			else 
			{	
				if (def.getName().equals("Opponent"))
					this.s = this.s + "Enemy ";
				this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP. ";
			}
			//Checking if the attack should steal HP
			if (iAtt.getStatus()==44)
			{
				int heal = 0;
				if (def.getCurrentPkmn().getCurrentHp() - damage < 0)
					heal = def.getCurrentPkmn().getCurrentHp()/2;
				else
					heal = damage/2;
				atk.getCurrentPkmn().setCurrentHp(heal);
				if (atk.getName().equals("Opponent"))
					this.s = this.s + "\nEnemy ";
				else
					this.s = this.s + "\n";
				this.s = this.s + atk.getCurrentPkmn().getName()+" stole "+heal+" HP and now has "+atk.getCurrentPkmn().getCurrentHp()+"/"+atk.getCurrentPkmn().getBaseHp()+".";
			}
			if (getpPkmn(def).getCountBide() > 0)
				getpPkmn(def).setTotalBideDmg(getpPkmn(def).getTotalBideDmg() + damage);
		}
		def.getCurrentPkmn().setCurrentHp(-damage);
		this.s = this.s + def.checkHpLeft();
		//Checks if the attack should inflict recoil damages
		if (iAtt.getStatus() == 49)
		{
			int recoil = damage/4;
			if (recoil == 0)
				recoil = 1;
			getpPkmn(atk).setCurrentHp(-recoil);
			this.s = this.s + "\n";
			if (atk.getName().equals("Opponent"))
				this.s = this.s + "Enemy ";
			this.s = this.s + getpPkmn(atk).getName() + "'s hit with recoil ! It lost "+ recoil +" HP. ";
			this.s = this.s + atk.checkHpLeft();
		}
		//Checks if the attack should kill its user (explosion/self-destruct)
		else if (iAtt.getStatus() == 50)
		{
			getpPkmn(atk).setHpSubstitute(0);
			getpPkmn(atk).setSub(false);
			getpPkmn(atk).setCurrentHp(-(getpPkmn(atk).getBaseHp()));
			this.s = this.s + atk.checkHpLeft();
		}
		getpPkmn(def).setLastdamagesuffered(damage);
		return damage;
	}
	
	public boolean checkSeed(Player att, Player def, Window win)
	{
		int seed;
		if (getpPkmn(def).getSeeded())
		{
			if (getpPkmn(att).getStatus() == 9)
			{
				getpPkmn(def).setSeeded(false);
				return false;
			}
			else
			{
				seed = getpPkmn(def).getBaseHp()/8;
				getpPkmn(def).setCurrentHp(-seed);
				getpPkmn(att).setCurrentHp(seed/2);
				this.s = this.s + "\n" + getpPkmn(def).getName() + " lost " + seed + " HP from LEECH SIDE.\n";
				this.s = this.s + getpPkmn(att).getName() + " was healed " + seed/2 + " HP from LEECH SIDE.";
				this.s = this.s + def.checkHpLeft();
				this.s = this.s + att.checkHpLeft();
				this.s = this.s + "\n***********************";
				win.logTrace(s);
				return true;
			}
		}
		else
			return false;
	}

	public void reinitPrio() 
	{
		getpPkmn(p1).setPrio(false);
		getpPkmn(p2).setPrio(false);
	}
	
	public void endOfTurn(Window win)
	{
		if (p1.getCountWall() > 0)
		{
			p1.setCountWall(p1.getCountWall() - 1);
			if (p1.getCountWall() == 0)
				win.logTrace("The wall of protection vanished !");
		}
		if (p2.getCountWall() > 0)
		{
			p2.setCountWall(p1.getCountWall() - 1);
			if (p2.getCountWall() == 0)
				win.logTrace("The enemy's wall of protection vanished !");
		}
		//Seed checking
		if (checkSeed(p1,p2,win) && p1.getCurrentPkmn().getStatus() != 9)
			p1.checkDead(win);
		if (checkSeed(p2,p1,win) && p2.getCurrentPkmn().getStatus() != 9)
			p2.checkDead(win);
		
		//Trap checking
		if (getpPkmn(p1).getStatus() != 9)
		{
			win.logTrace(getpPkmn(p1).checkTrap(p1));
			p1.checkDead(win);
		}
		if (getpPkmn(p2).getStatus() != 9)
		{
			win.logTrace(getpPkmn(p2).checkTrap(p2));
			p2.checkDead(win);
		}
		//Shows attacks if both player and opponent are not dead
		if (getpPkmn(p2).getStatus() != 9 && getpPkmn(p1).getStatus() != 9)
			win.logTrace(getpPkmn(p1).showAttacks());
		reinitPrio();
		getpPkmn(p1).setLastattacksuffered(new Attack());
		getpPkmn(p2).setLastattacksuffered(new Attack());
		p2.getCurrentPkmn().setCanAttack(true);
	}
}