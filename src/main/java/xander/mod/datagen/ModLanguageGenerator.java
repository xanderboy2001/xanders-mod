package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import xander.mod.ModBlocks;

public class ModLanguageGenerator extends FabricLanguageProvider {
  public ModLanguageGenerator(FabricPackOutput dataOutput,
      CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, "en_us", registryLookup);
  }

  @Override
  public void generateTranslations(HolderLookup.Provider registryLookup,
      TranslationBuilder translationBuilder) {
    ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
      String wallName = createReadableName(wall.getDescriptionId());
      translationBuilder.add(wall, wallName);
      translationBuilder.add(wall.asItem(), wallName);
    });
    ModBlocks.BLOCK_TO_STAIRS.forEach((block, stairs) -> {
      String stairsName = createReadableName(stairs.getDescriptionId());
      translationBuilder.add(stairs, stairsName);
      translationBuilder.add(stairs.asItem(), stairsName);
    });
    ModBlocks.BLOCK_TO_SLAB.forEach((block, slab) -> {
      String slabName = createReadableName(slab.getDescriptionId());
      translationBuilder.add(slab, slabName);
      translationBuilder.add(slab.asItem(), slabName);
    });
  }

  private String createReadableName(String translationKey) {
    String[] parts = translationKey.split("\\.");
    String name = parts[parts.length - 1].replace("_", " ");

    StringBuilder result = new StringBuilder();
    for (String word : name.split(" ")) {
      if (!word.isEmpty()) {
        result.append(Character.toUpperCase(word.charAt(0)))
            .append(word.substring(1))
            .append(" ");
      }
    }
    return result.toString().trim();
  }
}