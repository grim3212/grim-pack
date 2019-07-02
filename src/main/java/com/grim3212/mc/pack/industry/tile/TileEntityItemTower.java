package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.client.model.ModelItemTower;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.SoundEvent;

public class TileEntityItemTower extends TileEntityStorage {

	// private int animate = 0;
	public ModelItemTower model = new ModelItemTower();

	@Override
	protected String getStorageName() {
		return "item_tower";
	}

	@Override
	protected SoundEvent getCloseSound() {
		return null;
	}

	@Override
	protected SoundEvent getOpenSound() {
		return null;
	}

	@Override
	public BlockState getBreakTextureState() {
		return Blocks.IRON_BLOCK.getDefaultState();
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	public void animate(int animId) {
		model.setAnimation(animId);
	}

	// public int getAnimate() {
	// return animate;
	// }
	//
	// public boolean isAnimate() {
	// return animate != 0;
	// }

	// public int getLowestMetadata() {
	// int ymod = 0;
	//
	// while (this.world.getBlockState(this.pos.down(ymod - 1)).getBlock() ==
	// this.world.getBlockState(pos).getBlock())
	// ymod++;
	//
	// return this.world.getBlockMetadata(this.xCoord, this.yCoord - ymod,
	// this.zCoord);
	// }

}