package com.pkmn;
/* 
 * This class regroups every game data. It is created at the opening of the game and contains all assets such as the 151 Pokémons, all attacks and all types.
 * It will be instantiated under the name "gd" and should be accessed from everywhere. Everything is contained in its array and will get an Id
 * which corresponds to its place in the array.
 * It will also contains some logic of the game. 
 */

public final class GameData 
{
	Pokemon[] allPkmn;
	Attack[] allAtks;
	Type[] allTypes;
	public GameData(Window win) throws InterruptedException
	{
		this.createTypes(win);
		this.createAttacks(win);
		this.createPkmn(win);
	}
	
	private void createTypes(Window win) throws InterruptedException
	{
		this.allTypes = new Type[15];
		//Creating all types
		this.allTypes[0] = new Type("Normal",0);
		this.allTypes[1] = new Type("Fight",1);
		this.allTypes[2] = new Type("Flying",2);
		this.allTypes[3] = new Type("Poison",3);
		this.allTypes[4] = new Type("Ground",4);
		this.allTypes[5] = new Type("Rock",5);
		this.allTypes[6] = new Type("Bug",6);
		this.allTypes[7] = new Type("Ghost",7);
		this.allTypes[8] = new Type("Fire",8);
		this.allTypes[9] = new Type("Water",9);
		this.allTypes[10] = new Type("Grass",10);
		this.allTypes[11] = new Type("Electric",11);
		this.allTypes[12] = new Type("Psychic",12);
		this.allTypes[13] = new Type("Ice",13);
		this.allTypes[14] = new Type("Dragon",14);
		
		//Assign types their strength/weakness/uselessness
		//Normal : no strength, weak against rock, useless against ghost.
		this.allTypes[0].getWeak().add(this.allTypes[5]);
		this.allTypes[0].getUseless().add(this.allTypes[7]);
		
		//Fight : strong against normal and rock, weak against flying, poison, bug and psychic
		this.allTypes[1].getStrength().add(this.allTypes[0]);
		this.allTypes[1].getStrength().add(this.allTypes[5]);
		this.allTypes[1].getWeak().add(this.allTypes[2]);
		this.allTypes[1].getWeak().add(this.allTypes[3]);
		this.allTypes[1].getWeak().add(this.allTypes[6]);
		this.allTypes[1].getWeak().add(this.allTypes[12]);
		
		//Flying : strong against fight, bug and grass, weak against rock and electric
		this.allTypes[2].getStrength().add(this.allTypes[1]);
		this.allTypes[2].getStrength().add(this.allTypes[6]);
		this.allTypes[2].getStrength().add(this.allTypes[10]);
		this.allTypes[2].getWeak().add(this.allTypes[5]);
		this.allTypes[2].getWeak().add(this.allTypes[11]);

		//Poison : strong against grass, weak against poison, rock, ground and ghost
		this.allTypes[3].getStrength().add(this.allTypes[10]);
		this.allTypes[3].getWeak().add(this.allTypes[3]);
		this.allTypes[3].getWeak().add(this.allTypes[4]);
		this.allTypes[3].getWeak().add(this.allTypes[5]);
		this.allTypes[3].getWeak().add(this.allTypes[7]);
		
		//Ground : Strong against poison, rock, fire and electric, weak against bug, useless against flying
		this.allTypes[4].getStrength().add(this.allTypes[3]);
		this.allTypes[4].getStrength().add(this.allTypes[5]);
		this.allTypes[4].getStrength().add(this.allTypes[8]);
		this.allTypes[4].getStrength().add(this.allTypes[11]);
		this.allTypes[4].getWeak().add(this.allTypes[6]);
		this.allTypes[4].getUseless().add(this.allTypes[2]);
		
		//Rock : Strong against flying, bug, fire and ice, weak against fight and ground
		this.allTypes[5].getStrength().add(this.allTypes[2]);
		this.allTypes[5].getStrength().add(this.allTypes[6]);
		this.allTypes[5].getStrength().add(this.allTypes[8]);
		this.allTypes[5].getStrength().add(this.allTypes[13]);
		this.allTypes[5].getWeak().add(this.allTypes[1]);
		this.allTypes[5].getWeak().add(this.allTypes[4]);
		
		//Bug : Strong against grass, poison and psychic, weak against fight, flying, ghost and fire
		this.allTypes[6].getStrength().add(this.allTypes[2]);
		this.allTypes[6].getStrength().add(this.allTypes[2]);
		this.allTypes[6].getStrength().add(this.allTypes[2]);
		this.allTypes[6].getWeak().add(this.allTypes[1]);
		this.allTypes[6].getWeak().add(this.allTypes[2]);
		this.allTypes[6].getWeak().add(this.allTypes[7]);
		this.allTypes[6].getWeak().add(this.allTypes[8]);

		//Ghost : Strong against ghost and psychic, useless against normal
		this.allTypes[7].getStrength().add(this.allTypes[7]);
		this.allTypes[7].getUseless().add(this.allTypes[0]);
		
		//Fire : Strong against bug, grass and ice, weak against rock, fire, water and dragon
		this.allTypes[8].getStrength().add(this.allTypes[6]);
		this.allTypes[8].getStrength().add(this.allTypes[10]);
		this.allTypes[8].getStrength().add(this.allTypes[13]);
		this.allTypes[8].getWeak().add(this.allTypes[5]);
		this.allTypes[8].getWeak().add(this.allTypes[8]);
		this.allTypes[8].getWeak().add(this.allTypes[9]);
		this.allTypes[8].getWeak().add(this.allTypes[14]);

		//Water : Strong against ground, rock and fire, weak against water, grass and dragon
		this.allTypes[9].getStrength().add(this.allTypes[4]);
		this.allTypes[9].getStrength().add(this.allTypes[5]);
		this.allTypes[9].getStrength().add(this.allTypes[8]);
		this.allTypes[9].getWeak().add(this.allTypes[9]);
		this.allTypes[9].getWeak().add(this.allTypes[10]);
		this.allTypes[9].getWeak().add(this.allTypes[14]);

		//Grass : Strong against ground, rock and water, weak against flying, poison, bug, fire, grass and dragon
		this.allTypes[10].getStrength().add(this.allTypes[4]);
		this.allTypes[10].getStrength().add(this.allTypes[5]);
		this.allTypes[10].getStrength().add(this.allTypes[9]);
		this.allTypes[10].getWeak().add(this.allTypes[2]);
		this.allTypes[10].getWeak().add(this.allTypes[3]);
		this.allTypes[10].getWeak().add(this.allTypes[6]);
		this.allTypes[10].getWeak().add(this.allTypes[8]);
		this.allTypes[10].getWeak().add(this.allTypes[10]);
		this.allTypes[10].getWeak().add(this.allTypes[14]);
		
		//Eletric : Strong against flying and water, weak against grass, electric and dragon, useless against ground
		this.allTypes[11].getStrength().add(this.allTypes[2]);
		this.allTypes[11].getStrength().add(this.allTypes[9]);
		this.allTypes[11].getWeak().add(this.allTypes[14]);
		this.allTypes[11].getWeak().add(this.allTypes[14]);
		this.allTypes[11].getWeak().add(this.allTypes[14]);
		this.allTypes[11].getUseless().add(this.allTypes[4]);
		
		//Psychic : Strong against fight and poison, weak against psychic
		this.allTypes[12].getStrength().add(this.allTypes[1]);
		this.allTypes[12].getStrength().add(this.allTypes[3]);
		this.allTypes[12].getWeak().add(this.allTypes[12]);
		
		//Ice : Strong against flying, ground, grass and dragon, weak against water and ice
		this.allTypes[13].getStrength().add(this.allTypes[2]);
		this.allTypes[13].getStrength().add(this.allTypes[4]);
		this.allTypes[13].getStrength().add(this.allTypes[10]);
		this.allTypes[13].getStrength().add(this.allTypes[14]);
		this.allTypes[13].getWeak().add(this.allTypes[9]);
		this.allTypes[13].getWeak().add(this.allTypes[13]);

		//Dragon : Strong against dragon
		this.allTypes[14].getStrength().add(this.allTypes[14]);
		
		//Tracing that loading is OK
		win.logTrace("Types loaded.");
		Thread.sleep(500);
	}
	
