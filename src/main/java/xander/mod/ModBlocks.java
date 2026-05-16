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
  public static final Map<Block, Block> BLOCK_TO_WALL = new HashMap<>();
  public static final Map<Block, Block> BLOCK_TO_STAIRS = new HashMap<>();
  public static final Map<Block, Block> BLOCK_TO_SLAB = new HashMap<>();

  public static Block registerWall(String name, Block baseBlock) {
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
    return wall;
  }

  public static Block registerStairs(String name, Block baseBlock) {
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

    return stairs;
  }

  public static Block registerSlab(String name, Block baseBlock) {
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

    return slab;
  }

  public static void registerLogWalls() {
    // Logs
    Block oakWall = registerWall("oak", Blocks.OAK_LOG);
    Block spruceWall = registerWall("spruce", Blocks.SPRUCE_LOG);
    Block birchWall = registerWall("birch", Blocks.BIRCH_LOG);
    Block jungleWall = registerWall("jungle", Blocks.JUNGLE_LOG);
    Block acaciaWall = registerWall("acacia", Blocks.ACACIA_LOG);
    Block darkOakWall = registerWall("dark_oak", Blocks.DARK_OAK_LOG);
    Block mangroveWall = registerWall("mangrove", Blocks.MANGROVE_LOG);
    Block cherryWall = registerWall("cherry", Blocks.CHERRY_LOG);
    Block paleOakWall = registerWall("pale_oak", Blocks.PALE_OAK_LOG);

    // Stripped Logs
    Block strippedOakWall = registerWall("stripped_oak", Blocks.STRIPPED_OAK_LOG);
    Block strippedSpruceWall = registerWall("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG);
    Block strippedBirchWall = registerWall("stripped_birch", Blocks.STRIPPED_BIRCH_LOG);
    Block strippedJungleWall = registerWall("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG);
    Block strippedAcaciaWall = registerWall("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG);
    Block strippedDarkOakWall = registerWall("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG);
    Block strippedMangroveWall = registerWall("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG);
    Block strippedCherryWall = registerWall("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG);
    Block strippedPaleOakWall = registerWall("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG);

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
    registerWall("stone", Blocks.STONE);
  }

  public static void registerWoolStairs() {
    registerStairs("white_wool", Blocks.WHITE_WOOL);
    registerStairs("orange_wool", Blocks.ORANGE_WOOL);
    registerStairs("magenta_wool", Blocks.MAGENTA_WOOL);
    registerStairs("light_blue_wool", Blocks.LIGHT_BLUE_WOOL);
    registerStairs("yellow_wool", Blocks.YELLOW_WOOL);
    registerStairs("lime_wool", Blocks.LIME_WOOL);
    registerStairs("pink_wool", Blocks.PINK_WOOL);
    registerStairs("gray_wool", Blocks.GRAY_WOOL);
    registerStairs("light_gray_wool", Blocks.LIGHT_GRAY_WOOL);
    registerStairs("cyan_wool", Blocks.CYAN_WOOL);
    registerStairs("purple_wool", Blocks.PURPLE_WOOL);
    registerStairs("blue_wool", Blocks.BLUE_WOOL);
    registerStairs("brown_wool", Blocks.BROWN_WOOL);
    registerStairs("green_wool", Blocks.GREEN_WOOL);
    registerStairs("red_wool", Blocks.RED_WOOL);
    registerStairs("black_wool", Blocks.BLACK_WOOL);
  }

  public static void registerLogStairs() {
    // Logs
    Block oakStairs = registerStairs("oak", Blocks.OAK_LOG);
    Block spruceStairs = registerStairs("spruce", Blocks.SPRUCE_LOG);
    Block birchStairs = registerStairs("birch", Blocks.BIRCH_LOG);
    Block jungleStairs = registerStairs("jungle", Blocks.JUNGLE_LOG);
    Block acaciaStairs = registerStairs("acacia", Blocks.ACACIA_LOG);
    Block darkOakStairs = registerStairs("dark_oak", Blocks.DARK_OAK_LOG);
    Block mangroveStairs = registerStairs("mangrove", Blocks.MANGROVE_LOG);
    Block cherryStairs = registerStairs("cherry", Blocks.CHERRY_LOG);
    Block paleOakStairs = registerStairs("pale_oak", Blocks.PALE_OAK_LOG);

    // Stripped Logs
    Block strippedOakStairs = registerStairs("stripped_oak", Blocks.STRIPPED_OAK_LOG);
    Block strippedSpruceStairs = registerStairs("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG);
    Block strippedBirchStairs = registerStairs("stripped_birch", Blocks.STRIPPED_BIRCH_LOG);
    Block strippedJungleStairs = registerStairs("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG);
    Block strippedAcaciaStairs = registerStairs("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG);
    Block strippedDarkOakStairs = registerStairs("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG);
    Block strippedMangroveStairs = registerStairs("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG);
    Block strippedCherryStairs = registerStairs("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG);
    Block strippedPaleOakStairs = registerStairs("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG);

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
    Block oakSlab = registerSlab("oak", Blocks.OAK_LOG);
    Block spruceSlab = registerSlab("spruce", Blocks.SPRUCE_LOG);
    Block birchSlab = registerSlab("birch", Blocks.BIRCH_LOG);
    Block jungleSlab = registerSlab("jungle", Blocks.JUNGLE_LOG);
    Block acaciaSlab = registerSlab("acacia", Blocks.ACACIA_LOG);
    Block darkOakSlab = registerSlab("dark_oak", Blocks.DARK_OAK_LOG);
    Block mangroveSlab = registerSlab("mangrove", Blocks.MANGROVE_LOG);
    Block cherrySlab = registerSlab("cherry", Blocks.CHERRY_LOG);
    Block paleOakSlab = registerSlab("pale_oak", Blocks.PALE_OAK_LOG);

    // Stripped Logs
    Block strippedOakSlab = registerSlab("stripped_oak", Blocks.STRIPPED_OAK_LOG);
    Block strippedSpruceSlab = registerSlab("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG);
    Block strippedBirchSlab = registerSlab("stripped_birch", Blocks.STRIPPED_BIRCH_LOG);
    Block strippedJungleSlab = registerSlab("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG);
    Block strippedAcaciaSlab = registerSlab("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG);
    Block strippedDarkOakSlab = registerSlab("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG);
    Block strippedMangroveSlab = registerSlab("stripped_mangrove", Blocks.STRIPPED_MANGROVE_LOG);
    Block strippedCherrySlab = registerSlab("stripped_cherry", Blocks.STRIPPED_CHERRY_LOG);
    Block strippedPaleOakSlab = registerSlab("stripped_pale_oak", Blocks.STRIPPED_PALE_OAK_LOG);

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
    registerSlab("white_wool", Blocks.WHITE_WOOL);
    registerSlab("orange_wool", Blocks.ORANGE_WOOL);
    registerSlab("magenta_wool", Blocks.MAGENTA_WOOL);
    registerSlab("light_blue_wool", Blocks.LIGHT_BLUE_WOOL);
    registerSlab("yellow_wool", Blocks.YELLOW_WOOL);
    registerSlab("lime_wool", Blocks.LIME_WOOL);
    registerSlab("pink_wool", Blocks.PINK_WOOL);
    registerSlab("gray_wool", Blocks.GRAY_WOOL);
    registerSlab("light_gray_wool", Blocks.LIGHT_GRAY_WOOL);
    registerSlab("cyan_wool", Blocks.CYAN_WOOL);
    registerSlab("purple_wool", Blocks.PURPLE_WOOL);
    registerSlab("blue_wool", Blocks.BLUE_WOOL);
    registerSlab("brown_wool", Blocks.BROWN_WOOL);
    registerSlab("green_wool", Blocks.GREEN_WOOL);
    registerSlab("red_wool", Blocks.RED_WOOL);
    registerSlab("black_wool", Blocks.BLACK_WOOL);
  }
}
