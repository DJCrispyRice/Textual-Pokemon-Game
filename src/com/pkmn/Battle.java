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
		this.s = new String("\n");
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
				case 6 :
					this.s = this.s + "confused.+++\n";
					break;
			}
		}
		s = s + "***********************\nWhat should "+this.getpPkmn(p1).getName()+" do ?";
		for (int i=0;i<this.getpPkmn(p1).getAttacks().size();i++)
		{
			s = s + "\n "+Integer.toString(i+1)+". " + this.getpattack(this.p1, i).getType().getName() + " - " + this.getpattack(this.p1,i).getName()+" - "+this.getpattack(this.p1,i).getDescription();
		}
		s = s + "\n*********************** ";
		return s;
	}
	
	//Is used whatever an attack is launched by a pokemon. att is the attacker, def is the defender
	//i is used to see if it's the first or the second attack in this turn
	public String useAttack (int iAtt, Player att, Player def, int i)
	{
		//By default, the attack should occur
		Boolean atkok = true;
		this.s = new String();
		//If the pokemon is paralysed, it has 25% chance not to attack
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
				this.s = this.getpPkmn(att).getName() + " woke up !";
		}
		//If the pokemon is frozen, it can't attack except if it unfrozes
		else if (this.getpPkmn(att).getStatus()==5)
		{
			int rngfrz = ThreadLocalRandom.current().nextInt(0,100);
			if (rngfrz>90)
			{
				this.getpPkmn(att).setStatus(1);
				this.s = this.getpPkmn(att).getName() + " unfroze !";
			}
			else
				this.s = this.getpPkmn(att).getName() + " is frozen solid !";
			atkok = false;
		}
		//If the pokemon is confused, it has 50% of hitting itself
		else if (this.getpPkmn(att).getStatus()==6)
		{
			this.getpPkmn(att).setCountConfusion(this.getpPkmn(att).getCountConfusion() - 1);
			if (this.getpPkmn(att).getCountConfusion()>0)
			{
				this.s = this.s + this.getpPkmn(att).getName() + " is confused...\n";
				int ckcon = ThreadLocalRandom.current().nextInt(0,100);
				if (ckcon > 50)
				{
					int damage = ((2*20)/2 + 2)*40;
					damage = damage * (this.getpPkmn(att).getCurrentAtk()/this.getpPkmn(att).getCurrentDef());
					damage = damage/50 + 2;
					this.s = this.s + this.getpPkmn(att).getName() + " hurts himself in confusion and lost "+damage+" HP.";
					this.getpPkmn(p1).setCurrentHp(this.getpPkmn(p1).getCurrentHp() - damage);
					this.checkHpLeft(att);
					atkok = false;
				}
			}
			else
			{
				this.s = this.s + this.getpPkmn(att).getName() + " snapped out of confusion !\n";
				this.getpPkmn(att).setStatus(0);
			}	
		}
		//If the attack can occur (no status avoiding it to happen), it does...
		if (atkok)
		{
			this.s = this.s + this.getpPkmn(att).getName()+" used "+this.getpattack(att,iAtt).getName()+". ";
			if (checkHit(this.getpattack(att,iAtt),this.getpPkmn(att),this.getpPkmn(def)))
				this.s = this.doDamages(iAtt, att, def, i);
			else
				this.s = this.s + "\n"+this.getpPkmn(this.p1).getName() + " missed !";
		}
		//After the attack occurs, applies whatever damage it should if there is a status
		//If the pokémon is poisoned, lose 1/16 of its HP
		if (this.getpPkmn(att).getStatus()==3)
		{
			this.s = this.s + "\n"+this.getpPkmn(att).getName() + " suffers "+Integer.toString(this.getpPkmn(att).getBaseHp()/16) + " HP due to poison.";
			this.getpPkmn(att).setCurrentHp(this.getpPkmn(att).getCurrentHp() - this.getpPkmn(att).getBaseHp()/16);
			this.checkHpLeft(att);
		}
		//If the pokémon is burn, lose 1/16 of its HP.
		else if (this.getpPkmn(att).getStatus()==4)
		{
			this.s = this.s + "\n"+this.getpPkmn(att).getName() + " suffers "+Integer.toString(this.getpPkmn(att).getBaseHp()/16) + " HP by the burn.";
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
			if (getpattack(att,iAtt).getId() == 29)
				hits = 2;
			for (j = 1; j <= hits; j++)
			{
				nb++;
				deal(att,def,iAtt,0);
				this.s = this.s + "\n";
				if (getpPkmn(def).getCurrentHp()==0)
					break;
			}
			if (getpPkmn(def).getCurrentHp()>0)
				this.s = this.s + "Hit " + getpPkmn(def).getName() + " " + nb + " times.";
		}
		else if (this.getpattack(att,iAtt).getPower()>0 || this.getpattack(att, iAtt).getStatus() != 46)
			deal(att, def, iAtt, 1);
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
						this.s = this.s + this.getpPkmn(att).getName() + " healed "+ heal +" HP and now has "+att.getCurrentPkmn().getCurrentHp() +"/" + att.getCurrentPkmn().getBaseHp()+".";
					}
					else
					{
						att.setCurrentStats(false);
						def.setCurrentStats(false);
						this.s = this.s + "All status changes were eliminated !";
					}
					break;
			}
		}
		//Status change can only occur if the Pokémon is not already altered by another status.
		if (this.getpPkmn(def).getStatus()==0 && (this.getpattack(att,iAtt).getStatus()>=1 && this.getpattack(att,iAtt).getStatus()<=6))
		{
			//Checks if the status alteration hits using the accu_status.
			int randomstat2 = ThreadLocalRandom.current().nextInt(0,100);
			if (randomstat2 >= 100 - this.getpattack(att, iAtt).getAccu_status())
			{
				statusModifier(def,this.getpattack(att,iAtt).getStatus());
			}
		}
		//Shows the "avoid attack" message if the purpose of the move is only status alteration. Exception with healing move that does not hit opponent
		else if (this.getpattack(att,iAtt).getPower()==0 && this.getpattack(att,iAtt).getStatus() != 45)
		{
			this.s = this.s + this.getpPkmn(def).getName()+" avoid the attack !";
		}
		return s;
	}
	
	private boolean checkHit(Attack atk, Pokemon att, Pokemon def)
	{
		int accurate = 100;
		accurate = accurate - (100 - atk.getAccuracy()) - (100 - att.getCurrentAccu() - (def.getCurrentEvasion() - 100));
		if (accurate < 0)
			accurate = 0;
		int random = ThreadLocalRandom.current().nextInt(0,100);
		if (random > accurate)
			return false;
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
	
	//To write how many hp left the pokémon has. def is the player who is being checked.
	public void checkHpLeft(Player def)
	{
		//Checks if the pokemon is dead
		if (def.getCurrentPkmn().getStatus() == 9)
		{
			this.s = this.s + "\n***********************\n"+def.getCurrentPkmn().getName()+" fainted !";
			if (def.getName().equals("Player"))
			{
				this.p1.getTeam().remove(def.getCurrentPkmn());
				this.s = this.s + "\nYou have "+this.p1.getTeam().size()+" pokémons left.";
			}
			else
			{
				this.p2.getTeam().remove(def.getCurrentPkmn());
				this.s = this.s + "\nYour opponent has "+this.p2.getTeam().size()+" pokémons left.";
			}
		}
		else
			this.s = this.s +"\n"+def.getCurrentPkmn().getName()+" has "+def.getCurrentPkmn().getCurrentHp()+"/"+def.getCurrentPkmn().getBaseHp()+" HP.";
	}

	private boolean checkCrit(Attack att, Pokemon pk)
	{
		//Check if the attack has high critical ratio
		if (att.getStatus() == 47)
		{
			int T = pk.getBaseSpd() * 4;
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
			int T = pk.getBaseSpd() / 2;
			int P = ThreadLocalRandom.current().nextInt(0,256);
			if (T > P)
			{
				this.s = s + "\nA critical hit ! ";
				return true;
			}
			else
				return false;
		}
	}
	
	//Dealing damages
	//atk is attacker, def is defender
	private void deal(Player atk, Player def, int iAtt, int i)
	{
		int damage = 0;
		int power;
		power = this.getpattack(atk,iAtt).getPower();
		//Verify if STAB
		if (this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType1()) || this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType2()))
		{	
			power = (int) (power * 1.5);
		}
		//Mathematical calculation for damages
		damage = ((2*20)/2 + 2)*power;
		//Checking crit to see if we use base or current stats
		if (checkCrit(this.getpattack(atk,iAtt), atk.getCurrentPkmn()))
		{
			if (this.getpattack(atk,iAtt).getPhy())
				damage = damage * (atk.getCurrentPkmn().getBaseAtk()/def.getCurrentPkmn().getBaseDef());
			else
				damage = damage * (atk.getCurrentPkmn().getBaseSpe()/def.getCurrentPkmn().getBaseSpe());
			damage = damage/50 + 2;
			damage = (int) (damage * 1.5);
		}
		else
		{
			if (this.getpattack(atk,iAtt).getPhy())
				damage = damage * (atk.getCurrentPkmn().getCurrentAtk()/def.getCurrentPkmn().getCurrentDef());
			else
				damage = damage * (atk.getCurrentPkmn().getCurrentSpe()/def.getCurrentPkmn().getCurrentSpe());
			damage = damage/50 + 2;
		}
		//Checks strength/weakness
		damage = this.checkStrWeak(damage, this.getpattack(atk,iAtt), def.getCurrentPkmn());
		if (damage != 0)
		{
			//Checking if the attack should steal HP
			if (this.getpattack(atk, iAtt).getStatus()==44)
			{
				int heal = 0;
				if (def.getCurrentPkmn().getCurrentHp() - damage < 0)
					heal = def.getCurrentPkmn().getCurrentHp()/2;
				else
					heal = damage/2;
				this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
				atk.getCurrentPkmn().setCurrentHp(atk.getCurrentPkmn().getCurrentHp()+heal);
				this.s = this.s + "\n"+atk.getCurrentPkmn().getName()+" stole "+heal+" HP and now has "+atk.getCurrentPkmn().getCurrentHp()+"/"+atk.getCurrentPkmn().getBaseHp()+".";
			}
			else
				this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
		}
		def.getCurrentPkmn().setCurrentHp(def.getCurrentPkmn().getCurrentHp() - damage);
		this.checkHpLeft(def);
	}
	
	//Applies status alteration and prints the result
	private void statusModifier(Player p, int status)
	{
		p.getCurrentPkmn().setStatus(status);
		switch (status)
		{
			case 1 :
				this.s = this.s + "\n" + p.getCurrentPkmn().getName() + " is paralysed ! It may not be able to attack !";
				p.getCurrentPkmn().setCurrentSpd((int) (p.getCurrentPkmn().getBaseSpd()*0.25));
				break;
			case 2 :
				this.s = this.s + "\n" + p.getCurrentPkmn().getName() + " felt asleep !";
				p.getCurrentPkmn().setCountSleep(ThreadLocalRandom.current().nextInt(1,7));
				break;
			case 3 :
				this.s = this.s + "\n" + p.getCurrentPkmn().getName() + " is poisonned !";
				break;
			case 4 :
				this.s = this.s + "\n" + p.getCurrentPkmn().getName() + " got burnt !";
				break;
			case 5 :
				this.s = this.s + "\n" + p.getCurrentPkmn().getName() + " was frozen solid !";
				break;
			case 6 :
				this.s = this.s + "\n" + p.getCurrentPkmn().getName() + " became confused !";
				p.getCurrentPkmn().setCountConfusion(ThreadLocalRandom.current().nextInt(1,4));
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
				p.getCurrentPkmn().setStageAtk(p.getCurrentPkmn().getStageAtk() + modifier);
				if (p.getCurrentPkmn().getStageAtk()>6)
				{
					max = true;
					break;
				}
				else if (p.getCurrentPkmn().getStageAtk()<-6)
				{
					min = true;
					break;
				}
				p.getCurrentPkmn().setStageAtk(p.getCurrentPkmn().getStageAtk() + modifier);
				p.getCurrentPkmn().setCurrentAtk(calculateStat(p.getCurrentPkmn().getStageAtk(),p.getCurrentPkmn().getBaseAtk()));
				break;
			case "defense" :
				if (p.getCurrentPkmn().getStageDef()>6)
				{
					max = true;
					break;
				}
				else if (p.getCurrentPkmn().getStageDef()<-6)
				{
					min = true;
					break;
				}
				p.getCurrentPkmn().setStageDef(p.getCurrentPkmn().getStageDef() + modifier);
				p.getCurrentPkmn().setCurrentDef(calculateStat(p.getCurrentPkmn().getStageDef(),p.getCurrentPkmn().getBaseDef()));
				break;
			case "speed" :
				if (p.getCurrentPkmn().getStageSpd()>6)
				{
					max = true;
					break;
				}
				else if (p.getCurrentPkmn().getStageSpd()<-6)
				{
					min = true;
					break;
				}
				p.getCurrentPkmn().setStageSpd(p.getCurrentPkmn().getStageSpd() + modifier);
				p.getCurrentPkmn().setCurrentSpd(calculateStat(p.getCurrentPkmn().getStageSpd(),p.getCurrentPkmn().getBaseSpd()));
				break;
			case "special" :
				if (p.getCurrentPkmn().getStageSpd()>6)
				{
					max = true;
					break;
				}
				else if (p.getCurrentPkmn().getStageSpd()<-6)
				{
					min = true;
					break;
				}
				p.getCurrentPkmn().setStageSpd(p.getCurrentPkmn().getStageSpd() + modifier);
				p.getCurrentPkmn().setCurrentSpe(calculateStat(p.getCurrentPkmn().getStageSpe(),p.getCurrentPkmn().getBaseSpe()));
				break;
			case "accuracy" :
				if (p.getCurrentPkmn().getStageAccu()>6)
				{
					max = true;
					break;
				}
				else if (p.getCurrentPkmn().getStageAccu()<-6)
				{
					min = true;
					break;
				}
				p.getCurrentPkmn().setStageAccu(p.getCurrentPkmn().getStageAccu() + modifier);
				p.getCurrentPkmn().setCurrentAccu(calculateStat(p.getCurrentPkmn().getStageAccu(),p.getCurrentPkmn().getBaseAccu()));
				break;
			case "evasion" :
				if (p.getCurrentPkmn().getStageEvasion()>6)
				{
					max = true;
					break;
				}
				else if (p.getCurrentPkmn().getStageEvasion()<-6)
				{
					min = true;
					break;
				}
				p.getCurrentPkmn().setStageEvasion(p.getCurrentPkmn().getStageEvasion() + modifier);
				p.getCurrentPkmn().setCurrentEvasion(calculateStat(p.getCurrentPkmn().getStageEvasion(),p.getCurrentPkmn().getBaseEvasion()));
				break;
			
		}
		this.s = this.s + p.getCurrentPkmn().getName()+"'s " + stat;
		if (max)
			this.s = this.s + "won't go higher !";
		else if (min)
			this.s = this.s + "won't go lower !";
		else
		{
			if (modifier > 0)
				this.s = this.s + " rose !";
			else
				this.s = this.s + " fell !";
		}
	}
	
	private int calculateStat(int stage,int stat)
	{
		switch (stage)
		{
			case 6 :
				return stat * 4;
			case 5 : 
				return (int) (stat * 3.5);
			case 4 : 
				return (int) (stat * 3);
			case 3 : 
				return (int) (stat * 2.5);
			case 2 : 
				return (int) (stat * 2);
			case 1 :
				return (int) (stat * 1.5);
			case 0 :
				return stat;
			case -1 :
				return (int) (stat * 0.66);
			case -2 :
				return (int) (stat * 0.5);
			case -3 :
				return (int) (stat * 0.4);
			case -4 :
				return (int) (stat * 0.33);
			case -5 :
				return (int) (stat * 0.28);
			case -6 :
				return (int) (stat * 0.25);
		}
		return stat;
	}
}
