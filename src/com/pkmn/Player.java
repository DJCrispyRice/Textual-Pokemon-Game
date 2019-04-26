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
	
	//stat is used because haze should not reset status.
	public void setCurrentStats(boolean stat)
	{
		currentPkmn.setCurrentHp(currentPkmn.getBaseHp());
		currentPkmn.setCurrentAtk(currentPkmn.getBaseAtk());
		currentPkmn.setStageAtk(0);
		currentPkmn.setCurrentDef(currentPkmn.getBaseDef());
		currentPkmn.setStageDef(0);
		currentPkmn.setCurrentSpe(currentPkmn.getBaseSpe());
		currentPkmn.setStageSpe(0);
		currentPkmn.setCurrentSpd(currentPkmn.getBaseSpd());
		currentPkmn.setStageSpd(0);
		currentPkmn.setCurrentAccu(currentPkmn.getBaseAccu());
		currentPkmn.setStageAccu(0);
		if (stat)
			currentPkmn.setStatus(0);
	}
}
