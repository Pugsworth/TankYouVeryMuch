package uriah.tankyouverymuch.Items

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import uriah.tankyouverymuch.Blocks.ModBlocks
import uriah.tankyouverymuch.TankYouVeryMuch.getIdentifier

object ModItems {
    val ITEM_GROUP = FabricItemGroupBuilder.create(getIdentifier("tanks_group"))
        .icon { ItemStack(ModBlocks.TANK_BLOCK) }
        .build()

    val TANK_BLOCK = registerItem(getIdentifier("tank"), BlockItem(ModBlocks.TANK_BLOCK, FabricItemSettings().group(ITEM_GROUP)))

    private fun registerItem(identifier: Identifier, item: Item): Item {
        return Registry.register(Registry.ITEM, identifier, item)
    }

    fun registerModItems() {
    }
}