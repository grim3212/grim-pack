package com.grim3212.mc.pack.industry.client.particle;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.block.BlockFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan.FanMode;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleAir extends Particle {

	private static final ResourceLocation AIR_TEXTURE = new ResourceLocation(GrimPack.modID, "entities/whiteAir");

	private TileEntityFan fan;
	private double xSpeed;
	private double ySpeed;
	private double zSpeed;
	private BlockPos moveToPos;
	private EnumFacing facing;

	public ParticleAir(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, TileEntityFan fan) {
		super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);

		this.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(AIR_TEXTURE.toString()));

		this.xSpeed = xSpeedIn;
		this.ySpeed = ySpeedIn;
		this.zSpeed = zSpeedIn;
		this.fan = fan;
		this.facing = worldIn.getBlockState(fan.getPos()).getValue(BlockFan.FACING);

		if (fan.getMode() == FanMode.BLOW) {
			this.moveToPos = fan.getPos().offset(facing, fan.getRange());
		} else if (fan.getMode() == FanMode.SUCK) {
			this.moveToPos = fan.getPos();
		}

		if (this.fan.getRange() > 16) {
			this.particleMaxAge = (int) ((this.fan.getRange() / 4.0F) / (this.rand.nextFloat() * 0.9F + 0.1F));
		}

		particleRed = particleGreen = particleBlue = 1.0F;
		particleGravity = 0.0F;
		particleScale /= 2.0F;
		motionX = motionY = motionZ = 0.0D;
		this.particleGravity = 0.0F;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.isCollided) {
			this.setExpired();
		}

		double d = getDistance(this.moveToPos.getX() + 0.5D, this.moveToPos.getY() + 0.5D, this.moveToPos.getZ() + 0.5D) - 1;
		d = 1 - (d / fan.getRange());
		double multiplier = fan.getRange();
		multiplier = multiplier > 8 ? 8 : multiplier;
		double power = 0.029999999999999999D * multiplier;
		power = power * d;

		if (fan.getMode() == FanMode.BLOW) {
			switch (facing) {
			case DOWN:
				if (this.motionY > 0)
					this.setExpired();
				break;
			case EAST:
				if (this.motionX < 0)
					this.setExpired();
				break;
			case NORTH:
				if (this.motionZ > 0)
					this.setExpired();
				break;
			case SOUTH:
				if (this.motionZ < 0)
					this.setExpired();
				break;
			case UP:
				if (this.motionY < 0)
					this.setExpired();
				break;
			case WEST:
				if (this.motionX > 0)
					this.setExpired();
				break;
			}
		} else if (fan.getMode() == FanMode.SUCK) {
			switch (facing) {
			case DOWN:
				if (this.motionY < 0) {
					this.ySpeed = -ySpeed;
					this.motionY = -this.motionY;
				}
				break;
			case EAST:
				if (this.motionX > 0) {
					this.xSpeed = -xSpeed;
					this.motionX = -this.motionX;
				}
				break;
			case NORTH:
				if (this.motionZ < 0) {
					this.zSpeed = -zSpeed;
					this.motionZ = -this.motionZ;
				}
				break;
			case SOUTH:
				if (this.motionZ > 0) {
					this.zSpeed = -zSpeed;
					this.motionZ = -this.motionZ;
				}
				break;
			case UP:
				if (this.motionY > 0) {
					this.ySpeed = -ySpeed;
					this.motionY = -this.motionY;
				}
				break;
			case WEST:
				if (this.motionX < 0) {
					this.xSpeed = -xSpeed;
					this.motionX = -this.motionX;
				}
				break;
			}
		}

		this.addVelocity(power * xSpeed, power * ySpeed, power * zSpeed);
	}

	public double getDistance(double x, double y, double z) {
		double d0 = this.posX - x;
		double d1 = this.posY - y;
		double d2 = this.posZ - z;
		return (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
	}

	public void addVelocity(double x, double y, double z) {
		this.motionX += x;
		this.motionY += y;
		this.motionZ += z;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}
}
