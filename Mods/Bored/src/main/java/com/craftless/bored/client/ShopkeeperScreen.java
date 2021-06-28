package com.craftless.test.client;

import java.util.Set;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ShopkeeperScreen extends Screen
{

	private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("minecraft", "textures/gui/recipe_button.png");
	protected PlayerEntity player;
	
	public ShopkeeperScreen(ITextComponent titleIn, PlayerEntity player) {
		super(titleIn);
		
		this.addButton(new ImageButton(10 + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
	         if (checkIfPlayerHasEnough(4, Items.IRON_INGOT))
	         {
	        	 player.addItemStackToInventory(new ItemStack(Items.WHITE_WOOL, 16));
	         }
	         ((ImageButton)button).setPosition(10 + 5, this.height / 2 - 49);
	      }));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		
		this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private boolean checkIfPlayerHasEnough(int amount, Item item)
	{
		if (player.inventory.count(item) >= amount)
		{
			int amount2 = amount;
			for(int j = 0; j < player.inventory.getSizeInventory(); ++j) 
			{
		         ItemStack itemstack = player.inventory.getStackInSlot(j);
		         if (itemstack.getItem().equals(item)) 
		         {
		        	if (itemstack.getCount() > amount2)
		        	{
		        		itemstack.shrink(amount2);
		        		amount2 -= amount2;
		        	}
		        	else
		        	{
		        		amount2 -= itemstack.getCount();
		        	}
		         }
		         if (amount2 <= 0)
		         {
		        	 continue;
		         }
		     }
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	

}
 