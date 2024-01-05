package uriah.tankyouverymuch.Blocks

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import uriah.tankyouverymuch.Blocks.Entity.TankBlockEntity
import uriah.tankyouverymuch.TankYouVeryMuch.getIdentifier
import uriah.tankyouverymuch.util.Predicates

object ModBlocks {

    val TANK_BLOCK = registerBlock(
        getIdentifier("tank"),
        TankBlock(
            FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
                .nonOpaque()
                .strength(10.0f, 10.0f)
                .luminance { state -> (state.get(TankBlock.LIGHTSTATE)+1)*4 - 1 }
                .sounds(BlockSoundGroup.GLASS)
                .suffocates(Predicates::never)
                .allowsSpawning(Predicates::never)
        )
    )

    val TANK_BLOCK_ENTITY = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        getIdentifier("tank"),
        FabricBlockEntityTypeBuilder.create(::TankBlockEntity, TANK_BLOCK).build()
    )

    fun registerModBlocks() {
    }

    private fun registerBlock(identifier: Identifier, block: Block): Block {
        return Registry.register(Registry.BLOCK, identifier, block)
    }

    fun registerModBlockEntities() {
    }
}