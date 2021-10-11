package kz.hapyl.fight.game.talents.storage;

import kz.hapyl.fight.game.Response;
import kz.hapyl.fight.game.heroes.HeroHandle;
import kz.hapyl.fight.game.talents.Talent;
import kz.hapyl.fight.game.task.GameTask;
import kz.hapyl.fight.util.Utils;
import kz.hapyl.spigotutils.module.math.Geometry;
import kz.hapyl.spigotutils.module.math.gometry.Draw;
import kz.hapyl.spigotutils.module.math.gometry.Quality;
import kz.hapyl.spigotutils.module.player.PlayerLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Sound.BLOCK_HONEY_BLOCK_SLIDE;

public class SlowingAura extends Talent {
	public SlowingAura() {
		super("Slowing Aura", "Creates a slowness pool at your target block that slows anyone in range.", Material.BONE_MEAL);
		this.setCd(200);
	}

	@Override
	protected Response execute(Player player) {
		if (HeroHandle.DARK_MAGE.isUsingUltimate(player)) {
			return Response.error("Unable to use while in ultimate form!");
		}

		final Block targetBlock = player.getTargetBlockExact(20);

		if (targetBlock == null) {
			return Response.error("No valid block in sight!");
		}

		final Location location = targetBlock.getRelative(BlockFace.UP).getLocation();

		new GameTask() {
			private int tick = 10;

			@Override
			public void run() {
				if (tick-- <= 0) {
					this.cancel();
					return;
				}

				double radius = 4.0d;
				Geometry.drawCircle(location, radius, Quality.LOW, new Draw(Particle.SPELL) {
					@Override
					public void draw(Location location) {
						final World world = location.getWorld();
						if (world != null) {
							world.spawnParticle(
									this.getParticle(),
									location.getX(),
									location.getY(),
									location.getZ(),
									1, 0, 0, 0, null
							);
						}
					}
				});

				PlayerLib.playSound(location, BLOCK_HONEY_BLOCK_SLIDE, 0.0f);
				Utils.getPlayersInRange(location, radius).forEach(entity -> {
					PlayerLib.addEffect(entity, PotionEffectType.SLOW, 10, 3);
				});

			}
		}.runTaskTimer(0, 5);
		return Response.OK;
	}
}