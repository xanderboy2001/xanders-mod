package xander.mod;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
  public static final Map<Block, Block> BLOCK_TO_WALL = new HashMap<>();
  public static final Map<Block, Block> BLOCK_TO_STAIRS = new HashMap<>();

  public static Block registerWall(String name, Block baseBlock, MapColor color) {
    Identifier id = Identifier.of("xander", name + "_wall");
    RegistryKey<Block> blockKey = RegistryKey.of(Registries.BLOCK.getKey(), id);
    RegistryKey<Item> itemKey = RegistryKey.of(Registries.ITEM.getKey(), id);

    BlockSoundGroup soundGroup = baseBlock.getDefaultState().getSoundGroup();

    Block wall = new ModWallBlock(
      AbstractBlock.Settings
        .copy(baseBlock)
        .mapColor(color)
        .registryKey(blockKey)
        .strength(baseBlock.getHardness(), baseBlock.getBlastResistance())
        .sounds(soundGroup)
      );
    Registry.register(Registries.BLOCK, id, wall);

    Registry.register(
      Registries.ITEM,
      itemKey,
      new BlockItem(wall, new Item.Settings().registryKey(itemKey)));

    BLOCK_TO_WALL.put(baseBlock, wall);
    return wall;
  }

  public static Block registerStairs(String name, Block baseBlock, MapColor color) {
    Identifier id = Identifier.of("xander", name + "_stair");
    RegistryKey<Block> blockKey = RegistryKey.of(Registries.BLOCK.getKey(), id);
    RegistryKey<Item> itemKey = RegistryKey.of(Registries.ITEM.getKey(), id);

    BlockSoundGroup soundGroup = baseBlock.getDefaultState().getSoundGroup();

    Block stairs = new ModStairsBlock(
      baseBlock.getDefaultState(),
      AbstractBlock.Settings
        .copy(baseBlock)
        .mapColor(color)
        .registryKey(blockKey)
        .strength(baseBlock.getHardness(), baseBlock.getBlastResistance())
        .sounds(soundGroup)
    );
    Registry.register(Registries.BLOCK, id, stairs);

    Registry.register(
      Registries.ITEM,
      itemKey,
      new BlockItem(stairs, new Item.Settings().registryKey(itemKey))
    );

    BLOCK_TO_STAIRS.put(baseBlock, stairs);

    return stairs;
  }

  public static void registerWalls() {
    // Logs
    Block oakWall = registerWall("oak", Blocks.OAK_LOG, MapColor.OAK_TAN);
    Block spruceWall = registerWall("spruce", Blocks.SPRUCE_LOG, MapColor.SPRUCE_BROWN);
    Block birchWall = registerWall("birch", Blocks.BIRCH_LOG, MapColor.PALE_YELLOW);
    Block jungleWall = registerWall("jungle", Blocks.JUNGLE_LOG, MapColor.DIRT_BROWN);
    Block acaciaWall = registerWall("acacia", Blocks.ACACIA_LOG, MapColor.ORANGE);
    Block darkOakWall = registerWall("dark_oak", Blocks.DARK_OAK_LOG, MapColor.BROWN);
    Block mangroveWall = registerWall("mangrove", Blocks.MANGROVE_LOG, MapColor.RED);
    Block cherryWall = registerWall("cherry", Blocks.CHERRY_LOG, MapColor.TERRACOTTA_WHITE);
    Block paleOakWall = registerWall("pale_oak", Blocks.PALE_OAK_LOG, MapColor.LIGHT_GRAY);

    // Stripped Logs
    Block strippedOakWall = registerWall("stripped_oak", Blocks.STRIPPED_OAK_LOG, MapColor.OAK_TAN);
    Block strippedSpruceWall = registerWall("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG, MapColor.SPRUCE_BROWN);
    Block strippedBirchWall = registerWall("stripped_birch", Blocks.STRIPPED_BIRCH_LOG, MapColor.PALE_YELLOW);
    Block strippedJungleWall = registerWall("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG, MapColor.DIRT_BROWN);
    Block strippedAcaciaWall = registerWall("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG, MapColor.ORANGE);
    Block strippedDarkOakWall = registerWall("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG, MapColor.BROWN);
    Block strippedMangroveWall = registerWall("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG, MapColor.RED);
    Block strippedCherryWall = registerWall("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG, MapColor.TERRACOTTA_WHITE);
    Block strippedPaleOakWall = registerWall("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG, MapColor.LIGHT_GRAY);

    // Link logs to stripped logs
    ModWallBlock.STRIPPABLES.put(oakWall, strippedOakWall);
    ModWallBlock.STRIPPABLES.put(spruceWall, strippedSpruceWall);
    ModWallBlock.STRIPPABLES.put(birchWall, strippedBirchWall);
    ModWallBlock.STRIPPABLES.put(jungleWall, strippedJungleWall);
    ModWallBlock.STRIPPABLES.put(acaciaWall, strippedAcaciaWall);
    ModWallBlock.STRIPPABLES.put(darkOakWall, strippedDarkOakWall);
    ModWallBlock.STRIPPABLES.put(mangroveWall, strippedMangroveWall);
    ModWallBlock.STRIPPABLES.put(cherryWall, strippedCherryWall);
    ModWallBlock.STRIPPABLES.put(paleOakWall, strippedPaleOakWall);

    // Stone
    registerWall("stone", Blocks.STONE, MapColor.STONE_GRAY);
  }

  public static void registerWoolStairs() {
    registerStairs("white_wool", Blocks.WHITE_WOOL, MapColor.WHITE);
    registerStairs("orange_wool", Blocks.ORANGE_WOOL, MapColor.ORANGE);
    registerStairs("magenta_wool", Blocks.MAGENTA_WOOL, MapColor.MAGENTA);
    registerStairs("light_blue_wool", Blocks.LIGHT_BLUE_WOOL, MapColor.LIGHT_BLUE);
    registerStairs("yellow_wool", Blocks.YELLOW_WOOL, MapColor.YELLOW);
    registerStairs("lime_wool", Blocks.LIME_WOOL, MapColor.LIME);
    registerStairs("pink_wool", Blocks.PINK_WOOL, MapColor.PINK);
    registerStairs("gray_wool", Blocks.GRAY_WOOL, MapColor.GRAY);
    registerStairs("light_gray_wool", Blocks.LIGHT_GRAY_WOOL, MapColor.LIGHT_GRAY);
    registerStairs("cyan_wool", Blocks.CYAN_WOOL, MapColor.CYAN);
    registerStairs("purple_wool", Blocks.PURPLE_WOOL, MapColor.PURPLE);
    registerStairs("blue_wool", Blocks.BLUE_WOOL, MapColor.BLUE);
    registerStairs("brown_wool", Blocks.BROWN_WOOL, MapColor.BROWN);
    registerStairs("green_wool", Blocks.GREEN_WOOL, MapColor.GREEN);
    registerStairs("red_wool", Blocks.RED_WOOL, MapColor.RED);
    registerStairs("black_wool", Blocks.BLACK_WOOL, MapColor.BLACK);
  }
}
