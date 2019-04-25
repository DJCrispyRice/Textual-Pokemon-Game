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
	
	//Shows attacks that can be used by your pokémon
	public String showAttacks()
	{
		this.s = new String();
		s = "***********************\nPlease choose an attack.";
		for (int i=0;i<this.getpPkmn(p1).getAttacks().size();i++)
		{
			s = s + "\n "+Integer.toString(i+1)+". " + this.getpattack(this.p1, i).getType().getName() + " - " + this.getpattack(this.p1,i).getName()+" - "+this.getpattack(this.p1,i).getDescription();
		}
		s = s + "\n*********************** ";
		return s;
	}
	
	//Is used whatever an attack is launched by a pokemon. att is the attacker, def is the defender
	public String useAttack (int iAtt, Player att, Player def)
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
			if (checkHit(this.getpattack(att,iAtt),this.getpPkmn(att)))
				this.s = this.doDamages(iAtt, att, def);
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
	private String doDamages(int iAtt, Player att, Player def)
	{
		//Checking the status of the attack to see what will happen next
		//If move's power is higher than 0, do damages. Then check status change
		if (this.getpattack(att,iAtt).getPower()>0)
			atk0(att, def, iAtt, 1);
		
		//Checking if the attack does anything but statut alteration
		//Checks if the status alteration hits using the accu_status.
		int randomstat1 = ThreadLocalRandom.current().nextInt(0,100);
		if (randomstat1 >= 100 - this.getpattack(att, iAtt).getAccu_status())
		{
			switch (this.getpattack(att, iAtt).getStatus())
			{
				//Attack drop for opponent
				case 8 : 
					atk8(def);
					break;
				//Attack boost for user
				case 9 :
					atk9(att);
					break;
				//Attack drop for user - same as atk8 but with the other player in parameter
				case 10 :
					atk8(def);
					break;
				//Case 11 is never used
				//Double attack drop for opponent
				case 12 :
					atk12(def);
					break;
				//Double attack boost for user
				case 13 :
					atk13(att);
					break;
				//Case 14 is never used
				//Defense drop for opponent
				case 16:
					atk16(def);
					break;
			}
		}
		//Status change can only occur if the Pokémon is not already altered by another status.
		if (this.getpPkmn(def).getStatus()==0)
		{
			//Checks if the status alteration hits using the accu_status.
			int randomstat2 = ThreadLocalRandom.current().nextInt(0,100);
			if (randomstat2 >= 100 - this.getpattack(att, iAtt).getAccu_status())
			{
				switch (this.getpattack(att, iAtt).getStatus())
				{			
					//Can cause paralysis
					case 1 : 
						atk1(def);
						break;
					//Can cause sleeping
					case 2 : 
						atk2(def);
						break;
					//Can cause poison
					case 3 : 
						atk3(def);
						break;
					//Can cause burn
					case 4 : 
						atk4(def);
						break;
					//Can cause freeze
					case 5 : 
						atk5(def);
						break;
					//Can cause confusion
					case 6 : 
						atk6(def);
						break;
				}
			}
		}
		//Shows the "avoid attack" message if the purpose of the move is only status alteration
		else if (this.getpattack(att,iAtt).getPower()==0)
		{
			this.s = this.s + this.getpPkmn(this.p2).getName()+" avoid the attack !";
		}
		return s;
	}
	
	public Pokemon getpPkmn(Player p)
	{
		return p.getCurrentPkmn();
	}
	
	public Attack getpattack(Player p, int i)
	{
		return p.getCurrentPkmn().getAttacks().get(i);
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
	
	private boolean checkHit(Attack att, Pokemon pk)
	{
		int accurate = 100;
		accurate = accurate - (100 - att.getAccuracy()) - (100 - pk.getCurrentAccu());
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
	
	//Mechanics for status 0 attack which is basically dealing damage.
	//atk is attacker, def is defender
	private void atk0(Player atk, Player def, int iAtt, int i)
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
			this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
		def.getCurrentPkmn().setCurrentHp(def.getCurrentPkmn().getCurrentHp() - damage);
		this.checkHpLeft(def);
	}
	
	//Mechanics for atk2 which is causing paralysis
	private void atk1(Player def)
	{
		def.getCurrentPkmn().setStatus(1);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " is paralysed ! It may not be able to attack !";
		def.getCurrentPkmn().setCurrentSpd((int) (def.getCurrentPkmn().getBaseSpd()*0.25));
	}
	
	//Mechanics for atk2 which is causing sleep
	private void atk2(Player def)
	{
		def.getCurrentPkmn().setStatus(2);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " felt asleep !";
		def.getCurrentPkmn().setCountSleep(ThreadLocalRandom.current().nextInt(1,7));
	}
	
	//Mechanics for atk3 which is get poisoned
	private void atk3(Player def)
	{
		def.getCurrentPkmn().setStatus(3);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " is poisoned !";
	}
	
	//Mechanics for atk3 which is get poisoned
	private void atk4(Player def)
	{
		def.getCurrentPkmn().setStatus(4);
		def.getCurrentPkmn().setCurrentAtk(def.getCurrentPkmn().getBaseAtk()/2);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " got burnt !";
	}
	
	//Mechanics for atk5 which is get frozen
	private void atk5(Player def)
	{
		def.getCurrentPkmn().setStatus(5);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " was frozen solid !";
	}
	
	//Mechanics for atk5 which is confusion
	private void atk6(Player def)
	{
		def.getCurrentPkmn().setStatus(6);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " became confused !";
		def.getCurrentPkmn().setCountConfusion(ThreadLocalRandom.current().nextInt(1,4));
	}
	
	//Mechanics for atk8 which is single atk drop
	private void atk8(Player def)
	{
		if (def.getCurrentPkmn().getStageAtk()==6)
			this.s = this.s + def.getCurrentPkmn().getName()+"'s attack won't go lower !";
		else
		{
			def.getCurrentPkmn().setStageAtk(def.getCurrentPkmn().getStageAtk() - 1);
			this.s = this.s + def.getCurrentPkmn().getName()+"'s attack fell !";
			def.getCurrentPkmn().setCurrentAtk(calculateStat(def.getCurrentPkmn().getStageAtk(),def.getCurrentPkmn().getBaseAtk()));
		}
	}
	
	//Mechanics for atk9 which is single atk boost
	private void atk9(Player atk)
	{
		System.out.println(atk.getCurrentPkmn().getStageAtk());
		if (atk.getCurrentPkmn().getStageAtk()>=6)
			this.s = this.s + atk.getCurrentPkmn().getName()+"'s attack won't go higher !";
		else
		{
			atk.getCurrentPkmn().setStageAtk(atk.getCurrentPkmn().getStageAtk() + 1);
			this.s = this.s + atk.getCurrentPkmn().getName()+"'s attack rose !";
			atk.getCurrentPkmn().setCurrentAtk(calculateStat(atk.getCurrentPkmn().getStageAtk(),atk.getCurrentPkmn().getBaseAtk()));
		}
	}
	
	//Mechanics for atk12 which is double atk drop
	private void atk12(Player def)
	{
		if (def.getCurrentPkmn().getStageAtk()==6)
			this.s = this.s + def.getCurrentPkmn().getName()+"'s attack won't go lower !";
		else
		{
			def.getCurrentPkmn().setStageAtk(def.getCurrentPkmn().getStageAtk() - 2);
			if (def.getCurrentPkmn().getStageAtk()<-6)
				def.getCurrentPkmn().setStageAtk(-6);
			this.s = this.s + def.getCurrentPkmn().getName()+"'s attack sharply fell !";
			def.getCurrentPkmn().setCurrentAtk(calculateStat(def.getCurrentPkmn().getStageAtk(),def.getCurrentPkmn().getBaseAtk()));
		}
	}
	
	//Mechanics for atk13 which is double atk boost
	private void atk13(Player atk)
	{
		System.out.println(atk.getCurrentPkmn().getStageAtk());
		if (atk.getCurrentPkmn().getStageAtk()>=6)
			this.s = this.s + atk.getCurrentPkmn().getName()+"'s attack won't go higher !";
		else
		{
			atk.getCurrentPkmn().setStageAtk(atk.getCurrentPkmn().getStageAtk() + 2);
			if (atk.getCurrentPkmn().getStageAtk()>6)
				atk.getCurrentPkmn().setStageAtk(6);
			this.s = this.s + atk.getCurrentPkmn().getName()+"'s attack sharply rose !";
			atk.getCurrentPkmn().setCurrentAtk(calculateStat(atk.getCurrentPkmn().getStageAtk(),atk.getCurrentPkmn().getBaseAtk()));
		}
	}
	
	//Mechanics for atk16 which is single def drop
	private void atk16(Player def)
	{
		if (def.getCurrentPkmn().getStageDef()==6)
			this.s = this.s + def.getCurrentPkmn().getName()+"'s defense won't go lower !";
		else
		{
			def.getCurrentPkmn().setStageDef(def.getCurrentPkmn().getStageDef() - 1);
			this.s = this.s + def.getCurrentPkmn().getName()+"'s defense fell !";
			def.getCurrentPkmn().setCurrentDef(calculateStat(def.getCurrentPkmn().getStageDef(),def.getCurrentPkmn().getBaseDef()));
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
