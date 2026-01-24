package xander.mod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;

@Mixin(CampfireBlock.class)
public class CampfirePlacementMixin {

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void forceUnlit(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockState originalState = cir.getReturnValue();

        // Ensure we don't crash if the state is somehow null
        if (originalState != null && originalState.contains(Properties.LIT)) {
            cir.setReturnValue(originalState.with(Properties.LIT, false));
        }
    }
}
