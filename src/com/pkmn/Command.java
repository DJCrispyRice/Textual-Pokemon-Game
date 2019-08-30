package com.pkmn;

public abstract class Command 
{
	public static void walk(Player p,GameData gd, String area)
	{
		System.out.println("Walking to : " +area);
	}
}
