package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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
    TagBuilder axeTag = getTagBuilder(BlockTags.AXE_MINEABLE);
    TagBuilder logTag = getTagBuilder(BlockTags.LOGS_THAT_BURN);

    TagBuilder stairsTag = getTagBuilder(BlockTags.STAIRS);
    TagBuilder hoeMineableTag = getTagBuilder(BlockTags.HOE_MINEABLE);
    TagBuilder woolTag = getTagBuilder(BlockTags.WOOL);

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      Identifier stairsId = Identifier.of("xander", stairs.getTranslationKey().replace("block.xander.", ""));
      stairsTag.add(stairsId);
      hoeMineableTag.add(stairsId);
      woolTag.add(stairsId);
      
    });

    ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
      Identifier wallId = Identifier.of("xander", wall.getTranslationKey().replace("block.xander.", ""));
      wallTag.add(wallId);
      axeTag.add(wallId);
      logTag.add(wallId);
    });
  }
}
