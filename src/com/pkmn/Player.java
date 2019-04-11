package com.pkmn;
/* 
 * This class contains the team of each player (two player per battle usually). The team contains at least one pokemon and max 6. 
 * The current pokemon is copied in the currentPkmn object. 
 */

import java.util.ArrayList;

public class Player 
{
	ArrayList <Pokemon> team;
	Pokemon currentPkmn;
	public Player()
	{
		this.team = new ArrayList <Pokemon>();
		this.currentPkmn = new Pokemon();
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
	private Pokemon getCurrentPkmn() 
	{
		return currentPkmn;
	}
	private void setCurrentPkmn(Pokemon currentPkmn) 
	{
		this.currentPkmn = currentPkmn;
	}
}
