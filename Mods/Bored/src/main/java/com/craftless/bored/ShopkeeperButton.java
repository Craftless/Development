package com.craftless.test;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class ShopkeeperButton extends Button
{

	public ShopkeeperButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction,
			ITooltip onTooltip) {
		super(x, y, width, height, title, pressedAction, onTooltip);
		
	}
	
	public ShopkeeperButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
		super(x, y, width, height, title, pressedAction);
	}

	

}
