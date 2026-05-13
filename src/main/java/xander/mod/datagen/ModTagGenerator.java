package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.util.Identifier;
import xander.mod.ModBlocks;

public class ModTagGenerator extends FabricTagProvider.BlockTagProvider {
  public ModTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries) {
    TagBuilder wallTag = getTagBuilder(BlockTags.WALLS);
    TagBuilder stairsTag = getTagBuilder(BlockTags.STAIRS);
    TagBuilder slabTag = getTagBuilder(BlockTags.SLABS);

    TagBuilder axeTag = getTagBuilder(BlockTags.AXE_MINEABLE);
    TagBuilder pickaxeTag = getTagBuilder(BlockTags.PICKAXE_MINEABLE);
    TagBuilder hoeTag = getTagBuilder(BlockTags.HOE_MINEABLE);

    TagBuilder logsThatBurnTag = getTagBuilder(BlockTags.LOGS_THAT_BURN);
    TagBuilder woolTag = getTagBuilder(BlockTags.WOOL);

    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      Identifier wallId = Identifier.of("xander", wall.getTranslationKey().replace("block.xander.", ""));
      wallTag.add(wallId);
      if (isLog(base)) {
        axeTag.add(wallId);
      logsThatBurnTag.add(wallId);
      } else {
        pickaxeTag.add(wallId);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      Identifier stairsId = Identifier.of("xander", stairs.getTranslationKey().replace("block.xander.", ""));
      stairsTag.add(stairsId);
      if (isWool(base)) {
        woolTag.add(stairsId);
        hoeTag.add(stairsId);
      } else if (isLog(base)) {
        axeTag.add(stairsId);
        logsThatBurnTag.add(stairsId);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      Identifier slabId = Identifier.of("xander", slab.getTranslationKey().replace("block.xander.", ""));
      slabTag.add(slabId);
      if (isWool(base)) {
        woolTag.add(slabId);
        hoeTag.add(slabId);
      } else if (isLog(base)) {
        axeTag.add(slabId);
        logsThatBurnTag.add(slabId);
      }
    });
  }

  private boolean isWool(Block block) {
    return Registries.BLOCK.getId(block).getPath().contains("wool");
  }

  private boolean isLog(Block block) {
    return Registries.BLOCK.getId(block).getPath().contains("log");
  }
}
