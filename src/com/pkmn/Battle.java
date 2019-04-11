package com.pkmn;

/* 
 * This class will contain all the battle's logic such as attacking, calculating damages, accuracy, etc. All it will contain is two players 
 * since every thing else comes from it.
 */

public class Battle 
{
	Player p1;
	Player p2;
	GameData gd;
	
	public Battle(GameData gd, Window win)
	{
		this.p1 = new Player();
		this.p2 = new Player();
	}
	
}
