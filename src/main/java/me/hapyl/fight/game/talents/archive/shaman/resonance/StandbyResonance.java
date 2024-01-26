package me.hapyl.fight.game.talents.archive.shaman.resonance;

import me.hapyl.fight.game.talents.archive.shaman.Totem;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class StandbyResonance extends TotemResonance {
    protected StandbyResonance() {
        super(Material.STONE, "Standby", "Does nothing.");

        setDisplayData(
                "{Passengers:[{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{}},transformation:[1.0000f,0.0000f,0.0000f,-0.7500f,0.0000f,1.0000f,0.0000f,-0.8750f,0.0000f,0.0000f,1.0000f,-0.7500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{east:\"true\"}},transformation:[1.0000f,0.0000f,0.0000f,-0.7500f,0.0000f,1.0000f,0.0000f,-0.8750f,0.0000f,0.0000f,1.0000f,-0.7500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{north:\"true\"}},transformation:[1.0000f,0.0000f,0.0000f,-0.7500f,0.0000f,1.0000f,0.0000f,-0.8750f,0.0000f,0.0000f,1.0000f,-0.2500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{west:\"true\"}},transformation:[1.0000f,0.0000f,0.0000f,-0.2500f,0.0000f,1.0000f,0.0000f,-0.8750f,0.0000f,0.0000f,1.0000f,-0.2500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{south:\"true\"}},transformation:[1.0000f,0.0000f,0.0000f,-0.2500f,0.0000f,1.0000f,0.0000f,-0.8750f,0.0000f,0.0000f,1.0000f,-0.7500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{east:\"true\"}},transformation:[-1.0000f,-0.0000f,0.0000f,0.7500f,0.0000f,-1.0000f,0.0000f,1.1250f,0.0000f,0.0000f,1.0000f,-0.7500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{north:\"true\"}},transformation:[-1.0000f,-0.0000f,0.0000f,0.7500f,0.0000f,-1.0000f,0.0000f,1.1250f,0.0000f,0.0000f,1.0000f,-0.2500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{west:\"true\"}},transformation:[-1.0000f,-0.0000f,0.0000f,0.2500f,0.0000f,-1.0000f,0.0000f,1.1250f,0.0000f,0.0000f,1.0000f,-0.2500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{south:\"true\"}},transformation:[-1.0000f,-0.0000f,0.0000f,0.2500f,0.0000f,-1.0000f,0.0000f,1.1250f,0.0000f,0.0000f,1.0000f,-0.7500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{}},transformation:[0.8125f,0.0000f,0.0000f,-0.1600f,0.0000f,0.8125f,0.0000f,0.6875f,0.0000f,0.0000f,0.8125f,-0.1600f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{}},transformation:[0.8125f,0.0000f,0.0000f,-0.6589f,0.0000f,0.8125f,0.0000f,0.5625f,0.0000f,0.0000f,0.8125f,-0.1600f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{}},transformation:[0.8125f,0.0000f,0.0000f,-0.6589f,0.0000f,0.8125f,0.0000f,0.8125f,0.0000f,0.0000f,0.8125f,-0.6568f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:spruce_fence\",Properties:{}},transformation:[0.8125f,0.0000f,0.0000f,-0.1540f,0.0000f,0.8125f,0.0000f,0.4375f,0.0000f,0.0000f,0.8125f,-0.6568f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_terracotta\",Properties:{}},transformation:[0.5000f,0.0000f,0.0000f,-0.2500f,0.0000f,1.6250f,0.0000f,-0.9375f,0.0000f,0.0000f,0.5000f,-0.2500f,0.0000f,0.0000f,0.0000f,1.0000f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_trapdoor\",Properties:{facing:\"east\",half:\"bottom\",open:\"false\"}},transformation:[1.0000f,0.0000f,0.0000f,-0.5000f,0.0000f,1.0000f,0.0000f,-1.0000f,0.0000f,0.0000f,1.0000f,-0.5000f,0.0000f,0.0000f,0.0000f,1.0000f]}]}"
        );
    }

    @Override
    public void resonate(@Nonnull Totem totem) {
    }
}
