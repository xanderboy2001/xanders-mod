package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import xander.mod.ModBlocks;

public class ModLootTableGenerator extends FabricBlockLootSubProvider {
  public ModLootTableGenerator(
      FabricPackOutput dataOutput,
      CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
      this.dropSelf(wall);
    });
    ModBlocks.BLOCK_TO_STAIRS.forEach((block, stairs) -> {
      this.dropSelf(stairs);
    });
    ModBlocks.BLOCK_TO_SLAB.forEach((block, slab) -> {
      this.add(slab, this.createSlabItemTable(slab));
    });
  }
}
