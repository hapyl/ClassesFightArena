package kz.hapyl.fight;

import kz.hapyl.fight.cmds.*;
import kz.hapyl.fight.event.PlayerEvent;
import kz.hapyl.fight.game.ChatController;
import kz.hapyl.fight.game.Manager;
import kz.hapyl.fight.game.database.Database;
import kz.hapyl.fight.game.scoreboard.GamePlayerUI;
import kz.hapyl.fight.game.scoreboard.ScoreList;
import kz.hapyl.fight.game.task.TaskList;
import kz.hapyl.spigotutils.module.command.CommandProcessor;
import kz.hapyl.spigotutils.module.command.SimplePlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin {

	private static Main plugin;

	private Manager manager;
	private TaskList taskList;
	private ScoreList scoreList;

	@Override
	public void onEnable() {
		plugin = this;
		regCommands();
		regEvents();

		for (final World world : Bukkit.getWorlds()) {
			world.setGameRule(GameRule.NATURAL_REGENERATION, false);
		}

		this.manager = new Manager();
		this.taskList = new TaskList();
		this.scoreList = new ScoreList();

		// update database
		for (final Player player : Bukkit.getOnlinePlayers()) {
			handlePlayer(player);
		}

	}

	public Manager getManager() {
		return manager;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public ScoreList getScoreList() {
		return scoreList;
	}

	public void handlePlayer(Player player) {
		Database.getDatabase(player); // this will create database again (load)
		this.manager.loadLastHero(player);
		new GamePlayerUI(player);
	}

	@Override
	public void onDisable() {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			Database.getDatabase(player).saveToFile();
		}

		if (this.manager.isGameInProgress()) {
			this.manager.stopCurrentGame();
		}
	}

	private void regCommands() {
		final CommandProcessor processor = new CommandProcessor();
		processor.registerCommand(new HeroCommand("hero"));
		processor.registerCommand(new GameCommand("cf"));
		processor.registerCommand(new ReportCommandCommand("report"));
		processor.registerCommand(new UltimateCommand("ultimate"));
		processor.registerCommand(new ParticleCommand("part"));
		processor.registerCommand(new GameEffectCommand("gameeffect"));

		// these are small shortcuts not feeling creating a class D:
		processor.registerCommand(new SimplePlayerCommand("start") {
			@Override
			protected void execute(Player player, String[] strings) {
				player.performCommand("cf start");
			}
		});

		processor.registerCommand(new SimplePlayerCommand("stop") {
			@Override
			protected void execute(Player player, String[] args) {
				// true -> stop server, false -> stop game instance
				final boolean type = args.length == 1 && (args[0].equalsIgnoreCase("server") || args[0].equalsIgnoreCase("s"));
				player.performCommand(type ? "minecraft:stop" : "cf stop");
			}

			@Override
			protected List<String> tabComplete(CommandSender sender, String[] args) {
				return super.completerSort(Arrays.asList("game", "server"), args);
			}
		});

	}

	private void regEvents() {
		final PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new PlayerEvent(), this);
		pm.registerEvents(new ChatController(), this);
	}

	private void addCommand(String cmd, CommandExecutor exec) {
		this.getCommand(cmd).setExecutor(exec);
	}

	public void addEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	private void addCommand(String cmd, CommandExecutor exec, boolean includeTabCompleter) {
		this.getCommand(cmd).setExecutor(exec);
		if (includeTabCompleter)
			this.getCommand(cmd).setTabCompleter((TabCompleter)exec);
	}

	public void registerEvent(Listener listener) {
		addEvent(listener);
	}

	public static Main getPlugin() {
		return plugin;
	}

}
