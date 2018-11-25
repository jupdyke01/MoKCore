package me.jupdyke01.mokcore.characters;

import java.util.ArrayList;
import java.util.HashMap;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Race;
import me.jupdyke01.mokcore.enums.Skill;
import me.jupdyke01.mokcore.enums.Town;
import me.jupdyke01.mokcore.session.Session;

public class CharacterManager {

	private Main main;
	private ArrayList<Character> characterSheets = new ArrayList<>();
	
	public CharacterManager(Main main) {
		this.main = main;
	}

	public void addCharacter(Character character) {
		characterSheets.add(character);
	}

	public void remCharacter(Character character) {
		characterSheets.remove(character);
	}

	public Character getCharacter(String pname) {
		for (Character character : characterSheets) {
			if (character.getPname().equals(pname))
				return character;
		}
		return null;
	}

	public ArrayList<Character> getCharacters() {
		return characterSheets;
	}

	public boolean hasCharacter(String pname) {
		if (getCharacter(pname) != null)  {
			return true;
		}
		return false;
	}

	public void loadCharacters() {
		if (main.getConfig().contains("characters")) {
			for (String str : main.getConfig().getConfigurationSection("characters").getKeys(false)) {
				String pname = str;
				String name = main.getConfig().getString("characters." + str + ".name");
				String desc = main.getConfig().getString("characters." + str + ".desc");
				Race race = Race.valueOf(main.getConfig().getString("characters." + str + ".race"));
				ArrayList<Integer> stats = new ArrayList<>();
				int cooldown = 0;
				long lastJoined = 0;
				long lastLeft = 0;
				HashMap<String, ArrayList<Session>> sessions = new HashMap<>();
				if (main.getConfig().contains("characters." + str + ".stats"))
				for (String str2 : main.getConfig().getConfigurationSection("characters." + str + ".stats").getKeys(false)) {
					stats.add(main.getConfig().getInt("characters." + str + ".stats." + str2));
				}
				Town town = Town.valueOf(main.getConfig().getString("characters." + str + ".town"));
				HashMap<Skill, HashMap<Integer, Integer>> skills = new HashMap<>();
				if (main.getConfig().contains("characters." + str + ".skills"))
					for (String skill : main.getConfig().getConfigurationSection("characters." + str + ".skills").getKeys(false)) {
						HashMap<Integer, Integer> level = new HashMap<>();
						level.put(main.getConfig().getInt("characters." + str + ".skills." + skill + ".level"), main.getConfig().getInt("characters." + str + ".skills." + skill + ".exp"));
						skills.put(Skill.valueOf(skill), level);
					}
				if (main.getConfig().contains("characters." + str + ".skillcooldown"))
					cooldown = main.getConfig().getInt("characters." + str + ".skillcooldown");
				if (main.getConfig().contains("characters." + str + ".lastJoined"))
					lastJoined = main.getConfig().getLong("characters." + str + ".lastJoined");
				if (main.getConfig().contains("characters." + str + ".lastLeft"))
					lastLeft = main.getConfig().getLong("characters." + str + ".lastLeft");
				if (main.getConfig().contains("characters." + str + ".sessions")) {
					ArrayList<Session> sessionArray = new ArrayList<>();
					for (String date : main.getConfig().getConfigurationSection("characters." + str + ".sessions").getKeys(false)) {
						for (String session : main.getConfig().getConfigurationSection("characters." + str + ".sessions." + date).getKeys(false)) {
							long startTime = Long.valueOf(session);
							long endTime = main.getConfig().getLong("characters." + str + ".sessions." + date + "." + session);
							sessionArray.add(new Session(startTime, endTime));
						}
						sessions.put(date, sessionArray);
					}
				}
				addCharacter(new Character(pname, name, desc, race, stats, town, skills, cooldown, lastJoined, lastLeft, sessions));
			}
		}
	}

	public void saveCharacters() {
		if (!getCharacters().isEmpty()) {
			for (Character c : getCharacters()) {
				main.getConfig().set("characters." + c.getPname() + ".name", c.getName());
				main.getConfig().set("characters." + c.getPname() + ".desc", c.getDesc());
				main.getConfig().set("characters." + c.getPname() + ".race", c.getRace().toString());
				main.getConfig().set("characters." + c.getPname() + ".stats.str", c.getStats().get(0));
				main.getConfig().set("characters." + c.getPname() + ".stats.dex", c.getStats().get(1));
				main.getConfig().set("characters." + c.getPname() + ".stats.con", c.getStats().get(2));
				main.getConfig().set("characters." + c.getPname() + ".stats.int", c.getStats().get(3));
				main.getConfig().set("characters." + c.getPname() + ".stats.wis", c.getStats().get(4));
				main.getConfig().set("characters." + c.getPname() + ".stats.cha", c.getStats().get(5));
				main.getConfig().set("characters." + c.getPname() + ".town", c.getTown().toString());
				for (Skill skill : c.getSkills().keySet()) {
					main.getConfig().set("characters." + c.getPname() + ".skills." + skill.toString() + ".level", c.getSkills().get(skill).keySet().iterator().next());
					main.getConfig().set("characters." + c.getPname() + ".skills." + skill.toString() + ".exp", c.getSkills().get(skill).get(c.getSkills().get(skill).keySet().iterator().next()));
				}
				main.getConfig().set("characters." + c.getPname() + ".skillcooldown", c.getSkillCooldown());
				main.getConfig().set("characters." + c.getPname() + ".lastJoined", c.getLastJoined());
				main.getConfig().set("characters." + c.getPname() + ".lastLeft", c.getLastLeft());
				if (!c.getSessions().isEmpty())
				for (String date : c.getSessions().keySet()) {
					for (Session session : c.getSessions().get(date)) {
						main.getConfig().set("characters." + c.getPname() + ".sessions." + date + "." + session.getStartTime(), session.getEndTime());
					}
				}
			}
			main.saveConfig();
		}
	}
}
