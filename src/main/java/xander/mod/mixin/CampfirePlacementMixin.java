package xander.mod.mixin;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfirePlacementMixin {

    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    private void forceUnlit(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockState originalState = cir.getReturnValue();

        // Ensure we don't crash if the state is somehow null
        if (originalState != null && originalState.hasProperty(BlockStateProperties.LIT)) {
            cir.setReturnValue(originalState.setValue(BlockStateProperties.LIT, false));
        }
    }
}
