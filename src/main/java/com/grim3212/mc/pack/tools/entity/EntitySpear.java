package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.entity.EntityProjectile;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.tools.util.EnumSpearType;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpear extends EntityProjectile {

	protected EnumSpearType type;

	public EntitySpear(World worldIn) {
		super(worldIn);
	}

	public EntitySpear(World world, double x, double y, double z, EnumSpearType type) {
		super(world, x, y, z);
		this.type = type;
		setDamage(this.type.getDamage());
	}

	public EntitySpear(World world, EntityLivingBase shooter, EnumSpearType type) {
		super(world, shooter);
		this.type = type;
		setDamage(this.type.getDamage());
	}

	@Override
	public ItemStack getArrowStack() {
		switch (type) {
		case STONE:
			return new ItemStack(ToolsItems.spear);
		case IRON:
			return new ItemStack(ToolsItems.iron_spear);
		case DIAMOND:
			return new ItemStack(ToolsItems.diamond_spear);
		case EXPLOSIVE:
			return new ItemStack(ToolsItems.iron_spear);
		case FIRE:
			return new ItemStack(ToolsItems.spear);
		case SLIME:
			return new ItemStack(ToolsItems.slime_spear);
		case LIGHT:
			return new ItemStack(ToolsItems.spear);
		case LIGHTNING:
			return new ItemStack(ToolsItems.iron_spear);
		default:
			return new ItemStack(ToolsItems.spear);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("type", this.type.name());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.type = EnumSpearType.valueOf(compound.getString("type"));
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		super.arrowHit(living);

		if (this.type == EnumSpearType.EXPLOSIVE) {
			if (!worldObj.isRemote)
				worldObj.createExplosion(null, posX, posY, posZ, 3F, true);
			setDead();
		} else if (this.type == EnumSpearType.LIGHTNING) {
			EntityLightningBolt entitylightningbolt1 = new EntityLightningBolt(worldObj, 1.0D, 1.0D, 1.0D, true);
			entitylightningbolt1.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.addWeatherEffect(entitylightningbolt1);
			this.inGround = true;
		} else if (this.type == EnumSpearType.FIRE) {
			for (int fire = 0; fire < 6; ++fire) {
				BlockPos blockPos = this.getPosition().add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);

				if (worldObj.getBlockState(blockPos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(worldObj, blockPos)) {
					worldObj.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
				}
			}
			setDead();
		}
	}

	@Override
	protected void arrowLand(RayTraceResult raytraceResultIn, BlockPos pos, IBlockState state) {
		super.arrowLand(raytraceResultIn, pos, state);

		if (this.type == EnumSpearType.LIGHT) {
			if (raytraceResultIn.sideHit == EnumFacing.UP) {
				if (!worldObj.isRemote && state.getBlock() != Blocks.AIR && this.getEntityWorld().getBlockState(raytraceResultIn.getBlockPos().up(2)).getBlock() == Blocks.AIR) {
					worldObj.setBlockState(raytraceResultIn.getBlockPos().up(), Blocks.TORCH.getDefaultState());
				}
			} else {
				if (!worldObj.isRemote && state.getBlock() != Blocks.AIR && this.getEntityWorld().getBlockState(raytraceResultIn.getBlockPos().offset(raytraceResultIn.sideHit, 2)).getBlock() == Blocks.AIR) {
					worldObj.setBlockState(raytraceResultIn.getBlockPos().offset(raytraceResultIn.sideHit), Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, raytraceResultIn.sideHit));
				}
			}
			this.inGround = true;
		} else if (this.type == EnumSpearType.LIGHTNING) {
			EntityLightningBolt entitylightningbolt1 = new EntityLightningBolt(worldObj, 1.0D, 1.0D, 1.0D, true);
			entitylightningbolt1.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.addWeatherEffect(entitylightningbolt1);
			this.inGround = true;
		} else if (this.type == EnumSpearType.FIRE) {
			for (int fire = 0; fire < 6; ++fire) {
				BlockPos blockPos = this.getPosition().add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);

				if (worldObj.getBlockState(blockPos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(worldObj, blockPos)) {
					worldObj.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
				}
			}
			setDead();
		} else if (this.type == EnumSpearType.EXPLOSIVE) {
			if (!worldObj.isRemote)
				worldObj.createExplosion(null, posX, posY, posZ, 3F, true);
			setDead();
		} else {
			this.inGround = true;
		}
	}
}