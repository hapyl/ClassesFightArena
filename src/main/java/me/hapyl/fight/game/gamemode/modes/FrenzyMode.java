package me.hapyl.fight.game.gamemode.modes;

import com.google.common.collect.Maps;
import me.hapyl.fight.CF;
import me.hapyl.fight.game.GameInstance;
import me.hapyl.fight.game.entity.GamePlayer;
import me.hapyl.fight.game.gamemode.CFGameMode;
import me.hapyl.fight.util.collection.LinkedValue2IntegerReverseMap;
import me.hapyl.spigotutils.module.scoreboard.Scoreboarder;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;

public class FrenzyMode extends CFGameMode {

    private final int maxLives = 9;
    private final Map<GamePlayer, Integer> playerLivesMap;

    public FrenzyMode() {
        super("Frenzy", 1200);

        setDescription("""
                A free for all with limited lives!
                                
                &aMax Lives: &b%s
                """.formatted(maxLives));

        setMaterial(Material.RED_DYE);
        setPlayerRequirements(2);
        setAllowRespawn(true);
        setRespawnTime(30);

        playerLivesMap = Maps.newHashMap();
    }

    @Override
    public boolean shouldRespawn(@Nonnull GamePlayer gamePlayer) {
        return playerLivesMap.containsKey(gamePlayer);
    }

    @Override
    public void formatScoreboard(Scoreboarder builder, GameInstance instance, GamePlayer player) {
        final int playerLives = playerLivesMap.getOrDefault(player, -1);
        final LinkedValue2IntegerReverseMap<GamePlayer> reverse = LinkedValue2IntegerReverseMap.of(playerLivesMap);

        builder.addLines("");
        builder.addLine("&6Frenzy: &8%s ❤", playerLives);

        reverse.forEach(3, (index, gamePlayer, live) -> {
            if (gamePlayer == null) {
                builder.addLine(" &e#" + (index + 1) + " ...");
            }
            else {
                builder.addLine(gamePlayer.formatTeamNameScoreboardPosition(index + 1, " &c" + live + "❤"));
            }
        });
    }

    @Override
    public void onStart(@Nonnull GameInstance instance) {
        playerLivesMap.clear();

        CF.getPlayers().forEach(player -> playerLivesMap.put(player, maxLives));
    }

    @Override
    public void onDeath(@Nonnull GameInstance instance, @Nonnull GamePlayer player) {
        int lives = playerLivesMap.compute(player, (pl, value) -> Objects.requireNonNullElse(value, maxLives) - 1);

        switch (lives) {
            case 0 -> {
                playerLivesMap.remove(player);
                player.sendMessage("&7[&4☠&7] &4It was nice knowing you.");
            }
            case 1 -> {
                player.sendMessage("&7[&4☠&7] &cThis is your final life, don't waste it!");
            }
            default -> {
                player.sendMessage("&7[&4☠&7] &a%s lives remaining!", lives);
            }
        }
    }

    @Override
    public boolean onStop(@Nonnull GameInstance instance) {
        for (GamePlayer gamePlayer : playerLivesMap.keySet()) {
            instance.getGameResult().getWinners().add(gamePlayer);
            return true;
        }

        return true;
    }

    @Override
    public boolean testWinCondition(@Nonnull GameInstance instance) {
        return playerLivesMap.size() == 1;
    }
}
