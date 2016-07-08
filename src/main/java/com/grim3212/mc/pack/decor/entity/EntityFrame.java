package com.grim3212.mc.pack.decor.entity;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.grim3212.mc.pack.core.util.WorldHelper;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.util.EnumFrame;

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
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFrame extends EntityHanging implements IEntityAdditionalSpawnData {

	public int material = 0;
	public float resistance = 0.0F;
	public AxisAlignedBB fireboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	public static final int[] colorValues = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 8816262, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 16777215 };

	private static final DataParameter<Boolean> BURNT = EntityDataManager.createKey(EntityFrame.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> FRAME_ID = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_RED = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_GREEN = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_BLUE = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);

	public EntityFrame(World world) {
		super(world);
	}

	public EntityFrame(World world, BlockPos pos, EnumFacing direction, int material) {
		super(world, pos);
		this.material = material;

		for (int i = 0; i < EnumFrame.VALUES.length; i++) {
			EnumFrame tryFrame = EnumFrame.VALUES[i];
			this.getDataManager().set(FRAME_ID, tryFrame.id);
			updateFacingWithBoundingBox(direction);
			if (onValidSurface()) {
				// For the first valid direction update the frame and you don't
				// need to search anymore
				break;
			}
		}

		setResistance(this.material);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(FRAME_ID, 1);
		this.getDataManager().register(COLOR_RED, 256);
		this.getDataManager().register(COLOR_GREEN, 256);
		this.getDataManager().register(COLOR_BLUE, 256);
		this.getDataManager().register(BURNT, false);
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) {
		ItemStack itemstack = player.inventory.getCurrentItem();
		if (player.canPlayerEdit(hangingPosition, this.facingDirection, itemstack)) {
			if (itemstack != null) {
				if ((DecorConfig.dyeFrames) && (itemstack.getItem() == Items.DYE)) {
					if (dyeFrame(itemstack.getItemDamage())) {
						if (!player.capabilities.isCreativeMode) {
							itemstack.stackSize -= 1;
						}
						return EnumActionResult.SUCCESS;
					}
				} else if ((itemstack.getItem() == DecorItems.frame) && (itemstack.getItemDamage() == this.material)) {
					return updateFrame() ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
				}
			}
		}
		return EnumActionResult.FAIL;
	}

	public boolean updateFrame() {
		boolean foundOld = false;
		boolean looking = true;
		int oldFrameID = this.getFrameID();

		// Wrapped in a while to account for if the the next valid frame is at
		// the beginning of the EnumFrame values
		while (looking) {
			for (int i = 0; i < EnumFrame.VALUES.length; i++) {
				EnumFrame tryFrame = EnumFrame.VALUES[i];
				this.getDataManager().set(FRAME_ID, tryFrame.id);
				updateFacingWithBoundingBox(this.facingDirection);
				if (onValidSurface()) {
					if (foundOld) {
						// The next valid frame we stop looking for the next
						// frame to use
						looking = false;
						break;
					} else {
						if (tryFrame.id == oldFrameID) {
							foundOld = true;
						}
					}
				} else {
					if (tryFrame.id == oldFrameID) {
						looking = false;
						break;
					}
				}
			}
		}

		if (!this.worldObj.isRemote)
			playPlaceSound();

		return true;
	}

	public boolean dyeFrame(int color, boolean burn) {
		int newred = (colorValues[color] & 0xFF0000) >> 16;
		int newgreen = (colorValues[color] & 0xFF00) >> 8;
		int newblue = colorValues[color] & 0xFF;

		if ((newred == this.getFrameColor()[0]) && (newgreen == this.getFrameColor()[1]) && (newblue == this.getFrameColor()[2])) {
			return false;
		}

		this.getDataManager().set(COLOR_RED, newred);
		this.getDataManager().set(COLOR_GREEN, newgreen);
		this.getDataManager().set(COLOR_BLUE, newblue);
		this.getDataManager().set(BURNT, burn);

		if (!this.worldObj.isRemote) {
			if (burn) {
				playBurnSound();
			} else {
				playPlaceSound();
			}
		}

		return true;
	}

	public boolean dyeFrame(int color) {
		return dyeFrame(color, false);
	}

	public void setResistance(int material) {
		switch (material) {
		case 0:
			this.resistance = 9.0F;
			break;
		case 1:
			this.resistance = 18.0F;
		}
	}

	@Override
	protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
		Validate.notNull(facingDirectionIn);
		Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
		this.facingDirection = facingDirectionIn;

		if (facingDirectionIn.getAxis() == EnumFacing.Axis.Z)
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getOpposite().getHorizontalIndex() * 90);
		else
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getHorizontalIndex() * 90);

		double x = (double) this.hangingPosition.getX() + 0.5D;
		double y = (double) this.hangingPosition.getY() + 0.5D;
		double z = (double) this.hangingPosition.getZ() + 0.5D;
		double widthOffset = this.offs(this.getWidthPixels());
		double heightOffset = this.offs(this.getHeightPixels());
		x = x - (double) this.facingDirection.getFrontOffsetX() * 0.46875D;
		z = z - (double) this.facingDirection.getFrontOffsetZ() * 0.46875D;
		y = y + heightOffset;
		EnumFacing enumfacing = this.facingDirection.rotateYCCW();
		x = x + widthOffset * (double) enumfacing.getFrontOffsetX();
		z = z + widthOffset * (double) enumfacing.getFrontOffsetZ();
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		double width = (double) this.getWidthPixels();
		double height = (double) this.getHeightPixels();
		double depth = (double) this.getWidthPixels();

		if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
			depth = 1.0D;
		} else {
			width = 1.0D;
		}

		width = width / 32.0D;
		height = height / 32.0D;
		depth = depth / 32.0D;

		this.setEntityBoundingBox(new AxisAlignedBB(x - width, y - height, z - depth, x + width, y + height, z + depth));

		if (facingDirectionIn.getAxis() == EnumFacing.Axis.Z) {
			width += 0.1F;
			height += 0.1F;
			depth = 1.0F;
		} else {
			width = 1.0F;
			height += 0.1F;
			depth += 0.1F;
		}
		this.fireboundingBox = new AxisAlignedBB(x - width, y - height, z - depth, x + width, y + height, z + depth);
	}

	private double offs(int size) {
		return size % 32 == 0 ? 0.5D : 0.0D;
	}

	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
		this.setPosition(x, y, z);
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
		this.setPosition((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
	}

	@Override
	public void onUpdate() {
		if ((this.material == 0) && (DecorConfig.burnFrames)) {
			if (WorldHelper.isAABBInMaterial(worldObj, this.fireboundingBox.expand(-0.001D, -0.001D, -0.001D), Material.FIRE)) {
				if (!this.getBurned()) {
					dyeFrame(8, true);
				}
			}
		}
	}

	public EnumFrame getCurrentFrame() {
		return EnumFrame.getFrameById(this.getDataManager().get(FRAME_ID));
	}

	@Override
	public boolean onValidSurface() {
		if (!this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
			return false;
		} else {
			int i = Math.max(1, this.getWidthPixels() / 16);
			int j = Math.max(1, this.getHeightPixels() / 16);
			BlockPos blockpos = this.hangingPosition.offset(facingDirection.getOpposite());
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

			if (getCurrentFrame().isCollidable) {
				List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
				for (int m = 0; m < entities.size(); m++) {
					if ((entities.get(m) instanceof EntityFrame)) {
						EntityFrame entFrame = (EntityFrame) entities.get(m);

						if (entFrame.facingDirection == this.facingDirection) {
							return false;
						}
					}
				}
			}

			return true;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		if (getCurrentFrame().isCollidable) {
			return getEntityBoundingBox();
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canBeCollidedWith() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null) {
			if ((this.material == 0) && (((itemstack.getItem() == DecorItems.frame) && (itemstack.getItemDamage() == this.material)) || (itemstack.getItem() instanceof ItemAxe) || (itemstack.getItem() == Items.DYE))) {
				return true;
			}

			if ((this.material == 1) && (((itemstack.getItem() == DecorItems.frame) && (itemstack.getItemDamage() == this.material)) || (itemstack.getItem() instanceof ItemPickaxe) || (itemstack.getItem() == Items.DYE))) {
				return true;
			}
		}

		return false;
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public boolean attackEntityFrom(DamageSource damagesource, float damage) {
		if (!this.isDead) {

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

			if ((damagesource.getEntity() instanceof EntityPlayer)) {
				EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (!entityplayer.canPlayerEdit(hangingPosition, this.facingDirection, itemstack)) {
					return false;
				}

				setDead();
				setBeenAttacked();
				playPlaceSound();

				if (entityplayer.capabilities.isCreativeMode) {
					return true;
				}

				if (!this.worldObj.isRemote) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.frame, 1, this.material)));
				}

				return true;
			}

			if (damage > this.resistance) {
				setDead();
				setBeenAttacked();

				if (!this.worldObj.isRemote) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.frame, 1, this.material)));
				}

				return true;
			}
		}

		return false;
	}

	public int getFrameID() {
		return this.getDataManager().get(FRAME_ID);
	}

	public int[] getFrameColor() {
		return new int[] { this.getDataManager().get(COLOR_RED), this.getDataManager().get(COLOR_GREEN), this.getDataManager().get(COLOR_BLUE) };
	}

	public boolean getBurned() {
		return this.getDataManager().get(BURNT);
	}

	public void playBurnSound() {
		this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("Motive", this.getFrameID());
		int[] color = getFrameColor();
		nbttagcompound.setInteger("Red", color[0]);
		nbttagcompound.setInteger("Green", color[1]);
		nbttagcompound.setInteger("Blue", color[2]);
		nbttagcompound.setInteger("Material", this.material);
		nbttagcompound.setBoolean("Burnt", getBurned());

		nbttagcompound.setByte("Facing", (byte) this.facingDirection.getHorizontalIndex());
		BlockPos blockpos = this.getHangingPosition();
		nbttagcompound.setInteger("TileX", blockpos.getX());
		nbttagcompound.setInteger("TileY", blockpos.getY());
		nbttagcompound.setInteger("TileZ", blockpos.getZ());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.getDataManager().set(COLOR_RED, nbttagcompound.getInteger("Red"));
		this.getDataManager().set(COLOR_GREEN, nbttagcompound.getInteger("Green"));
		this.getDataManager().set(COLOR_BLUE, nbttagcompound.getInteger("Blue"));
		setResistance(this.material = nbttagcompound.getInteger("Material"));
		this.getDataManager().set(FRAME_ID, nbttagcompound.getInteger("Motive"));
		this.getDataManager().set(BURNT, nbttagcompound.getBoolean("Burnt"));

		this.hangingPosition = new BlockPos(nbttagcompound.getInteger("TileX"), nbttagcompound.getInteger("TileY"), nbttagcompound.getInteger("TileZ"));
		this.updateFacingWithBoundingBox(EnumFacing.getHorizontal(nbttagcompound.getByte("Facing")));
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeInt(this.facingDirection.getHorizontalIndex());
		data.writeInt(this.hangingPosition.getX());
		data.writeInt(this.hangingPosition.getY());
		data.writeInt(this.hangingPosition.getZ());
		data.writeInt(this.material);
		data.writeInt(this.getFrameID());

		int[] color = getFrameColor();
		data.writeInt(color[0]);
		data.writeInt(color[1]);
		data.writeInt(color[2]);
		data.writeBoolean(this.getBurned());
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		this.facingDirection = EnumFacing.getHorizontal(data.readInt());
		this.hangingPosition = new BlockPos(data.readInt(), data.readInt(), data.readInt());
		this.material = data.readInt();
		this.getDataManager().set(FRAME_ID, data.readInt());

		updateFacingWithBoundingBox(this.facingDirection);
		setResistance(this.material);

		this.getDataManager().set(COLOR_RED, data.readInt());
		this.getDataManager().set(COLOR_GREEN, data.readInt());
		this.getDataManager().set(COLOR_BLUE, data.readInt());
		this.getDataManager().set(BURNT, data.readBoolean());
	}

	@Override
	public void moveEntity(double x, double y, double z) {
		if ((!this.worldObj.isRemote) && (!this.isDead) && (x * x + y * y + z * z > 0.0D)) {
			setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.frame, 1, this.material)));
		}
	}

	@Override
	public void addVelocity(double x, double y, double z) {
		if ((!this.worldObj.isRemote) && (!this.isDead) && (x * x + y * y + z * z > 0.0D)) {
			setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.frame, 1, this.material)));
		}
	}

	@Override
	public int getWidthPixels() {
		return this.getCurrentFrame().sizeX;
	}

	@Override
	public int getHeightPixels() {
		return this.getCurrentFrame().sizeY;
	}

	@Override
	public void onBroken(Entity entity) {
	}

	@Override
	public void playPlaceSound() {
		switch (this.material) {
		case 0:
			this.playSound(SoundEvents.BLOCK_WOOD_STEP, 1.0F, 0.8F);
			break;
		case 1:
			this.playSound(SoundEvents.BLOCK_STONE_STEP, 1.0F, 1.2F);
		}
	}
}