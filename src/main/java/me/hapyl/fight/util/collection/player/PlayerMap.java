package me.hapyl.fight.util.collection.player;

import me.hapyl.fight.game.entity.GamePlayer;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public interface PlayerMap<V> extends Map<GamePlayer, V> {

    @Nonnull
    static <V> PlayerHashMap<V> newMap() {
        return new PlayerHashMap<>();
    }

    @Nonnull
    static <V> ConcurrentPlayerMap<V> newConcurrentMap() {
        return new ConcurrentPlayerMap<>();
    }

    @Nonnull
    static <V> LinkedPlayerMap<V> newLinkedMap() {
        return new LinkedPlayerMap<>();
    }

    default boolean removeIf(@Nonnull GamePlayer key, @Nonnull Function<V, Boolean> fn) {
        final V v = get(key);

        if (v != null && fn.apply(v)) {
            remove(key);
            return true;
        }

        return false;
    }

    default void removeAnd(@Nonnull GamePlayer key, @Nonnull Consumer<V> consumer) {
        final V value = remove(key);

        if (value != null) {
            consumer.accept(value);
        }
    }

    default void forEachAndClear(@Nonnull Consumer<V> consumer) {
        forEach((k, v) -> {
            consumer.accept(v);
        });
        clear();
    }

}
