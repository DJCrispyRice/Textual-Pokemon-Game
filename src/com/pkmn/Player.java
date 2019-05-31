package com.pkmn;
/* 
 * This class contains the team of each player (two player per battle usually). The team contains at least one pokemon and max 6. 
 * The current pokemon is copied in the currentPkmn object. 
 */

import java.util.ArrayList;

public class Player 
{
	String name;
	ArrayList <Pokemon> team;
	Pokemon currentPkmn;
	int wall = 0;
	int countWall = 0;
	boolean seeded = false;
	
	public Player(String name)
	{
		this.team = new ArrayList <Pokemon>();
		this.currentPkmn = new Pokemon();
		this.name = new String(name);
	}
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public ArrayList<Pokemon> getTeam() 
	{
		return team;
	}
	
	public boolean setTeam(Pokemon pkmn) 
	{
		if (team.size()<6)
		{
			this.team.add(pkmn);
			return true;
		}
		else
			return false;
	}
	
	public Pokemon getCurrentPkmn() 
	{
		return currentPkmn;
	}
	
	public void setCurrentPkmn(Pokemon currentPkmn) 
	{
		this.currentPkmn = currentPkmn;
	}
	
	public int getWall() 
	{
		return wall;
	}
	
	public void setWall(int wall) 
	{
		this.wall = wall;
	}
	
	public int getCountWall() 
	{
		return countWall;
	}
	
	public void setCountWall(int countWall) 
	{
		this.countWall = countWall;
		if (this.countWall == 0)
			this.setWall(0);
	}
	
	public boolean getSeeded() 
	{
		return seeded;
	}
	
	public void setSeeded(boolean seeded) 
	{
		this.seeded = seeded;
	}
	
	//stat is used because haze should not reset status.
	public void setCurrentStats(boolean status)
	{
		currentPkmn.setCurrentHp(currentPkmn.getCurrentHp());
		currentPkmn.getAttack().setStage(0);
		currentPkmn.getDefense().setStage(0);
		currentPkmn.getSpecial().setStage(0);
		currentPkmn.getSpeed().setStage(0);
		currentPkmn.getAccuracy().setStage(0);
		currentPkmn.getEvasion().setStage(0);
		this.setSeeded(false);
		if (status)
			currentPkmn.setStatus(0);
		currentPkmn.setTwoturnstatus(0);
		currentPkmn.setSeeded(false);
		currentPkmn.setCanAttack(true);
		currentPkmn.setCountTrap(0);
	}
}
