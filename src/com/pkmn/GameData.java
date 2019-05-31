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
	
	public GameData()
	{
		
	}
	
	public GameData(Window win)
	{
		this.createTypes();
		this.createAttacks();
		this.createPkmn(win);
	}
	
	private void createTypes()
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
		
		//Eletric : Strong against flying and water, weak against grass, rock, electric and dragon, useless against ground
		this.allTypes[11].getStrength().add(this.allTypes[2]);
		this.allTypes[11].getStrength().add(this.allTypes[9]);
		this.allTypes[11].getWeak().add(this.allTypes[10]);
		this.allTypes[11].getWeak().add(this.allTypes[11]);
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
	}
	
	//Creating all 164 attacks in Pokémon gen 1. Yay. One-hit K-O attacks are excluded.
	private void createAttacks()
	{
		this.allAtks = new Attack[158];
		int i = 0;
		//Name - Description - Id - Power - PP - Physical/Special - Type - ID - Status (refer to attack class) - accuracy for status change, accuracy
		this.allAtks[i] = new Attack("Absorb", "User recovers half the HP inflicted on opponent.", 0, 20, 25, false, this.allTypes[10],44,100,100); i++;
		this.allAtks[i] = new Attack("Acid", "May lower opponent's Special.", 1, 40, 30, false, this.allTypes[3],32,10,100); i++;
		this.allAtks[i] = new Attack("Acid Armor", "Sharply raises user's Defense.", 2, 0, 20, false, this.allTypes[3],21,100,100); i++;
		this.allAtks[i] = new Attack("Agility", "Sharply raises user's Speed.", 3, 0, 30, false, this.allTypes[12],29,100,100); i++;
		this.allAtks[i] = new Attack("Amnesia", "Sharply raises user's Special.", 4, 0, 20, false, this.allTypes[12],37,100,100); i++;
		this.allAtks[i] = new Attack("Aurora Beam", "May freeze the opponent", 5, 65, 20, false, this.allTypes[13],5,10,100); i++;
		this.allAtks[i] = new Attack("Barrage", "Hits 2-5 times in one turn.", 6, 15, 20, true, this.allTypes[0],46,100,85); i++;
		this.allAtks[i] = new Attack("Barrier", "Sharply raises user's Defense.", 7, 0, 20, false, this.allTypes[12],21,100,100); i++;
		this.allAtks[i] = new Attack("Bide", "User takes damage for two turns then strikes back double.", 8, 0, 10, true, this.allTypes[10],54,100,100); i++; // OK
		this.allAtks[i] = new Attack("Bite", "May cause flinching", 9, 60, 25, true, this.allTypes[0],43,30,100); i++;
		this.allAtks[i] = new Attack("Blizzard", "May freeze opponent.", 10, 110, 5, false, this.allTypes[13],5,10,70); i++;
		this.allAtks[i] = new Attack("Body Slam", "May paralyze opponent.", 11, 85, 15, true, this.allTypes[0],1 ,30,100); i++;
		this.allAtks[i] = new Attack("Bone Club", "May cause flinching.", 12, 65, 20, true, this.allTypes[4],43,10,85); i++;
		this.allAtks[i] = new Attack("Bonemerang", "Hits twice in one turn.", 13, 50, 10, true, this.allTypes[4],0,100,90); i++;
		this.allAtks[i] = new Attack("Bubble", "May lower opponent's Speed.", 14, 40, 30, false, this.allTypes[9],24,10,100); i++;
		this.allAtks[i] = new Attack ("Bubble Beam","May lower opponent's Speed.",15,65,20,false,this.allTypes[9],24,10,100); i++;
		this.allAtks[i] = new Attack ("Clamp","Traps opponent, damaging them for 2-5 turns.",16,35,10,true,this.allTypes[9],54,0,85); i++; // OK
		this.allAtks[i] = new Attack ("Comet Punch","Hits 2-5 times in one turn.",17,18,15,true,this.allTypes[0],46,0,85); i++;
		this.allAtks[i] = new Attack ("Confuse Ray","Confuses opponent.",18,0,10,false,this.allTypes[7],6,100,100); i++;
		this.allAtks[i] = new Attack ("Confusion","May confuse opponent.",19,50,25,false,this.allTypes[12],6,10,100); i++;
		this.allAtks[i] = new Attack ("Constrict","May lower opponent's Speed by one stage.",20,10,35,true,this.allTypes[0],24,10,100); i++;
		this.allAtks[i] = new Attack ("Conversion","Changes user's type to enemy's",21,0,30,false,this.allTypes[0],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Counter","When hit by a Physical Attack, user strikes back with 2x power.",22,0,20,true,this.allTypes[1],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Crabhammer","High critical hit ratio.",23,100,10,true,this.allTypes[9],47,1000,90); i++;
		this.allAtks[i] = new Attack ("Cut","Standard attack",24,50,30,true,this.allTypes[0],0,0,95); i++;
		this.allAtks[i] = new Attack ("Defense Curl","Raises user's Defense.",25,0,40,false,this.allTypes[0],17,1000,100); i++;
		this.allAtks[i] = new Attack ("Dig","Digs underground on first turn, attacks on second. Can also escape from caves.",26,80,10,true,this.allTypes[4],48,100,100); i++;
		this.allAtks[i] = new Attack ("Disable","Opponent can't use a random attack for a few turns.",27,0,20,false,this.allTypes[0],54,0,55); i++; // OK
		this.allAtks[i] = new Attack ("Dizzy Punch","May confuse opponent.",28,70,10,true,this.allTypes[0],6,20,100); i++;
		this.allAtks[i] = new Attack ("Double Kick","Hits twice in one turn.",29,30,30,true,this.allTypes[1],46,0,100); i++;
		this.allAtks[i] = new Attack ("Double Slap","Hits 2-5 times in one turn.",30,15,10,true,this.allTypes[0],46,0,85); i++;
		this.allAtks[i] = new Attack ("Double Team","Raises user's Evasiveness.",31,0,15,false,this.allTypes[0],41,0,100); i++;
		this.allAtks[i] = new Attack ("Double-Edge","User receives recoil damage.",32,100,15,true,this.allTypes[0],49,0,100); i++;
		this.allAtks[i] = new Attack ("Dragon Rage","Always inflicts 40 HP.",33,40,10,false,this.allTypes[14],53,0,100); i++;
		this.allAtks[i] = new Attack ("Dream Eater","User recovers half the HP inflicted on a sleeping opponent.",34,100,15,false,this.allTypes[12],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Drill Peck","Standard attack",35,80,20,true,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Earthquake","Power is doubled if opponent is underground from using Dig.",36,100,10,true,this.allTypes[4],0,0,100); i++;
		this.allAtks[i] = new Attack ("Egg Bomb","Standard attack",37,100,10,true,this.allTypes[0],0,0,75); i++;
		this.allAtks[i] = new Attack ("Ember","May burn opponent.",38,40,25,false,this.allTypes[8],4,10,100); i++;
		this.allAtks[i] = new Attack ("Explosion","User faints.",39,250,5,true,this.allTypes[0],50,0,100); i++;
		this.allAtks[i] = new Attack ("Fire Blast","May burn opponent.",40,110,5,false,this.allTypes[8],4,10,85); i++;
		this.allAtks[i] = new Attack ("Fire Punch","May burn opponent.",41,75,15,true,this.allTypes[8],4,10,100); i++;
		this.allAtks[i] = new Attack ("Fire Spin","Traps opponent, damaging them for 2-5 turns.",42,35,15,false,this.allTypes[8],54,100,85); i++; // OK
		this.allAtks[i] = new Attack ("Flamethrower","May burn opponent.",43,90,15,false,this.allTypes[8],4,10,100); i++;
		this.allAtks[i] = new Attack ("Flash","Lowers opponent's Accuracy.",44,0,20,false,this.allTypes[0],40,100,100); i++;
		this.allAtks[i] = new Attack ("Fly","Flies up on first turn, attacks on second turn.",45,90,15,true,this.allTypes[2],48,0,95); i++;
		this.allAtks[i] = new Attack ("Fury Attack","Hits 2-5 times in one turn.",46,15,20,true,this.allTypes[0],46,0,85); i++;
		this.allAtks[i] = new Attack ("Fury Swipes","Hits 2-5 times in one turn.",47,18,15,true,this.allTypes[0],46,0,80); i++;
		this.allAtks[i] = new Attack ("Glare","Paralyzes opponent.",48,0,30,false,this.allTypes[0],1,100,100); i++;
		this.allAtks[i] = new Attack ("Growl","Lowers opponent's Attack.",49,0,40,false,this.allTypes[0],8,100,100); i++;
		this.allAtks[i] = new Attack ("Growth","Raises user's special.",50,0,40,false,this.allTypes[0],33,100,100); i++;
		this.allAtks[i] = new Attack ("Gust","Standard attack",51,40,35,false,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Harden","Raises user's Defense.",52,0,30,false,this.allTypes[0],17,100,100); i++;
		this.allAtks[i] = new Attack ("Haze","Resets all stat changes.",53,0,30,false,this.allTypes[13],45,100,100); i++;
		this.allAtks[i] = new Attack ("Headbutt","May cause flinching.",54,70,15,true,this.allTypes[0],43,30,100); i++;
		this.allAtks[i] = new Attack ("High Jump Kick","If it misses, the user loses HP.",55,85,10,true,this.allTypes[1],54,0,90); i++; // OK
		this.allAtks[i] = new Attack ("Horn Attack","Standard attack",56,65,25,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Hydro Pump","Standard attack",57,110,5,false,this.allTypes[9],0,0,80); i++;
		this.allAtks[i] = new Attack ("Hyper Beam","User must recharge next turn.",58,150,5,false,this.allTypes[0],54,0,90); i++; // OK
		this.allAtks[i] = new Attack ("Hyper Fang","May cause flinching.",59,80,15,true,this.allTypes[0],43,10,90); i++;
		this.allAtks[i] = new Attack ("Hypnosis","Puts opponent to sleep.",60,0,20,false,this.allTypes[12],2,100,60); i++;
		this.allAtks[i] = new Attack ("Ice Beam","May freeze opponent.",61,90,10,false,this.allTypes[13],5,10,100); i++;
		this.allAtks[i] = new Attack ("Ice Punch","May freeze opponent.",62,75,15,true,this.allTypes[13],5,10,100); i++;
		this.allAtks[i] = new Attack ("Jump Kick","If it misses, the user loses HP.",63,70,10,true,this.allTypes[1],54,0,95); i++; // OK
		this.allAtks[i] = new Attack ("Karate Chop","High critical hit ratio.",64,50,25,true,this.allTypes[1],47,100,100); i++;
		this.allAtks[i] = new Attack ("Kinesis","Lowers opponent's Accuracy.",65,0,15,false,this.allTypes[12],40,100,80); i++;
		this.allAtks[i] = new Attack ("Leech Life","User recovers half the HP inflicted on opponent.",66,80,10,true,this.allTypes[6],44,100,100); i++;
		this.allAtks[i] = new Attack ("Leech Seed","Drains HP from opponent each turn.",67,0,10,false,this.allTypes[10],54,100,90); i++; // OK
		this.allAtks[i] = new Attack ("Leer","Lowers opponent's Defense.",68,0,30,false,this.allTypes[0],16,100,100); i++;
		this.allAtks[i] = new Attack ("Lick","May paralyze opponent.",69,30,30,true,this.allTypes[7],1,30,100); i++;
		this.allAtks[i] = new Attack ("Light Screen","Halves damage from Specialattacks for 5 turns.",70,0,30,false,this.allTypes[12],52,0,100); i++;
		this.allAtks[i] = new Attack ("Lovely Kiss","Puts opponent to sleep.",71,0,10,false,this.allTypes[0],2,100,75); i++;
		this.allAtks[i] = new Attack ("Low Kick","Standard attack",72,60,20,true,this.allTypes[1],0,0,100); i++;
		this.allAtks[i] = new Attack ("Meditate","Raises user's Attack.",73,0,40,false,this.allTypes[12],9,100,100); i++;
		this.allAtks[i] = new Attack ("Mega Drain","User recovers half the HP inflicted on opponent.",74,40,15,false,this.allTypes[10],44,100,100); i++;
		this.allAtks[i] = new Attack ("Mega Kick","Standard attack",75,120,5,true,this.allTypes[0],0,0,75); i++;
		this.allAtks[i] = new Attack ("Mega Punch","Standard attack",76,80,20,true,this.allTypes[0],0,0,85); i++;
		this.allAtks[i] = new Attack ("Metronome","User performs almost any move in the game at random.",77,0,10,false,this.allTypes[0],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Mimic","Copies the opponent's last move.",78,0,10,false,this.allTypes[0],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Minimize","Sharply raises user's Evasiveness.",79,0,10,false,this.allTypes[0],41,0,100); i++;
		this.allAtks[i] = new Attack ("Mirror Move","User performs the opponent's last move.",80,0,20,false,this.allTypes[2],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Night Shade","Inflicts damage equal to user's level.",81,50,15,false,this.allTypes[7],53,0,100); i++;
		this.allAtks[i] = new Attack ("Pay Day","Standard attack",82,40,20,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Peck","Standard attack",83,35,35,true,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Petal Dance","User attacks for 2-3 turns but then becomes confused.",84,120,10,false,this.allTypes[10],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Pin Missile","Hits 2-5 times in one turn.",85,25,20,true,this.allTypes[6],46,0,95); i++;
		this.allAtks[i] = new Attack ("Poison Gas","Poisons opponent.",86,0,40,false,this.allTypes[3],3,100,90); i++;
		this.allAtks[i] = new Attack ("Poison Powder","Poisons opponent.",87,0,35,false,this.allTypes[3],3,100,75); i++;
		this.allAtks[i] = new Attack ("Poison Sting","May poison the opponent.",88,15,35,true,this.allTypes[3],3,30,100); i++;
		this.allAtks[i] = new Attack ("Pound","Standard attack",89,40,35,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Psybeam","May confuse opponent.",90,65,20,false,this.allTypes[12],6,10,100); i++;
		this.allAtks[i] = new Attack ("Psychic","May lower opponent's SpecialDefense.",91,90,10,false,this.allTypes[12],32,10,100); i++;
		this.allAtks[i] = new Attack ("Psywave","Inflicts damage 50-150% of user's level.",92,0,15,false,this.allTypes[12],54,0,80); i++;
		this.allAtks[i] = new Attack ("Quick Attack","User attacks first.",93,40,30,true,this.allTypes[0],51,0,100); i++;
		this.allAtks[i] = new Attack ("Rage","Raises user's Attack when hit.",94,20,20,true,this.allTypes[0],9,100,100); i++;
		this.allAtks[i] = new Attack ("Razor Leaf","High critical hit ratio.",95,55,25,true,this.allTypes[10],47,100,95); i++;
		this.allAtks[i] = new Attack ("Razor Wind","Charges on first turn, attacks on second. High critical hit ratio.",96,80,10,false,this.allTypes[0],48,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Recover","User recovers half its max HP.",97,0,10,false,this.allTypes[0],45,100,100); i++;
		this.allAtks[i] = new Attack ("Reflect","Halves damage from Physical attacks for 5 turns.",98,0,20,false,this.allTypes[12],52,100,100); i++;
		this.allAtks[i] = new Attack ("Rest","User sleeps for 2 turns, but user is fully healed.",99,0,10,false,this.allTypes[12],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Roar","In battles, the opponent switches. In the wild, the Pokémon runs.",100,0,20,false,this.allTypes[0],54,100,85); i++; // OK
		this.allAtks[i] = new Attack ("Rock Slide","May cause flinching.",101,75,10,true,this.allTypes[5],43,30,90); i++;
		this.allAtks[i] = new Attack ("Rock Throw","Standard attack",102,50,15,true,this.allTypes[5],0,0,90); i++;
		this.allAtks[i] = new Attack ("Rolling Kick","May cause flinching.",103,60,15,true,this.allTypes[1],43,30,85); i++;
		this.allAtks[i] = new Attack ("Sand Attack","Lowers opponent's Accuracy.",104,0,15,false,this.allTypes[4],40,100,100); i++;
		this.allAtks[i] = new Attack ("Scratch","Standard attack",105,40,35,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Screech","Sharply lowers opponent's Defense.",106,0,40,false,this.allTypes[0],20,100,85); i++;
		this.allAtks[i] = new Attack ("Seismic Toss","Inflicts damage equal to user's level.",107,50,20,true,this.allTypes[1],53,0,100); i++;
		this.allAtks[i] = new Attack ("Self-Destruct","User faints.",108,200,5,true,this.allTypes[0],50,0,100); i++;
		this.allAtks[i] = new Attack ("Sharpen","Raises user's Attack.",109,0,30,false,this.allTypes[0],9,0,100); i++;
		this.allAtks[i] = new Attack ("Sing","Puts opponent to sleep.",110,0,15,false,this.allTypes[0],2,100,55); i++;
		this.allAtks[i] = new Attack ("Skull Bash","Charges on first turn, attacks on second.",111,130,10,true,this.allTypes[0],48,0,100); i++;
		this.allAtks[i] = new Attack ("Sky Attack","Charges on first turn, attacks on second. May cause flinching.",112,140,5,true,this.allTypes[2],48,100,90); i++; // OK
		this.allAtks[i] = new Attack ("Slam","Standard attack",113,80,20,true,this.allTypes[0],0,0,75); i++;
		this.allAtks[i] = new Attack ("Slash","High critical hit ratio.",114,70,20,true,this.allTypes[0],47,0,100); i++;
		this.allAtks[i] = new Attack ("Sleep Powder","Puts opponent to sleep.",115,0,15,false,this.allTypes[10],2,100,75); i++;
		this.allAtks[i] = new Attack ("Sludge","May poison opponent.",116,65,20,false,this.allTypes[3],3,30,100); i++;
		this.allAtks[i] = new Attack ("Smog","May poison opponent.",117,30,20,false,this.allTypes[3],3,40,70); i++;
		this.allAtks[i] = new Attack ("Smokescreen","Lowers opponent's Accuracy.",118,0,20,false,this.allTypes[0],40,0,100); i++;
		this.allAtks[i] = new Attack ("Soft-Boiled","User recovers half its max HP.",119,0,10,false,this.allTypes[0],45,0,100); i++;
		this.allAtks[i] = new Attack ("Solar Beam","Charges on first turn, attacks on second.",120,120,10,false,this.allTypes[10],48,0,100); i++;
		this.allAtks[i] = new Attack ("Sonic Boom","Always inflicts 20 HP.",121,20,20,false,this.allTypes[0],53,0,90); i++;
		this.allAtks[i] = new Attack ("Spike Cannon","Hits 2-5 times in one turn.",122,20,15,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Splash","Maybe has a secret purpose...",123,0,40,false,this.allTypes[0],0,100,100); i++;
		this.allAtks[i] = new Attack ("Spore","Puts opponent to sleep.",124,0,15,false,this.allTypes[10],2,100,100); i++;
		this.allAtks[i] = new Attack ("Stomp","May cause flinching.",125,65,20,true,this.allTypes[0],43,30,100); i++;
		this.allAtks[i] = new Attack ("Strength","Standard attack",126,80,15,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("String Shot","Sharply lowers opponent's Speed.",127,0,40,false,this.allTypes[6],28,100,95); i++;
		this.allAtks[i] = new Attack ("Struggle","Only usable when all PP are gone. Hurts the user.",128,50,1000,true,this.allTypes[0],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Stun Spore","Paralyzes opponent.",129,0,30,false,this.allTypes[10],1,100,75); i++;
		this.allAtks[i] = new Attack ("Submission","User receives recoil damage.",130,80,20,true,this.allTypes[1],49,0,80); i++;
		this.allAtks[i] = new Attack ("Substitute","Uses HP to create a decoy that takes hits.",131,0,10,false,this.allTypes[0],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Super Fang","Always takes off half of the opponent's HP.",132,0,10,true,this.allTypes[0],54,0,90); i++; // OK
		this.allAtks[i] = new Attack ("Supersonic","Confuses opponent.",133,0,20,false,this.allTypes[0],6,100,55); i++;
		this.allAtks[i] = new Attack ("Surf","Standard attack.",134,90,15,false,this.allTypes[9],0,0,100); i++;
		this.allAtks[i] = new Attack ("Swift","Ignores Accuracy and Evasiveness.",135,60,20,false,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Swords Dance","Sharply raises user's Attack.",136,0,20,false,this.allTypes[0],13,100,100); i++;
		this.allAtks[i] = new Attack ("Tackle","Standard attack",137,40,35,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Tail Whip","Lowers opponent's Defense.",138,0,30,false,this.allTypes[0],16,100,100); i++;
		this.allAtks[i] = new Attack ("Take Down","User receives recoil damage.",139,90,20,true,this.allTypes[0],49,0,85); i++;
		this.allAtks[i] = new Attack ("Thrash","User attacks for 2-3 turns but then becomes confused.",140,120,10,true,this.allTypes[0],54,0,100); i++; // OK
		this.allAtks[i] = new Attack ("Thunder","May paralyze opponent.",141,110,10,false,this.allTypes[11],1,30,70); i++;
		this.allAtks[i] = new Attack ("Thunder Punch","May paralyze opponent.",142,75,15,true,this.allTypes[11],1,10,100); i++;
		this.allAtks[i] = new Attack ("Thunder Shock","May paralyze opponent.",143,40,30,false,this.allTypes[11],1,10,100); i++;
		this.allAtks[i] = new Attack ("Thunder Wave","Paralyzes opponent.",144,0,20,false,this.allTypes[11],1,100,90); i++;
		this.allAtks[i] = new Attack ("Thunderbolt","May paralyze opponent.",145,90,15,false,this.allTypes[11],1,10,100); i++;
		this.allAtks[i] = new Attack ("Toxic","Badly poisons opponent.",146,0,10,false,this.allTypes[3],3,100,90); i++;
		this.allAtks[i] = new Attack ("Transform","User takes on the form and attacks of the opponent.",147,0,10,false,this.allTypes[0],54,0,100); i++; // To code
		this.allAtks[i] = new Attack ("Tri Attack","May paralyze, burn or freeze opponent.",148,80,10,false,this.allTypes[0],54,0,100); i++; // To code
		this.allAtks[i] = new Attack ("Twineedle","Hits twice in one turn. May poison opponent.",149,25,20,true,this.allTypes[6],46,20,100); i++;
		this.allAtks[i] = new Attack ("Vice Grip","Standard attack",150,55,30,true,this.allTypes[0],0,0,100); i++;
		this.allAtks[i] = new Attack ("Vine Whip","Standard attack",151,45,25,true,this.allTypes[10],0,0,100); i++;
		this.allAtks[i] = new Attack ("Water Gun","Standard attack",152,40,25,false,this.allTypes[9],0,0,100); i++;
		this.allAtks[i] = new Attack ("Waterfall","May cause flinching.",153,80,15,true,this.allTypes[9],43,20,100); i++;
		this.allAtks[i] = new Attack ("Whirlwind","In battles, the opponent switches. In the wild, the Pokémon runs.",154,0,20,false,this.allTypes[0],54,100,85); i++; // OK
		this.allAtks[i] = new Attack ("Wing Attack","Standard attack",155,60,35,true,this.allTypes[2],0,0,100); i++;
		this.allAtks[i] = new Attack ("Withdraw","Raises user's Defense.",156,0,40,false,this.allTypes[9],17,100,100); i++;
		this.allAtks[i] = new Attack ("Wrap","Traps opponent, damaging them for 2-5 turns.",157,15,20,true,this.allTypes[0],54,0,90); i++; // OK
		
		//Tracing that loading is OK
	}
	
	private void createPkmn(Window win)
	{
		this.allPkmn = new Pokemon[152]; //Pokémon at index 0 will be null to simplify further researches in the array (index will be exactly the pokédex's number)
		int i = 0;
		this.allPkmn[i] = new Pokemon(); i++;
		// Name - id - Type1 - Type2 (may be null) - HP - Attacks - Defense - Speed - Special
		//First we create all the pokémon, then we will assign them their attacks.
		this.allPkmn[i] = new Pokemon("Bulbasaur",i,this.allTypes[10],this.allTypes[3],132,80,81,77,97); i++;
		this.allPkmn[i] = new Pokemon("Ivysaur",i,this.allTypes[10],this.allTypes[3],141,87,89,85,106);i++;
		this.allPkmn[i] = new Pokemon("Venusaur",i,this.allTypes[10],this.allTypes[3],156,103,104,102,121);i++;
		this.allPkmn[i] = new Pokemon("Charmander",i,this.allTypes[8],null,126,83,75,97,82);i++;
		this.allPkmn[i] = new Pokemon("Charmeleon",i,this.allTypes[8],null,141,91,86,107,93);i++;
		this.allPkmn[i] = new Pokemon("Charizard",i,this.allTypes[8],this.allTypes[2],154,105,99,122,106);i++;
		this.allPkmn[i] = new Pokemon("Squirtle",i,this.allTypes[9],null,131,79,97,75,82);i++;
		this.allPkmn[i] = new Pokemon("Wartortle",i,this.allTypes[9],null,140,88,106,83,91);i++;
		this.allPkmn[i] = new Pokemon("Blastoise",i,this.allTypes[9],null,155,104,121,100,106);i++;
		this.allPkmn[i] = new Pokemon("Caterpie",i,this.allTypes[6],null,132,61,67,77,52);i++;
		this.allPkmn[i] = new Pokemon("Metapod",i,this.allTypes[6],null,137,51,87,62,57);i++;
		this.allPkmn[i] = new Pokemon("Butterfree",i,this.allTypes[6],this.allTypes[2],141,70,76,95,106);i++;
		this.allPkmn[i] = new Pokemon("Weedle",i,this.allTypes[6],this.allTypes[3],127,66,62,82,52);i++;
		this.allPkmn[i] = new Pokemon("Kakuna",i,this.allTypes[6],this.allTypes[3],132,56,82,67,57);i++;
		this.allPkmn[i] = new Pokemon("Beedrill",i,this.allTypes[6],this.allTypes[3],146,105,66,100,71);i++;
		this.allPkmn[i] = new Pokemon("Pidgey",i,this.allTypes[0],this.allTypes[2],127,76,72,88,67);i++;
		this.allPkmn[i] = new Pokemon("Pidgeotto",i,this.allTypes[0],this.allTypes[2],147,89,83,99,79);i++;
		this.allPkmn[i] = new Pokemon("Pidgeot",i,this.allTypes[0],this.allTypes[2],161,104,99,114,93);i++;
		this.allPkmn[i] = new Pokemon("Rattata",i,this.allTypes[0],null,117,87,67,104,57);i++;
		this.allPkmn[i] = new Pokemon("Raticate",i,this.allTypes[0],null,136,106,86,122,76);i++;
		this.allPkmn[i] = new Pokemon("Spearow",i,this.allTypes[0],this.allTypes[2],127,91,62,102,63);i++;
		this.allPkmn[i] = new Pokemon("Fearow",i,this.allTypes[0],this.allTypes[2],143,114,89,123,84);i++;
		this.allPkmn[i] = new Pokemon("Ekans",i,this.allTypes[3],null,122,91,76,87,72);i++;
		this.allPkmn[i] = new Pokemon("Arbok",i,this.allTypes[3],null,138,109,93,103,88);i++;
		this.allPkmn[i] = new Pokemon("Pikachu",i,this.allTypes[11],null,122,86,62,122,82);i++;
		this.allPkmn[i] = new Pokemon("Raichu",i,this.allTypes[11],null,138,114,79,123,113);i++;
		this.allPkmn[i] = new Pokemon("Sandshrew",i,this.allTypes[4],null,134,104,113,68,59);i++;
		this.allPkmn[i] = new Pokemon("Sandslash",i,this.allTypes[4],null,151,121,131,87,76);i++;
		this.allPkmn[i] = new Pokemon("Nidoran♀",i,this.allTypes[3],null,142,78,84,73,72);i++;
		this.allPkmn[i] = new Pokemon("Nidorina",i,this.allTypes[3],null,151,87,93,81,81);i++;
		this.allPkmn[i] = new Pokemon("Nidoqueen",i,this.allTypes[3],this.allTypes[4],166,103,108,98,96);i++;
		this.allPkmn[i] = new Pokemon("Nidoran♂",i,this.allTypes[3],null,135,90,74,84,74);i++;
		this.allPkmn[i] = new Pokemon("Nidorino",i,this.allTypes[3],null,142,97,83,90,81);i++;
		this.allPkmn[i] = new Pokemon("Nidoking",i,this.allTypes[3],this.allTypes[4],157,113,98,107,96);i++;
		this.allPkmn[i] = new Pokemon("Clefairy",i,this.allTypes[0],null,157,76,80,67,92);i++;
		this.allPkmn[i] = new Pokemon("Clefable",i,this.allTypes[0],null,173,94,97,83,108);i++;
		this.allPkmn[i] = new Pokemon("Vulpix",i,this.allTypes[8],null,125,72,72,97,97);i++;
		this.allPkmn[i] = new Pokemon("Ninetales",i,this.allTypes[8],null,149,97,96,122,121);i++;
		this.allPkmn[i] = new Pokemon("Jigglypuff",i,this.allTypes[0],null,202,76,52,52,57);i++;
		this.allPkmn[i] = new Pokemon("Wigglytuff",i,this.allTypes[0],null,221,95,71,70,76);i++;
		this.allPkmn[i] = new Pokemon("Zubat",i,this.allTypes[3],this.allTypes[2],127,76,67,87,72);i++;
		this.allPkmn[i] = new Pokemon("Golbat",i,this.allTypes[3],this.allTypes[2],153,104,94,113,98);i++;
		this.allPkmn[i] = new Pokemon("Oddish",i,this.allTypes[10],this.allTypes[3],132,81,87,62,107);i++;
		this.allPkmn[i] = new Pokemon("Gloom",i,this.allTypes[10],this.allTypes[3],141,90,96,65,111);i++;
		this.allPkmn[i] = new Pokemon("Vileplume",i,this.allTypes[10],this.allTypes[3],153,104,109,73,123);i++;
		this.allPkmn[i] = new Pokemon("Paras",i,this.allTypes[6],this.allTypes[10],122,101,87,57,87);i++;
		this.allPkmn[i] = new Pokemon("Parasect",i,this.allTypes[6],this.allTypes[10],141,120,106,55,106);i++;
		this.allPkmn[i] = new Pokemon("Venonat",i,this.allTypes[6],this.allTypes[3],147,86,82,77,72);i++;
		this.allPkmn[i] = new Pokemon("Venomoth",i,this.allTypes[6],this.allTypes[3],148,89,84,113,113);i++;
		this.allPkmn[i] = new Pokemon("Diglett",i,this.allTypes[4],null,97,86,57,127,77);i++;
		this.allPkmn[i] = new Pokemon("Dugtrio",i,this.allTypes[4],null,113,104,74,143,93);i++;
		this.allPkmn[i] = new Pokemon("Meowth",i,this.allTypes[0],null,127,76,67,122,72);i++;
		this.allPkmn[i] = new Pokemon("Persian",i,this.allTypes[0],null,143,94,84,138,88);i++;
		this.allPkmn[i] = new Pokemon("Psyduck",i,this.allTypes[9],null,137,83,80,87,82);i++;
		this.allPkmn[i] = new Pokemon("Golduck",i,this.allTypes[9],null,156,103,99,107,103);i++;
		this.allPkmn[i] = new Pokemon("Mankey",i,this.allTypes[1],null,127,111,67,102,67);i++;
		this.allPkmn[i] = new Pokemon("Primeape",i,this.allTypes[1],null,143,129,84,118,83);i++;
		this.allPkmn[i] = new Pokemon("Growlithe",i,this.allTypes[8],null,139,99,73,88,79);i++;
		this.allPkmn[i] = new Pokemon("Arcanine",i,this.allTypes[8],null,163,128,98,113,98);i++;
		this.allPkmn[i] = new Pokemon("Poliwag",i,this.allTypes[9],null,127,81,72,122,72);i++;
		this.allPkmn[i] = new Pokemon("Poliwhirl",i,this.allTypes[9],null,146,90,91,115,76);i++;
		this.allPkmn[i] = new Pokemon("Poliwrath",i,this.allTypes[9],this.allTypes[1],166,106,116,92,91);i++;
		this.allPkmn[i] = new Pokemon("Abra",i,this.allTypes[12],null,112,51,47,122,137);i++;
		this.allPkmn[i] = new Pokemon("Kadabra",i,this.allTypes[12],null,121,60,56,130,146);i++;
		this.allPkmn[i] = new Pokemon("Alakazam",i,this.allTypes[12],null,131,71,66,142,156);i++;
		this.allPkmn[i] = new Pokemon("Machop",i,this.allTypes[1],null,154,109,78,63,64);i++;
		this.allPkmn[i] = new Pokemon("Machoke",i,this.allTypes[1],null,161,125,96,70,76);i++;
		this.allPkmn[i] = new Pokemon("Machamp",i,this.allTypes[1],null,166,151,101,77,86);i++;
		this.allPkmn[i] = new Pokemon("Bellsprout",i,this.allTypes[10],this.allTypes[3],134,104,63,68,99);i++;
		this.allPkmn[i] = new Pokemon("Weepinbell",i,this.allTypes[10],this.allTypes[3],146,115,76,80,111);i++;
		this.allPkmn[i] = new Pokemon("Victreebel",i,this.allTypes[10],this.allTypes[3],156,126,86,92,121);i++;
		this.allPkmn[i] = new Pokemon("Tentacool",i,this.allTypes[9],this.allTypes[3],124,69,63,98,129);i++;
		this.allPkmn[i] = new Pokemon("Tentacruel",i,this.allTypes[9],this.allTypes[3],156,91,86,122,141);i++;
		this.allPkmn[i] = new Pokemon("Geodude",i,this.allTypes[5],this.allTypes[4],124,109,128,48,59);i++;
		this.allPkmn[i] = new Pokemon("Graveler",i,this.allTypes[5],this.allTypes[4],136,120,141,60,71);i++;
		this.allPkmn[i] = new Pokemon("Golem",i,this.allTypes[5],this.allTypes[4],156,131,151,67,76);i++;
		this.allPkmn[i] = new Pokemon("Ponyta",i,this.allTypes[8],null,131,110,81,115,91);i++;
		this.allPkmn[i] = new Pokemon("Rapidash",i,this.allTypes[8],null,141,121,91,127,101);i++;
		this.allPkmn[i] = new Pokemon("Slowpoke",i,this.allTypes[9],this.allTypes[12],174,94,93,43,69);i++;
		this.allPkmn[i] = new Pokemon("Slowbro",i,this.allTypes[9],this.allTypes[12],173,99,134,53,103);i++;
		this.allPkmn[i] = new Pokemon("Magnemite",i,this.allTypes[11],null,109,64,98,73,124);i++;
		this.allPkmn[i] = new Pokemon("Magneton",i,this.allTypes[11],null,128,84,119,93,143);i++;
		this.allPkmn[i] = new Pokemon("Farfetch'd",i,this.allTypes[0],this.allTypes[2],136,94,83,88,87);i++;
		this.allPkmn[i] = new Pokemon("Doduo",i,this.allTypes[0],this.allTypes[2],119,114,73,103,64);i++;
		this.allPkmn[i] = new Pokemon("Dodrio",i,this.allTypes[0],this.allTypes[2],138,134,94,123,83);i++;
		this.allPkmn[i] = new Pokemon("Seel",i,this.allTypes[9],null,149,74,83,73,99);i++;
		this.allPkmn[i] = new Pokemon("Dewgong",i,this.allTypes[13],null,166,91,101,92,116);i++;
		this.allPkmn[i] = new Pokemon("Grimer",i,this.allTypes[3],null,164,109,78,53,69);i++;
		this.allPkmn[i] = new Pokemon("Muk",i,this.allTypes[3],null,183,129,99,73,88);i++;
		this.allPkmn[i] = new Pokemon("Shellder",i,this.allTypes[9],null,114,94,128,68,74);i++;
		this.allPkmn[i] = new Pokemon("Cloyster",i,this.allTypes[13],null,123,113,198,88,103);i++;
		this.allPkmn[i] = new Pokemon("Gastly",i,this.allTypes[7],this.allTypes[3],114,64,58,108,129);i++;
		this.allPkmn[i] = new Pokemon("Haunter",i,this.allTypes[7],this.allTypes[3],126,75,71,120,141);i++;
		this.allPkmn[i] = new Pokemon("Gengar",i,this.allTypes[7],this.allTypes[3],136,86,81,132,151);i++;
		this.allPkmn[i] = new Pokemon("Onix",i,this.allTypes[5],this.allTypes[4],116,70,186,95,56);i++;
		this.allPkmn[i] = new Pokemon("Drowzee",i,this.allTypes[12],null,144,77,73,70,119);i++;
		this.allPkmn[i] = new Pokemon("Hypno",i,this.allTypes[12],null,161,94,91,89,136);i++;
		this.allPkmn[i] = new Pokemon("Krabby",i,this.allTypes[9],null,114,134,118,79,54);i++;
		this.allPkmn[i] = new Pokemon("Kingler",i,this.allTypes[9],null,131,151,136,97,71);i++;
		this.allPkmn[i] = new Pokemon("Voltorb",i,this.allTypes[11],null,124,59,78,128,84);i++;
		this.allPkmn[i] = new Pokemon("Electrode",i,this.allTypes[11],null,138,74,96,163,103);i++;
		this.allPkmn[i] = new Pokemon("Exeggcute",i,this.allTypes[10],this.allTypes[12],144,69,108,68,89);i++;
		this.allPkmn[i] = new Pokemon("Exeggutor",i,this.allTypes[10],this.allTypes[12],168,113,103,73,143);i++;
		this.allPkmn[i] = new Pokemon("Cubone",i,this.allTypes[4],null,134,79,123,63,69);i++;
		this.allPkmn[i] = new Pokemon("Marowak",i,this.allTypes[4],null,141,105,136,70,76);i++;
		this.allPkmn[i] = new Pokemon("Hitmonlee",i,this.allTypes[1],null,131,145,79,112,61);i++;
		this.allPkmn[i] = new Pokemon("Hitmonchan",i,this.allTypes[1],null,131,130,105,101,61);i++;
		this.allPkmn[i] = new Pokemon("Lickitung",i,this.allTypes[0],null,171,80,101,55,86);i++;
		this.allPkmn[i] = new Pokemon("Koffing",i,this.allTypes[3],null,124,94,123,63,89);i++;
		this.allPkmn[i] = new Pokemon("Weezing",i,this.allTypes[3],null,141,111,141,82,106);i++;
		this.allPkmn[i] = new Pokemon("Rhyhorn",i,this.allTypes[4],this.allTypes[5],161,110,121,50,56);i++;
		this.allPkmn[i] = new Pokemon("Rhydon",i,this.allTypes[4],this.allTypes[5],181,151,141,62,66);i++;
		this.allPkmn[i] = new Pokemon("Chansey",i,this.allTypes[0],null,326,26,26,72,126);i++;
		this.allPkmn[i] = new Pokemon("Tangela",i,this.allTypes[10],null,143,79,139,83,123);i++;
		this.allPkmn[i] = new Pokemon("Kangaskhan",i,this.allTypes[0],null,181,116,101,112,61);i++;
		this.allPkmn[i] = new Pokemon("Horsea",i,this.allTypes[9],null,114,69,98,88,99);i++;
		this.allPkmn[i] = new Pokemon("Seadra",i,this.allTypes[9],null,133,89,119,108,118);i++;
		this.allPkmn[i] = new Pokemon("Goldeen",i,this.allTypes[9],null,129,96,88,91,79);i++;
		this.allPkmn[i] = new Pokemon("Seaking",i,this.allTypes[9],null,158,116,89,91,103);i++;
		this.allPkmn[i] = new Pokemon("Staryu",i,this.allTypes[9],null,114,74,83,113,99);i++;
		this.allPkmn[i] = new Pokemon("Starmie",i,this.allTypes[9],this.allTypes[12],136,96,106,137,121);i++;
		this.allPkmn[i] = new Pokemon("Mr. Mime",i,this.allTypes[12],null,121,70,91,115,126);i++;
		this.allPkmn[i] = new Pokemon("Scyther",i,this.allTypes[6],this.allTypes[2],146,131,101,127,76);i++;
		this.allPkmn[i] = new Pokemon("Jynx",i,this.allTypes[13],this.allTypes[12],146,75,61,120,121);i++;
		this.allPkmn[i] = new Pokemon("Electabuzz",i,this.allTypes[11],null,143,107,81,128,108);i++;
		this.allPkmn[i] = new Pokemon("Magmar",i,this.allTypes[8],null,143,119,81,116,108);i++;
		this.allPkmn[i] = new Pokemon("Pinsir",i,this.allTypes[6],null,141,146,121,107,76);i++;
		this.allPkmn[i] = new Pokemon("Tauros",i,this.allTypes[0],null,151,121,116,132,91);i++;
		this.allPkmn[i] = new Pokemon("Magikarp",i,this.allTypes[9],null,107,41,87,112,52);i++;
		this.allPkmn[i] = new Pokemon("Gyarados",i,this.allTypes[9],this.allTypes[2],168,143,97,99,118);i++;
		this.allPkmn[i] = new Pokemon("Lapras",i,this.allTypes[9],this.allTypes[13],206,106,101,82,116);i++;
		this.allPkmn[i] = new Pokemon("Ditto",i,this.allTypes[0],null,135,79,80,80,80);i++;
		this.allPkmn[i] = new Pokemon("Eevee",i,this.allTypes[0],null,139,84,78,83,94);i++;
		this.allPkmn[i] = new Pokemon("Vaporeon",i,this.allTypes[9],null,206,86,81,87,131);i++;
		this.allPkmn[i] = new Pokemon("Jolteon",i,this.allTypes[11],null,141,86,81,152,131);i++;
		this.allPkmn[i] = new Pokemon("Flareon",i,this.allTypes[8],null,141,151,81,87,131);i++;
		this.allPkmn[i] = new Pokemon("Porygon",i,this.allTypes[0],null,146,85,96,65,101);i++;
		this.allPkmn[i] = new Pokemon("Omanyte",i,this.allTypes[5],this.allTypes[9],119,69,128,63,119);i++;
		this.allPkmn[i] = new Pokemon("Omastar",i,this.allTypes[5],this.allTypes[9],146,81,146,77,136);i++;
		this.allPkmn[i] = new Pokemon("Kabuto",i,this.allTypes[5],this.allTypes[9],114,109,118,83,74);i++;
		this.allPkmn[i] = new Pokemon("Kabutops",i,this.allTypes[5],this.allTypes[9],136,136,126,102,91);i++;
		this.allPkmn[i] = new Pokemon("Aerodactyl",i,this.allTypes[5],this.allTypes[2],156,126,86,152,81);i++;
		this.allPkmn[i] = new Pokemon("Snorlax",i,this.allTypes[0],null,236,131,86,52,86);i++;
		this.allPkmn[i] = new Pokemon("Articuno",i,this.allTypes[13],this.allTypes[2],162,102,117,102,142);i++;
		this.allPkmn[i] = new Pokemon("Zapdos",i,this.allTypes[11],this.allTypes[2],162,107,102,117,142);i++;
		this.allPkmn[i] = new Pokemon("Moltres",i,this.allTypes[8],this.allTypes[2],162,117,107,107,142);i++;
		this.allPkmn[i] = new Pokemon("Dratini",i,this.allTypes[14],null,128,95,77,82,82);i++;
		this.allPkmn[i] = new Pokemon("Dragonair",i,this.allTypes[14],null,142,109,91,95,96);i++;
		this.allPkmn[i] = new Pokemon("Dragonite",i,this.allTypes[14],this.allTypes[2],163,151,112,97,117);i++;
		this.allPkmn[i] = new Pokemon("Mewtwo",i,this.allTypes[12],null,250,140,96,146,153);i++;
		this.allPkmn[i] = new Pokemon("Mew",i,this.allTypes[12],null,264,120,120,120,120);
		
		win.logTrace("Pokémon loaded.");
		//Assigning attacks to Pokémons.

		i = 1;
		this.allPkmn[i].getAttacks().add(this.allAtks[10]); i++;
		/*this.allPkmn[i].getAttacks().add(this.allAtks[95]);
		this.allPkmn[i].getAttacks().add(this.allAtks[67]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;*/
		this.allPkmn[i].getAttacks().add(this.allAtks[95]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[115]);
		this.allPkmn[i].getAttacks().add(this.allAtks[50]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[67]);
		this.allPkmn[i].getAttacks().add(this.allAtks[139]);
		this.allPkmn[i].getAttacks().add(this.allAtks[87]);
		this.allPkmn[i].getAttacks().add(this.allAtks[120]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[42]);
		this.allPkmn[i].getAttacks().add(this.allAtks[43]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[43]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[22]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[42]);
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[136]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[99]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[57]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[111]);
		this.allPkmn[i].getAttacks().add(this.allAtks[156]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[137]);
		this.allPkmn[i].getAttacks().add(this.allAtks[127]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[137]);
		this.allPkmn[i].getAttacks().add(this.allAtks[127]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);
		this.allPkmn[i].getAttacks().add(this.allAtks[129]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[88]);
		this.allPkmn[i].getAttacks().add(this.allAtks[127]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[88]);
		this.allPkmn[i].getAttacks().add(this.allAtks[127]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[58]);
		this.allPkmn[i].getAttacks().add(this.allAtks[149]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[106]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[93]);
		this.allPkmn[i].getAttacks().add(this.allAtks[139]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[93]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);
		this.allPkmn[i].getAttacks().add(this.allAtks[80]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[132]);
		this.allPkmn[i].getAttacks().add(this.allAtks[59]);
		this.allPkmn[i].getAttacks().add(this.allAtks[93]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[59]);
		this.allPkmn[i].getAttacks().add(this.allAtks[59]);
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[138]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[35]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);
		this.allPkmn[i].getAttacks().add(this.allAtks[80]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[35]);
		this.allPkmn[i].getAttacks().add(this.allAtks[46]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[80]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[1]);
		this.allPkmn[i].getAttacks().add(this.allAtks[106]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[157]);
		this.allPkmn[i].getAttacks().add(this.allAtks[48]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[113]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[75]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);
		this.allPkmn[i].getAttacks().add(this.allAtks[44]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[29]);
		this.allPkmn[i].getAttacks().add(this.allAtks[9]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[88]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[56]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[49]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[94]);
		this.allPkmn[i].getAttacks().add(this.allAtks[131]);
		this.allPkmn[i].getAttacks().add(this.allAtks[111]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[110]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[43]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[111]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);
		this.allPkmn[i].getAttacks().add(this.allAtks[138]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[110]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[110]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[9]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);
		this.allPkmn[i].getAttacks().add(this.allAtks[53]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[84]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[84]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[139]);
		this.allPkmn[i].getAttacks().add(this.allAtks[129]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[84]);
		this.allPkmn[i].getAttacks().add(this.allAtks[24]);
		this.allPkmn[i].getAttacks().add(this.allAtks[1]);
		this.allPkmn[i].getAttacks().add(this.allAtks[115]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[124]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[120]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[139]);
		this.allPkmn[i].getAttacks().add(this.allAtks[124]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[129]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[120]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[58]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[15]);
		this.allPkmn[i].getAttacks().add(this.allAtks[49]);
		this.allPkmn[i].getAttacks().add(this.allAtks[78]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[19]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[27]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[106]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[72]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[47]);
		this.allPkmn[i].getAttacks().add(this.allAtks[106]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[43]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[98]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[139]);
		this.allPkmn[i].getAttacks().add(this.allAtks[33]);
		this.allPkmn[i].getAttacks().add(this.allAtks[131]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[4]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[60]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[57]);
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[60]);
		this.allPkmn[i].getAttacks().add(this.allAtks[22]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[90]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);
		this.allPkmn[i].getAttacks().add(this.allAtks[98]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[97]);
		this.allPkmn[i].getAttacks().add(this.allAtks[22]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[27]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[109]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[109]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[72]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[22]);
		this.allPkmn[i].getAttacks().add(this.allAtks[109]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[95]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[129]);
		this.allPkmn[i].getAttacks().add(this.allAtks[50]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[95]);
		this.allPkmn[i].getAttacks().add(this.allAtks[1]);
		this.allPkmn[i].getAttacks().add(this.allAtks[157]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[120]);
		this.allPkmn[i].getAttacks().add(this.allAtks[113]);
		this.allPkmn[i].getAttacks().add(this.allAtks[1]);
		this.allPkmn[i].getAttacks().add(this.allAtks[98]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[57]);
		this.allPkmn[i].getAttacks().add(this.allAtks[24]);
		this.allPkmn[i].getAttacks().add(this.allAtks[1]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[108]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[3]);
		this.allPkmn[i].getAttacks().add(this.allAtks[42]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[42]);
		this.allPkmn[i].getAttacks().add(this.allAtks[125]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);
		this.allPkmn[i].getAttacks().add(this.allAtks[4]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[27]);
		this.allPkmn[i].getAttacks().add(this.allAtks[156]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[106]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[35]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[98]);
		this.allPkmn[i].getAttacks().add(this.allAtks[3]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[56]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[5]);
		this.allPkmn[i].getAttacks().add(this.allAtks[54]);
		this.allPkmn[i].getAttacks().add(this.allAtks[99]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[116]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[106]);
		this.allPkmn[i].getAttacks().add(this.allAtks[108]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[116]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[57]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[108]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[16]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[122]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[19]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);
		this.allPkmn[i].getAttacks().add(this.allAtks[60]);
		this.allPkmn[i].getAttacks().add(this.allAtks[34]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[81]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[18]);
		this.allPkmn[i].getAttacks().add(this.allAtks[60]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[19]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[60]);
		this.allPkmn[i].getAttacks().add(this.allAtks[34]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[54]);
		this.allPkmn[i].getAttacks().add(this.allAtks[60]);
		this.allPkmn[i].getAttacks().add(this.allAtks[34]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[23]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[15]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[23]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[131]);
		this.allPkmn[i].getAttacks().add(this.allAtks[125]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[108]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[19]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[67]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[37]);
		this.allPkmn[i].getAttacks().add(this.allAtks[129]);
		this.allPkmn[i].getAttacks().add(this.allAtks[67]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[13]);
		this.allPkmn[i].getAttacks().add(this.allAtks[140]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[12]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[55]);
		this.allPkmn[i].getAttacks().add(this.allAtks[75]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[77]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[62]);
		this.allPkmn[i].getAttacks().add(this.allAtks[142]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[116]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[108]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[116]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[58]);
		this.allPkmn[i].getAttacks().add(this.allAtks[39]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[36]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[26]);
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[99]);
		this.allPkmn[i].getAttacks().add(this.allAtks[79]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[74]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[50]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[28]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[15]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[118]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[118]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);
		this.allPkmn[i].getAttacks().add(this.allAtks[138]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[153]);
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[56]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[19]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[97]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[52]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[77]);
		this.allPkmn[i].getAttacks().add(this.allAtks[7]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[155]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);
		this.allPkmn[i].getAttacks().add(this.allAtks[68]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[62]);
		this.allPkmn[i].getAttacks().add(this.allAtks[76]);
		this.allPkmn[i].getAttacks().add(this.allAtks[71]);
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[142]);
		this.allPkmn[i].getAttacks().add(this.allAtks[76]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[41]);
		this.allPkmn[i].getAttacks().add(this.allAtks[76]);
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[118]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[126]);
		this.allPkmn[i].getAttacks().add(this.allAtks[107]);
		this.allPkmn[i].getAttacks().add(this.allAtks[52]);
		this.allPkmn[i].getAttacks().add(this.allAtks[94]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[138]);
		this.allPkmn[i].getAttacks().add(this.allAtks[8]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[137]);
		this.allPkmn[i].getAttacks().add(this.allAtks[123]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[33]);
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[9]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[120]);
		this.allPkmn[i].getAttacks().add(this.allAtks[110]);
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[147]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[146]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[93]);
		this.allPkmn[i].getAttacks().add(this.allAtks[2]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[85]);
		this.allPkmn[i].getAttacks().add(this.allAtks[1]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[139]);
		this.allPkmn[i].getAttacks().add(this.allAtks[117]);
		this.allPkmn[i].getAttacks().add(this.allAtks[104]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[148]);
		this.allPkmn[i].getAttacks().add(this.allAtks[109]);
		this.allPkmn[i].getAttacks().add(this.allAtks[21]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[134]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[32]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[57]);
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[122]);
		this.allPkmn[i].getAttacks().add(this.allAtks[156]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[15]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[114]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[57]);
		this.allPkmn[i].getAttacks().add(this.allAtks[130]);
		this.allPkmn[i].getAttacks().add(this.allAtks[75]);
		this.allPkmn[i].getAttacks().add(this.allAtks[136]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[33]);
		this.allPkmn[i].getAttacks().add(this.allAtks[58]);
		this.allPkmn[i].getAttacks().add(this.allAtks[133]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[75]);
		this.allPkmn[i].getAttacks().add(this.allAtks[101]);
		this.allPkmn[i].getAttacks().add(this.allAtks[99]);
		this.allPkmn[i].getAttacks().add(this.allAtks[77]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[10]);
		this.allPkmn[i].getAttacks().add(this.allAtks[112]);
		this.allPkmn[i].getAttacks().add(this.allAtks[131]);
		this.allPkmn[i].getAttacks().add(this.allAtks[96]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[112]);
		this.allPkmn[i].getAttacks().add(this.allAtks[44]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[40]);
		this.allPkmn[i].getAttacks().add(this.allAtks[45]);
		this.allPkmn[i].getAttacks().add(this.allAtks[131]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[11]);
		this.allPkmn[i].getAttacks().add(this.allAtks[58]);
		this.allPkmn[i].getAttacks().add(this.allAtks[145]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[58]);
		this.allPkmn[i].getAttacks().add(this.allAtks[135]);
		this.allPkmn[i].getAttacks().add(this.allAtks[61]);
		this.allPkmn[i].getAttacks().add(this.allAtks[144]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[141]);
		this.allPkmn[i].getAttacks().add(this.allAtks[113]);
		this.allPkmn[i].getAttacks().add(this.allAtks[33]);
		this.allPkmn[i].getAttacks().add(this.allAtks[3]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[97]);
		this.allPkmn[i].getAttacks().add(this.allAtks[76]);
		this.allPkmn[i].getAttacks().add(this.allAtks[4]);i++;
		this.allPkmn[i].getAttacks().add(this.allAtks[91]);
		this.allPkmn[i].getAttacks().add(this.allAtks[76]);
		this.allPkmn[i].getAttacks().add(this.allAtks[77]);
		this.allPkmn[i].getAttacks().add(this.allAtks[31]);
		
		//Tracing that loading is OK
		win.logTrace("Attacks assigned.");
	}
	
	//Handles the random attack choic for convienient reason
	public Attack randomAttack(int i)
	{
		this.createTypes();
		this.createAttacks();
		return this.allAtks[i];
	}
}
