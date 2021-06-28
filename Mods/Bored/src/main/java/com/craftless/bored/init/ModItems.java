package com.craftless.bored.init;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;

import com.craftless.bored.Bored;
import com.craftless.bored.items.AOTE;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems 
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bored.MOD_ID);
	
	
	public static final RegistryObject<SwordItem> ASPECT_OF_THE_END = ITEMS.register("aspect_of_the_end", () -> new AOTE(ItemTier.DIAMOND, 7, -2, new Item.Properties()));

	//Block Items
	public static final RegistryObject<Item> SHOPKEEPER_BLOCK_ITEM = ITEMS.register("shopkeeper", () -> new BlockItem(ModBlocks.SHOPKEEPER_BLOCK.get(), new Item.Properties().group(Bored.BORED_TAB)));
	
	
}
