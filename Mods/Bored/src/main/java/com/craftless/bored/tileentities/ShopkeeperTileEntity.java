package com.craftless.test.tileentities;

import com.craftless.test.init.ModTileEntityTypes;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ShopkeeperTileEntity extends TileEntity
{

	public ShopkeeperTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}
	
	public ShopkeeperTileEntity()
	{
		this(ModTileEntityTypes.SHOPKEEPER_TILE_ENTITY.get());
	}

}
