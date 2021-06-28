package com.craftless.test.containers;

import com.craftless.test.init.ModContainerTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;

public class ShopkeeperContainer extends Container
{

	public IWorldPosCallable distance;
	
	public ShopkeeperContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	public ShopkeeperContainer(int id, PlayerInventory inv, PacketBuffer buffer)
	{
		this(ModContainerTypes.SHOPKEEPER.get(), id);

	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return false;
	}
	
	
	
	
	
}
