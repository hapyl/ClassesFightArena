package me.hapyl.fight.command;

import me.hapyl.fight.database.PlayerDatabase;
import me.hapyl.fight.database.rank.PlayerRank;
import me.hapyl.fight.ux.Notifier;
import me.hapyl.spigotutils.module.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand extends SimpleCommand { // Don't make this CFCommand

    private final static String ARGUMENT_SET_ADMIN = "-ConfirmMakeStaff";
    private final static PlayerRank MIN_RANK = PlayerRank.ADMIN;

    public RankCommand(String name) {
        super(name);

        setUsage("rank (player) [rank]");
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        final PlayerRank rank = PlayerRank.getRank(sender);

        if (!rank.isOrHigher(MIN_RANK)) {
            Notifier.Error.NOT_PERMISSIONS_NEED_RANK.send(sender, MIN_RANK.getPrefixWithFallback());
            return;
        }

        if (args.length == 0) {
            Notifier.success(sender, "Your rank is {}!", rank.getPrefixWithFallback());
            return;
        }

        final Player target = getArgument(args, 0).toPlayer();
        final PlayerRank rankToSet = getArgument(args, 1).toEnum(PlayerRank.class);
        final boolean confirmedStaff = getArgument(args, 2).toString().equals(ARGUMENT_SET_ADMIN);

        if (target == null) {
            Notifier.error(sender, "%s is not online!", args[0]);
            return;
        }

        final PlayerDatabase targetDatabase = PlayerDatabase.getDatabase(target);

        if (rankToSet == null) {
            final PlayerRank playerRank = targetDatabase.getRank();

            Notifier.info(sender, "{}'s rank is {}.", target.getName(), playerRank.getPrefixWithFallback());
            return;
        }

        if (rankToSet.isStaff() && !confirmedStaff) {
            Notifier.error(sender, "Not making &e{} staff without &e{} argument!", target.getName(), ARGUMENT_SET_ADMIN);
            return;
        }

        final PlayerRank oldRank = targetDatabase.getRank();
        targetDatabase.setRank(rankToSet);

        Notifier.success(sender, "Set &a{}'s rank to {}!", target.getName(), rankToSet.getPrefixWithFallback());
        Notifier.success(target, "You are now {}!", rankToSet.getPrefixWithFallback());

        Notifier.broadcastStaff(
                "{} changed {} rank '{}' » '{}'.",
                sender.getName(),
                target.getName(),
                oldRank.getPrefixWithFallback(),
                rankToSet.getPrefixWithFallback()
        );
    }

}
