package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Shooter extends Item 
{

	public Shooter() 
	{
		super(new Item.Properties().maxStackSize(1).group(Tutorial.TAB).isBurnable());
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		stack.damageItem(1, entityLiving, null);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	

}
