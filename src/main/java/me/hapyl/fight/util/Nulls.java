package me.hapyl.fight.util;

import me.hapyl.fight.game.cosmetic.Cosmetics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public class Nulls {

    public static <N extends Number> short increment(@Nullable N number) {
        return (short) (number == null ? 1 : number.shortValue() + 1);
    }

    public static <N extends Number> short decrement(@Nullable N number) {
        return (short) (number == null ? -1 : number.shortValue() - 1);
    }

    public static <E, R> R getIfNotNull(@Nullable E e, @Nonnull Function<E, R> function, R def) {
        if (e == null) {
            return def;
        }

        return function.apply(e);
    }

    public static <E> void runIfNotNull(@Nullable E e, @Nonnull Consumer<E> function) {
        if (e != null) {
            function.accept(e);
        }
    }

    public static Cosmetics notNullOr(@Nullable Cosmetics selected, @Nonnull Cosmetics def) {
        if (selected == null) {
            return def;
        }

        return selected;
    }

    @Nullable
    public static <T, R> R getOrNull(@Nullable T t, @Nonnull Function<T, R> fn) {
        return t != null ? fn.apply(t) : null;
    }
}
