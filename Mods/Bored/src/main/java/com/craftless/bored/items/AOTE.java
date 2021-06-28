package com.craftless.test.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AOTE extends SwordItem
{

	public AOTE(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.setPositionAndRotationDirect(playerIn.getPosition().getX() + playerIn.getLookVec().getX() * 8, playerIn.getPosition().getY() + playerIn.getLookVec().getY() * 8, playerIn.getPosition().getZ() + playerIn.getLookVec().getZ() * 8, playerIn.getPitchYaw().y, playerIn.getPitchYaw().x, 3, false);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	
	
}
