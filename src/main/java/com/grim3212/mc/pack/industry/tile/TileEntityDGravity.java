package com.grim3212.mc.pack.industry.tile;

import java.util.List;

import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class TileEntityDGravity extends TileEntity implements ITickable {

	private int type, side;
	private double power;
	private double gravity;
	public static final double[][] BBfromSide = new double[][] { { 0.0D, -17.0D, 0.0D, 1.0D, 0.0D, 1.0D }, { 0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 1.0D }, { 0.0D, 0.0D, -17.0D, 1.0D, 1.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 16.0D }, { -17.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D }, { 0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 1.0D } };

	public TileEntityDGravity() {
	}

	public TileEntityDGravity(int type, int side) {
		this.type = type;
		this.side = side;
		this.gravity = 0.9800000190734863D;
		this.power = this.gravity * (double) this.type;
	}

	@Override
	public void update() {
		double var1 = 1.0D;
		double var3 = 1.0D;
		double var5 = 1.0D;
		double var7 = 1.0D;
		double var9 = 1.0D;
		double var11 = 1.0D;

		int powerLevel = world.isBlockIndirectlyGettingPowered(pos);

		if (powerLevel > 0) {
			var1 = BBfromSide[side][0];
			var3 = BBfromSide[side][1];
			var5 = BBfromSide[side][2];
			var7 = BBfromSide[side][3];
			var9 = BBfromSide[side][4];
			var11 = BBfromSide[side][5];
			AxisAlignedBB aabb = new AxisAlignedBB((double) pos.getX() + var1, (double) pos.getY() + var3, (double) pos.getZ() + var5, (double) pos.getX() + var7, (double) pos.getY() + var9, (double) pos.getZ() + var11);
			List<Entity> entityList = this.world.getEntitiesWithinAABBExcludingEntity((Entity) null, aabb);

			for (int i = 0; i < entityList.size(); ++i) {
				Item gravBoots = IndustryItems.gravity_boots;
				Entity entity = entityList.get(i);
				if (!(entity instanceof EntityWeatherEffect)) {
					if (entity instanceof PlayerEntity) {
						// Check for GravityBoots
						ItemStack armorStack = ((PlayerEntity) entity).inventory.armorInventory.get(0);
						ItemStack heldStack = ((PlayerEntity) entity).inventory.getCurrentItem();
						if (armorStack != null) {
							if (armorStack.getItem() == gravBoots) {
								continue;
							}
						}

						if (heldStack != null) {
							if (heldStack.getItem() == gravBoots) {
								continue;
							}
						}
					}

					double var38 = entityList.get(i).getDistance((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
					if (var38 <= 12.0D) {
						Vec3d vector = new Vec3d(entity.posX - (double) pos.getX() - 0.5D, entity.posY - (double) pos.getY() - 0.5D, entity.posZ - (double) pos.getZ() - 0.5D);
						double var40 = vector.lengthVector();
						if (var40 <= 0.5D) {
							var40 = 0.5D;
						}

						double var23 = this.power / (var40 * var40 * var40);
						if (var1 == 0.0D && var7 == 1.0D) {
							var1 = 0.0D;
						} else {
							var1 = 1.0D;
						}

						if (var3 == 0.0D && var9 == 1.0D) {
							var3 = 0.0D;
						} else {
							var3 = 1.0D;
						}

						if (var5 == 0.0D && var11 == 1.0D) {
							var5 = 0.0D;
						} else {
							var5 = 1.0D;
						}

						// Calculate the vertical uplift more if it is a
						// Gravitor
						double gravitorPower = 0.0D;
						if (this.type == 0) {
							gravitorPower = 1.0D;
						}

						double upForce = 0D;
						if (entity.motionY <= 0.0D) {
							upForce += 0.084D * gravitorPower;
						}

						double x = vector.x * var23 * var1;
						double y = upForce + vector.y * var23 * var3;
						double z = vector.z * var23 * var5;

						double motionX = Math.max(Math.abs(0), Math.abs(entity.motionX));
						double motionY = Math.max(Math.abs(0), Math.abs(entity.motionY));
						double motionZ = Math.max(Math.abs(0), Math.abs(entity.motionZ));
						double fullMotion = motionX * motionX + motionY * motionY + motionZ * motionZ;

						if (fullMotion < 90.0D) {
							entity.addVelocity(x, y, z);

							// Reset fall damage
							entity.fallDistance = 0;
						}
					}
				}
			}
		}
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SUpdateTileEntityPacket(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return writeToNBT(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		readFromNBT(tag);
	}
}
