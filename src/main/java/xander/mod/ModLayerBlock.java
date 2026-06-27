package xander.mod;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModLayerBlock extends Block {

  // Reuse standard vanilla 1-8 layer property
  public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;

  // Cache bounding boxes for all 8 layers
  protected static final VoxelShape[] SHAPES = new VoxelShape[] {
      Shapes.empty(),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
  };

  public ModLayerBlock(Properties properties) {
    super(properties);

    // Register default state at 1 layer height
    this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1));
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPES[state.getValue(LAYERS)];
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(LAYERS);
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
    if (blockState.is(this)) {
      int currentLayers = blockState.getValue(LAYERS);
      // If it's already full (8), let it handle default stacking placement rules
      return currentLayers < 8 ? blockState.setValue(LAYERS, Math.min(8, currentLayers + 1))
          : super.getStateForPlacement(context);
    }
    return super.getStateForPlacement(context);
  }

  @Override
  public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
    int currentLayers = state.getValue(LAYERS);
    if (useContext.getItemInHand().is(this.asItem()) && currentLayers < 8) {
      if (useContext.replacingClickedOnBlock()) {
        return useContext.getClickedFace() == net.minecraft.core.Direction.UP;
      }
      return true;
    }
    return false;
  }
}
