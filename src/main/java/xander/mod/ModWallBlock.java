package xander.mod;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModWallBlock extends WallBlock {
  public static final Map<Block, Block> STRIPPABLES = new HashMap<>();

  public ModWallBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    ItemStack stack = player.getStackInHand(player.getActiveHand());

    if (stack.getItem() instanceof AxeItem && STRIPPABLES.containsKey(this)) {
      Block strippedBlock = STRIPPABLES.get(this);

      world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);

      if (!world.isClient()) {
        BlockState newState = strippedBlock.getDefaultState()
            .with(UP, state.get(UP))
            .with(NORTH_WALL_SHAPE, state.get(NORTH_WALL_SHAPE))
            .with(EAST_WALL_SHAPE, state.get(EAST_WALL_SHAPE))
            .with(SOUTH_WALL_SHAPE, state.get(SOUTH_WALL_SHAPE))
            .with(WEST_WALL_SHAPE, state.get(WEST_WALL_SHAPE))
            .with(WATERLOGGED, state.get(WATERLOGGED));

        world.setBlockState(pos, newState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);

        stack.damage(1, player,
            player.getActiveHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
      }
      return ActionResult.SUCCESS;
    }
    return super.onUse(state, world, pos, player, hit);
  }
}
