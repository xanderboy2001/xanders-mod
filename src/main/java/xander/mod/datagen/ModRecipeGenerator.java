package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import xander.mod.ModBlocks;

public class ModRecipeGenerator extends FabricRecipeProvider {
  public ModRecipeGenerator(FabricPackOutput output,
      CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected RecipeProvider createRecipeProvider(
      HolderLookup.Provider registries,
      RecipeOutput exporter) {
    return new RecipeProvider(registries, exporter) {
      @Override
      public void buildRecipes() {
        ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
          wall(RecipeCategory.BUILDING_BLOCKS, wall, log);
          stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, wall, log);
        });

        ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
          stairBuilder(stairs, Ingredient.of(base))
              .unlockedBy(getHasName(base), has(base))
              .save(output);

          stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stairs, base);
        });

        ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
          slab(RecipeCategory.BUILDING_BLOCKS, slab, base);
          stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, slab, base);
        });

        ModBlocks.BLOCK_TO_LAYER.forEach((base, layer) -> {
          shaped(RecipeCategory.BUILDING_BLOCKS, layer, 6)
            .pattern("###")
            .define('#', base)
            .unlockedBy(getHasName(base), has(base))
            .save(output);
          stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, layer, base, 8);
        });
      }
    };
  }

  @Override
  public String getName() {
    return "Xander's Mod Recipes";
  }
}
