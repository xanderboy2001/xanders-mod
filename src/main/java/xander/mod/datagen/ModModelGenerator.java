package xander.mod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.util.Identifier;
import xander.mod.ModBlocks;

import java.util.Optional;

public class ModModelGenerator extends FabricModelProvider {

    public static final Model TEMPLATE_LOG_WALL_POST = new Model(
            Optional.of(Identifier.of("minecraft", "block/template_wall_post")),
            Optional.empty(),
            TextureKey.TOP, TextureKey.SIDE);

    public ModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
            if (base.getTranslationKey().contains("log") || base.getTranslationKey().contains("wood")
                    || base.getTranslationKey().contains("stem")) {
                generateLogWallModels(generator, base, wall);
            } else {
                generateStandardWallModels(generator, base, wall);
            }
        });
    }

    private void generateLogWallModels(BlockStateModelGenerator generator, Block log, Block wall) {
        TextureMap textureMap = new TextureMap()
                .put(TextureKey.SIDE, TextureMap.getId(log))
                .put(TextureKey.TOP, TextureMap.getSubId(log, "_top"));

        Identifier postModelId = TEMPLATE_LOG_WALL_POST.upload(wall, "_post", textureMap, generator.modelCollector);

        TextureMap sideTextureMap = new TextureMap().put(TextureKey.WALL, TextureMap.getId(log));
        Identifier sideModelId = Models.TEMPLATE_WALL_SIDE.upload(wall, sideTextureMap, generator.modelCollector);
        Identifier tallModelId = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, sideTextureMap, generator.modelCollector);

        generator.blockStateCollector
                .accept(BlockStateModelGenerator.createWallBlockState(
                        wall,
                        BlockStateModelGenerator.createWeightedVariant(postModelId),
                        BlockStateModelGenerator.createWeightedVariant(sideModelId),
                        BlockStateModelGenerator.createWeightedVariant(tallModelId)));

        generator.registerParentedItemModel(wall,
                Models.WALL_INVENTORY.upload(wall, sideTextureMap, generator.modelCollector));
    }

    private void generateStandardWallModels(BlockStateModelGenerator generator, Block base, Block wall) {
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
