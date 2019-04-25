package com.pkmn;
/* 
 * This class contains any attacks that will be used by Pokemons. It consists of a name with a description, an Id (useful to find the attack in the game data),
 * the power of the attack (used to calculate damages), the number of PP, a type, if it can trigger a status (paralysis, sleep...) with which accuracy 
 * and its accuracy to hit.
 * 
 * Causes = causes to opponent
 * Applies = applies to the pokémon that used the move
 * 
 * Status 0 : Normal (no status)
 * Status 1 : Paralysis
 * Status 2 : Sleep
 * Status 3 : Poison
 * Status 4 : Burn
 * Status 5 : Frozen
 * Status 6 : Confused
 * Status 7 : Causes attack boost > never used
 * Status 8 : Causes attack drop
 * Status 9 : Applies attack boost
 * Status 10 : Applies attack drop
 * Status 11 : Causes double attack boost > never used
 * Status 12 : Causes double attack drop
 * Status 13 : Applies double attack boost
 * Status 14 : Applies double attack drop > never used
 * Status 15 : Causes defense boost > never used
 * Status 16 : Causes defense drop
 * Status 17 : Applies defense boost
 * Status 18 : Applies defense drop
 * Status 19 : Causes double defense boost
 * Status 20 : Causes double defense drop
 * Status 21 : Applies double defense boost
 * Status 22 : Applies double defense drop
 * Status 23 : Causes speed boost
 * Status 24 : Causes speed drop
 * Status 25 : Applies speed boost
 * Status 26 : Applies speed drop
 * Status 27 : Causes double speed boost
 * Status 28 : Causes double speed drop
 * Status 29 : Applies double speed boost
 * Status 30 : Applies double speed drop
 * Status 31 : Causes special boost
 * Status 32 : Causes special drop
 * Status 33 : Applies special boost
 * Status 34 : Applies special drop
 * Status 35 : Causes double special boost
 * Status 36 : Causes double special drop
 * Status 37 : Applies double special boost
 * Status 38 : Applies double special drop
 * Status 39 : Causes accuracy boost > never used
 * Status 40 : Causes accuracy drop (hi sand attack)
 * Status 41 : Applies accuracy boost (shouldn't be used since no attack does increase your accuracy as far as I know)
 * Status 42 : Applies accuracy drop > never used
 * Status 43 : Flinch
 * Status 44 : Steal HP
 * Status 45 : Heal HP/status
 * Status 46 : Multi-hit move
 * Status 47 : High-critical ratio
 * Status 48 : Two-turn attack
 * Status 49 : Attack with recoil
 * Status 50 : Suicidal attack
 * Status 51 : Priority move
 * Status 52 : Walls
 * Status 53 : Fixed damage
 * Status 54 : Special attacks with unique mechanic
 * Status 55 : Trapped
 * 
 */
public class Attack 
{
	String name;
	String description;
	int id;
	int power;
	int pp;
	boolean phy;//physical = true, special = false
	Type type;
	int status;
	int accu_status;
	int accuracy;
	
	public String getName() 
	{
		return name;
	}
	
	private void setName(String name) 
	{
		this.name = name;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	private void setDescription(String description) 
	{
		this.description = description;
	}
	
	public int getId() 
	{
		return id;
	}
	
	private void setId(int id) 
	{
		this.id = id;
	}
	
	public int getPower() 
	{
		return power;
	}
	
	private void setPower(int power) 
	{
		if (power < 0)
			power = 0;
		this.power = power;
	}
	
	public int getPp() 
	{
		return pp;
	}
	
	private void setPp(int pp) 
	{
		this.pp = pp;
	}
	
	public boolean getPhy() 
	{
		return phy;
	}
	
	private void setPhy(boolean phy) 
	{
		this.phy = phy;
	}
	
	public Type getType() 
	{
		return type;
	}
	
	private void setType(Type type) 
	{
		this.type = type;
	}
	
	public int getStatus() 
	{
		return status;
	}
	
	private void setStatus(int status) 
	{
		this.status = status;
	}
	
	public int getAccuracy() 
	{
		return accuracy;
	}
	
	private void setAccuracy(int accuracy) 
	{
		if (accuracy>100)
			accuracy = 100;
		else if (accuracy < 0)
			accuracy = 0;
		this.accuracy = accuracy;
	}
	
	public int getAccu_status() 
	{
		return accu_status;
	}
	
	private void setAccu_status(int accu_status) 
	{
		if (accu_status>100)
			accu_status = 100;
		else if (accu_status < 0)
			accu_status = 0;
		this.accu_status = accu_status;
	}
	
	public Attack (String name, String description, int id, int power, int pp, boolean phy, Type type, int status, int accu_status, int accuracy)
	{
		this.setName(name);
		this.setDescription(description);
		this.setId(id);
		this.setPower(power);
		this.setPp(pp);
		this.setPhy(phy);
		this.setType(type);
		this.setStatus(status);
		this.setAccu_status(accu_status);
		this.setAccuracy(accuracy);
	}
}
