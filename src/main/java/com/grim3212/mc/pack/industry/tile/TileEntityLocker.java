package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.inventory.ContainerLocker;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityLocker extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "locker";
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.IRON_BLOCK.getDefaultState();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerLocker(playerIn, this);
	}

	@Override
	public int getSizeInventory() {
		return 45;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return hasUpperLocker() ? super.getRenderBoundingBox().addCoord(0, 1, 0) : super.getRenderBoundingBox();
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
