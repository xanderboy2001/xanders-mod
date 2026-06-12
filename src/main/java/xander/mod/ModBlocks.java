package xander.mod;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
  /**
   * Category of a base block. Used to drive tag generation, creative tab routing,
   * and other consumers without resorting to string matching on block IDs.
   */
  public enum BlockCategory {
    WOOL, // mineable with hoe, COLORED_BLOCKS tab
    LOG, // mineable with axe, BUILDING_BLOCKS tab, strippable
    PLANK, // mineable with axe, BUILDING_BLOCKS tab
    TERRACOTTA, // mineable with pickaxe, COLORED_BLOCKS tab
    CONCRETE, // mineable with pickaxe, COLORED_BLOCKS tab
    NATURAL, // mineable with shovel, NATURAL_BLOCKS tab
    STONE // mineable with pickaxe, BUILDING_BLOCKS tab
  }

  public static final Map<Block, Block> BLOCK_TO_WALL = new HashMap<>();
  public static final Map<Block, Block> BLOCK_TO_STAIRS = new HashMap<>();
  public static final Map<Block, Block> BLOCK_TO_SLAB = new HashMap<>();

  /** Maps each registered base block to its BlockCategory. */
  public static final Map<Block, BlockCategory> CATEGORY = new HashMap<>();

  /**
   * Looks up the category of a base block, defaulting to STONE if unregistered.
   */
  public static BlockCategory categoryOf(Block block) {
    return CATEGORY.getOrDefault(block, BlockCategory.STONE);
  }

  public static boolean isWool(Block b) {
    return categoryOf(b) == BlockCategory.WOOL;
  }

  public static boolean isLog(Block b) {
    return categoryOf(b) == BlockCategory.LOG;
  }

  public static boolean isPlank(Block b) {
    return categoryOf(b) == BlockCategory.PLANK;
  }

  public static boolean isTerracotta(Block b) {
    return categoryOf(b) == BlockCategory.TERRACOTTA;
  }

  public static boolean isConcrete(Block b) {
    return categoryOf(b) == BlockCategory.CONCRETE;
  }

  public static boolean isNatural(Block b) {
    return categoryOf(b) == BlockCategory.NATURAL;
  }

  public static boolean isStone(Block b) {
    return categoryOf(b) == BlockCategory.STONE;
  }

  public static Block registerWall(String name, Block baseBlock, BlockCategory category) {
    Identifier id = Identifier.fromNamespaceAndPath("xander", name + "_wall");
    ResourceKey<Block> blockKey = ResourceKey.create(BuiltInRegistries.BLOCK.key(), id);
    ResourceKey<Item> itemKey = ResourceKey.create(BuiltInRegistries.ITEM.key(), id);

    SoundType soundGroup = baseBlock.defaultBlockState().getSoundType();

    MapColor mapColor = baseBlock.defaultMapColor();

    Block wall = new ModWallBlock(
        BlockBehaviour.Properties
            .ofFullCopy(baseBlock)
            .mapColor(mapColor)
            .setId(blockKey)
            .strength(baseBlock.defaultDestroyTime(), baseBlock.getExplosionResistance())
            .sound(soundGroup));
    Registry.register(BuiltInRegistries.BLOCK, id, wall);

    Registry.register(
        BuiltInRegistries.ITEM,
        itemKey,
        new BlockItem(wall, new Item.Properties().setId(itemKey)));

    BLOCK_TO_WALL.put(baseBlock, wall);
    CATEGORY.putIfAbsent(baseBlock, category);
    return wall;
  }

  public static Block registerStairs(String name, Block baseBlock, BlockCategory category) {
    Identifier id = Identifier.fromNamespaceAndPath("xander", name + "_stairs");
    ResourceKey<Block> blockKey = ResourceKey.create(BuiltInRegistries.BLOCK.key(), id);
    ResourceKey<Item> itemKey = ResourceKey.create(BuiltInRegistries.ITEM.key(), id);

    SoundType soundGroup = baseBlock.defaultBlockState().getSoundType();

    MapColor mapColor = baseBlock.defaultMapColor();

    Block stairs = new ModStairsBlock(
        baseBlock.defaultBlockState(),
        BlockBehaviour.Properties
            .ofFullCopy(baseBlock)
            .mapColor(mapColor)
            .setId(blockKey)
            .strength(baseBlock.defaultDestroyTime(), baseBlock.getExplosionResistance())
            .sound(soundGroup));
    Registry.register(BuiltInRegistries.BLOCK, id, stairs);

    Registry.register(
        BuiltInRegistries.ITEM,
        itemKey,
        new BlockItem(stairs, new Item.Properties().setId(itemKey)));

    BLOCK_TO_STAIRS.put(baseBlock, stairs);
    CATEGORY.putIfAbsent(baseBlock, category);

    return stairs;
  }

  public static Block registerSlab(String name, Block baseBlock, BlockCategory category) {
    Identifier id = Identifier.fromNamespaceAndPath("xander", name + "_slab");
    ResourceKey<Block> blockKey = ResourceKey.create(BuiltInRegistries.BLOCK.key(), id);
    ResourceKey<Item> itemKey = ResourceKey.create(BuiltInRegistries.ITEM.key(), id);

    SoundType soundGroup = baseBlock.defaultBlockState().getSoundType();
    MapColor mapColor = baseBlock.defaultMapColor();

    Block slab = new ModSlabBlock(
        BlockBehaviour.Properties
            .ofFullCopy(baseBlock)
            .mapColor(mapColor)
            .setId(blockKey)
            .strength(baseBlock.defaultDestroyTime(), baseBlock.getExplosionResistance())
            .sound(soundGroup));
    Registry.register(BuiltInRegistries.BLOCK, id, slab);

    Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(slab, new Item.Properties().setId(itemKey)));

    BLOCK_TO_SLAB.put(baseBlock, slab);
    CATEGORY.putIfAbsent(baseBlock, category);

    return slab;
  }

  public static void registerLogWalls() {
    // Logs
    Block oakWall = registerWall("oak", Blocks.OAK_LOG, BlockCategory.LOG);
    Block spruceWall = registerWall("spruce", Blocks.SPRUCE_LOG, BlockCategory.LOG);
    Block birchWall = registerWall("birch", Blocks.BIRCH_LOG, BlockCategory.LOG);
    Block jungleWall = registerWall("jungle", Blocks.JUNGLE_LOG, BlockCategory.LOG);
    Block acaciaWall = registerWall("acacia", Blocks.ACACIA_LOG, BlockCategory.LOG);
    Block darkOakWall = registerWall("dark_oak", Blocks.DARK_OAK_LOG, BlockCategory.LOG);
    Block mangroveWall = registerWall("mangrove", Blocks.MANGROVE_LOG, BlockCategory.LOG);
    Block cherryWall = registerWall("cherry", Blocks.CHERRY_LOG, BlockCategory.LOG);
    Block paleOakWall = registerWall("pale_oak", Blocks.PALE_OAK_LOG, BlockCategory.LOG);

    // Stripped Logs
    Block strippedOakWall = registerWall("stripped_oak", Blocks.STRIPPED_OAK_LOG, BlockCategory.LOG);
    Block strippedSpruceWall = registerWall("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG, BlockCategory.LOG);
    Block strippedBirchWall = registerWall("stripped_birch", Blocks.STRIPPED_BIRCH_LOG, BlockCategory.LOG);
    Block strippedJungleWall = registerWall("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG, BlockCategory.LOG);
    Block strippedAcaciaWall = registerWall("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG, BlockCategory.LOG);
    Block strippedDarkOakWall = registerWall("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG, BlockCategory.LOG);
    Block strippedMangroveWall = registerWall("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG, BlockCategory.LOG);
    Block strippedCherryWall = registerWall("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG, BlockCategory.LOG);
    Block strippedPaleOakWall = registerWall("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG, BlockCategory.LOG);

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
  }

  public static void registerStoneWalls() {
    // Stone
    registerWall("stone", Blocks.STONE, BlockCategory.STONE);
  }

  public static void registerWoolStairs() {
    registerStairs("white_wool", Blocks.WHITE_WOOL, BlockCategory.WOOL);
    registerStairs("orange_wool", Blocks.ORANGE_WOOL, BlockCategory.WOOL);
    registerStairs("magenta_wool", Blocks.MAGENTA_WOOL, BlockCategory.WOOL);
    registerStairs("light_blue_wool", Blocks.LIGHT_BLUE_WOOL, BlockCategory.WOOL);
    registerStairs("yellow_wool", Blocks.YELLOW_WOOL, BlockCategory.WOOL);
    registerStairs("lime_wool", Blocks.LIME_WOOL, BlockCategory.WOOL);
    registerStairs("pink_wool", Blocks.PINK_WOOL, BlockCategory.WOOL);
    registerStairs("gray_wool", Blocks.GRAY_WOOL, BlockCategory.WOOL);
    registerStairs("light_gray_wool", Blocks.LIGHT_GRAY_WOOL, BlockCategory.WOOL);
    registerStairs("cyan_wool", Blocks.CYAN_WOOL, BlockCategory.WOOL);
    registerStairs("purple_wool", Blocks.PURPLE_WOOL, BlockCategory.WOOL);
    registerStairs("blue_wool", Blocks.BLUE_WOOL, BlockCategory.WOOL);
    registerStairs("brown_wool", Blocks.BROWN_WOOL, BlockCategory.WOOL);
    registerStairs("green_wool", Blocks.GREEN_WOOL, BlockCategory.WOOL);
    registerStairs("red_wool", Blocks.RED_WOOL, BlockCategory.WOOL);
    registerStairs("black_wool", Blocks.BLACK_WOOL, BlockCategory.WOOL);
  }

  public static void registerLogStairs() {
    // Logs
    Block oakStairs = registerStairs("oak", Blocks.OAK_LOG, BlockCategory.LOG);
    Block spruceStairs = registerStairs("spruce", Blocks.SPRUCE_LOG, BlockCategory.LOG);
    Block birchStairs = registerStairs("birch", Blocks.BIRCH_LOG, BlockCategory.LOG);
    Block jungleStairs = registerStairs("jungle", Blocks.JUNGLE_LOG, BlockCategory.LOG);
    Block acaciaStairs = registerStairs("acacia", Blocks.ACACIA_LOG, BlockCategory.LOG);
    Block darkOakStairs = registerStairs("dark_oak", Blocks.DARK_OAK_LOG, BlockCategory.LOG);
    Block mangroveStairs = registerStairs("mangrove", Blocks.MANGROVE_LOG, BlockCategory.LOG);
    Block cherryStairs = registerStairs("cherry", Blocks.CHERRY_LOG, BlockCategory.LOG);
    Block paleOakStairs = registerStairs("pale_oak", Blocks.PALE_OAK_LOG, BlockCategory.LOG);

    // Stripped Logs
    Block strippedOakStairs = registerStairs("stripped_oak", Blocks.STRIPPED_OAK_LOG, BlockCategory.LOG);
    Block strippedSpruceStairs = registerStairs("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG, BlockCategory.LOG);
    Block strippedBirchStairs = registerStairs("stripped_birch", Blocks.STRIPPED_BIRCH_LOG, BlockCategory.LOG);
    Block strippedJungleStairs = registerStairs("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG, BlockCategory.LOG);
    Block strippedAcaciaStairs = registerStairs("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG, BlockCategory.LOG);
    Block strippedDarkOakStairs = registerStairs("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG, BlockCategory.LOG);
    Block strippedMangroveStairs = registerStairs("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG, BlockCategory.LOG);
    Block strippedCherryStairs = registerStairs("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG, BlockCategory.LOG);
    Block strippedPaleOakStairs = registerStairs("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG, BlockCategory.LOG);

    // Link logs to stripped logs
    ModStairsBlock.STRIPPABLES.put(oakStairs, strippedOakStairs);
    ModStairsBlock.STRIPPABLES.put(spruceStairs, strippedSpruceStairs);
    ModStairsBlock.STRIPPABLES.put(birchStairs, strippedBirchStairs);
    ModStairsBlock.STRIPPABLES.put(jungleStairs, strippedJungleStairs);
    ModStairsBlock.STRIPPABLES.put(acaciaStairs, strippedAcaciaStairs);
    ModStairsBlock.STRIPPABLES.put(darkOakStairs, strippedDarkOakStairs);
    ModStairsBlock.STRIPPABLES.put(mangroveStairs, strippedMangroveStairs);
    ModStairsBlock.STRIPPABLES.put(cherryStairs, strippedCherryStairs);
    ModStairsBlock.STRIPPABLES.put(paleOakStairs, strippedPaleOakStairs);
  }

  public static void registerLogSlabs() {
    // Logs
    Block oakSlab = registerSlab("oak", Blocks.OAK_LOG, BlockCategory.LOG);
    Block spruceSlab = registerSlab("spruce", Blocks.SPRUCE_LOG, BlockCategory.LOG);
    Block birchSlab = registerSlab("birch", Blocks.BIRCH_LOG, BlockCategory.LOG);
    Block jungleSlab = registerSlab("jungle", Blocks.JUNGLE_LOG, BlockCategory.LOG);
    Block acaciaSlab = registerSlab("acacia", Blocks.ACACIA_LOG, BlockCategory.LOG);
    Block darkOakSlab = registerSlab("dark_oak", Blocks.DARK_OAK_LOG, BlockCategory.LOG);
    Block mangroveSlab = registerSlab("mangrove", Blocks.MANGROVE_LOG, BlockCategory.LOG);
    Block cherrySlab = registerSlab("cherry", Blocks.CHERRY_LOG, BlockCategory.LOG);
    Block paleOakSlab = registerSlab("pale_oak", Blocks.PALE_OAK_LOG, BlockCategory.LOG);

    // Stripped Logs
    Block strippedOakSlab = registerSlab("stripped_oak", Blocks.STRIPPED_OAK_LOG, BlockCategory.LOG);
    Block strippedSpruceSlab = registerSlab("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG, BlockCategory.LOG);
    Block strippedBirchSlab = registerSlab("stripped_birch", Blocks.STRIPPED_BIRCH_LOG, BlockCategory.LOG);
    Block strippedJungleSlab = registerSlab("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG, BlockCategory.LOG);
    Block strippedAcaciaSlab = registerSlab("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG, BlockCategory.LOG);
    Block strippedDarkOakSlab = registerSlab("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG, BlockCategory.LOG);
    Block strippedMangroveSlab = registerSlab("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG, BlockCategory.LOG);
    Block strippedCherrySlab = registerSlab("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG, BlockCategory.LOG);
    Block strippedPaleOakSlab = registerSlab("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG, BlockCategory.LOG);

    // Link logs to stripped logs
    ModSlabBlock.STRIPPABLES.put(oakSlab, strippedOakSlab);
    ModSlabBlock.STRIPPABLES.put(spruceSlab, strippedSpruceSlab);
    ModSlabBlock.STRIPPABLES.put(birchSlab, strippedBirchSlab);
    ModSlabBlock.STRIPPABLES.put(jungleSlab, strippedJungleSlab);
    ModSlabBlock.STRIPPABLES.put(acaciaSlab, strippedAcaciaSlab);
    ModSlabBlock.STRIPPABLES.put(darkOakSlab, strippedDarkOakSlab);
    ModSlabBlock.STRIPPABLES.put(mangroveSlab, strippedMangroveSlab);
    ModSlabBlock.STRIPPABLES.put(cherrySlab, strippedCherrySlab);
    ModSlabBlock.STRIPPABLES.put(paleOakSlab, strippedPaleOakSlab);
  }

  public static void registerWoolSlabs() {
    registerSlab("white_wool", Blocks.WHITE_WOOL, BlockCategory.WOOL);
    registerSlab("orange_wool", Blocks.ORANGE_WOOL, BlockCategory.WOOL);
    registerSlab("magenta_wool", Blocks.MAGENTA_WOOL, BlockCategory.WOOL);
    registerSlab("light_blue_wool", Blocks.LIGHT_BLUE_WOOL, BlockCategory.WOOL);
    registerSlab("yellow_wool", Blocks.YELLOW_WOOL, BlockCategory.WOOL);
    registerSlab("lime_wool", Blocks.LIME_WOOL, BlockCategory.WOOL);
    registerSlab("pink_wool", Blocks.PINK_WOOL, BlockCategory.WOOL);
    registerSlab("gray_wool", Blocks.GRAY_WOOL, BlockCategory.WOOL);
    registerSlab("light_gray_wool", Blocks.LIGHT_GRAY_WOOL, BlockCategory.WOOL);
    registerSlab("cyan_wool", Blocks.CYAN_WOOL, BlockCategory.WOOL);
    registerSlab("purple_wool", Blocks.PURPLE_WOOL, BlockCategory.WOOL);
    registerSlab("blue_wool", Blocks.BLUE_WOOL, BlockCategory.WOOL);
    registerSlab("brown_wool", Blocks.BROWN_WOOL, BlockCategory.WOOL);
    registerSlab("green_wool", Blocks.GREEN_WOOL, BlockCategory.WOOL);
    registerSlab("red_wool", Blocks.RED_WOOL, BlockCategory.WOOL);
    registerSlab("black_wool", Blocks.BLACK_WOOL, BlockCategory.WOOL);
  }

  public static void registerPlankWalls() {
    registerWall("oak_planks", Blocks.OAK_PLANKS, BlockCategory.PLANK);
    registerWall("spruce_planks", Blocks.SPRUCE_PLANKS, BlockCategory.PLANK);
    registerWall("birch_planks", Blocks.BIRCH_PLANKS, BlockCategory.PLANK);
    registerWall("jungle_planks", Blocks.JUNGLE_PLANKS, BlockCategory.PLANK);
    registerWall("acacia_planks", Blocks.ACACIA_PLANKS, BlockCategory.PLANK);
    registerWall("dark_oak_planks", Blocks.DARK_OAK_PLANKS, BlockCategory.PLANK);
    registerWall("mangrove_planks", Blocks.MANGROVE_PLANKS, BlockCategory.PLANK);
    registerWall("cherry_planks", Blocks.CHERRY_PLANKS, BlockCategory.PLANK);
    registerWall("pale_oak_planks", Blocks.PALE_OAK_PLANKS, BlockCategory.PLANK);
    registerWall("bamboo_planks", Blocks.BAMBOO_PLANKS, BlockCategory.PLANK);
    registerWall("crimson_planks", Blocks.CRIMSON_PLANKS, BlockCategory.PLANK);
    registerWall("warped_planks", Blocks.WARPED_PLANKS, BlockCategory.PLANK);
  }

  public static void registerWoolWalls() {
    registerWall("white_wool", Blocks.WHITE_WOOL, BlockCategory.WOOL);
    registerWall("orange_wool", Blocks.ORANGE_WOOL, BlockCategory.WOOL);
    registerWall("magenta_wool", Blocks.MAGENTA_WOOL, BlockCategory.WOOL);
    registerWall("light_blue_wool", Blocks.LIGHT_BLUE_WOOL, BlockCategory.WOOL);
    registerWall("yellow_wool", Blocks.YELLOW_WOOL, BlockCategory.WOOL);
    registerWall("lime_wool", Blocks.LIME_WOOL, BlockCategory.WOOL);
    registerWall("pink_wool", Blocks.PINK_WOOL, BlockCategory.WOOL);
    registerWall("gray_wool", Blocks.GRAY_WOOL, BlockCategory.WOOL);
    registerWall("light_gray_wool", Blocks.LIGHT_GRAY_WOOL, BlockCategory.WOOL);
    registerWall("cyan_wool", Blocks.CYAN_WOOL, BlockCategory.WOOL);
    registerWall("purple_wool", Blocks.PURPLE_WOOL, BlockCategory.WOOL);
    registerWall("blue_wool", Blocks.BLUE_WOOL, BlockCategory.WOOL);
    registerWall("brown_wool", Blocks.BROWN_WOOL, BlockCategory.WOOL);
    registerWall("green_wool", Blocks.GREEN_WOOL, BlockCategory.WOOL);
    registerWall("red_wool", Blocks.RED_WOOL, BlockCategory.WOOL);
    registerWall("black_wool", Blocks.BLACK_WOOL, BlockCategory.WOOL);
  }

  public static void registerNaturalSlabs() {
    registerSlab("dirt", Blocks.DIRT, BlockCategory.NATURAL);
    registerSlab("grass_block", Blocks.GRASS_BLOCK, BlockCategory.NATURAL);
  }

  public static void registerNaturalStairs() {
    registerStairs("dirt", Blocks.DIRT, BlockCategory.NATURAL);
    registerStairs("grass_block", Blocks.GRASS_BLOCK, BlockCategory.NATURAL);
  }
}
