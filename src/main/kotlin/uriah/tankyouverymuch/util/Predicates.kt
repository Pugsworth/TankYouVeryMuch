package uriah.tankyouverymuch.util

import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

class Predicates {
    // These are private in Blocks.java...
    companion object {
        fun never(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>): Boolean {
            return false
        }

        fun never(state: BlockState, world: BlockView, pos: BlockPos): Boolean {
            return false
        }

        fun always(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>): Boolean {
            return true
        }

        fun always(state: BlockState, world: BlockView, pos: BlockPos): Boolean { return true
        }
    }
}