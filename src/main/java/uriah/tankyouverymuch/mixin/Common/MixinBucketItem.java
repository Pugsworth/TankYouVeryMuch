package uriah.tankyouverymuch.mixin.Common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export=true)
@Mixin(BucketItem.class)
public class MixinBucketItem extends Item {
    @Shadow @Final private Fluid fluid;

    public MixinBucketItem(Settings settings) {
        super(settings);
    }

    public Fluid getFluid() {
        return fluid;
    }


    @Inject( method="use", at=@At(value = "HEAD") )
    void use(
            World world,
            PlayerEntity user,
            Hand hand,
            CallbackInfoReturnable<TypedActionResult<ItemStack>> cir
    ) {
/*
        final Fluid fluid = this.getFluid();

        BlockHitResult blockHitResult = Item.raycast(world, user, RaycastContext.FluidHandling.NONE);

        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        final BlockPos blockPos = blockHitResult.getBlockPos();
        final BlockEntity blockEnt = world.getBlockEntity(blockPos);

        if (!(blockEnt instanceof TankBlockEntity)) {
            return;
        }

        final TankBlockEntity tankBlockEnt = (TankBlockEntity) blockEnt;

        if ( fluid == Fluids.WATER || fluid == Fluids.LAVA ) {
            FluidStorageUtil.interactWithFluidStorage(tankBlockEnt.getFluidStorage(), user, hand);
        }
*/
    }
}
