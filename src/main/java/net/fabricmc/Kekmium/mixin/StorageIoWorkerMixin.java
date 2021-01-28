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

import java.util.ArrayList;
import java.util.List;

@Mixin(StorageIoWorker.class)
public class StorageIoWorkerMixin {
	private static final Logger logger = LogManager.getLogger();
	private static long lastTime = System.currentTimeMillis();
	List<String> done = new ArrayList<>();

	@Inject(at = @At("RETURN"), method = "write")
	private void write(ChunkPos pos, StorageIoWorker.Result result, CallbackInfo ci) {
		if ( (System.currentTimeMillis() - lastTime > 1000)) {
			lastTime = System.currentTimeMillis();
			if(!done.contains(pos.toString()))
				done.add(pos.toString());
			logger.log(Level.INFO, "[Kekmium] {} chunks written in the last second", done.size());
			done.clear();
		} else {
			if(!done.contains(pos.toString()))
				done.add(pos.toString());
		}
	}
}
