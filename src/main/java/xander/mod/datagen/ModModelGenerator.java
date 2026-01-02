package xander.mod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import xander.mod.ModBlocks;

public class ModModelGenerator extends FabricModelProvider {
    public ModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        ModBlocks.LOG_TO_WALL.forEach((log, wall) -> {
            TextureMap textureMap = TextureMap.all(log);

            Models.TEMPLATE_WALL_POST.upload(wall, textureMap, generator.modelCollector);
            Models.TEMPLATE_WALL_SIDE.upload(wall, textureMap, generator.modelCollector);
            Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, textureMap, generator.modelCollector);
            Models.WALL_INVENTORY.upload(wall, textureMap, generator.modelCollector);

            generator.registerParentedItemModel(wall, Models.WALL_INVENTORY.upload(wall, textureMap, generator.modelCollector));
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {}
}
