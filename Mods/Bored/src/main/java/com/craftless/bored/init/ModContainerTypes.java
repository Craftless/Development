package com.craftless.test.init;

import com.craftless.test.Test;
import com.craftless.test.containers.ShopkeeperContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes 
{
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Test.MOD_ID);
	
	public static final RegistryObject<ContainerType<ShopkeeperContainer>> SHOPKEEPER = CONTAINER_TYPES.register("shopkeeper", () -> IForgeContainerType.create(ShopkeeperContainer::new));

}
