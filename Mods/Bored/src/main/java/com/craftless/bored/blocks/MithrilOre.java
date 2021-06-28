package com.craftless.test.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class MithrilOre extends OreBlock
{

	public MithrilOre() {
		super(Properties.from(Blocks.IRON_ORE));
	}
	
	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? MathHelper.nextInt(RANDOM, 1, 20) : 0;
	}

}
