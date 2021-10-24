package kz.hapyl.fight.game.effect.storage.extra;

import kz.hapyl.spigotutils.module.util.Holder;
import org.bukkit.entity.Player;

public class LockdownData extends Holder<Player> {

	private final boolean flying;
	private final boolean allowedFlight;
	private final float flightSpeed;

	public LockdownData(Player player) {
		super(player);
		flying = player.isFlying();
		allowedFlight = player.getAllowFlight();
		flightSpeed = player.getFlySpeed();
	}

	public void applyData(Player player) {
		if (player != this.get()) {
			return;
		}

		player.setAllowFlight(allowedFlight);
		player.setFlying(flying);
		player.setFlySpeed(flightSpeed);
	}
}
