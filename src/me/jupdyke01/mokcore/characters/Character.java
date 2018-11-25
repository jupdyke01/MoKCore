package me.jupdyke01.mokcore.characters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import me.jupdyke01.mokcore.enums.Race;
import me.jupdyke01.mokcore.enums.Skill;
import me.jupdyke01.mokcore.enums.Town;
import me.jupdyke01.mokcore.session.Session;

public class Character {

	String pname;
	String name;
	String desc;
	Race race;
	ArrayList<Integer> stats;
	Town town;
	HashMap<Skill, HashMap<Integer, Integer>> skills;
	HashMap<String, ArrayList<Session>> sessions;
	int skillCooldown;
	long lastJoined;
	long lastLeft;
	
	public Character(String pname, String name, String desc, Race race, ArrayList<Integer> stats, Town town, HashMap<Skill, HashMap<Integer, Integer>> skills, int skillCooldown, long lastJoined, long lastLeft, HashMap<String, ArrayList<Session>> sessions) {
		this.pname = pname;
		this.name = name;
		this.desc = desc;
		this.race = race;
		this.stats = stats;
		this.town = town;
		this.skills = skills;
		this.skillCooldown = skillCooldown;
		this.lastJoined = lastJoined;
		this.lastLeft = lastLeft;
		this.sessions = sessions;
	}
	
	public void setSkillLevel(Skill skill, int level) {
		HashMap<Integer, Integer> exp = new HashMap<>();
		exp.put(level, 0);
		skills.put(skill, exp);
	}
	
	public int getSkillExperience(Skill skill) {
		if (skills.containsKey(skill))
			return skills.get(skill).get(skills.get(skill).keySet().iterator().next());
		return 0;
	}
	
	public int getSkillLevel(Skill skill) {
		if (skills.containsKey(skill))
			return skills.get(skill).keySet().iterator().next();
		return 0;
	}
	
	public void setSkillExperience(Skill skill, int exp) {
		if (skills.containsKey(skill)) {
			skills.get(skill).put(getSkillLevel(skill), exp);
		}
	}
	
	public int getExperienceRemaining(Skill skill) {
		ArrayList<Integer> exp = new ArrayList<>();
		for (int expValue : skill.exp)
			exp.add(expValue);
		return exp.get(getSkillLevel(skill));
	}
	
	public int getDies(Skill skill) {
		int level = getSkillLevel(skill);
		if (level < 8)
			return level + 1;
		return 10;
	}
	
	public String getPname() {
		return pname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}
	
	public ArrayList<Integer> getStats() {
		return stats;
	}

	public void setStats(ArrayList<Integer> stats) {
		this.stats = stats;
	}

	public void fillStats() {
		stats.clear();
		if (!race.equals(Race.Half_Elf) && !race.equals(Race.Unselected)) {
			stats.add(race.getStr());
			stats.add(race.getDex());
			stats.add(race.getCon());
			stats.add(race.getInt());
			stats.add(race.getWis());
			stats.add(race.getCha());
		}
	}
	
	public void fillStats(Race parent1, Race parent2) {
		stats.clear();
		if (race.equals(Race.Half_Elf)) {
			
			int str1 = parent1.getStr();
			int dex1 = parent1.getDex();
			int con1 = parent1.getCon();
			int int1 = parent1.getInt();
			int wis1 = parent1.getWis();
			int cha1 = parent1.getCha();
			
			int str2 = parent2.getStr();
			int dex2 = parent2.getDex();
			int con2 = parent2.getCon();
			int int2 = parent2.getInt();
			int wis2 = parent2.getWis();
			int cha2 = parent2.getCha();
			
			stats.add((str1+str2) / 2);
			stats.add((dex1+dex2) / 2);
			stats.add((con1+con2) / 2);
			stats.add((int1+int2) / 2);
			stats.add((wis1+wis2) / 2);
			stats.add((cha1+cha2) / 2);
		}
	}
	
	public Town getTown() {
		return town;
	}
	
	public void setTown(Town town) {
		this.town = town;
	}

	public HashMap<Skill, HashMap<Integer, Integer>> getSkills() {
		return skills;
	}

	public void setSkills(HashMap<Skill, HashMap<Integer, Integer>> skills) {
		this.skills = skills;
	}
	
	public int getStatModifier(int stat) {
		return (stats.get(stat) / 2) - 5;
	}

	public int getSkillCooldown() {
		return skillCooldown;
	}

	public void setSkillCooldown(int skillCooldown) {
		this.skillCooldown = skillCooldown;
	}

	public long getLastJoined() {
		return lastJoined;
	}

	public void setLastJoined(long lastJoined) {
		this.lastJoined = lastJoined;
	}

	public long getLastLeft() {
		return lastLeft;
	}

	public void setLastLeft(long lastLeft) {
		this.lastLeft = lastLeft;
	}

	public HashMap<String, ArrayList<Session>> getSessions() {
		return sessions;
	}

	public void setSessions(HashMap<String, ArrayList<Session>> sessions) {
		this.sessions = sessions;
	}
	
	public void addSession() {
		ArrayList<Session> sessionArray = new ArrayList<>();
		if (getSessions().containsKey(formatDate(getLastJoined()))) 
			sessionArray.addAll(getSessions().get(formatDate(getLastJoined())));
		sessionArray.add(new Session(getLastJoined(), getLastLeft()));
		sessions.put(formatDate(getLastJoined()), sessionArray);
	}
	
	public String formatDate(long d){
		LocalDateTime utcDate = Instant.ofEpochMilli(d).atZone(ZoneId.systemDefault()).toLocalDateTime();		
		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("MM-dd-yy");
		return simpleDateFormat.format(utcDate);
	}
}
