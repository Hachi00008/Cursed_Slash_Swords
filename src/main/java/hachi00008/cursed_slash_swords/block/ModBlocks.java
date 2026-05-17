package hachi00008.cursed_slash_swords.block;

import hachi00008.cursed_slash_swords.CursedSlashSwords;
import hachi00008.cursed_slash_swords.block.custom.SoulForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
    public static final Block SPIRITUAL_IRON_BLOCK = registerBlock("spiritual_iron_block",
            properties -> new Block(properties.strength(4f)
                    .requiresCorrectToolForDrops().sound(SoundType.IRON)));

    public static final Block SOUL_FORGE = registerBlock("soul_forge",
            properties -> new SoulForge(properties.strength(4f)
                    .requiresCorrectToolForDrops().sound(SoundType.STONE)));



    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, name),
                toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, name),
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, name)))));
    }

    public static void registerModBlocks() {
        CursedSlashSwords.LOGGER.info("ブロックの読み込み完了：MOD ID -> " + CursedSlashSwords.MOD_ID);
    }
}
