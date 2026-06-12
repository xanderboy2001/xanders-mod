package xander.mod;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ModItemGroups {
  public static void register() {
    CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
      addBuildingVariants(entries, false); // false = exclude wool
    });

    CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
      addColoredVariants(entries);
    });
  }

  private static void addBuildingVariants(FabricCreativeModeTabOutput entries, boolean isColoredGroup) {
    // add variants in reverse order (Wall -> Slab -> Stairs)
    // because "insertAfter" inserts immediately after the base block.
    // Desired [Base] -> [Stairs] -> [Slab] -> [Wall]

    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      if (!ModBlocks.isWool(base) && !ModBlocks.isNatural(base)) {
        entries.insertAfter(base, wall);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      if (!ModBlocks.isWool(base) && !ModBlocks.isNatural(base)) {
        entries.insertAfter(base, slab);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      if (!ModBlocks.isWool(base) && !ModBlocks.isNatural(base)) {
        entries.insertAfter(base, stairs);
      }
    });
  }

  public static void addColoredVariants(FabricCreativeModeTabOutput entries) {
    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      if (ModBlocks.isWool(base)) {
        entries.insertAfter(base, wall);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      if (ModBlocks.isWool(base)) {
        entries.insertAfter(base, slab);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      if (ModBlocks.isWool(base)) {
        entries.insertAfter(base, stairs);
      }
    });
  }

  public static void addNaturalVariants(FabricCreativeModeTabOutput entries) {
    ModBlocks.BLOCK_TO_WALL.forEach((base, wall) -> {
      if (ModBlocks.isNatural(base)) {
        entries.insertAfter(base, wall);
      }
    });

    ModBlocks.BLOCK_TO_SLAB.forEach((base, slab) -> {
      if (ModBlocks.isNatural(base)) {
        entries.insertAfter(base, slab);
      }
    });

    ModBlocks.BLOCK_TO_STAIRS.forEach((base, stairs) -> {
      if (ModBlocks.isNatural(base)) {
        entries.insertAfter(base, stairs);
      }
    });
  }
}