	//Creating all 164 attacks in Pokémon gen 1. Yay. Bind is excluded because it sucks, one-hit K-O attacks are excluded.
	private void createAttacks(Window win) throws InterruptedException
	{
		this.allAtks = new Attack[158];
		int i = 0;
		//Name - Description - Id - Power - PP - Physical/Special - Type - ID - Status (refer to attack class) - accuracy for status change, accuracy
		this.allAtks[i] = new Attack("Absorb", "User recovers half the HP inflicted on opponent.", 0, 20, 25, false, this.allTypes[10],44,100,100); i++;
		this.allAtks[i] = new Attack("Acid", "May lower opponent's Special.", 1, 40, 30, false, this.allTypes[3],32,10,100); i++;
		this.allAtks[i] = new Attack("Acid Armor", "Sharply raises user's Defense.", 2, 0, 20, false, this.allTypes[3],21,100,100); i++;
		this.allAtks[i] = new Attack("Agility", "Sharply raises user's Speed.", 3, 0, 30, false, this.allTypes[12],29,100,100); i++;
		this.allAtks[i] = new Attack("Amnesia", "Sharply raises user's Special.", 4, 0, 20, false, this.allTypes[12],21,100,100); i++;
		this.allAtks[i] = new Attack("Aurora Beam", "May freeze the opponent", 5, 65, 20, false, this.allTypes[13],5,10,100); i++;
		this.allAtks[i] = new Attack("Barrage", "Hits 2-5 times in one turn.", 6, 15, 20, true, this.allTypes[0],46,100,85); i++;
		this.allAtks[i] = new Attack("Barrier", "Sharply raises user's Defense.", 7, 0, 20, false, this.allTypes[12],21,100,100); i++;
		this.allAtks[i] = new Attack("Bide", "User takes damage for two turns then strikes back double.", 8, 0, 10, true, this.allTypes[10],54,100,100); i++;
		this.allAtks[i] = new Attack("Bite", "May cause flinching", 9, 60, 25, true, this.allTypes[0],43,30,100); i++;
		this.allAtks[i] = new Attack("Blizzard", "May freeze opponent.", 10, 110, 5, false, this.allTypes[13],5,10,70); i++;
		this.allAtks[i] = new Attack("Body Slam", "May paralyze opponent.", 11, 85, 15, true, this.allTypes[0],1 ,30,100); i++;
		this.allAtks[i] = new Attack("Bone Club", "May cause flinching.", 12, 65, 20, true, this.allTypes[4],43,10,85); i++;
		this.allAtks[i] = new Attack("Bonemerang", "Hits twice in one turn.", 13, 50, 10, true, this.allTypes[4],0,100,90); i++;
		this.allAtks[i] = new Attack("Bubble", "May lower opponent's Speed.", 14, 40, 30, false, this.allTypes[9],24,10,100); i++;
		this.allAtks[i] = new Attack ("Bubble Beam","May lower opponent's Speed.",15,65,20,false,this.allTypes[9],24,10,100); i++;
		this.allAtks[i] = new Attack ("Clamp","Traps opponent, damaging them for 4-5 turns.",16,35,10,true,this.allTypes[9],55,0,85); i++;
		this.allAtks[i] = new Attack ("Comet Punch","Hits 2-5 times in one turn.",17,18,15,true,this.allTypes[0],46,0,85); i++;
		this.allAtks[i] = new Attack ("Confuse Ray","Confuses opponent.",18,0,10,false,this.allTypes[7],6,100,100); i++;
		this.allAtks[i] = new Attack ("Confusion","May confuse opponent.",19,50,25,false,this.allTypes[12],6,10,100); i++;
		this.allAtks[i] = new Attack ("Constrict","May lower opponent's Speed by one stage.",20,10,35,true,this.allTypes[0],24,10,100); i++;
		this.allAtks[i] = new Attack ("Conversion","Changes user's type to that of its first move.",21,0,30,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Counter","When hit by a Physical Attack, user strikes back with 2x power.",22,0,20,true,this.allTypes[1],54,0,100); i++;
		this.allAtks[i] = new Attack ("Crabhammer","High critical hit ratio.",23,100,10,true,this.allTypes[9],47,1000,90); i++;
		this.allAtks[i] = new Attack ("Cut","Standard attack",24,50,30,true,this.allTypes[0],0,0,95); i++;
		this.allAtks[i] = new Attack ("Defense Curl","Raises user's Defense.",25,0,40,false,this.allTypes[0],17,1000,100); i++;
		this.allAtks[i] = new Attack ("Dig","Digs underground on first turn, attacks on second. Can also escape from caves.",26,80,10,true,this.allTypes[4],48,100,100); i++;
		this.allAtks[i] = new Attack ("Disable","Opponent can't use its last attack for a few turns.",27,0,20,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Dizzy Punch","May confuse opponent.",28,70,10,true,this.allTypes[0],6,20,100); i++;
		this.allAtks[i] = new Attack ("Double Kick","Hits twice in one turn.",29,30,30,true,this.allTypes[1],46,0,100); i++;
		this.allAtks[i] = new Attack ("Double Slap","Hits 2-5 times in one turn.",30,15,10,true,this.allTypes[0],46,0,85); i++;
		this.allAtks[i] = new Attack ("Double Team","Raises user's Evasiveness.",31,0,15,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Double-Edge","User receives recoil damage.",32,100,15,true,this.allTypes[0],49,0,100); i++;
		this.allAtks[i] = new Attack ("Dragon Rage","Always inflicts 40 HP.",33,0,10,false,this.allTypes[14],53,0,100); i++;
		this.allAtks[i] = new Attack ("Dream Eater","User recovers half the HP inflicted on a sleeping opponent.",34,100,15,false,this.allTypes[12],54,0,100); i++;
		this.allAtks[i] = new Attack ("Drill Peck","Standard attack",35,80,20,true,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Earthquake","Power is doubled if opponent is underground from using Dig.",36,100,10,true,this.allTypes[4],54,0,100); i++;
		this.allAtks[i] = new Attack ("Egg Bomb","Standard attack",37,100,10,true,this.allTypes[0],0,0,75); i++;
		this.allAtks[i] = new Attack ("Ember","May burn opponent.",38,40,25,false,this.allTypes[8],4,10,100); i++;
		this.allAtks[i] = new Attack ("Explosion","User faints.",39,250,5,true,this.allTypes[0],50,0,100); i++;
		this.allAtks[i] = new Attack ("Fire Blast","May burn opponent.",40,110,5,false,this.allTypes[8],4,10,85); i++;
		this.allAtks[i] = new Attack ("Fire Punch","May burn opponent.",41,75,15,true,this.allTypes[8],4,10,100); i++;
		this.allAtks[i] = new Attack ("Fire Spin","Traps opponent, damaging them for 4-5 turns.",42,35,15,false,this.allTypes[8],55,0,85); i++;
		this.allAtks[i] = new Attack ("Flamethrower","May burn opponent.",43,90,15,false,this.allTypes[8],4,10,100); i++;
		this.allAtks[i] = new Attack ("Flash","Lowers opponent's Accuracy.",44,0,20,false,this.allTypes[0],40,100,100); i++;
		this.allAtks[i] = new Attack ("Fly","Flies up on first turn, attacks on second turn.",45,90,15,true,this.allTypes[2],48,0,95); i++;
		this.allAtks[i] = new Attack ("Fury Attack","Hits 2-5 times in one turn.",46,15,20,true,this.allTypes[0],46,0,85); i++;
		this.allAtks[i] = new Attack ("Fury Swipes","Hits 2-5 times in one turn.",47,18,15,true,this.allTypes[0],46,0,80); i++;
		this.allAtks[i] = new Attack ("Glare","Paralyzes opponent.",48,0,30,false,this.allTypes[0],1,100,100); i++;
		this.allAtks[i] = new Attack ("Growl","Lowers opponent's Attack.",49,0,40,false,this.allTypes[0],8,100,100); i++;
		this.allAtks[i] = new Attack ("Growth","Raises user's Attack and SpecialAttack.",50,0,40,false,this.allTypes[0],9,100,100); i++;
		this.allAtks[i] = new Attack ("Gust","Hits Pokémon using Fly/Bounce with double power.",51,40,35,false,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Harden","Raises user's Defense.",52,0,30,false,this.allTypes[0],17,100,100); i++;
		this.allAtks[i] = new Attack ("Haze","Resets all stat changes.",53,0,30,false,this.allTypes[13],45,100,100); i++;
		this.allAtks[i] = new Attack ("Headbutt","May cause flinching.",54,70,15,true,this.allTypes[0],43,30,100); i++;
		this.allAtks[i] = new Attack ("High Jump Kick","If it misses, the user loses half their HP.",55,130,10,true,this.allTypes[1],0,0,90); i++;
		this.allAtks[i] = new Attack ("Horn Attack","Standard attack",56,65,25,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Hydro Pump","Standard attack",57,110,5,false,this.allTypes[9],0,0,80); i++;
		this.allAtks[i] = new Attack ("Hyper Beam","User must recharge next turn.",58,150,5,false,this.allTypes[0],54,0,90); i++;
		this.allAtks[i] = new Attack ("Hyper Fang","May cause flinching.",59,80,15,true,this.allTypes[0],43,10,90); i++;
		this.allAtks[i] = new Attack ("Hypnosis","Puts opponent to sleep.",60,0,20,false,this.allTypes[12],2,100,60); i++;
		this.allAtks[i] = new Attack ("Ice Beam","May freeze opponent.",61,90,10,false,this.allTypes[13],5,10,100); i++;
		this.allAtks[i] = new Attack ("Ice Punch","May freeze opponent.",62,75,15,true,this.allTypes[13],5,10,100); i++;
		this.allAtks[i] = new Attack ("Jump Kick","If it misses, the user loses half their HP.",63,100,10,true,this.allTypes[1],0,0,95); i++;
		this.allAtks[i] = new Attack ("Karate Chop","High critical hit ratio.",64,50,25,true,this.allTypes[1],47,100,100); i++;
		this.allAtks[i] = new Attack ("Kinesis","Lowers opponent's Accuracy.",65,0,15,false,this.allTypes[12],40,100,80); i++;
		this.allAtks[i] = new Attack ("Leech Life","User recovers half the HP inflicted on opponent.",66,80,10,true,this.allTypes[6],44,100,100); i++;
		this.allAtks[i] = new Attack ("Leech Seed","Drains HP from opponent each turn.",67,0,10,false,this.allTypes[10],54,100,90); i++;
		this.allAtks[i] = new Attack ("Leer","Lowers opponent's Defense.",68,0,30,false,this.allTypes[0],16,100,100); i++;
		this.allAtks[i] = new Attack ("Lick","May paralyze opponent.",69,30,30,true,this.allTypes[7],1,30,100); i++;
		this.allAtks[i] = new Attack ("Light Screen","Halves damage from Specialattacks for 5 turns.",70,0,30,false,this.allTypes[12],52,0,100); i++;
		this.allAtks[i] = new Attack ("Lovely Kiss","Puts opponent to sleep.",71,0,10,false,this.allTypes[0],2,100,75); i++;
		this.allAtks[i] = new Attack ("Low Kick","Standard attack",72,60,20,true,this.allTypes[1],0,0,100); i++;
		this.allAtks[i] = new Attack ("Meditate","Raises user's Attack.",73,0,40,false,this.allTypes[12],9,100,100); i++;
		this.allAtks[i] = new Attack ("Mega Drain","User recovers half the HP inflicted on opponent.",74,40,15,false,this.allTypes[10],44,100,100); i++;
		this.allAtks[i] = new Attack ("Mega Kick","Standard attack",75,120,5,true,this.allTypes[0],0,0,75); i++;
		this.allAtks[i] = new Attack ("Mega Punch","Standard attack",76,80,20,true,this.allTypes[0],0,0,85); i++;
		this.allAtks[i] = new Attack ("Metronome","User performs almost any move in the game at random.",77,0,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Mimic","Copies the opponent's last move.",78,0,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Minimize","Sharply raises user's Evasiveness.",79,0,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Mirror Move","User performs the opponent's last move.",80,0,20,false,this.allTypes[2],54,0,100); i++;
		this.allAtks[i] = new Attack ("Night Shade","Inflicts damage equal to user's level.",81,0,15,false,this.allTypes[7],53,0,100); i++;
		this.allAtks[i] = new Attack ("Pay Day","Standard attack",82,40,20,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Peck","Standard attack",83,35,35,true,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Petal Dance","User attacks for 2-3 turns but then becomes confused.",84,120,10,false,this.allTypes[10],54,0,100); i++;
		this.allAtks[i] = new Attack ("Pin Missile","Hits 2-5 times in one turn.",85,25,20,true,this.allTypes[6],46,0,95); i++;
		this.allAtks[i] = new Attack ("Poison Gas","Poisons opponent.",86,0,40,false,this.allTypes[3],3,100,90); i++;
		this.allAtks[i] = new Attack ("Poison Powder","Poisons opponent.",87,0,35,false,this.allTypes[3],3,100,75); i++;
		this.allAtks[i] = new Attack ("Poison Sting","May poison the opponent.",88,15,35,true,this.allTypes[3],3,30,100); i++;
		this.allAtks[i] = new Attack ("Pound","Standard attack",89,40,35,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Psybeam","May confuse opponent.",90,65,20,false,this.allTypes[12],6,10,100); i++;
		this.allAtks[i] = new Attack ("Psychic","May lower opponent's SpecialDefense.",91,90,10,false,this.allTypes[12],32,10,100); i++;
		this.allAtks[i] = new Attack ("Psywave","Inflicts damage 50-150% of user's level.",92,0,15,false,this.allTypes[12],53,0,80); i++;
		this.allAtks[i] = new Attack ("Quick Attack","User attacks first.",93,40,30,true,this.allTypes[0],51,0,100); i++;
		this.allAtks[i] = new Attack ("Rage","Raises user's Attack when hit.",94,20,20,true,this.allTypes[0],9,100,100); i++;
		this.allAtks[i] = new Attack ("Razor Leaf","High critical hit ratio.",95,55,25,true,this.allTypes[10],47,100,95); i++;
		this.allAtks[i] = new Attack ("Razor Wind","Charges on first turn, attacks on second. High critical hit ratio.",96,80,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Recover","User recovers half its max HP.",97,0,10,false,this.allTypes[0],45,100,100); i++;
		this.allAtks[i] = new Attack ("Reflect","Halves damage from Physical attacks for 5 turns.",98,0,20,false,this.allTypes[12],52,0,100); i++;
		this.allAtks[i] = new Attack ("Rest","User sleeps for 2 turns, but user is fully healed.",99,0,10,false,this.allTypes[12],54,0,100); i++;
		this.allAtks[i] = new Attack ("Roar","In battles, the opponent switches. In the wild, the Pokémon runs.",100,0,20,false,this.allTypes[0],54,100,100); i++;
		this.allAtks[i] = new Attack ("Rock Slide","May cause flinching.",101,75,10,true,this.allTypes[5],43,30,90); i++;
		this.allAtks[i] = new Attack ("Rock Throw","Standard attack",102,50,15,true,this.allTypes[5],0,0,90); i++;
		this.allAtks[i] = new Attack ("Rolling Kick","May cause flinching.",103,60,15,true,this.allTypes[1],43,30,85); i++;
		this.allAtks[i] = new Attack ("Sand Attack","Lowers opponent's Accuracy.",104,0,15,false,this.allTypes[4],40,0,100); i++;
		this.allAtks[i] = new Attack ("Scratch","Standard attack",105,40,35,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Screech","Sharply lowers opponent's Defense.",106,0,40,false,this.allTypes[0],19,100,85); i++;
		this.allAtks[i] = new Attack ("Seismic Toss","Inflicts damage equal to user's level.",107,0,20,true,this.allTypes[1],53,0,100); i++;
		this.allAtks[i] = new Attack ("Self-Destruct","User faints.",108,200,5,true,this.allTypes[0],50,0,100); i++;
		this.allAtks[i] = new Attack ("Sharpen","Raises user's Attack.",109,0,30,false,this.allTypes[0],9,0,100); i++;
		this.allAtks[i] = new Attack ("Sing","Puts opponent to sleep.",110,0,15,false,this.allTypes[0],2,100,55); i++;
		this.allAtks[i] = new Attack ("Skull Bash","Raises Defense on first turn, attacks on second.",111,130,10,true,this.allTypes[0],48,0,100); i++;
		this.allAtks[i] = new Attack ("Sky Attack","Charges on first turn, attacks on second. May cause flinching.",112,140,5,true,this.allTypes[2],54,30,90); i++;
		this.allAtks[i] = new Attack ("Slam","Standard attack",113,80,20,true,this.allTypes[0],0,0,75); i++;
		this.allAtks[i] = new Attack ("Slash","High critical hit ratio.",114,70,20,true,this.allTypes[0],47,0,100); i++;
		this.allAtks[i] = new Attack ("Sleep Powder","Puts opponent to sleep.",115,0,15,false,this.allTypes[10],2,100,75); i++;
		this.allAtks[i] = new Attack ("Sludge","May poison opponent.",116,65,20,false,this.allTypes[3],3,30,100); i++;
		this.allAtks[i] = new Attack ("Smog","May poison opponent.",117,30,20,false,this.allTypes[3],3,40,70); i++;
		this.allAtks[i] = new Attack ("Smokescreen","Lowers opponent's Accuracy.",118,0,20,false,this.allTypes[0],40,0,100); i++;
		this.allAtks[i] = new Attack ("Soft-Boiled","User recovers half its max HP.",119,0,10,false,this.allTypes[0],45,0,100); i++;
		this.allAtks[i] = new Attack ("Solar Beam","Charges on first turn, attacks on second.",120,120,10,false,this.allTypes[10],48,0,100); i++;
		this.allAtks[i] = new Attack ("Sonic Boom","Always inflicts 20 HP.",121,0,20,false,this.allTypes[0],53,0,90); i++;
		this.allAtks[i] = new Attack ("Spike Cannon","Hits 2-5 times in one turn.",122,20,15,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Splash","Doesn't do ANYTHING.",123,0,40,false,this.allTypes[0],0,100,100); i++;
		this.allAtks[i] = new Attack ("Spore","Puts opponent to sleep.",124,0,15,false,this.allTypes[10],2,100,100); i++;
		this.allAtks[i] = new Attack ("Stomp","May cause flinching.",125,65,20,true,this.allTypes[0],43,30,100); i++;
		this.allAtks[i] = new Attack ("Strength","Standard attack",126,80,15,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("String Shot","Sharply lowers opponent's Speed.",127,0,40,false,this.allTypes[6],28,100,95); i++;
		this.allAtks[i] = new Attack ("Struggle","Only usable when all PP are gone. Hurts the user.",128,50,1000,true,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Stun Spore","Paralyzes opponent.",129,0,30,false,this.allTypes[10],1,100,75); i++;
		this.allAtks[i] = new Attack ("Submission","User receives recoil damage.",130,80,20,true,this.allTypes[1],49,0,80); i++;
		this.allAtks[i] = new Attack ("Substitute","Uses HP to create a decoy that takes hits.",131,0,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Super Fang","Always takes off half of the opponent's HP.",132,0,10,true,this.allTypes[0],53,0,90); i++;
		this.allAtks[i] = new Attack ("Supersonic","Confuses opponent.",133,0,20,false,this.allTypes[0],6,100,55); i++;
		this.allAtks[i] = new Attack ("Surf","Standard attack.",134,90,15,false,this.allTypes[9],0,0,100); i++;
		this.allAtks[i] = new Attack ("Swift","Ignores Accuracy and Evasiveness.",135,60,20,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Swords Dance","Sharply raises user's Attack.",136,0,20,false,this.allTypes[0],13,100,100); i++;
		this.allAtks[i] = new Attack ("Tackle","Standard attack",137,40,35,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Tail Whip","Lowers opponent's Defense.",138,0,30,false,this.allTypes[0],16,100,100); i++;
		this.allAtks[i] = new Attack ("Take Down","User receives recoil damage.",139,90,20,true,this.allTypes[0],49,0,85); i++;
		this.allAtks[i] = new Attack ("Thrash","User attacks for 2-3 turns but then becomes confused.",140,120,10,true,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Thunder","May paralyze opponent.",141,110,10,false,this.allTypes[11],1,30,70); i++;
		this.allAtks[i] = new Attack ("Thunder Punch","May paralyze opponent.",142,75,15,true,this.allTypes[11],1,10,100); i++;
		this.allAtks[i] = new Attack ("Thunder Shock","May paralyze opponent.",143,40,30,false,this.allTypes[11],1,10,100); i++;
		this.allAtks[i] = new Attack ("Thunder Wave","Paralyzes opponent.",144,0,20,false,this.allTypes[11],1,100,90); i++;
		this.allAtks[i] = new Attack ("Thunderbolt","May paralyze opponent.",145,90,15,false,this.allTypes[11],1,10,100); i++;
		this.allAtks[i] = new Attack ("Toxic","Badly poisons opponent.",146,0,10,false,this.allTypes[3],3,100,90); i++;
		this.allAtks[i] = new Attack ("Transform","User takes on the form and attacks of the opponent.",147,0,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Tri Attack","May paralyze, burn or freeze opponent.",148,80,10,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Twineedle","Hits twice in one turn. May poison opponent.",149,25,20,true,this.allTypes[6],46,20,100); i++;
		this.allAtks[i] = new Attack ("Vice Grip","Standard attack",150,55,30,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Vine Whip","Standard attack",151,45,25,true,this.allTypes[10],0,0,100); i++;
		this.allAtks[i] = new Attack ("Water Gun","Standard attack",152,40,25,false,this.allTypes[9],0,0,100); i++;
		this.allAtks[i] = new Attack ("Waterfall","May cause flinching.",153,80,15,true,this.allTypes[9],43,20,100); i++;
		this.allAtks[i] = new Attack ("Whirlwind","In battles, the opponent switches. In the wild, the Pokémon runs.",154,0,20,false,this.allTypes[0],54,0,100); i++;
		this.allAtks[i] = new Attack ("Wing Attack","Standard attack",155,60,35,true,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Withdraw","Raises user's Defense.",156,0,40,false,this.allTypes[9],17,100,100); i++;
		this.allAtks[i] = new Attack ("Wrap","Traps opponent, damaging them for 4-5 turns.",157,15,20,true,this.allTypes[0],55,0,90); i++;
		
		//Tracing that loading is OK
		win.logTrace("Attacks loaded.");
		Thread.sleep(500);
	}
	
	private void createPkmn(Window win) throws InterruptedException
	{
		this.allPkmn = new Pokemon[152]; //Pokémon at index 0 will be null to simplify further researches in the array (index will be exactly the pokédex's number)
		int i = 0;
		this.allPkmn[i] = new Pokemon(); i++;
		// Name - id - Type1 - Type2 (may be null) - HP - Attacks - Defense - Speed - Special
		//First we create all the pokémons, then we will assign them their attacks.
		this.allPkmn[i] = new Pokemon("Bulbasaur",i,this.allTypes[10],this.allTypes[3],45,49,49,45,65); i++;
		this.allPkmn[i] = new Pokemon("Ivysaur",i,this.allTypes[10],this.allTypes[3],60,62,63,60,80);i++;
		this.allPkmn[i] = new Pokemon("Venusaur",i,this.allTypes[10],this.allTypes[3],80,82,83,80,100);i++;
		this.allPkmn[i] = new Pokemon("Charmander",i,this.allTypes[8],null,39,52,43,65,50);i++;
		this.allPkmn[i] = new Pokemon("Charmeleon",i,this.allTypes[8],null,58,64,58,80,65);i++;
		this.allPkmn[i] = new Pokemon("Charizard",i,this.allTypes[8],this.allTypes[2],78,84,78,100,85);i++;
		this.allPkmn[i] = new Pokemon("Squirtle",i,this.allTypes[9],null,44,48,65,43,50);i++;
		this.allPkmn[i] = new Pokemon("Wartortle",i,this.allTypes[9],null,59,63,80,58,65);i++;
		this.allPkmn[i] = new Pokemon("Blastoise",i,this.allTypes[9],null,79,83,100,78,85);i++;
		this.allPkmn[i] = new Pokemon("Caterpie",i,this.allTypes[6],null,45,30,35,45,20);i++;
		this.allPkmn[i] = new Pokemon("Metapod",i,this.allTypes[6],null,50,20,55,30,25);i++;
		this.allPkmn[i] = new Pokemon("Butterfree",i,this.allTypes[6],this.allTypes[2],60,45,50,70,80);i++;
		this.allPkmn[i] = new Pokemon("Weedle",i,this.allTypes[6],this.allTypes[3],40,35,30,50,20);i++;
		this.allPkmn[i] = new Pokemon("Kakuna",i,this.allTypes[6],this.allTypes[3],45,25,50,35,25);i++;
		this.allPkmn[i] = new Pokemon("Beedrill",i,this.allTypes[6],this.allTypes[3],65,80,40,75,45);i++;
		this.allPkmn[i] = new Pokemon("Pidgey",i,this.allTypes[0],this.allTypes[2],40,45,40,56,35);i++;
		this.allPkmn[i] = new Pokemon("Pidgeotto",i,this.allTypes[0],this.allTypes[2],63,60,55,71,50);i++;
		this.allPkmn[i] = new Pokemon("Pidgeot",i,this.allTypes[0],this.allTypes[2],83,80,75,91,70);i++;
		this.allPkmn[i] = new Pokemon("Rattata",i,this.allTypes[0],null,30,56,35,72,25);i++;
		this.allPkmn[i] = new Pokemon("Raticate",i,this.allTypes[0],null,55,81,60,97,50);i++;
		this.allPkmn[i] = new Pokemon("Spearow",i,this.allTypes[0],this.allTypes[2],40,60,30,70,31);i++;
		this.allPkmn[i] = new Pokemon("Fearow",i,this.allTypes[0],this.allTypes[2],65,90,65,100,61);i++;
		this.allPkmn[i] = new Pokemon("Ekans",i,this.allTypes[3],null,35,60,44,55,40);i++;
		this.allPkmn[i] = new Pokemon("Arbok",i,this.allTypes[3],null,60,85,69,80,65);i++;
		this.allPkmn[i] = new Pokemon("Pikachu",i,this.allTypes[11],null,35,55,30,90,50);i++;
		this.allPkmn[i] = new Pokemon("Raichu",i,this.allTypes[11],null,60,90,55,100,90);i++;
		this.allPkmn[i] = new Pokemon("Sandshrew",i,this.allTypes[4],null,50,75,85,40,30);i++;
		this.allPkmn[i] = new Pokemon("Sandslash",i,this.allTypes[4],null,75,100,110,65,55);i++;
		this.allPkmn[i] = new Pokemon("Nidoran♀",i,this.allTypes[3],null,55,47,52,41,40);i++;
		this.allPkmn[i] = new Pokemon("Nidorina",i,this.allTypes[3],null,70,62,67,56,55);i++;
		this.allPkmn[i] = new Pokemon("Nidoqueen",i,this.allTypes[3],this.allTypes[4],90,82,87,76,75);i++;
		this.allPkmn[i] = new Pokemon("Nidoran♂",i,this.allTypes[3],null,46,57,40,50,40);i++;
		this.allPkmn[i] = new Pokemon("Nidorino",i,this.allTypes[3],null,61,72,57,65,55);i++;
		this.allPkmn[i] = new Pokemon("Nidoking",i,this.allTypes[3],this.allTypes[4],81,92,77,85,75);i++;
		this.allPkmn[i] = new Pokemon("Clefairy",i,this.allTypes[0],null,70,45,48,35,60);i++;
		this.allPkmn[i] = new Pokemon("Clefable",i,this.allTypes[0],null,95,70,73,60,85);i++;
		this.allPkmn[i] = new Pokemon("Vulpix",i,this.allTypes[8],null,38,41,40,65,65);i++;
		this.allPkmn[i] = new Pokemon("Ninetales",i,this.allTypes[8],null,73,76,75,100,100);i++;
		this.allPkmn[i] = new Pokemon("Jigglypuff",i,this.allTypes[0],null,115,45,20,20,25);i++;
		this.allPkmn[i] = new Pokemon("Wigglytuff",i,this.allTypes[0],null,140,70,45,45,50);i++;
		this.allPkmn[i] = new Pokemon("Zubat",i,this.allTypes[3],this.allTypes[2],40,45,35,55,40);i++;
		this.allPkmn[i] = new Pokemon("Golbat",i,this.allTypes[3],this.allTypes[2],75,80,70,90,75);i++;
		this.allPkmn[i] = new Pokemon("Oddish",i,this.allTypes[10],this.allTypes[3],45,50,55,30,75);i++;
		this.allPkmn[i] = new Pokemon("Gloom",i,this.allTypes[10],this.allTypes[3],60,65,70,40,85);i++;
		this.allPkmn[i] = new Pokemon("Vileplume",i,this.allTypes[10],this.allTypes[3],75,80,85,50,100);i++;
		this.allPkmn[i] = new Pokemon("Paras",i,this.allTypes[6],this.allTypes[10],35,70,55,25,55);i++;
		this.allPkmn[i] = new Pokemon("Parasect",i,this.allTypes[6],this.allTypes[10],60,95,80,30,80);i++;
		this.allPkmn[i] = new Pokemon("Venonat",i,this.allTypes[6],this.allTypes[3],60,55,50,45,40);i++;
		this.allPkmn[i] = new Pokemon("Venomoth",i,this.allTypes[6],this.allTypes[3],70,65,60,90,90);i++;
		this.allPkmn[i] = new Pokemon("Diglett",i,this.allTypes[4],null,10,55,25,95,45);i++;
		this.allPkmn[i] = new Pokemon("Dugtrio",i,this.allTypes[4],null,35,80,50,120,70);i++;
		this.allPkmn[i] = new Pokemon("Meowth",i,this.allTypes[0],null,40,45,35,90,40);i++;
		this.allPkmn[i] = new Pokemon("Persian",i,this.allTypes[0],null,65,70,60,115,65);i++;
		this.allPkmn[i] = new Pokemon("Psyduck",i,this.allTypes[9],null,50,52,48,55,50);i++;
		this.allPkmn[i] = new Pokemon("Golduck",i,this.allTypes[9],null,80,82,78,85,80);i++;
		this.allPkmn[i] = new Pokemon("Mankey",i,this.allTypes[1],null,40,80,35,70,35);i++;
		this.allPkmn[i] = new Pokemon("Primeape",i,this.allTypes[1],null,65,105,60,95,60);i++;
		this.allPkmn[i] = new Pokemon("Growlithe",i,this.allTypes[8],null,55,70,45,60,50);i++;
		this.allPkmn[i] = new Pokemon("Arcanine",i,this.allTypes[8],null,90,110,80,95,80);i++;
		this.allPkmn[i] = new Pokemon("Poliwag",i,this.allTypes[9],null,40,50,40,90,40);i++;
		this.allPkmn[i] = new Pokemon("Poliwhirl",i,this.allTypes[9],null,65,65,65,90,50);i++;
		this.allPkmn[i] = new Pokemon("Poliwrath",i,this.allTypes[9],this.allTypes[1],90,85,95,70,70);i++;
		this.allPkmn[i] = new Pokemon("Abra",i,this.allTypes[12],null,25,20,15,90,105);i++;
		this.allPkmn[i] = new Pokemon("Kadabra",i,this.allTypes[12],null,40,35,30,105,120);i++;
		this.allPkmn[i] = new Pokemon("Alakazam",i,this.allTypes[12],null,55,50,45,120,135);i++;
		this.allPkmn[i] = new Pokemon("Machop",i,this.allTypes[1],null,70,80,50,35,35);i++;
		this.allPkmn[i] = new Pokemon("Machoke",i,this.allTypes[1],null,80,100,70,45,50);i++;
		this.allPkmn[i] = new Pokemon("Machamp",i,this.allTypes[1],null,90,130,80,55,65);i++;
		this.allPkmn[i] = new Pokemon("Bellsprout",i,this.allTypes[10],this.allTypes[3],50,75,35,40,70);i++;
		this.allPkmn[i] = new Pokemon("Weepinbell",i,this.allTypes[10],this.allTypes[3],65,90,50,55,85);i++;
		this.allPkmn[i] = new Pokemon("Victreebel",i,this.allTypes[10],this.allTypes[3],80,105,65,70,100);i++;
		this.allPkmn[i] = new Pokemon("Tentacool",i,this.allTypes[9],this.allTypes[3],40,40,35,70,100);i++;
		this.allPkmn[i] = new Pokemon("Tentacruel",i,this.allTypes[9],this.allTypes[3],80,70,65,100,120);i++;
		this.allPkmn[i] = new Pokemon("Geodude",i,this.allTypes[5],this.allTypes[4],40,80,100,20,30);i++;
		this.allPkmn[i] = new Pokemon("Graveler",i,this.allTypes[5],this.allTypes[4],55,95,115,35,45);i++;
		this.allPkmn[i] = new Pokemon("Golem",i,this.allTypes[5],this.allTypes[4],80,110,130,45,55);i++;
		this.allPkmn[i] = new Pokemon("Ponyta",i,this.allTypes[8],null,50,85,55,90,65);i++;
		this.allPkmn[i] = new Pokemon("Rapidash",i,this.allTypes[8],null,65,100,70,105,80);i++;
		this.allPkmn[i] = new Pokemon("Slowpoke",i,this.allTypes[9],this.allTypes[12],90,65,65,15,40);i++;
		this.allPkmn[i] = new Pokemon("Slowbro",i,this.allTypes[9],this.allTypes[12],95,75,110,30,80);i++;
		this.allPkmn[i] = new Pokemon("Magnemite",i,this.allTypes[11],null,25,35,70,45,95);i++;
		this.allPkmn[i] = new Pokemon("Magneton",i,this.allTypes[11],null,50,60,95,70,120);i++;
		this.allPkmn[i] = new Pokemon("Farfetch'd",i,this.allTypes[0],this.allTypes[2],52,65,55,60,58);i++;
		this.allPkmn[i] = new Pokemon("Doduo",i,this.allTypes[0],this.allTypes[2],35,85,45,75,35);i++;
		this.allPkmn[i] = new Pokemon("Dodrio",i,this.allTypes[0],this.allTypes[2],60,110,70,100,60);i++;
		this.allPkmn[i] = new Pokemon("Seel",i,this.allTypes[9],null,65,45,55,45,70);i++;
		this.allPkmn[i] = new Pokemon("Dewgong",i,this.allTypes[13],null,90,70,80,70,95);i++;
		this.allPkmn[i] = new Pokemon("Grimer",i,this.allTypes[3],null,80,80,50,25,40);i++;
		this.allPkmn[i] = new Pokemon("Muk",i,this.allTypes[3],null,105,105,75,50,65);i++;
		this.allPkmn[i] = new Pokemon("Shellder",i,this.allTypes[9],null,30,65,100,40,45);i++;
		this.allPkmn[i] = new Pokemon("Cloyster",i,this.allTypes[13],null,50,95,180,70,85);i++;
		this.allPkmn[i] = new Pokemon("Gastly",i,this.allTypes[7],this.allTypes[3],30,35,30,80,100);i++;
		this.allPkmn[i] = new Pokemon("Haunter",i,this.allTypes[7],this.allTypes[3],45,50,45,95,115);i++;
		this.allPkmn[i] = new Pokemon("Gengar",i,this.allTypes[7],this.allTypes[3],60,65,60,110,130);i++;
		this.allPkmn[i] = new Pokemon("Onix",i,this.allTypes[5],this.allTypes[4],35,45,160,70,30);i++;
		this.allPkmn[i] = new Pokemon("Drowzee",i,this.allTypes[12],null,60,48,45,42,90);i++;
		this.allPkmn[i] = new Pokemon("Hypno",i,this.allTypes[12],null,85,73,70,67,115);i++;
		this.allPkmn[i] = new Pokemon("Krabby",i,this.allTypes[9],null,30,105,90,50,25);i++;
		this.allPkmn[i] = new Pokemon("Kingler",i,this.allTypes[9],null,55,130,115,75,50);i++;
		this.allPkmn[i] = new Pokemon("Voltorb",i,this.allTypes[11],null,40,30,50,100,55);i++;
		this.allPkmn[i] = new Pokemon("Electrode",i,this.allTypes[11],null,60,50,70,140,80);i++;
		this.allPkmn[i] = new Pokemon("Exeggcute",i,this.allTypes[10],this.allTypes[12],60,40,80,40,60);i++;
		this.allPkmn[i] = new Pokemon("Exeggutor",i,this.allTypes[10],this.allTypes[12],95,95,85,55,125);i++;
		this.allPkmn[i] = new Pokemon("Cubone",i,this.allTypes[4],null,50,50,95,35,40);i++;
		this.allPkmn[i] = new Pokemon("Marowak",i,this.allTypes[4],null,60,80,110,45,50);i++;
		this.allPkmn[i] = new Pokemon("Hitmonlee",i,this.allTypes[1],null,50,120,53,87,35);i++;
		this.allPkmn[i] = new Pokemon("Hitmonchan",i,this.allTypes[1],null,50,105,79,76,35);i++;
		this.allPkmn[i] = new Pokemon("Lickitung",i,this.allTypes[0],null,90,55,75,30,60);i++;
		this.allPkmn[i] = new Pokemon("Koffing",i,this.allTypes[3],null,40,65,95,35,60);i++;
		this.allPkmn[i] = new Pokemon("Weezing",i,this.allTypes[3],null,65,90,120,60,85);i++;
		this.allPkmn[i] = new Pokemon("Rhyhorn",i,this.allTypes[4],this.allTypes[5],80,85,95,25,30);i++;
		this.allPkmn[i] = new Pokemon("Rhydon",i,this.allTypes[4],this.allTypes[5],105,130,120,40,45);i++;
		this.allPkmn[i] = new Pokemon("Chansey",i,this.allTypes[0],null,250,5,5,50,105);i++;
		this.allPkmn[i] = new Pokemon("Tangela",i,this.allTypes[10],null,65,55,115,60,100);i++;
		this.allPkmn[i] = new Pokemon("Kangaskhan",i,this.allTypes[0],null,105,95,80,90,40);i++;
		this.allPkmn[i] = new Pokemon("Horsea",i,this.allTypes[9],null,30,40,70,60,70);i++;
		this.allPkmn[i] = new Pokemon("Seadra",i,this.allTypes[9],null,55,65,95,85,95);i++;
		this.allPkmn[i] = new Pokemon("Goldeen",i,this.allTypes[9],null,45,67,60,63,50);i++;
		this.allPkmn[i] = new Pokemon("Seaking",i,this.allTypes[9],null,80,92,65,68,80);i++;
		this.allPkmn[i] = new Pokemon("Staryu",i,this.allTypes[9],null,30,45,55,85,70);i++;
		this.allPkmn[i] = new Pokemon("Starmie",i,this.allTypes[9],this.allTypes[12],60,75,85,115,100);i++;
		this.allPkmn[i] = new Pokemon("Mr. Mime",i,this.allTypes[12],null,40,45,65,90,100);i++;
		this.allPkmn[i] = new Pokemon("Scyther",i,this.allTypes[6],this.allTypes[2],70,110,80,105,55);i++;
		this.allPkmn[i] = new Pokemon("Jynx",i,this.allTypes[13],this.allTypes[12],65,50,35,95,95);i++;
		this.allPkmn[i] = new Pokemon("Electabuzz",i,this.allTypes[11],null,65,83,57,105,85);i++;
		this.allPkmn[i] = new Pokemon("Magmar",i,this.allTypes[8],null,65,95,57,93,85);i++;
		this.allPkmn[i] = new Pokemon("Pinsir",i,this.allTypes[6],null,65,125,100,85,55);i++;
		this.allPkmn[i] = new Pokemon("Tauros",i,this.allTypes[0],null,75,100,95,110,70);i++;
		this.allPkmn[i] = new Pokemon("Magikarp",i,this.allTypes[9],null,20,10,55,80,20);i++;
		this.allPkmn[i] = new Pokemon("Gyarados",i,this.allTypes[9],this.allTypes[2],95,125,79,81,100);i++;
		this.allPkmn[i] = new Pokemon("Lapras",i,this.allTypes[9],this.allTypes[13],130,85,80,60,95);i++;
		this.allPkmn[i] = new Pokemon("Ditto",i,this.allTypes[0],null,48,48,48,48,48);i++;
		this.allPkmn[i] = new Pokemon("Eevee",i,this.allTypes[0],null,55,55,50,55,65);i++;
		this.allPkmn[i] = new Pokemon("Vaporeon",i,this.allTypes[9],null,130,65,60,65,110);i++;
		this.allPkmn[i] = new Pokemon("Jolteon",i,this.allTypes[11],null,65,65,60,130,110);i++;
		this.allPkmn[i] = new Pokemon("Flareon",i,this.allTypes[8],null,65,130,60,65,110);i++;
		this.allPkmn[i] = new Pokemon("Porygon",i,this.allTypes[0],null,65,60,70,40,75);i++;
		this.allPkmn[i] = new Pokemon("Omanyte",i,this.allTypes[5],this.allTypes[9],35,40,100,35,90);i++;
		this.allPkmn[i] = new Pokemon("Omastar",i,this.allTypes[5],this.allTypes[9],70,60,125,55,115);i++;
		this.allPkmn[i] = new Pokemon("Kabuto",i,this.allTypes[5],this.allTypes[9],30,80,90,55,45);i++;
		this.allPkmn[i] = new Pokemon("Kabutops",i,this.allTypes[5],this.allTypes[9],60,115,105,80,70);i++;
		this.allPkmn[i] = new Pokemon("Aerodactyl",i,this.allTypes[5],this.allTypes[2],80,105,65,130,60);i++;
		this.allPkmn[i] = new Pokemon("Snorlax",i,this.allTypes[0],null,160,110,65,30,65);i++;
		this.allPkmn[i] = new Pokemon("Articuno",i,this.allTypes[13],this.allTypes[2],90,85,100,85,125);i++;
		this.allPkmn[i] = new Pokemon("Zapdos",i,this.allTypes[11],this.allTypes[2],90,90,85,100,125);i++;
		this.allPkmn[i] = new Pokemon("Moltres",i,this.allTypes[8],this.allTypes[2],90,100,90,90,125);i++;
		this.allPkmn[i] = new Pokemon("Dratini",i,this.allTypes[14],null,41,64,45,50,50);i++;
		this.allPkmn[i] = new Pokemon("Dragonair",i,this.allTypes[14],null,61,84,65,70,70);i++;
		this.allPkmn[i] = new Pokemon("Dragonite",i,this.allTypes[14],this.allTypes[2],91,134,95,80,100);i++;
		this.allPkmn[i] = new Pokemon("Mewtwo",i,this.allTypes[12],null,106,110,90,130,154);i++;
		this.allPkmn[i] = new Pokemon("Mew",i,this.allTypes[12],null,100,100,100,100,100);
		
		//Assigning attacks to Pokémons. Currently assigning fixed attacks for testing purposes.
		for (i=1;i<=151;i++)
		{
			this.allPkmn[i].getAttacks().add(this.allAtks[137]);
			this.allPkmn[i].getAttacks().add(this.allAtks[143]);
		}
		
		//Tracing that loading is OK
		win.logTrace("Pokémons loaded.");
		Thread.sleep(500);
	}
}
