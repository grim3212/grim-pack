package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.inventory.ContainerLocker;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityLocker extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "locker";
	}

	@Override
	public BlockState getBreakTextureState() {
		return Blocks.IRON_BLOCK.getDefaultState();
	}

	@Override
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
		return new ContainerLocker(playerIn, this);
	}

	@Override
	public int getSizeInventory() {
		return 45;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return hasUpperLocker() ? super.getRenderBoundingBox().expand(0, 1, 0) : super.getRenderBoundingBox();
	}

	public boolean isUpperLocker() {
		if (this.world == null)
			return false;
		return this.world.getBlockState(pos.down()) == this.world.getBlockState(pos);
	}

	public boolean hasUpperLocker() {
		if (this.world == null)
			return false;
		return this.world.getBlockState(this.pos.up()) == this.world.getBlockState(pos);
	}

	public TileEntityLocker getUpperLocker() {
		if ((this.world == null) || (!hasUpperLocker()))
			return null;
		return (TileEntityLocker) this.world.getTileEntity(pos.up());
	}

	public boolean isBottomLocker() {
		if (this.world == null)
			return false;
		return this.world.getBlockState(pos.up()) == this.world.getBlockState(pos);
	}

	public boolean hasBottomLocker() {
		if (this.world == null)
			return false;
		return this.world.getBlockState(this.pos.down()) == this.world.getBlockState(pos);
	}

	public TileEntityLocker getBottomLocker() {
		if ((this.world == null) || (!hasUpperLocker()))
			return null;
		return (TileEntityLocker) this.world.getTileEntity(pos.down());
	}
}
