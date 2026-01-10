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
    public static final Map<Block, Block> BLOCK_TO_WALL = new HashMap<>();

    public static Block registerWall(String name, Block baseBlock) {
        Identifier id = Identifier.of("xander", name + "_wall");
        RegistryKey<Block> blockKey = RegistryKey.of(Registries.BLOCK.getKey(), id);
        RegistryKey<Item> itemKey = RegistryKey.of(Registries.ITEM.getKey(), id);

        BlockSoundGroup soundGroup = baseBlock.getDefaultState().getSoundGroup();

        Block wall = new WallBlock(
                AbstractBlock.Settings
                        .copy(baseBlock)
                        .registryKey(blockKey)
                        .strength(baseBlock.getHardness(), baseBlock.getBlastResistance())
                        .sounds(soundGroup));
        Registry.register(Registries.BLOCK, id, wall);

        Registry.register(
                Registries.ITEM,
                itemKey,
                new BlockItem(wall, new Item.Settings().registryKey(itemKey)));

        BLOCK_TO_WALL.put(baseBlock, wall);
        return wall;
    }

    public static void registerWalls() {
        // Logs
        registerWall("oak", Blocks.OAK_LOG);
        registerWall("spruce", Blocks.SPRUCE_LOG);
        registerWall("birch", Blocks.BIRCH_LOG);
        registerWall("jungle", Blocks.JUNGLE_LOG);
        registerWall("acacia", Blocks.ACACIA_LOG);
        registerWall("dark_oak", Blocks.DARK_OAK_LOG);
        registerWall("mangrove", Blocks.MANGROVE_LOG);
        registerWall("cherry", Blocks.CHERRY_LOG);
        registerWall("pale_oak", Blocks.PALE_OAK_LOG);

        // Stone
        registerWall("stone", Blocks.STONE);
    }
}
