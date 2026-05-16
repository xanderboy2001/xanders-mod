package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.world.level.block.Block;
import xander.mod.ModBlocks;

public class ModTagGenerator extends FabricTagsProvider.BlockTagsProvider {
  public ModTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider registries) {
    TagBuilder wallTag = getOrCreateRawBuilder(BlockTags.WALLS);
    TagBuilder stairsTag = getOrCreateRawBuilder(BlockTags.STAIRS);
    TagBuilder slabTag = getOrCreateRawBuilder(BlockTags.SLABS);

    TagBuilder axeTag = getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_AXE);
    TagBuilder pickaxeTag = getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
    TagBuilder hoeTag = getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_HOE);

    TagBuilder logsThatBurnTag = getOrCreateRawBuilder(BlockTags.LOGS_THAT_BURN);
    TagBuilder woolTag = getOrCreateRawBuilder(BlockTags.WOOL);

    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      Identifier wallId = Identifier.fromNamespaceAndPath("xander", wall.getDescriptionId().replace("block.xander.", ""));
      wallTag.addElement(wallId);
      if (isLog(base)) {
        axeTag.addElement(wallId);
      logsThatBurnTag.addElement(wallId);
      } else {
        pickaxeTag.addElement(wallId);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      Identifier stairsId = Identifier.fromNamespaceAndPath("xander", stairs.getDescriptionId().replace("block.xander.", ""));
      stairsTag.addElement(stairsId);
      if (isWool(base)) {
        woolTag.addElement(stairsId);
        hoeTag.addElement(stairsId);
      } else if (isLog(base)) {
        axeTag.addElement(stairsId);
        logsThatBurnTag.addElement(stairsId);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      Identifier slabId = Identifier.fromNamespaceAndPath("xander", slab.getDescriptionId().replace("block.xander.", ""));
      slabTag.addElement(slabId);
      if (isWool(base)) {
        woolTag.addElement(slabId);
        hoeTag.addElement(slabId);
      } else if (isLog(base)) {
        axeTag.addElement(slabId);
        logsThatBurnTag.addElement(slabId);
      }
    });
  }

  private boolean isWool(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block).getPath().contains("wool");
  }

  private boolean isLog(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block).getPath().contains("log");
  }
}
