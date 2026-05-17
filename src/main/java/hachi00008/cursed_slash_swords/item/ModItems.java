package hachi00008.cursed_slash_swords.item;

import hachi00008.cursed_slash_swords.CursedSlashSwords;
import hachi00008.cursed_slash_swords.item.custom.EmptySpiritBottleItem;
import hachi00008.cursed_slash_swords.item.custom.SpiritBottleItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public class ModItems {
    public static final Item SPIRITUAL_IRON_INGOT = registerItem("spiritual_iron_ingot", Item::new);
    public static final Item FOX_CHERRY_BLOSSOM_STONE = registerItem("fox_cherry_blossom_stone", properties -> new Item(properties.rarity(Rarity.RARE)));
    public static final Item EMPTY_SPIRIT_BOTTLE = registerItem("empty_spirit_bottle", properties -> new EmptySpiritBottleItem(properties.stacksTo(16)));
    public static final Item SPIRIT_BOTTLE = registerItem("spirit_bottle", properties -> new SpiritBottleItem(properties.stacksTo(1)));


    public static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, name)))));
    }

    public static void registerModItems() {
        CursedSlashSwords.LOGGER.info("アイテムの読み込み完了：MOD ID -> " + CursedSlashSwords.MOD_ID);
    }
}
