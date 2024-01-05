package uriah.tankyouverymuch.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.*
import uriah.tankyouverymuch.Blocks.ModBlocks
import uriah.tankyouverymuch.TankYouVeryMuch.getIdentifier
import java.util.*

class ModModelProvider(dataGenerator: FabricDataGenerator?): FabricModelProvider(dataGenerator) {
    companion object {
        object MODELS {
            val TANK_BASE = getIdentifier("block/tank_base")
        }
    }
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        registerColumnLikeBlock(ModBlocks.TANK_BLOCK, blockStateModelGenerator)
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
    }

    private fun registerColumnLikeBlock(block: Block, blockStateModelGenerator: BlockStateModelGenerator) {
        val textureMap = TextureMap.sideEnd(TextureMap.getSubId(block, "_side"), TextureMap.getSubId(block, "_top"))
        val tank_base = Model(
            Optional.of(MODELS.TANK_BASE),
            Optional.empty(),
            TextureKey.TOP, TextureKey.SIDE
        )
        val identifier = tank_base.upload(block, textureMap, blockStateModelGenerator.modelCollector)
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier))
    }
}