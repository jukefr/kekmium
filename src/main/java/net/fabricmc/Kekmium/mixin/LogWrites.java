package net.fabricmc.Kekmium.mixin;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.StorageIoWorker;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(StorageIoWorker.class)
public class LogWrites {
	private static final Logger logger = LogManager.getLogger();

	@Inject(at = @At("RETURN"), method = "write", locals = LocalCapture.CAPTURE_FAILSOFT)
	private void preSave(ChunkPos pos, StorageIoWorker.Result result, CallbackInfo ci) {
		logger.log(Level.INFO, "[Kekmium] wrote chunk {}",  pos.toString());
	}
}
