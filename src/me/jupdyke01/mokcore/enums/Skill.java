package me.jupdyke01.mokcore.enums;

public enum Skill {
	MINING(new int[] {0,24,81,192,648,1029,1536}), WOODCUTTING(new int[] {0,12,54,128,250,432,686,1024}), FARMING(new int[] {0,24,81,192,648,1029,1536}), FISHING(new int[] {0,12,54,128,250,432,686,1024});
	
	public int[] exp;
	
	Skill(int[] exp) {
		this.exp = exp;
	}
}
