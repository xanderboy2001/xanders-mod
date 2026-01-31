package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import xander.mod.ModBlocks;

public class ModLanguageGenerator extends FabricLanguageProvider {
  public ModLanguageGenerator(FabricDataOutput dataOutput,
      CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, "en_us", registryLookup);
  }

  @Override
  public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup,
      TranslationBuilder translationBuilder) {
    ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
      String wallName = createReadableName(wall.getTranslationKey());
      translationBuilder.add(wall, wallName);
      translationBuilder.add(wall.asItem(), wallName);
    });
    ModBlocks.BLOCK_TO_STAIRS.forEach((block, stairs) -> {
      String stairsName = createReadableName(stairs.getTranslationKey());
      translationBuilder.add(stairs, stairsName);
      translationBuilder.add(stairs.asItem(), stairsName);
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