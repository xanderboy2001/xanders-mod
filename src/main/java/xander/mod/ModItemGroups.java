package xander.mod;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModItemGroups {
  public static void register() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
      addBuildingVariants(entries, false); // false = exclude wool
    });

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
      addColoredVariants(entries);
    });
  }

  private static void addBuildingVariants(FabricItemGroupEntries entries, boolean isColoredGroup) {
    // add variants in reverse order (Wall -> Slab -> Stairs)
    // because "addAfter" inserts immediately after the base block.
    // Desired [Base] -> [Stairs] -> [Slab] -> [Wall]

    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      if (!isWool(base)) {
        entries.addAfter(base, wall);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      if (!isWool(base)) {
        entries.addAfter(base, slab);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      if (!isWool(base)) {
        entries.addAfter(base, stairs);
      }
    });
  }

  public static void addColoredVariants(FabricItemGroupEntries entries) {
    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      if (isWool(base)) {
        entries.addAfter(base, wall);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      if (isWool(base)) {
        entries.addAfter(base, slab);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      if (isWool(base)) {
        entries.addAfter(base, stairs);
      }
    });
  }

  private static boolean isWool(Block block) {
    Identifier id = Registries.BLOCK.getId(block);

    return id.getPath().contains("wool");
  }
}
