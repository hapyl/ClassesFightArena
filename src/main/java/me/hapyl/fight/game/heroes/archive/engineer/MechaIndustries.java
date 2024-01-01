package me.hapyl.fight.game.heroes.archive.engineer;

import me.hapyl.fight.CF;
import me.hapyl.fight.game.attribute.Attributes;
import me.hapyl.fight.game.effect.GameEffectType;
import me.hapyl.fight.game.entity.GamePlayer;
import me.hapyl.fight.game.entity.LivingGameEntity;
import me.hapyl.fight.game.talents.Removable;
import me.hapyl.fight.game.task.TimedGameTask;
import me.hapyl.spigotutils.module.entity.Entities;
import me.hapyl.spigotutils.module.util.BukkitUtils;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class MechaIndustries extends TimedGameTask implements Removable {

    private final GamePlayer player;
    private final Engineer engineer;
    private final LivingGameEntity golem;

    public MechaIndustries(GamePlayer player, Engineer engineer) {
        super(engineer.getUltimate());

        this.player = player;
        this.engineer = engineer;
        this.golem = CF.createEntity(player.getLocation(), Entities.IRON_GOLEM, self -> {
            self.setPlayerCreated(true);
            self.setAI(false);

            final Attributes attributes = new Attributes();
            attributes.setHealth(195);
            attributes.setDefense(200);

            player.hideEntity(self);

            return new LivingGameEntity(self, attributes);
        });

        player.addEffect(GameEffectType.INVISIBILITY, 100000, true);

        // Fx
        player.spawnWorldParticle(Particle.EXPLOSION_HUGE, 1);

        runTaskTimer(0, 1);
    }

    @Override
    public void remove() {
        cancel();

        golem.remove();
        player.removeEffect(GameEffectType.INVISIBILITY);

        // Fx
        player.playWorldSound(Sound.ENTITY_IRON_GOLEM_DEATH, 0.75f);
        player.spawnWorldParticle(Particle.EXPLOSION_HUGE, 1);
    }

    @Override
    public void run(int tick) {
        if (golem.isDead()) {
            engineer.getPlayerData(player).removeMechaIndustries();
            return;
        }

        final int noDamageTicks = golem.getNoDamageTicks();
        final int maximumNoDamageTicks = golem.getMaximumNoDamageTicks();

        if (noDamageTicks <= maximumNoDamageTicks / 2 && golem.isInWater()) {
            golem.damage(engineer.ultimateInWaterDamage);
        }

        golem.teleport(player);

        // Fx
        player.spawnWorldParticle(Particle.VILLAGER_ANGRY, 1, 0.8, 0.8, 0.8, 0);
    }

    @Override
    public String toString() {
        return "&f&l🤖 " + golem.getHealthFormatted() + " &b" + BukkitUtils.decimalFormat((maxTick - getTick()) / 20.0d) + "s";
    }

    public void swing() {
        //Packets.Server.animation(golem.getNMSEntity(), NPCAnimation.SWING_MAIN_HAND);
        golem.getEntity().playEffect(EntityEffect.IRON_GOLEN_ATTACK);
    }
}
