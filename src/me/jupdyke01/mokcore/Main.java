package me.jupdyke01.mokcore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.jupdyke01.mokcore.characters.CharacterManager;
import me.jupdyke01.mokcore.commands.character.CharacterCMD;
import me.jupdyke01.mokcore.commands.character.DescCMD;
import me.jupdyke01.mokcore.commands.character.NameCMD;
import me.jupdyke01.mokcore.commands.character.RaceCMD;
import me.jupdyke01.mokcore.commands.character.RacesCMD;
import me.jupdyke01.mokcore.commands.chat.GlobalCMD;
import me.jupdyke01.mokcore.commands.chat.MeCMD;
import me.jupdyke01.mokcore.commands.chat.ShoutCMD;
import me.jupdyke01.mokcore.commands.chat.ToggleGCMD;
import me.jupdyke01.mokcore.commands.chat.WhisperCMD;
import me.jupdyke01.mokcore.commands.item.LoreCMD;
import me.jupdyke01.mokcore.commands.item.RenameCMD;
import me.jupdyke01.mokcore.commands.professions.FarmCMD;
import me.jupdyke01.mokcore.commands.professions.FishCMD;
import me.jupdyke01.mokcore.commands.professions.MineCMD;
import me.jupdyke01.mokcore.commands.professions.ProfCMD;
import me.jupdyke01.mokcore.commands.professions.WoodCMD;
import me.jupdyke01.mokcore.commands.ranks.RankCMD;
import me.jupdyke01.mokcore.commands.stats.StatsCMD;
import me.jupdyke01.mokcore.commands.ticket.TicketCMD;
import me.jupdyke01.mokcore.commands.util.BookCMD;
import me.jupdyke01.mokcore.commands.util.CoinCMD;
import me.jupdyke01.mokcore.commands.util.OmnomCMD;
import me.jupdyke01.mokcore.commands.util.RollCMD;
import me.jupdyke01.mokcore.commands.util.SignCMD;
import me.jupdyke01.mokcore.commands.util.SitCMD;
import me.jupdyke01.mokcore.events.BlockBreak;
import me.jupdyke01.mokcore.events.ChatListener;
import me.jupdyke01.mokcore.events.Command;
import me.jupdyke01.mokcore.events.Crafting;
import me.jupdyke01.mokcore.events.Dismount;
import me.jupdyke01.mokcore.events.DropItem;
import me.jupdyke01.mokcore.events.FoodLoss;
import me.jupdyke01.mokcore.events.Interact;
import me.jupdyke01.mokcore.events.InventoryMove;
import me.jupdyke01.mokcore.events.PlayerJoin;
import me.jupdyke01.mokcore.events.PlayerLeave;
import me.jupdyke01.mokcore.events.Respawn;
import me.jupdyke01.mokcore.tickets.TicketManager;
import me.jupdyke01.mokcore.user.UserManager;

public class Main extends JavaPlugin {

	private ProtocolManager pm;
	private CharacterManager cm;
	private UserManager um;
	private TicketManager tm;
	private TimeControl tc;
	public ArrayList<Player> disabledGlobal = new ArrayList<>();
	//private HashMap<Player, BlockStateMeta> sleeping = new HashMap<>();

	public void onEnable() {
		saveDefaultConfig();

		pm = ProtocolLibrary.getProtocolManager();
		//new PacketListen(this);
		
		//getCommand("build").setExecutor(new BuildCMD(this));
		//getCommand("vanish").setExecutor(new VanishCMD(this));
				
		cm = new CharacterManager(this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);

		um = new UserManager(this);
		tm = new TicketManager(this);
		tc = new TimeControl();
		um.loadUsers();
		//um.setupPerms();
		cm.loadCharacters();
		tm.loadTickets();
		
		getCommand("character").setExecutor(new CharacterCMD(this));
		getCommand("toggleg").setExecutor(new ToggleGCMD(this));
		getCommand("ticket").setExecutor(new TicketCMD(this));
		getCommand("stats").setExecutor(new StatsCMD(this));
		getCommand("fish").setExecutor(new FishCMD(this));
		getCommand("mine").setExecutor(new MineCMD(this));
		getCommand("farm").setExecutor(new FarmCMD(this));
		getCommand("wood").setExecutor(new WoodCMD(this));
		getCommand("name").setExecutor(new NameCMD(this));
		getCommand("desc").setExecutor(new DescCMD(this));
		getCommand("race").setExecutor(new RaceCMD(this));
		getCommand("roll").setExecutor(new RollCMD(this));
		getCommand("w").setExecutor(new WhisperCMD(this));
		getCommand("rank").setExecutor(new RankCMD(this));
		getCommand("prof").setExecutor(new ProfCMD(this));
		getCommand("g").setExecutor(new GlobalCMD(this));
		getCommand("s").setExecutor(new ShoutCMD(this));
		getCommand("sit").setExecutor(new SitCMD(this));
		getCommand("me").setExecutor(new MeCMD(this));
		getCommand("rename").setExecutor(new RenameCMD());
		getCommand("omnom").setExecutor(new OmnomCMD());
		getCommand("races").setExecutor(new RacesCMD());
		getCommand("coin").setExecutor(new CoinCMD());
		getCommand("lore").setExecutor(new LoreCMD());
		getCommand("sign").setExecutor(new SignCMD());
		getCommand("book").setExecutor(new BookCMD());
		//getCommand("scan").setExecutor(new ScanCMD());
		//getCommand("lay").setExecutor(new LayCMD());

		Bukkit.getServer().getPluginManager().registerEvents(new InventoryMove(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeave(this), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DropItem(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Command(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Respawn(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FoodLoss(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Crafting(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Dismount(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Interact(), this);
		
		getCommand("stats").setExecutor(new StatsCMD(this));
		
		for (World world : Bukkit.getWorlds()) {
			world.setGameRuleValue("doDaylightCycle", "false");
			tc.runClock(world, this);
		}
		
		countdown();
	}

	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (um.getBuildMode().containsKey(um.getUser(p)))
				um.getUser(p).setBuildMode(false);
		}
		um.saveUsers();
		cm.saveCharacters();
		tm.saveTickets();
		
	}

	public TicketManager getTicketManager() {
		return tm;
	}

	public UserManager getUserManager() {
		return um;
	}

	public CharacterManager getCharacterManager() {
		return cm;
	}

	public ProtocolManager getProtocolManager() {
		return pm;
	}
	
	public void countdown() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				for (me.jupdyke01.mokcore.characters.Character character : cm.getCharacters()) {
					if (character.getSkillCooldown() > 0) {
						if (character.getSkillCooldown() > 5)
							character.setSkillCooldown(character.getSkillCooldown() - 5);
						else
							character.setSkillCooldown(0);
					}
				}
				countdown();
			}
		}, 100);
	}

}
