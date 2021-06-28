package com.example.examplemod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class SHFI extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{

	public SHFI(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return null;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public void tick() {
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
	}

}
