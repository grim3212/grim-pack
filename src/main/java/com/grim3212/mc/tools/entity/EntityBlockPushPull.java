package com.grim3212.mc.tools.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBlockPushPull extends EntityFallingBlock {

	private double startX;
	private double startZ;

	public EntityBlockPushPull(World worldIn) {
		super(worldIn);
	}

	public EntityBlockPushPull(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
		super(worldIn, x, y, z, fallingBlockState);
		this.startX = x;
		this.startZ = z;
	}

	@Override
	public void onUpdate() {
		if (getBlock() != null) {
			Block block = this.getBlock().getBlock();

			if (block.getMaterial() == Material.air) {
				this.setDead();
			} else {
				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;

				if (this.fallTime++ == 0) {
					BlockPos blockpos = new BlockPos(this);

					if (this.worldObj.getBlockState(blockpos).getBlock() == block) {
						this.worldObj.setBlockToAir(blockpos);
					} else if (!this.worldObj.isRemote) {
						this.setDead();
						return;
					}
				}

				this.motionY -= 0.03999999910593033D;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.9800000190734863D;
				this.motionY *= 0.9800000190734863D;
				this.motionZ *= 0.9800000190734863D;

				BlockPos blockpos1 = new BlockPos(this);

				if (this.onGround && Math.abs(motionX) < 0.01D && Math.abs(motionZ) < 0.01D) {
					this.motionX *= 0.699999988079071D;
					this.motionZ *= 0.699999988079071D;
					this.motionY *= -0.5D;
					this.setDead();
					worldObj.setBlockState(blockpos1, this.getBlock(), 3);
				} else if (this.fallTime > 100 && !this.worldObj.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600) {
					if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
						this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.getBlock())), 0.0F);
					}

					this.setDead();
				}

				if (Math.abs(posX - this.startX) >= 1.0D) {
					motionX = 0.0D;
					posX = Math.floor(posX) + 0.5D;
				}
				if (Math.abs(posZ - this.startZ) >= 1.0D) {
					motionZ = 0.0D;
					posZ = Math.floor(posZ) + 0.5D;
				}
			}
		}
	}
}