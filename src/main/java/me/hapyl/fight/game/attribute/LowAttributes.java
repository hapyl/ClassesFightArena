package me.hapyl.fight.game.attribute;

public class LowAttributes extends Attributes {

    public LowAttributes() {
        setHealth(20);
        setDefense(100);
        setAttack(100);
        setValue(AttributeType.CRIT_CHANCE, 0);
    }

}
