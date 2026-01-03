package xander.mod;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Map<Block, Block> LOG_TO_WALL = new HashMap<>();

    public static Block registerLogWall(String name, Block baseLog) {
        Identifier id = Identifier.of("xander", name + "_wall");
        RegistryKey<Block> blockKey = RegistryKey.of(Registries.BLOCK.getKey(), id);
        RegistryKey<Item> itemKey = RegistryKey.of(Registries.ITEM.getKey(), id);

        Block wall = new WallBlock(
                AbstractBlock.Settings
                        .copy(Blocks.COBBLESTONE_WALL)
                        .registryKey(blockKey)
                        .strength(baseLog.getHardness(), baseLog.getBlastResistance())
                        .sounds(BlockSoundGroup.WOOD)
                        .burnable());
        Registry.register(Registries.BLOCK, id, wall);

        Registry.register(
                Registries.ITEM,
                itemKey,
                new BlockItem(wall, new Item.Settings().registryKey(itemKey)));

        LOG_TO_WALL.put(baseLog, wall);
        return wall;
    }

    public static void registerWalls() {
        registerLogWall("oak", Blocks.OAK_LOG);
        registerLogWall("spruce", Blocks.SPRUCE_LOG);
        registerLogWall("birch", Blocks.BIRCH_LOG);
        registerLogWall("jungle", Blocks.JUNGLE_LOG);
        registerLogWall("acacia", Blocks.ACACIA_LOG);
        registerLogWall("dark_oak", Blocks.DARK_OAK_LOG);
        registerLogWall("mangrove", Blocks.MANGROVE_LOG);
        registerLogWall("cherry", Blocks.CHERRY_LOG);
        registerLogWall("pale_oak", Blocks.PALE_OAK_LOG);
    }
}
