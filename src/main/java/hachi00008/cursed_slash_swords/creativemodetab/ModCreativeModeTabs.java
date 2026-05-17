package hachi00008.cursed_slash_swords.creativemodetab;

import hachi00008.cursed_slash_swords.CursedSlashSwords;
import hachi00008.cursed_slash_swords.block.ModBlocks;
import hachi00008.cursed_slash_swords.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab CURSED_INGREDIENTS = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, "cursed_ingredients"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SPIRITUAL_IRON_INGOT))
                    .title(Component.translatable("itemGroup.cursed_slash_swords.cursed_ingredients"))
                    .displayItems((parameters, output) -> {
                      output.accept(ModItems.SPIRITUAL_IRON_INGOT);
                      output.accept(ModItems.FOX_CHERRY_BLOSSOM_STONE);
                      output.accept(ModItems.EMPTY_SPIRIT_BOTTLE);
                      output.accept(ModItems.SPIRIT_BOTTLE);

                    }).build());

    public static final CreativeModeTab CURSED_BLOCKS = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, "cursed_blocks"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.SPIRITUAL_IRON_BLOCK))
                    .title(Component.translatable("itemGroup.cursed_slash_swords.cursed_blocks"))
                    .displayItems((parameters, output) -> {
                      output.accept(ModBlocks.SPIRITUAL_IRON_BLOCK);
                      output.accept(ModBlocks.SOUL_FORGE);

                    }).build());

    public static void registerCreativeModeTabs() {
        CursedSlashSwords.LOGGER.info("クリエイティブモードタブの読み込み完了：MOD ID -> " + CursedSlashSwords.MOD_ID);
    }
}
