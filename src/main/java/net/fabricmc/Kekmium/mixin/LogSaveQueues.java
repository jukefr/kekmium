package net.fabricmc.Kekmium.mixin;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThreadedAnvilChunkStorage.class)
public class LogSaveQueues {
	private static final Logger logger = LogManager.getLogger();

	@Inject(at = @At("RETURN"), method = "save(Lnet/minecraft/world/chunk/Chunk;)Z")
	private void postSave(Chunk chunk, CallbackInfoReturnable<Boolean> cir) {
		logger.log(Level.INFO, "[Kekmium] queued for saving chunk {}",  chunk.getPos().toString());

	}
}
