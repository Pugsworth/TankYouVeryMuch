package uriah.tankyouverymuch.Blocks.Entity

import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos
import uriah.tankyouverymuch.Blocks.ModBlocks
import uriah.tankyouverymuch.Blocks.TankBlock
import uriah.tankyouverymuch.TankYouVeryMuch

class TankBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ModBlocks.TANK_BLOCK_ENTITY, pos, state), RenderAttachmentBlockEntity {

    val fluidStorage = object: SingleFluidStorage() {
        override fun getCapacity(variant: FluidVariant?): Long {
            return FluidConstants.BUCKET * 8
        }

        override fun supportsInsertion(): Boolean {
            return true
        }

        override fun supportsExtraction(): Boolean {
            return true
        }

        override fun onFinalCommit() {
            val side = if (world?.isClient!!) "Client" else "Server"
            TankYouVeryMuch.LOGGER.info("$side: Fluid storage committed")

            markDirty()
            if (world?.isClient!!) {
                return
            }

            world?.server?.execute {
                updateBlockState()
            }
        }
    }

    override fun getRenderAttachmentData(): Any? {
        return fluidStorage.variant
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? {
        return BlockEntityUpdateS2CPacket.create(this)
    }

    override fun toInitialChunkDataNbt(): NbtCompound {
        return createNbt()
    }

    override fun writeNbt(tag: NbtCompound) {
        // val fluidTag = NbtCompound()
        // fluidStorage.writeNbt(fluidTag)
        // tag.put("fluid", fluidTag)

        fluidStorage.writeNbt(tag)

        super.writeNbt(tag)
    }

    override fun readNbt(tag: NbtCompound) {
        fluidStorage.readNbt(tag)

        // val fluidTag: NbtCompound = tag.getCompound("fluid")
        // val variant = fluidTag.get("variant") as NbtCompound
        // val amount = fluidTag.getLong("amount")
        // fluidStorage.variant = FluidVariant.fromNbt(variant)
        // fluidStorage.amount = amount

        super.readNbt(tag)
    }

    fun getFLuidLuminance(): Int {
        fluidStorage.variant.fluid.defaultState.blockState.luminance.let { light ->
            val value = when (light) {
                in 1..3 -> 1
                in 4..7 -> 2
                in 8..15 -> 3
                else -> 0
            }

            return value
        }
    }

    // Update the block state to match the fluid storage
    fun updateBlockState() {
        if (!world?.isClient!!) {
            fillDownwards()

            val newState = cachedState.with(TankBlock.LIGHTSTATE, getFLuidLuminance())
            world?.setBlockState(pos, newState, Block.NOTIFY_LISTENERS)
            world?.updateListeners(pos, cachedState, newState, Block.NOTIFY_LISTENERS)
        }
    }

    // TODO: Make this work, and make it "animate" (purely visual)
    // TODO: Instead of chaining downwards, look for the lowest tank that's not full iteratively
    // TODO: Also, what happens if there's a tank below that's not the same fluid?
    fun fillDownwards() {
        world?.getBlockEntity(pos.down())?.let { blockEntity ->
            if (blockEntity is TankBlockEntity) {
                val thisVariant = fluidStorage.variant
                val thatVariant = blockEntity.fluidStorage.variant

                if ( !thisVariant.isBlank && (thisVariant.equals(thatVariant) || thatVariant.isBlank) ) {
                    val thisAmount = fluidStorage.amount
                    val thatAmount = blockEntity.fluidStorage.amount
                    val thatCapacity = blockEntity.fluidStorage.capacity
                    val amountToTransfer = if (thisAmount + thatAmount > thatCapacity) {
                        thatCapacity - thatAmount
                    } else {
                        thisAmount
                    }

                    val transaction = Transaction.openOuter()
                    // TODO: There's an error somewhere here about "Transfer variant may not be blank"
                    val extracted = fluidStorage.extract(thisVariant, amountToTransfer, transaction)
                    val inserted = blockEntity.fluidStorage.insert(thisVariant, amountToTransfer, transaction)
                    if (extracted == inserted) {
                        transaction.commit()
                    }

                    // fluidStorage.amount -= amountToTransfer
                    // fluidStorage.extract(variant)
                    // blockEntity.fluidStorage.amount += amountToTransfer
                }
            }
        }
    }

    /**
     * Move the block entity to a new position.
     * This is used when the tank multiblock controller is changed.
     */
    fun move(pos: BlockPos) {
    }
}