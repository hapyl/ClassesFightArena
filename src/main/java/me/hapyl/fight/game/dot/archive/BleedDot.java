package me.hapyl.fight.game.dot.archive;

import me.hapyl.fight.game.dot.Dot;
import me.hapyl.fight.game.entity.LivingGameEntity;

import javax.annotation.Nonnull;

public class BleedDot extends Dot {
    public BleedDot() {
        super("Bleed", "Take damage and slow.");

        setDamage(1);
        setPeriod(15);
    }

    @Override
    public void affect(@Nonnull LivingGameEntity entity) {

    }
}
