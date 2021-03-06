package com.craftless.tutorial.blocks;

import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.tileentities.RubyChestTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RubyChestBlock extends Block
{

	
	public RubyChestBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return  true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return ModTileEntityTypes.RUBY_CHEST.get().create();
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {
		if (!worldIn.isRemote)
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof RubyChestTileEntity)
			{
				NetworkHooks.openGui((ServerPlayerEntity)player,  (RubyChestTileEntity)tile, pos);
				return ActionResultType.SUCCESS;
			}
			
		}
		return ActionResultType.FAIL;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof RubyChestTileEntity)
			{
				InventoryHelper.dropItems(worldIn, pos, ((RubyChestTileEntity)te).getItems());
			}
		}
	}
	
}


