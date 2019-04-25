package com.pkmn;

import java.util.ArrayList;

/* 
 * This class contains our favorite little monsters. They have a name, an Id (basically number in Pok�dex), at least one type (the second may be null), attacks
 * (at least 1, max 4). Every stat is covered by "base" and "current" since attacks may occur that alter stats. Accuracy is 100 by default but can be lowered
 * by sand attack for example. 
 * Status 0 : Normal (no status)
 * Status 1 : Paralysis
 * Status 2 : Sleep
 * Status 3 : Poison
 * Status 4 : Burn
 * Status 5 : Frozen
 * Status 6 : Confused
 * Status 7 : In the air
 * Status 8 : Underground
 * Status 9 : Dead
 * Status 10 : afraid
 */

public class Pokemon 
{
	String name;
	int id;
	int level = 50; //Since it's battling only, all Pokémon will be level 50. Useful only for damage calculation.
	Type type1;
	Type type2;
	ArrayList <Attack> attacks;
	int status = 0;
	int baseHp;
	int currentHp;
	int baseAtk;
	int currentAtk;
	int stageAtk = 0;
	int baseDef;
	int currentDef;
	int stageDef = 0;
	int baseSpd;
	int currentSpd;
	int stageSpd = 0;
	int baseSpe;
	int currentSpe;
	int stageSpe = 0;
	int baseAccu = 100;
	int currentAccu = 100;
	int stageAccu = 0;
	int baseEvasion= 100;
	int currentEvasion = 100;
	int stageEvasion = 0;
	int countSleep = 0;
	int countConfusion = 0;
	int countDot = 0; //Count Damage Over Time
	
	
	
	//Empty constructor only for the fake index 0 Pokémon
	public Pokemon()
	{
		this.setName("MissingNo");
		this.setId(0);
	}
	//Constructor to instantiate a pokémon through the GameData class. Since current stats are the same as base's initially and status is always 0 at the beginning, those variables are not in the constructor.
	//Also Accuracy is 100 by default as stated in the descriptor.
	public Pokemon(String name, int id, Type type1, Type type2, int baseHp, int baseAtk,  int baseDef,  int baseSpd, int baseSpe)
	{
		this.setName(name);
		this.setId(id);
		this.setType1(type1);
		this.setType2(type2);
		this.attacks = new ArrayList<Attack>(); // We will add attacks after the Pokémon is created
		this.setBaseHp(baseHp);
		this.setCurrentHp(baseHp);
		this.setBaseAtk(baseAtk);
		this.setCurrentAtk(baseAtk);
		this.setBaseDef(baseDef);
		this.setCurrentDef(baseDef);
		this.setBaseSpd(baseSpd);
		this.setCurrentSpd(baseSpd);
		this.setBaseSpe(baseSpe);
		this.setCurrentSpe(baseSpe);
	}
	
