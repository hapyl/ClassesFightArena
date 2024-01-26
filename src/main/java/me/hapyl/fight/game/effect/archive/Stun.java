package me.hapyl.fight.game.effect.archive;

import me.hapyl.fight.event.custom.GameDamageEvent;
import me.hapyl.fight.game.effect.EffectParticle;
import me.hapyl.fight.game.effect.Effect;
import me.hapyl.fight.game.effect.EffectType;
import me.hapyl.fight.game.effect.Effects;
import me.hapyl.fight.game.entity.LivingGameEntity;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public class Stun extends Effect implements Listener {

    public Stun() {
        super("Stun", EffectType.NEGATIVE);

        setDescription("""
                Stunned players cannot move or use their abilities.
                The effect will be cleared upon taking damage.
                """);

        setTalentBlocking(true);
        setEffectParticle(new EffectParticle(Particle.VILLAGER_ANGRY, 1));
    }

    @EventHandler()
    public void handleGameDamageEvent(GameDamageEvent ev) {
        final LivingGameEntity entity = ev.getEntity();

        if (entity.hasEffect(Effects.STUN)) {
            entity.removeEffect(Effects.STUN);
        }
    }

    @Override
    public void onTick(@Nonnull LivingGameEntity entity, int tick) {
        if (tick % 10 == 0) {
            displayParticles(entity.getEyeLocation().add(0, 0.5, 0), entity);
        }
    }

    @Override
    public void onStart(@Nonnull LivingGameEntity entity, int amplifier) {
        entity.getMetadata().canMove.setValue(false);

        // Fx
        entity.playWorldSound(Sound.BLOCK_ANVIL_LAND, 1.25f);
        entity.sendTitle("&7&lsᴛᴜɴɴᴇᴅ", null, 5, 1000000, 5);
    }

    @Override
    public void onStop(@Nonnull LivingGameEntity entity, int amplifier) {
        entity.getMetadata().canMove.setValue(true);
        entity.clearTitle();
    }
}
