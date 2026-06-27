package xander.mod.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xander.mod.ModBlocks;
import xander.mod.ModLayerBlock;

public class ModLootTableGenerator extends FabricBlockLootSubProvider {
  public ModLootTableGenerator(
      FabricPackOutput dataOutput,
      CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    ModBlocks.BLOCK_TO_WALL.forEach((log, wall) -> {
      this.dropSelf(wall);
    });
    ModBlocks.BLOCK_TO_STAIRS.forEach((block, stairs) -> {
      this.dropSelf(stairs);
    });
    ModBlocks.BLOCK_TO_SLAB.forEach((block, slab) -> {
      this.add(slab, this.createSlabItemTable(slab));
    });
    ModBlocks.BLOCK_TO_LAYER.forEach((block, layer) -> {
      this.add(layer, this.createLayerDropTable(layer));
    });
  }

  private LootTable.Builder createLayerDropTable(Block block) {
    LootItem.Builder<?> itemEntry = LootItem.lootTableItem(block);

    for (int i = 2; i <= 8; i++) {
      itemEntry = itemEntry.apply(
          SetItemCountFunction.setCount(ConstantValue.exactly((float) i))
              .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                  .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModLayerBlock.LAYERS, i))));
    }

    return LootTable.lootTable().withPool(
        LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(itemEntry));
  }
}
