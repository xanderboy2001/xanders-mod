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
        ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> generateWallModels(generator, log, wall));
    }

    private void generateWallModels(BlockStateModelGenerator generator, Block log, Block wall) {
        TextureMap textureMap = new TextureMap().put(TextureKey.WALL, TextureMap.getId(log));

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
