package me.hapyl.fight.game.dot;

import me.hapyl.fight.game.entity.LivingGameEntity;
import me.hapyl.fight.util.Ticking;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DotInstance implements Ticking {

    private final LivingGameEntity entity;
    private final LivingGameEntity applier;
    private final long startedAt;

    private int tick;

    public DotInstance(@Nonnull LivingGameEntity entity, @Nullable LivingGameEntity applier, int tick) {
        this.entity = entity;
        this.applier = applier;
        this.startedAt = System.currentTimeMillis();
        this.tick = tick;
    }

    @Nonnull
    public LivingGameEntity getEntity() {
        return entity;
    }

    @Nullable
    public LivingGameEntity getApplier() {
        return applier;
    }

    public long getStartedAt() {
        return startedAt;
    }

    @Override
    public void tick() {
        if (isDone()) {
            return;
        }

        tick--;
    }

    public int getTicksLeft() {
        return tick;
    }

    public boolean isDone() {
        return tick <= 0 || entity.isDead();
    }

}
