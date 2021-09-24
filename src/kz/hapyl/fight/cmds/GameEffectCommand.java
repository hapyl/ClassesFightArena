package kz.hapyl.fight.cmds;

import kz.hapyl.fight.game.GamePlayer;
import kz.hapyl.fight.game.Manager;
import kz.hapyl.fight.game.effect.GameEffectType;
import kz.hapyl.spigotutils.module.chat.Chat;
import kz.hapyl.spigotutils.module.command.SimplePlayerAdminCommand;
import kz.hapyl.spigotutils.module.util.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GameEffectCommand extends SimplePlayerAdminCommand {
	public GameEffectCommand(String name) {
		super(name);
		this.setUsage("ge (Effect) (stop/int)");
		this.setAliases("ge");
	}

	@Override
	protected void execute(Player player, String[] args) {
		// ge (Effect) (stop/ticks)
		if (args.length == 2) {

			if (!Manager.current().isGameInProgress()) {
				Chat.sendMessage(player, "&cThis command is only available during the game!");
				return;
			}

			final GameEffectType type = Validate.getEnumValue(GameEffectType.class, args[0]);
			final int ticks = args[1].equalsIgnoreCase("stop") ? -1 : Validate.getInt(args[1]);

			final GamePlayer gamePlayer = GamePlayer.getPlayer(player);
			if (type == null || gamePlayer == null) {
				Chat.sendMessage(player, "&cInvalid effect type.");
				return;
			}

			if (ticks <= -1) {
				if (gamePlayer.hasEffect(type)) {
					Chat.sendMessage(player, "&cYou don't have %s effect applied!", type.getGameEffect().getName());
					return;
				}
				gamePlayer.removeEffect(type);
				Chat.sendMessage(player, "&aStopped %s effect!", type.getGameEffect().getName());
				return;
			}

			gamePlayer.addEffect(type, ticks);
			Chat.sendMessage(player, "&aApplied %s effect for %st!", type.getGameEffect().getName(), ticks);

			return;
		}
		sendInvalidUsageMessage(player);
	}

	@Override
	protected List<String> tabComplete(CommandSender sender, String[] args) {
		if (args.length == 1) {
			return completerSort(arrayToList(GameEffectType.values()), args);
		}
		return null;
	}
}
