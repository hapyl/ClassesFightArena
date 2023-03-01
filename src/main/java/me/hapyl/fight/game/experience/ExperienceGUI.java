package me.hapyl.fight.game.experience;

import com.google.common.collect.Lists;
import me.hapyl.fight.Main;
import me.hapyl.fight.game.heroes.Heroes;
import me.hapyl.fight.game.reward.HeroUnlockReward;
import me.hapyl.fight.game.reward.Reward;
import me.hapyl.spigotutils.module.inventory.ItemBuilder;
import me.hapyl.spigotutils.module.inventory.gui.PlayerGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ExperienceGUI extends PlayerGUI {

    private final int[] slots;
    private final Experience experience;

    public ExperienceGUI(Player player) {
        super(player, "Experience", 6);

        this.experience = Main.getPlugin().getExperience();
        this.slots = new int[] {
                9, 10, 19, 28, 37, 46, 47, 48, 39, 30, 21, 12, 13, 14, 23, 32, 41, 50, 51, 52, 43, 34, 25, 16, 17
        };

        update(1);
    }

    public void update(int index) {
        clearEverything();

        // valida index is min 1 or throw error
        if (index < 1) {
            throw new IllegalArgumentException("Index must be greater than 0!");
        }

        // Add button for the previous page if index > 0
        if (index > 1) {
            setItem(1, ItemBuilder.of(Material.ARROW, "Previous Page").asIcon(), e -> update(index - slots.length));
        }

        // Add button for the next page if index < max experience level
        if (index + slots.length < experience.MAX_LEVEL) {
            setItem(7, ItemBuilder.of(Material.ARROW, "Next Page").asIcon(), e -> update(index + slots.length));
        }

        // put emerald icon in the middle
        // add a lore to the icon
        setItem(
                4,
                ItemBuilder.of(Material.EMERALD, "Experience").addSmartLore("&7Earn experience in game and unlock unique rewards!").asIcon()
        );

        for (int i = index; i < index + slots.length; i++) {
            final int slot = slots[i - index];
            final ExperienceLevel level = experience.getLevel(i);

            if (level == null) {
                break;
            }

            setItem(slot, createItem(level));
        }

        openInventory();
    }

    public ItemStack createItem(ExperienceLevel level) {
        final Player player = getPlayer();
        final ItemBuilder builder = new ItemBuilder(Material.PAPER);
        final boolean levelReached = level.getLevel() <= experience.getLevel(player);

        if (level.getLevel() == experience.getLevel(player) + 1) {
            builder.setType(Material.YELLOW_STAINED_GLASS_PANE);
        }
        else {
            if (levelReached) {
                builder.setType(Material.GREEN_STAINED_GLASS_PANE);
            }
            else {
                builder.setType(Material.RED_STAINED_GLASS_PANE);
            }
        }

        if (level.getLevel() == experience.getLevel(player) + 1) {
            builder.addLore();
            builder.addLore("&7Progress: &e" + experience.getProgressBar(player) + " &e" + experience.getExpScaled(player) + "&7/&a" +
                                    experience.getExpScaledNext(player));
        }

        builder.setAmount((int) level.getLevel());
        builder.setName("Level " + level.getLevel());
        builder.addLore();

        if (level.hasRewards()) {
            builder.addLore("&7Rewards: " + (levelReached ? "&a✔" : ""));

            final List<Reward> rewards = level.getRewards();
            final List<Heroes> heroesUnlock = Lists.newArrayList();

            for (Reward reward : rewards) {
                if (reward instanceof HeroUnlockReward heroUnlockReward) {
                    heroesUnlock.add(heroUnlockReward.getHero());
                }
                else {
                    builder.addLore("&7  - " + reward.getDisplay());
                }
            }

            // Iterate over heroesUnlock and concat their names into a string with commas replacing last hero with "and".
            // Then add either "hero" or "heroes" depending on the size of heroesUnlock.
            // Then add Reward.getDisplay() to the end of the string and add it to the lore.
            // Use .stream().map(Heroes::getName).collect(Collectors.joining(", ")) to get the string.
            // Replace last comma with "and" using .replaceFirst(",([^,]*)$", " and$1")
            // Use addSmartLore to add the string to the lore.
            // Color each name green and everything else gray.
            if (!heroesUnlock.isEmpty()) {
                builder.addSmartLore("&7- &6" + heroesUnlock.stream()
                        .map(Heroes::getName)
                        .collect(Collectors.joining("&7, &6"))
                        .replaceFirst(",([^,]*)$", " &7and$1") + " &7" + (heroesUnlock.size() == 1 ? "hero" : "heroes") + " &7" +
                                             rewards.get(rewards.size() - 1).getDisplay(), "  ");
            }


        }
        else {
            builder.addLore("&cNo rewards!");
        }

        return builder.build();
    }

}