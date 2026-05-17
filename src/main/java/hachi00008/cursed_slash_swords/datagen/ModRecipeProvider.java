package hachi00008.cursed_slash_swords.datagen;

import hachi00008.cursed_slash_swords.block.ModBlocks;
import hachi00008.cursed_slash_swords.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.MISC, ModBlocks.SPIRITUAL_IRON_BLOCK)
                        .pattern("SSS")
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ModItems.SPIRITUAL_IRON_INGOT)
                        .unlockedBy(getHasName(ModItems.SPIRITUAL_IRON_INGOT), has(ModItems.SPIRITUAL_IRON_INGOT))
                        .group("spiritual_iron_block")
                        .save(output);

                shapeless(RecipeCategory.MISC, ModItems.SPIRITUAL_IRON_INGOT, 9)
                        .requires(ModBlocks.SPIRITUAL_IRON_BLOCK)
                        .unlockedBy(getHasName(ModBlocks.SPIRITUAL_IRON_BLOCK), has(ModBlocks.SPIRITUAL_IRON_BLOCK))
                        .group("spiritual_iron_ingot")
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.EMPTY_SPIRIT_BOTTLE, 3)
                        .pattern("GSG")
                        .pattern(" G ")
                        .define('G', Items.GLASS)
                        .define('S', Items.SOUL_SAND)
                        .unlockedBy(getHasName(Items.SOUL_SAND), has(Items.SOUL_SAND))
                        .group("empty_spiritual_bottle")
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.SPIRITUAL_IRON_INGOT, 3)
                        .pattern("SI")
                        .pattern("II")
                        .define('S', ModItems.SPIRIT_BOTTLE)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy(getHasName(ModItems.SPIRIT_BOTTLE), has(ModItems.SPIRIT_BOTTLE))
                        .group("spiritual_iron_ingot")
                        .save(output, "crafting_spiritual_iron_ingot");

                shapeless(RecipeCategory.MISC, ModItems.FOX_CHERRY_BLOSSOM_STONE)
                        .requires(ModItems.SPIRITUAL_IRON_INGOT)
                        .requires(ModItems.SPIRIT_BOTTLE)
                        .requires(Items.ENDER_PEARL)
                        .requires(Items.CHERRY_SAPLING)
                        .unlockedBy(getHasName(ModItems.SPIRITUAL_IRON_INGOT), has(ModItems.SPIRITUAL_IRON_INGOT))
                        .group("fox_cherry_blossom_stone")
                        .save(output);

            }
        };
    }

    @Override
    public String getName() {
        return "Cursed Slash Swords Recipes";
    }
}
