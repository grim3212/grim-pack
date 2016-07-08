package com.grim3212.mc.pack.decor.entity;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.grim3212.mc.pack.core.util.WorldHelper;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWallpaper extends EntityHanging implements IEntityAdditionalSpawnData {

	public static final int[] colorValues = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 8816262, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 16777215, 2236962 };
	public boolean isBlockUp;
	public boolean isBlockDown;
	public boolean isBlockLeft;
	public boolean isBlockRight;

	public AxisAlignedBB fireboundingBox;

	private static final DataParameter<Boolean> BURNT = EntityDataManager.<Boolean> createKey(EntityFrame.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> WALLPAPER_ID = EntityDataManager.<Integer> createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_RED = EntityDataManager.<Integer> createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_GREEN = EntityDataManager.<Integer> createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_BLUE = EntityDataManager.<Integer> createKey(EntityFrame.class, DataSerializers.VARINT);

	public EntityWallpaper(World world) {
		super(world);
		this.isBlockUp = false;
		this.isBlockDown = false;
		this.isBlockLeft = false;
		this.isBlockRight = false;
		this.fireboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.facingDirection = EnumFacing.SOUTH;
		setSize(0.5F, 0.5F);
	}

	public EntityWallpaper(World world, BlockPos pos, EnumFacing direction) {
		this(world);
		this.hangingPosition = pos;
		this.updateFacingWithBoundingBox(direction);

		List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.fireboundingBox);

		for (int i = 0; i < entities.size(); i++) {
			if ((entities.get(i) instanceof EntityWallpaper)) {
				EntityWallpaper entWallpaper = (EntityWallpaper) entities.get(i);
				this.getDataManager().set(WALLPAPER_ID, entWallpaper.getWallpaperID());

				if ((DecorConfig.copyDye) && (!entWallpaper.getBurned())) {
					this.getDataManager().set(COLOR_RED, entWallpaper.getWallpaperColor()[0]);
					this.getDataManager().set(COLOR_GREEN, entWallpaper.getWallpaperColor()[1]);
					this.getDataManager().set(COLOR_BLUE, entWallpaper.getWallpaperColor()[2]);
				}
			}
		}
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
		if (stack != null) {
			if ((DecorConfig.dyeWallpaper) && (stack.getItem() == Items.DYE)) {
				dyeWallpaper(stack.getItemDamage());
				stack.stackSize -= 1;
				return true;
			}

			if (stack.getItem() == DecorItems.wallpaper) {
				return updateWallpaper();
			}
		}

		return false;
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(WALLPAPER_ID, 0);
		this.getDataManager().register(COLOR_RED, 256);
		this.getDataManager().register(COLOR_GREEN, 256);
		this.getDataManager().register(COLOR_BLUE, 256);
		this.getDataManager().register(BURNT, false);
	}

	public boolean updateWallpaper() {
		int newWallpaper = this.getWallpaperID() + 1;

		if (newWallpaper >= DecorConfig.numWallpapers) {
			newWallpaper = 0;
		}

		this.getDataManager().set(WALLPAPER_ID, newWallpaper);
		if (!this.worldObj.isRemote)
			playPlaceSound();

		return true;
	}

	public boolean updateWallpaper(int wallpaper) {
		this.getDataManager().set(WALLPAPER_ID, wallpaper);

		if (!this.worldObj.isRemote)
			playPlaceSound();

		return true;
	}

	public void dyeWallpaper(int color, boolean burn) {
		int newred = ((colorValues[color] & 0xFF0000) >> 16);
		int newgreen = ((colorValues[color] & 0xFF00) >> 8);
		int newblue = (colorValues[color] & 0xFF);

		if (color != 16) {
			this.getDataManager().set(BURNT, false);
		}

		this.getDataManager().set(COLOR_RED, newred);
		this.getDataManager().set(COLOR_GREEN, newgreen);
		this.getDataManager().set(COLOR_BLUE, newblue);

		if (!this.worldObj.isRemote) {
			if (burn) {
				playBurnSound();
			} else {
				playPlaceSound();
			}
		}
	}

	public void dyeWallpaper(int color) {
		dyeWallpaper(color, false);
	}

	@Override
	public void onUpdate() {
		if (!this.worldObj.isRemote) {
			if (!onValidSurface()) {
				setDead();
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
			}
		}

		if ((DecorConfig.burnWallpaper) && (WorldHelper.isAABBInMaterial(worldObj, this.fireboundingBox.expand(-0.001D, -0.001D, -0.001D), Material.FIRE)) && (!this.getBurned())) {
			dyeWallpaper(16, true);
			this.getDataManager().set(BURNT, true);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canBeCollidedWith() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = player.inventory.getCurrentItem();
		return (stack != null) && ((stack.getItem() == DecorItems.wallpaper) || (stack.getItem() instanceof ItemAxe) || (stack.getItem() == Items.DYE));
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public boolean attackEntityFrom(DamageSource damage, float amount) {
		if (this.isEntityInvulnerable(damage)) {
			return false;
		} else {
			if ((!this.isDead) && (!this.worldObj.isRemote)) {
				setDead();
				setBeenAttacked();
				EntityPlayer player = null;

				if ((damage.getEntity() instanceof EntityPlayer)) {
					player = (EntityPlayer) damage.getEntity();
					playPlaceSound();
				}

				if ((player != null) && (player.capabilities.isCreativeMode)) {
					return true;
				}

				switch (facingDirection) {
				case SOUTH:
					this.posZ--;
					this.posY -= 0.5D;
					break;
				case WEST:
					this.posX--;
					this.posY -= 0.5D;
					break;
				case NORTH:
					this.posZ++;
					this.posY -= 0.5D;
					break;
				case EAST:
					this.posX++;
					this.posY -= 0.5D;
					break;
				}

				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
			}
			return true;
		}
	}

	@Override
	protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
		Validate.notNull(facingDirectionIn);
		Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
		this.facingDirection = facingDirectionIn;

		if (facingDirectionIn == EnumFacing.SOUTH || facingDirectionIn == EnumFacing.NORTH)
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getOpposite().getHorizontalIndex() * 90);
		else
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getHorizontalIndex() * 90);

		double d0 = (double) this.hangingPosition.getX() + 0.5D;
		double d1 = (double) this.hangingPosition.getY() + 0.5D;
		double d2 = (double) this.hangingPosition.getZ() + 0.5D;
		double d4 = this.offs(this.getWidthPixels());
		double d5 = this.offs(this.getHeightPixels());
		d0 = d0 - (double) this.facingDirection.getFrontOffsetX() * 0.46875D;
		d2 = d2 - (double) this.facingDirection.getFrontOffsetZ() * 0.46875D;
		d1 = d1 + d5;
		EnumFacing enumfacing = this.facingDirection.rotateYCCW();
		d0 = d0 + d4 * (double) enumfacing.getFrontOffsetX();
		d2 = d2 + d4 * (double) enumfacing.getFrontOffsetZ();
		this.posX = d0;
		this.posY = d1;
		this.posZ = d2;
		double d6 = (double) this.getWidthPixels();
		double d7 = (double) this.getHeightPixels();
		double d8 = (double) this.getWidthPixels();

		if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
			d8 = 1.0D;
		} else {
			d6 = 1.0D;
		}

		d6 = d6 / 32.0D;
		d7 = d7 / 32.0D;
		d8 = d8 / 32.0D;
		this.setEntityBoundingBox(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
		d6 = 1.0F;
		d7 = 1.0F;
		d8 = 1.0F;
		this.fireboundingBox = new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
	}

	private double offs(int i) {
		return i % 32 == 0 ? 0.5D : 0.0D;
	}

	@Override
	public boolean onValidSurface() {
		if (!this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
			return false;
		} else {
			int i = Math.max(1, this.getWidthPixels() / 16);
			int j = Math.max(1, this.getHeightPixels() / 16);
			BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
			EnumFacing enumfacing = this.facingDirection.rotateYCCW();

			for (int k = 0; k < i; ++k) {
				for (int l = 0; l < j; ++l) {
					int i1 = i > 2 ? -1 : 0;
					int j1 = j > 2 ? -1 : 0;
					BlockPos blockpos1 = blockpos.offset(enumfacing, k + i1).up(l + j1);
					IBlockState iblockstate = this.worldObj.getBlockState(blockpos1);

					if (iblockstate.isSideSolid(this.worldObj, blockpos1, this.facingDirection))
						continue;

					if (!iblockstate.getMaterial().isSolid() && !BlockRedstoneDiode.isDiode(iblockstate)) {
						return false;
					}
				}
			}

			List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
			for (int m = 0; m < entities.size(); m++) {
				if ((entities.get(m) instanceof EntityWallpaper)) {
					EntityWallpaper entWallpaper = (EntityWallpaper) entities.get(m);

					if (entWallpaper.facingDirection == this.facingDirection) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public int getWallpaperID() {
		return this.getDataManager().get(WALLPAPER_ID);
	}

	public int[] getWallpaperColor() {
		return new int[] { this.getDataManager().get(COLOR_RED), this.getDataManager().get(COLOR_GREEN), this.getDataManager().get(COLOR_BLUE) };
	}

	public boolean getBurned() {
		return this.getDataManager().get(BURNT);
	}

	public void playBurnSound() {
		this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Motive", this.getWallpaperID());
		nbt.setInteger("Red", this.getWallpaperColor()[0]);
		nbt.setInteger("Green", this.getWallpaperColor()[1]);
		nbt.setInteger("Blue", this.getWallpaperColor()[2]);
		nbt.setBoolean("Burnt", this.getBurned());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.getDataManager().set(WALLPAPER_ID, nbt.getInteger("Motive"));
		this.getDataManager().set(COLOR_RED, nbt.getInteger("Red"));
		this.getDataManager().set(COLOR_GREEN, nbt.getInteger("Green"));
		this.getDataManager().set(COLOR_BLUE, nbt.getInteger("Blue"));
		this.getDataManager().set(BURNT, nbt.getBoolean("Burnt"));
	}

	@Override
	public void moveEntity(double var1, double var3, double var5) {
		if ((!this.worldObj.isRemote) && (!this.isDead) && (var1 * var1 + var3 * var3 + var5 * var5 > 0.0D)) {
			setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
		}
	}

	@Override
	public void addVelocity(double var1, double var3, double var5) {
		if ((!this.worldObj.isRemote) && (!this.isDead) && (var1 * var1 + var3 * var3 + var5 * var5 > 0.0D)) {
			setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buf) {
		buf.writeInt(this.hangingPosition.getX());
		buf.writeInt(this.hangingPosition.getY());
		buf.writeInt(this.hangingPosition.getZ());
		buf.writeInt(this.getWallpaperID());
		buf.writeInt(this.facingDirection.getHorizontalIndex());
		buf.writeInt(this.getWallpaperColor()[0]);
		buf.writeInt(this.getWallpaperColor()[1]);
		buf.writeInt(this.getWallpaperColor()[2]);
		buf.writeBoolean(this.getBurned());
	}

	@Override
	public void readSpawnData(ByteBuf buf) {
		this.hangingPosition = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.getDataManager().set(WALLPAPER_ID, buf.readInt());
		updateFacingWithBoundingBox(EnumFacing.getHorizontal(buf.readInt()));
		this.getDataManager().set(COLOR_RED, buf.readInt());
		this.getDataManager().set(COLOR_GREEN, buf.readInt());
		this.getDataManager().set(COLOR_BLUE, buf.readInt());
		this.getDataManager().set(BURNT, buf.readBoolean());
	}

	@Override
	public int getWidthPixels() {
		return 16;
	}

	@Override
	public int getHeightPixels() {
		return 16;
	}

	@Override
	public void onBroken(Entity entity) {
	}

	@Override
	public void playPlaceSound() {
		this.playSound(SoundEvents.BLOCK_CLOTH_STEP, 1.0F, 0.8F);
	}
}