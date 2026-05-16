package xander.mod;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


public class ModSlabBlock extends SlabBlock {
  public static final Map<Block, Block> STRIPPABLES = new HashMap<>();

  public ModSlabBlock(Properties settings) {
    super(settings);
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
    ItemStack stack = player.getItemInHand(player.getUsedItemHand());

    if (stack.getItem() instanceof AxeItem && STRIPPABLES.containsKey(this)) {
      Block strippedBlock = STRIPPABLES.get(this);

      world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0f);

      if (!world.isClientSide()) {
        BlockState newState = strippedBlock.defaultBlockState()
          .setValue(TYPE, state.getValue(TYPE))
          .setValue(WATERLOGGED, state.getValue(WATERLOGGED));

        world.setBlock(pos, newState, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);

        stack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
      }
      return InteractionResult.SUCCESS;
    }
    return super.useWithoutItem(state, world, pos, player, hit);
  }
}
