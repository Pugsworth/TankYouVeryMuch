package uriah.tankyouverymuch

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import uriah.tankyouverymuch.datagen.*

object TankYouVeryMuchDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
		generator.addProvider(ModBlockTagProvider(generator))
		generator.addProvider(ModItemTagProvider(generator))
		generator.addProvider(ModLootTableProvider(generator))
		generator.addProvider(ModModelProvider(generator))
		generator.addProvider(ModRecipeProvider(generator))
	}
}