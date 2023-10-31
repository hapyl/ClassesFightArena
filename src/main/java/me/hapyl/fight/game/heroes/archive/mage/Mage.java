package me.hapyl.fight.game.heroes.archive.mage;

import me.hapyl.fight.event.io.DamageInput;
import me.hapyl.fight.event.io.DamageOutput;
import me.hapyl.fight.game.entity.GamePlayer;
import me.hapyl.fight.game.entity.LivingGameEntity;
import me.hapyl.fight.game.heroes.Archetype;
import me.hapyl.fight.game.heroes.Hero;
import me.hapyl.fight.game.heroes.equipment.Equipment;
import me.hapyl.fight.game.talents.Talent;
import me.hapyl.fight.game.talents.Talents;
import me.hapyl.fight.game.talents.UltimateTalent;
import me.hapyl.fight.game.ui.UIComponent;
import me.hapyl.fight.util.collection.player.PlayerMap;
import me.hapyl.spigotutils.module.math.Numbers;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import javax.annotation.Nonnull;

public class Mage extends Hero implements UIComponent {

    private final int maxSoulsAmount = 26;

    public final MageSpell spellWyvernHeart = new WyvernHeartSpell();
    public final MageSpell spellDragonSkin = new DragonSkinSpell();

    private final PlayerMap<Integer> soulsCharge = PlayerMap.newMap();

    public Mage() {
        super("Mage");

        setArchetype(Archetype.MAGIC);

        setDescription("""
                Necromancer with the ability to absorb soul fragments upon hitting his foes to use them as fuel for his &e&lSoul &e&lEater&7.
                """);

        setItem("f41e6e4bcd2667bb284fb0dde361894840ea782efbfb717f6244e06b951c2b3f");

        final Equipment equipment = this.getEquipment();
        equipment.setChestPlate(82, 12, 135, TrimPattern.VEX, TrimMaterial.AMETHYST);
        equipment.setLeggings(82, 12, 135, TrimPattern.TIDE, TrimMaterial.AMETHYST);
        equipment.setBoots(Material.NETHERITE_BOOTS, TrimPattern.TIDE, TrimMaterial.AMETHYST);

        setWeapon(new MageWeapon(this));

        setUltimate(new UltimateTalent(
                "Magical Trainings",
                """
                        Retrieve two ancient spells and use one of them to your advantage!
                                                
                        %s
                        %s
                        Only one of the spells can be used at the same time, and you will &nnot&7 gain &b&l※ &7until spell is over.
                        """.formatted(spellWyvernHeart.getFormatted(), spellDragonSkin.getFormatted()),
                50
        ).setItem(Material.WRITABLE_BOOK).setCooldownSec(-1));
    }

    @Override
    public void useUltimate(@Nonnull GamePlayer player) {
        final PlayerInventory inventory = player.getInventory();
        setUsingUltimate(player, true);

        inventory.setItem(3, spellWyvernHeart.getSpellItem());
        inventory.setItem(5, spellDragonSkin.getSpellItem());
        inventory.setHeldItemSlot(4);
    }

    @Override
    public DamageOutput processDamageAsDamager(DamageInput input) {
        final LivingGameEntity victim = input.getEntity();
        final GamePlayer player = input.getDamagerAsPlayer();

        if (!victim.hasTag("LastDamage=Soul")) {
            addSouls(player, 1);
        }

        victim.removeTag("LastDamage=Soul");
        return null;
    }

    @Override
    public void onDeath(@Nonnull GamePlayer player) {
        soulsCharge.remove(player);
    }

    public void addSouls(GamePlayer player, int amount) {
        this.soulsCharge.put(player, Numbers.clamp(getSouls(player) + amount, 0, maxSoulsAmount));
    }

    public int getSouls(GamePlayer player) {
        return soulsCharge.getOrDefault(player, 0);
    }

    @Override
    public void onStop() {
        soulsCharge.clear();
    }

    @Override
    public Talent getFirstTalent() {
        return Talents.MAGE_TRANSMISSION.getTalent();
    }

    @Override
    public Talent getSecondTalent() {
        return Talents.ARCANE_MUTE.getTalent();
    }

    @Override
    public Talent getPassiveTalent() {
        return Talents.SOUL_HARVEST.getTalent();
    }

    @Override
    public @Nonnull String getString(@Nonnull GamePlayer player) {
        final int souls = getSouls(player);
        return "&e⦾ &l" + souls + (souls == maxSoulsAmount ? " FULL!" : "");
    }
}
