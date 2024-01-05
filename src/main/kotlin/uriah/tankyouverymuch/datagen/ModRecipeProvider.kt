package uriah.tankyouverymuch.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.RecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Items
import uriah.tankyouverymuch.Items.ModItems
import java.util.function.Consumer

class ModRecipeProvider(dataGenerator: FabricDataGenerator?) : FabricRecipeProvider(dataGenerator) {
    override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>?) {
        // Tank block recipe
        ShapedRecipeJsonBuilder.create(ModItems.TANK_BLOCK, 1)
            .pattern("PGP")
            .pattern("P P")
            .pattern("PGP")
            .input('G', Items.GLASS)
            .input('P', Items.GLASS_PANE)
            .criterion(hasItem(Items.GLASS), RecipeProvider.conditionsFromItem(Items.GLASS))
            .offerTo(exporter)
    }
}