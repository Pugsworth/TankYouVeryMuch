package uriah.tankyouverymuch

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.block.Block
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import org.slf4j.LoggerFactory
import uriah.tankyouverymuch.Blocks.Entity.TankBlockEntity
import uriah.tankyouverymuch.Blocks.ModBlocks
import uriah.tankyouverymuch.Items.ModItems

object TankYouVeryMuch : ModInitializer {
	const val MOD_NAMESPACE = "tankyouverymuch"
    private val logger = LoggerFactory.getLogger(MOD_NAMESPACE)
	val LOGGER = logger

	// TODO: Determine if this will even be necessary. This was supposed to be a lookup for the registered fluids for getting light levels.
	val fluidBlocks: MutableMap<String, Block> = mutableMapOf()

	fun getIdentifier(name: String): Identifier {
		return Identifier(MOD_NAMESPACE, name)
	}

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("TankYouVeryMuch is initializing!")

		ModBlocks.registerModBlocks()
		ModBlocks.registerModBlockEntities()
		ModItems.registerModItems()

		// TODO: move this into ModEntities or some other file.
		FluidStorage.SIDED.registerForBlockEntity( { blockEntity: TankBlockEntity, direction: Direction -> blockEntity.fluidStorage }, ModBlocks.TANK_BLOCK_ENTITY )
	}
}