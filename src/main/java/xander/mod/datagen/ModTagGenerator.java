package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import xander.mod.ModBlocks;
import xander.mod.ModBlocks.BlockCategory;

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
    TagBuilder shovelTag = getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_SHOVEL);

    TagBuilder logsThatBurnTag = getOrCreateRawBuilder(BlockTags.LOGS_THAT_BURN);
    TagBuilder woolTag = getOrCreateRawBuilder(BlockTags.WOOL);

    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      Identifier wallId = Identifier.fromNamespaceAndPath("xander", wall.getDescriptionId().replace("block.xander.", ""));
      wallTag.addElement(wallId);
      switch (ModBlocks.categoryOf(base)) {
        case WOOL -> {
          woolTag.addElement(wallId);
          hoeTag.addElement(wallId);
        }
        case LOG -> {
          axeTag.addElement(wallId);
          logsThatBurnTag.addElement(wallId);
        }
        case PLANK -> axeTag.addElement(wallId);
        case NATURAL -> shovelTag.addElement(wallId);
        default -> pickaxeTag.addElement(wallId);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      Identifier stairsId = Identifier.fromNamespaceAndPath("xander", stairs.getDescriptionId().replace("block.xander.", ""));
      stairsTag.addElement(stairsId);
      switch (ModBlocks.categoryOf(base)) {
        case WOOL -> {
          woolTag.addElement(stairsId);
          hoeTag.addElement(stairsId);
        }
        case LOG -> {
          axeTag.addElement(stairsId);
          logsThatBurnTag.addElement(stairsId);
        }
        case NATURAL -> {
          shovelTag.addElement(stairsId);
        }
        case PLANK -> axeTag.addElement(stairsId);
        default -> {}
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      Identifier slabId = Identifier.fromNamespaceAndPath("xander", slab.getDescriptionId().replace("block.xander.", ""));
      slabTag.addElement(slabId);
      switch (ModBlocks.categoryOf(base)) {
        case WOOL -> {
          woolTag.addElement(slabId);
          hoeTag.addElement(slabId);
        }
        case LOG -> {
          axeTag.addElement(slabId);
          logsThatBurnTag.addElement(slabId);
        }
        case NATURAL -> shovelTag.addElement(slabId);
        case PLANK -> axeTag.addElement(slabId);
        default-> {}
      }
    });
  }
}
