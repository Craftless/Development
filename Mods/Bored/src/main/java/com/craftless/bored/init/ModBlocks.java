package com.craftless.test.init;

import com.craftless.test.blocks.MithrilOre;
import com.craftless.test.blocks.ShopkeeperBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks 
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "test");
	
	public static final RegistryObject<Block> SHOPKEEPER_BLOCK = BLOCKS.register("shopkeeper_block", ShopkeeperBlock::new);
	public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", () -> new MithrilOre());
}
