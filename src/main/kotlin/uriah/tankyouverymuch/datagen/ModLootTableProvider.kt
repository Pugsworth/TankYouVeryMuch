package uriah.tankyouverymuch.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import uriah.tankyouverymuch.Blocks.ModBlocks

class ModLootTableProvider(dataGenerator: FabricDataGenerator): FabricBlockLootTableProvider(dataGenerator) {
    override fun generateBlockLootTables() {
        addDrop(ModBlocks.TANK_BLOCK)
    }
}
