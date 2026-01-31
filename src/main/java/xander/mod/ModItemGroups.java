package xander.mod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;

import java.util.Map;

public class ModItemGroups {
  public static void register() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
      for (Map.Entry<Block, Block> entry : ModBlocks.BLOCK_TO_WALL.entrySet()) {
        Block baseBlock = entry.getKey();
        Block modWall = entry.getValue();

        entries.addAfter(baseBlock, modWall);
      }
    });

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
      for (Map.Entry<Block, Block> entry : ModBlocks.BLOCK_TO_STAIRS.entrySet()) {
        Block baseWool = entry.getKey();
        Block modStair = entry.getValue();

        entries.addAfter(baseWool, modStair);
      }
    });
  }
}