	//Getters/Setters
	public String getName() 
	{
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	private void setLevel(int level) {
		this.level = level;
	}

	public Type getType1() {
		return type1;
	}

	private void setType1(Type type1) {
		this.type1 = type1;
	}

	public Type getType2() {
		return type2;
	}

	private void setType2(Type type2) {
		this.type2 = type2;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	private void setAttacks(ArrayList<Attack> attacks) {
		this.attacks = attacks;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBaseHp() {
		return baseHp;
	}

	private void setBaseHp(int baseHp) {
		this.baseHp = baseHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) 
	{
		if (currentHp > 0)
			this.currentHp = currentHp;
		else
		{
			this.currentHp = 0;
			this.setStatus(9);
		}
	}

	public int getBaseAtk() 
	{
		return baseAtk;
	}

	private void setBaseAtk(int baseAtk) 
	{
		this.baseAtk = baseAtk;
	}

	public int getCurrentAtk() 
	{
		return currentAtk;
	}

	public void setCurrentAtk(int currentAtk) 
	{
		this.currentAtk = currentAtk;
	}

	public int getBaseDef() 
	{
		return baseDef;
	}

	private void setBaseDef(int baseDef) 
	{
		this.baseDef = baseDef;
	}

	public int getCurrentDef() 
	{
		return currentDef;
	}

	public void setCurrentDef(int currentDef) 
	{
		this.currentDef = currentDef;
	}

	public int getBaseSpd() 
	{
		return baseSpd;
	}

	private void setBaseSpd(int baseSpd) 
	{
		this.baseSpd = baseSpd;
	}

	public int getCurrentSpd() 
	{
		return currentSpd;
	}

	public void setCurrentSpd(int currentSpd) 
	{
		this.currentSpd = currentSpd;
	}

	public int getBaseSpe() 
	{
		return baseSpe;
	}

	private void setBaseSpe(int baseSpe) 
	{
		this.baseSpe = baseSpe;
	}

	public int getCurrentSpe() 
	{
		return currentSpe;
	}

	public void setCurrentSpe(int currentSpe) 
	{
		this.currentSpe = currentSpe;
	}

	public int getBaseAccu() 
	{
		return baseAccu;
	}

	private void setBaseAccu(int baseAccu) 
	{
		this.baseAccu = baseAccu;
	}

	public int getCurrentAccu() 
	{
		return currentAccu;
	}

	public void setCurrentAccu(int currentAccu) 
	{
		this.currentAccu = currentAccu;
	}
	
	public int getCountConfusion() 
	{
		return countConfusion;
	}
	
	public void setCountConfusion(int countConfusion) 
	{
		this.countConfusion = countConfusion;
	}	
	
	public int getCountSleep() 
	{
		return countSleep;
	}
	
	public void setCountSleep(int countStatus) 
	{
		this.countSleep = countStatus;
	}
	
	public int getCountDot() 
	{
		return countDot;
	}
	
	public void setCountDot(int countDot) 
	{
		this.countDot = countDot;
	}
	
	public int getStageAtk() 
	{
		return stageAtk;
	}
	public void setStageAtk(int stageAtk) 
	{
		if (stageAtk > 6)
			this.stageAtk = 6;
		else if (stageAtk < - 6)
			this.stageAtk = -6;
		else
			this.stageAtk = stageAtk;
	}
	
	public int getStageDef() 
	{
		return stageDef;
	}
	
	public void setStageDef(int stageDef) 
	{
		if (stageDef > 6)
			this.stageDef = 6;
		else if (stageDef < - 6)
			this.stageDef = -6;
		else
			this.stageDef = stageDef;
	}
	
	public int getStageSpd() 
	{
		return stageSpd;
	}
	
	public void setStageSpd(int stageSpd) 
	{
		if (stageSpd > 6)
			this.stageSpd = 6;
		else if (stageSpd < - 6)
			this.stageSpd = -6;
		else
			this.stageSpd = stageSpd;
	}
	
	public int getStageSpe() 
	{
		return stageSpe;
	}
	
	public void setStageSpe(int stageSpe) 
	{
		if (stageSpe > 6)
			this.stageSpe = 6;
		else if (stageSpe < - 6)
			this.stageSpe = -6;
		else
			this.stageSpe = stageSpe;
	}
	
	public int getStageAccu() 
	{
		return stageAccu;
	}
	
	public void setStageAccu(int stageAccu) 
	{
		if (stageAccu > 6)
			this.stageAccu = 6;
		else if (stageAccu < - 6)
			this.stageAccu = -6;
		else
			this.stageAccu = stageAccu;
	}
	
	public int getBaseEvasion() 
	{
		return baseEvasion;
	}
	
	public void setBaseEvasion(int baseEvasion) 
	{
		this.baseEvasion = baseEvasion;
	}
	
	public int getCurrentEvasion() 
	{
		return currentEvasion;
	}
	
	public void setCurrentEvasion(int currentEvasion) 
	{
		this.currentEvasion = currentEvasion;
	}
	
	public int getStageEvasion() 
	{
		return stageEvasion;
	}
	
	public void setStageEvasion(int stageEvasion) 
	{
		if (stageEvasion > 6)
			this.stageEvasion = 6;
		else if (stageEvasion < - 6)
			this.stageEvasion = -6;
		else
			this.stageEvasion = stageEvasion;
	}
	
}
