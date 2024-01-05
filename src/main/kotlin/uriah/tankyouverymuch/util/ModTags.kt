package uriah.tankyouverymuch.util

import net.minecraft.block.Block
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry
import uriah.tankyouverymuch.TankYouVeryMuch.getIdentifier

object ModTags {
    object Blocks {
        val TANKS: TagKey<Block> = TagKey.of(Registry.BLOCK_KEY, getIdentifier("tanks"))
    }

    object Items {
    }
}