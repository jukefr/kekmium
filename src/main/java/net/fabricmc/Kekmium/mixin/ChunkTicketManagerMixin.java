package net.fabricmc.Kekmium.mixin;

import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketManagerMixin {
	private static final Logger logger = LogManager.getLogger();

	private static long lastTime = System.currentTimeMillis();

	@Inject(at = @At("HEAD"), method = "tick")
	private void tick(ThreadedAnvilChunkStorage chunkStorage, CallbackInfoReturnable<Boolean> cir) {
		int testing = chunkStorage.ticketManager.ticketsByPosition.size();
		if (testing > 0 && (System.currentTimeMillis() - lastTime > 1000)) {
			lastTime = System.currentTimeMillis();
			logger.log(Level.INFO, "[Kekmium] current ticket count is {}",  testing);
		}
	}
}
