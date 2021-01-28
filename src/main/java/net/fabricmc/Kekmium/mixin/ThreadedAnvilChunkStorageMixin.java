package net.fabricmc.Kekmium.mixin;

import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageMixin {
	private static final Logger logger = LogManager.getLogger();
	private static long lastTime = System.currentTimeMillis();
	List<String> done = new ArrayList<>();

	@Inject(at = @At("RETURN"), method = "save(Lnet/minecraft/world/chunk/Chunk;)Z")
	private void save(Chunk chunk, CallbackInfoReturnable<Boolean> cir) {
		if ( (System.currentTimeMillis() - lastTime > 1000)) {
			lastTime = System.currentTimeMillis();
			if(!done.contains(chunk.getPos().toString()))
				done.add(chunk.getPos().toString());
			logger.log(Level.INFO, "[Kekmium] {} chunks queued for save in the last second", done.size());
			done.clear();
		} else {
			if(!done.contains(chunk.getPos().toString()))
				done.add(chunk.getPos().toString());
		}
	}
}
