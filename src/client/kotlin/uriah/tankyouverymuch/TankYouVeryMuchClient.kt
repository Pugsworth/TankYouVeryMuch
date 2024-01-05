package uriah.tankyouverymuch

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import uriah.tankyouverymuch.Blocks.ModBlocks
import uriah.tankyouverymuch.Blocks.TankBlock

object TankYouVeryMuchClient : ClientModInitializer {
	override fun onInitializeClient() {
		ColorProviderRegistry.BLOCK.register({ state, world, pos, tintIndex ->
			when(state.get(TankBlock.CONTROLLER)) {
				true -> 0x7E70B8
				false -> 0xFFFFFF
			}
		}, ModBlocks.TANK_BLOCK)

		BlockEntityRendererFactories.register(ModBlocks.TANK_BLOCK_ENTITY) { TankBlockEntityRenderer() }

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TANK_BLOCK, RenderLayer.getCutout())
	}
}