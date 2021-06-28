package com.craftless.test.blocks;

import com.craftless.test.client.ShopkeeperScreen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ShopkeeperBlock extends Block
{

	public ShopkeeperBlock() {
		super(Block.Properties.from((Blocks.COBBLESTONE)));
	}
	/*
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.SHOPKEEPER_TILE_ENTITY.get().create();
	}
	
	*/
	
	
	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		if (worldIn.isRemote)
		{
			Minecraft.getInstance().displayGuiScreen(new ShopkeeperScreen(new StringTextComponent("HELLO"), player));
		}
		super.onBlockClicked(state, worldIn, pos, player);
	}
	
}
