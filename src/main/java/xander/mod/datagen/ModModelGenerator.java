package xander.mod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.util.Identifier;
import xander.mod.ModBlocks;

public class ModModelGenerator extends FabricModelProvider {

  public ModModelGenerator(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {
    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> generateWallModels(generator, base, wall));
    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> generateStairsModels(generator, base, stairs));
  }

  private void generateStairsModels(BlockStateModelGenerator generator, Block base, Block stairs) {
    Identifier textureId = TextureMap.getId(base);

    TextureMap textures = new TextureMap()
        .put(TextureKey.BOTTOM, textureId)
        .put(TextureKey.TOP, textureId)
        .put(TextureKey.SIDE, textureId);

    Identifier innerModel = Models.INNER_STAIRS.upload(stairs, textures, generator.modelCollector);
    Identifier straightModel = Models.STAIRS.upload(stairs, textures, generator.modelCollector);
    Identifier outerModel = Models.OUTER_STAIRS.upload(stairs, textures, generator.modelCollector);

    generator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(
        stairs,
        BlockStateModelGenerator.createWeightedVariant(
            innerModel),
        BlockStateModelGenerator.createWeightedVariant(
            straightModel),
        BlockStateModelGenerator.createWeightedVariant(
            outerModel)));

    generator.registerParentedItemModel(stairs, straightModel);
  }

  private void generateWallModels(BlockStateModelGenerator generator, Block base, Block wall) {
    TextureMap textureMap = new TextureMap().put(TextureKey.WALL, TextureMap.getId(base));

    Identifier postModelId = Models.TEMPLATE_WALL_POST.upload(wall, textureMap, generator.modelCollector);
    Identifier sideModelId = Models.TEMPLATE_WALL_SIDE.upload(wall, textureMap, generator.modelCollector);
    Identifier tallModelId = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, textureMap, generator.modelCollector);

    generator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(
        wall,
        BlockStateModelGenerator.createWeightedVariant(postModelId),
        BlockStateModelGenerator.createWeightedVariant(sideModelId),
        BlockStateModelGenerator.createWeightedVariant(tallModelId)));

    generator.registerParentedItemModel(wall,
        Models.WALL_INVENTORY.upload(wall, textureMap, generator.modelCollector));
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {
  }
}
