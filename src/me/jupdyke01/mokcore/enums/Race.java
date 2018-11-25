package me.jupdyke01.mokcore.enums;

public enum Race {
	Human(12,10,12,10,9,10),
	Dragonborn(13,10,14,10,11,12),
	Dwarf(14,8,12,14,10,10),
	Orc(14,11,12,8,7,14),
	Dark_Elf(14,12,13,9,8,11),
	Wood_Elf(8,14,9,12,12,10),
	High_Elf(8,8,10,14,14,13),
	Blood_Elf(7,7,9,13,15,12),
	Snow_Elf(13,13,13,9,10,8),
	Half_Elf(),
	Unselected();
	
	private int str;
	private int dex;
	private int con;
	private int Int;
	private int wis;
	private int cha;
	
	Race(int str, int dex, int con, int Int, int wis, int cha) {
		this.str = str;
		this.dex = dex;
		this.con = con;
		this.Int = Int;
		this.wis = wis;
		this.cha = cha;
	}
	Race() {
		
	}
	
	public int getStr() {
		return str;
	}
	public int getDex() {
		return dex;
	}
	public int getCon() {
		return con;
	}
	public int getInt() {
		return Int;
	}
	public int getWis() {
		return wis;
	}
	public int getCha() {
		return cha;
	}
	
}
