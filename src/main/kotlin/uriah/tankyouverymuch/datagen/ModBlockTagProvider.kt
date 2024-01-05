package uriah.tankyouverymuch.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import uriah.tankyouverymuch.Blocks.ModBlocks
import uriah.tankyouverymuch.util.ModTags

class ModBlockTagProvider(dataGenerator: FabricDataGenerator?) : FabricTagProvider.BlockTagProvider(dataGenerator) {
    override fun generateTags() {
        getOrCreateTagBuilder(ModTags.Blocks.TANKS).add(ModBlocks.TANK_BLOCK)
    }
}