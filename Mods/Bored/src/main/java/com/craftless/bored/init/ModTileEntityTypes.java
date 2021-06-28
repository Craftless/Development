package com.craftless.test.init;

import com.craftless.test.Test;
import com.craftless.test.tileentities.ShopkeeperTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes 
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Test.MOD_ID);
	
	public static final RegistryObject<TileEntityType<ShopkeeperTileEntity>> SHOPKEEPER_TILE_ENTITY = TILE_ENTITY_TYPES.register("shopkeeper_tile_entity", () -> TileEntityType.Builder.create(ShopkeeperTileEntity::new, ModBlocks.SHOPKEEPER_BLOCK.get()).build(null));
}
