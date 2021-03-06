package com.craftless.tutorial.tileentities;

import javax.annotation.Nullable;

import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.util.helpers.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class QuarryTileEntity extends TileEntity implements ITickableTileEntity
{

	public int x, y, z, tick, thing;
	boolean initialized = false;
	
	public QuarryTileEntity(final TileEntityType<?> tileEntityTypeIn) 
	{
		super(tileEntityTypeIn);
	}
	
	public QuarryTileEntity()
	{
		this(ModTileEntityTypes.QUARRY.get());
	}
	

	@Override
	public void tick() {
		if (!initialized)
		{
			init();
		}
		tick ++;
		if (tick == 40)
		{
			tick = 0;
			if (y > 2)
			{
				execute();
			}
		}
		
	}
	
	private void init() {
		initialized = true;
		x = this.pos.getX() - 1;
		y = this.pos.getY() - 1;
		z = this.pos.getZ() - 1;
		tick = 0;
		thing = 0;
	}
	private void execute()
	{
		int index = 0;
		Block[] blocksRemoved = new Block[25];
		for (int x = 0; x < Math.sqrt(blocksRemoved.length)/*blocksRemoved.length*/; x++)
		{
			for (int z = 0; z < Math.sqrt(blocksRemoved.length)/*blocksRemoved.length*/; z++)
			{
				BlockPos posToBreak = new BlockPos(this.x + x, this.y, this.z + z);
				blocksRemoved[index] = this.world.getBlockState(posToBreak).getBlock();
				destroyBlock(posToBreak, true, null);
				/*
				if(thing > 5)
				{
					world.setBlockState(posToBreak, Blocks.GLOWSTONE.getDefaultState());
					thing = 0;
				}
				*/
				index++;
			}
		}
		this.y--;
		//thing++;
	}
	
	private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) 
	{
		BlockState blockstate = world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();
		if (blockstate.isAir(world, pos) || block == Blocks.BEDROCK) 
			return false;
		else
		{
			FluidState fluidstate = world.getFluidState(pos);
			world.playEvent(2001, pos, Block.getStateId(blockstate));
			if (dropBlock)
			{
				TileEntity tileEntity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
				Block.spawnDrops(blockstate,  world,  this.pos.add(0, 1.5, 0), tileEntity, entity, ItemStack.EMPTY);
			}
			return world.setBlockState(pos, fluidstate.getBlockState(), 3);
		}
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("initvalues", NBTHelper.toNBT(this));
		return super.write(compound);
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		CompoundNBT initValues = compound.getCompound("initvalues");
		if (initValues != null)
		{
			this.x = initValues.getInt("x");
			this.y = initValues.getInt("y");
			this.z = initValues.getInt("z");
			this.tick = 0;
			initialized = true;
			return;
		}
		
		init();
		
	}
	
}
