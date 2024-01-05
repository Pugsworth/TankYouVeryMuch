package uriah.tankyouverymuch.Blocks

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import uriah.tankyouverymuch.Blocks.Entity.TankBlockEntity
import uriah.tankyouverymuch.TankYouVeryMuch

class TankBlock(settings: Settings) : Block(settings), BlockEntityProvider {

    companion object {
        protected val COLLISION_SHAPE: VoxelShape = createCuboidShape(2.0, 0.0, 2.0, 14.0, 15.0, 14.0)
        protected val OUTLINE_SHAPE: VoxelShape = createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)

        // What light level this block should emit from 0-3
        val LIGHTSTATE: IntProperty = IntProperty.of("light", 0, 3)
        // Is this tank the controller tank?
        val CONTROLLER: BooleanProperty = BooleanProperty.of("controller")
    }

    init {
        defaultState = defaultState
            .with(LIGHTSTATE, 0)
            .with(CONTROLLER, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder
            .add(LIGHTSTATE)
            .add(CONTROLLER)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        (world.getBlockEntity(pos) as? TankBlockEntity).let { tank ->
            if (FluidStorageUtil.interactWithFluidStorage(tank?.fluidStorage, player, hand)) {
                // Set the light level based on the fluid in the tank
                world.getBlockEntity(pos).let {
                    if (it is TankBlockEntity) {
                        it.updateBlockState()
                    }
                }
            }
            else {
                if (!player.getStackInHand(hand).isEmpty) {
                    return ActionResult.PASS
                }

                if (!world.isClient) {

                    val amount = tank?.fluidStorage?.amount ?: 0
                    val amtString = getFluidAmountString(amount)

                    player.sendMessage(
                        Text.literal("Fluid: ")
                            .append(FluidVariantAttributes.getName(tank?.fluidStorage?.variant ?: FluidVariant.blank()))
                            .append(", Amount: $amtString"),
                        false
                        )
                }
                return ActionResult.CONSUME
            }
        }

        return ActionResult.success(world.isClient)
    }

    override fun getOutlineShape( state: BlockState?, world: BlockView?, pos: BlockPos?, context: ShapeContext? ): VoxelShape {
        return OUTLINE_SHAPE
    }

    override fun getCollisionShape( state: BlockState?, world: BlockView?, pos: BlockPos?, context: ShapeContext? ): VoxelShape {
        return COLLISION_SHAPE
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return TankBlockEntity(pos, state)
    }

    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        val side = if (world.isClient) "Client" else "Server"
        TankYouVeryMuch.LOGGER.info("TankBlock.getStateForNeighborUpdate (${side})")

        // TODO: Update the fluid in the tank and the ones below.
        world.getBlockEntity(pos)?.let {
            if (it is TankBlockEntity) {
                it.updateBlockState()
            }
        }

        return state
    }

    override fun getPistonBehavior(state: BlockState?): PistonBehavior {
        return PistonBehavior.IGNORE
    }

    override fun canPlaceAt(state: BlockState?, world: WorldView?, pos: BlockPos?): Boolean {
        return true
    }

    override fun neighborUpdate(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        sourceBlock: Block?,
        sourcePos: BlockPos?,
        notify: Boolean
    ) {
    }

    override fun onPlaced(
        world: World?,
        pos: BlockPos?,
        state: BlockState?,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        // TODO: Find the lowest tank block and get the BlockEntity and register this block to it.
    }

    /**
     * Find the lowest tank block and return its position.
     */
    fun findController(world: World, pos: BlockPos): BlockPos? {
        val MAX_SEARCH = 10
        var search = 0
        var currentBlockState = world.getBlockState(pos)

        while (search < MAX_SEARCH) {
            val nextBlockState = world.getBlockState(pos.down(search))
            if (nextBlockState.block is TankBlock) {
                if (currentBlockState.get(CONTROLLER)) {
                    // Found a controller, but the block below is also a tank.
                    // We gotta remove the controller state from the current block.
                    // But this will be done in the calling function.
                }
                currentBlockState = nextBlockState
            } else {
                // Found the lowest tank block.
                return pos.down(search - 1)
            }
            search++
        }

        return null
    }

    fun setController(world: World, state: BlockState, pos: BlockPos, isController: Boolean): Boolean {
        val newState = state.with(CONTROLLER, false)
        return world.setBlockState(pos, newState)
    }

    fun getFluidAmountString(amount: Long): String {
        val buckets = amount / FluidConstants.BUCKET
        val bottles = (amount % FluidConstants.BUCKET) / FluidConstants.BOTTLE
        val droplets = (amount % FluidConstants.BOTTLE) / FluidConstants.DROPLET

        return "${buckets}B, ${bottles}b, ${droplets}d"
    }
}