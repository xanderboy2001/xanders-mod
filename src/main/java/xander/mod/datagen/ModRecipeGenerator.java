package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import xander.mod.ModBlocks;

public class ModRecipeGenerator extends FabricRecipeProvider {
  public ModRecipeGenerator(FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected RecipeGenerator getRecipeGenerator(
      RegistryWrapper.WrapperLookup registries,
      RecipeExporter exporter) {
    return new RecipeGenerator(registries, exporter) {
      @Override
      public void generate() {
        ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
          offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall, log);
          offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, wall, log);
        });

        ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
          createStairsRecipe(stairs, Ingredient.ofItem(base))
              .criterion(hasItem(base), conditionsFromItem(base))
              .offerTo(exporter);

          offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, stairs, base);
        });
      }
    };
  }

  @Override
  public String getName() {
    return "Xander's Mod Recipes";
  }
}
