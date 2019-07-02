package com.grim3212.mc.pack.decor.entity;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.WorldHelper;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.item.ItemFrame.EnumFrameType;
import com.grim3212.mc.pack.decor.util.EnumFrame;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityFrame extends HangingEntity implements IEntityAdditionalSpawnData, IManualEntity {

	public EnumFrameType material;
	public float resistance = 0.0F;
	public AxisAlignedBB fireboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	private static final DataParameter<Boolean> BURNT = EntityDataManager.createKey(EntityFrame.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> FRAME_ID = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_RED = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_GREEN = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_BLUE = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);

	public EntityFrame(EntityType<? extends EntityFrame> type, World world) {
		super(type, world);
	}

	public EntityFrame(World world, BlockPos pos, Direction direction, EnumFrameType type) {
		super(DecorEntities.FRAME, world, pos);
		this.material = type;

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
	protected void registerData() {
		this.getDataManager().register(FRAME_ID, 1);
		this.getDataManager().register(COLOR_RED, 256);
		this.getDataManager().register(COLOR_GREEN, 256);
		this.getDataManager().register(COLOR_BLUE, 256);
		this.getDataManager().register(BURNT, false);
	}

	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (player.canPlayerEdit(hangingPosition, this.facingDirection, itemstack)) {
			if (!itemstack.isEmpty()) {
				if (DecorConfig.dyeFrames.get()) {
					DyeColor color = DyeColor.getColor(itemstack);
					if (color != null) {
						if (dyeFrame(color)) {
							if (!player.abilities.isCreativeMode) {
								itemstack.shrink(1);
							}
							return ActionResultType.SUCCESS;
						}
					}
				} else if ((this.material == EnumFrameType.IRON && itemstack.getItem() == DecorItems.frame_iron) || (this.material == EnumFrameType.WOOD && itemstack.getItem() == DecorItems.frame_wood)) {
					return updateFrame() ? ActionResultType.SUCCESS : ActionResultType.FAIL;
				}
			}
		}
		return ActionResultType.FAIL;
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

		if (!this.world.isRemote)
			playPlaceSound();

		return true;
	}

	public boolean dyeFrame(DyeColor color, boolean burn) {
		int colorValue = color.getFireworkColor();
		int newred = (colorValue & 0xFF0000) >> 16;
		int newgreen = (colorValue & 0xFF00) >> 8;
		int newblue = colorValue & 0xFF;

		if ((newred == this.getFrameColor()[0]) && (newgreen == this.getFrameColor()[1]) && (newblue == this.getFrameColor()[2])) {
			return false;
		}

		this.getDataManager().set(COLOR_RED, newred);
		this.getDataManager().set(COLOR_GREEN, newgreen);
		this.getDataManager().set(COLOR_BLUE, newblue);
		this.getDataManager().set(BURNT, burn);

		if (!this.world.isRemote) {
			if (burn) {
				playBurnSound();
			} else {
				playPlaceSound();
			}
		}

		return true;
	}

	public boolean dyeFrame(DyeColor color) {
		return dyeFrame(color, false);
	}

	public void setResistance(EnumFrameType material) {
		switch (material) {
		case WOOD:
			this.resistance = 9.0F;
			break;
		case IRON:
			this.resistance = 18.0F;
		}
	}

	@Override
	protected void updateFacingWithBoundingBox(Direction facingDirectionIn) {
		Validate.notNull(facingDirectionIn);
		Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
		this.facingDirection = facingDirectionIn;

		if (facingDirectionIn.getAxis() == Direction.Axis.Z)
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getOpposite().getHorizontalIndex() * 90);
		else
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getHorizontalIndex() * 90);

		double x = (double) this.hangingPosition.getX() + 0.5D;
		double y = (double) this.hangingPosition.getY() + 0.5D;
		double z = (double) this.hangingPosition.getZ() + 0.5D;
		double widthOffset = this.offs(this.getWidthPixels());
		double heightOffset = this.offs(this.getHeightPixels());
		x = x - (double) this.facingDirection.getXOffset() * 0.46875D;
		z = z - (double) this.facingDirection.getZOffset() * 0.46875D;
		y = y + heightOffset;
		Direction enumfacing = this.facingDirection.rotateYCCW();
		x = x + widthOffset * (double) enumfacing.getXOffset();
		z = z + widthOffset * (double) enumfacing.getZOffset();
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		double width = (double) this.getWidthPixels();
		double height = (double) this.getHeightPixels();
		double depth = (double) this.getWidthPixels();

		if (this.facingDirection.getAxis() == Direction.Axis.Z) {
			depth = 1.0D;
		} else {
			width = 1.0D;
		}

		width = width / 32.0D;
		height = height / 32.0D;
		depth = depth / 32.0D;

		this.setBoundingBox(new AxisAlignedBB(x - width, y - height, z - depth, x + width, y + height, z + depth));

		if (facingDirectionIn.getAxis() == Direction.Axis.Z) {
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
	@OnlyIn(Dist.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
		this.setPosition((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
	}

	@Override
	public void tick() {
		if ((this.material == EnumFrameType.WOOD) && (DecorConfig.burnFrames.get())) {
			if (WorldHelper.isAABBInMaterial(world, this.fireboundingBox.expand(-0.001D, -0.001D, -0.001D), Material.FIRE)) {
				if (!this.getBurned()) {
					dyeFrame(DyeColor.LIGHT_GRAY, true);
				}
			}
		}
	}

	public EnumFrame getCurrentFrame() {
		return EnumFrame.getFrameById(this.getDataManager().get(FRAME_ID));
	}

	@Override
	public boolean onValidSurface() {
		if (!this.world.areCollisionShapesEmpty(this)) {
			return false;
		} else {
			int i = Math.max(1, this.getWidthPixels() / 16);
			int j = Math.max(1, this.getHeightPixels() / 16);
			BlockPos blockpos = this.hangingPosition.offset(facingDirection.getOpposite());
			Direction enumfacing = this.facingDirection.rotateYCCW();
			for (int k = 0; k < i; ++k) {
				for (int l = 0; l < j; ++l) {
					int i1 = i > 2 ? -1 : 0;
					int j1 = j > 2 ? -1 : 0;
					BlockPos blockpos1 = blockpos.offset(enumfacing, k + i1).up(l + j1);
					BlockState iblockstate = this.world.getBlockState(blockpos1);

					if (Block.hasSolidSide(iblockstate, this.world, blockpos1, this.facingDirection))
						continue;

					if (!iblockstate.getMaterial().isSolid() && !RedstoneDiodeBlock.isDiode(iblockstate)) {
						return false;
					}
				}
			}

			if (getCurrentFrame().isCollidable) {
				List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox());
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
			return getBoundingBox();
		}
		return null;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean canBeCollidedWith() {
		PlayerEntity player = Minecraft.getInstance().player;

		for (Hand hand : Hand.values()) {
			ItemStack handStack = player.getHeldItem(hand);

			if (!handStack.isEmpty()) {
				if ((this.material == EnumFrameType.WOOD) && (((handStack.getItem() == DecorItems.frame_wood)) || (handStack.getItem() instanceof AxeItem) || (DyeColor.getColor(handStack) != null))) {
					return true;
				}

				if ((this.material == EnumFrameType.IRON) && (((handStack.getItem() == DecorItems.frame_iron)) || (handStack.getItem() instanceof PickaxeItem) || (DyeColor.getColor(handStack) != null))) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public boolean attackEntityFrom(DamageSource damagesource, float damage) {
		if (this.isAlive()) {

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

			if ((damagesource.getTrueSource() instanceof PlayerEntity)) {
				PlayerEntity entityplayer = (PlayerEntity) damagesource.getTrueSource();
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (!entityplayer.canPlayerEdit(hangingPosition, this.facingDirection, itemstack)) {
					return false;
				}

				remove();
				markVelocityChanged();
				playPlaceSound();

				if (entityplayer.abilities.isCreativeMode) {
					return true;
				}

				if (!this.world.isRemote) {
					this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.material == EnumFrameType.WOOD ? DecorItems.frame_wood : DecorItems.frame_iron, 1)));
				}

				return true;
			}

			if (damage > this.resistance) {
				remove();
				markVelocityChanged();

				if (!this.world.isRemote) {
					this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.material == EnumFrameType.WOOD ? DecorItems.frame_wood : DecorItems.frame_iron, 1)));
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
	public void writeAdditional(CompoundNBT nbttagcompound) {
		super.writeAdditional(nbttagcompound);

		nbttagcompound.putInt("Motive", this.getFrameID());
		int[] color = getFrameColor();
		nbttagcompound.putInt("Red", color[0]);
		nbttagcompound.putInt("Green", color[1]);
		nbttagcompound.putInt("Blue", color[2]);
		nbttagcompound.putInt("Material", this.material.ordinal());
		nbttagcompound.putBoolean("Burnt", getBurned());
	}

	@Override
	public void readAdditional(CompoundNBT nbttagcompound) {
		super.readAdditional(nbttagcompound);

		this.getDataManager().set(COLOR_RED, nbttagcompound.getInt("Red"));
		this.getDataManager().set(COLOR_GREEN, nbttagcompound.getInt("Green"));
		this.getDataManager().set(COLOR_BLUE, nbttagcompound.getInt("Blue"));
		setResistance(this.material = EnumFrameType.getValues()[nbttagcompound.getInt("Material")]);
		this.getDataManager().set(FRAME_ID, nbttagcompound.getInt("Motive"));
		this.getDataManager().set(BURNT, nbttagcompound.getBoolean("Burnt"));
	}

	@Override
	public void writeSpawnData(PacketBuffer data) {
		data.writeInt(this.facingDirection.getHorizontalIndex());
		data.writeBlockPos(this.hangingPosition);
		data.writeInt(this.material.ordinal());
		data.writeInt(this.getFrameID());

		int[] color = getFrameColor();
		data.writeInt(color[0]);
		data.writeInt(color[1]);
		data.writeInt(color[2]);
		data.writeBoolean(this.getBurned());
	}

	@Override
	public void readSpawnData(PacketBuffer data) {
		this.facingDirection = Direction.byHorizontalIndex(data.readInt());
		this.hangingPosition = data.readBlockPos();
		this.material = EnumFrameType.getValues()[data.readInt()];
		this.getDataManager().set(FRAME_ID, data.readInt());

		updateFacingWithBoundingBox(this.facingDirection);
		setResistance(this.material);

		this.getDataManager().set(COLOR_RED, data.readInt());
		this.getDataManager().set(COLOR_GREEN, data.readInt());
		this.getDataManager().set(COLOR_BLUE, data.readInt());
		this.getDataManager().set(BURNT, data.readBoolean());
	}

	@Override
	public void move(MoverType type, Vec3d pos) {
		if ((!this.world.isRemote) && this.isAlive() && (pos.x * pos.x + pos.y * pos.y + pos.z * pos.z > 0.0D)) {
			remove();
			this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.material == EnumFrameType.WOOD ? DecorItems.frame_wood : DecorItems.frame_iron, 1)));
		}
	}

	@Override
	public void addVelocity(double x, double y, double z) {
		if ((!this.world.isRemote) && this.isAlive() && (x * x + y * y + z * z > 0.0D)) {
			remove();
			this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.material == EnumFrameType.WOOD ? DecorItems.frame_wood : DecorItems.frame_iron, 1)));
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
		case WOOD:
			this.playSound(SoundEvents.BLOCK_WOOD_STEP, 1.0F, 0.8F);
			break;
		case IRON:
			this.playSound(SoundEvents.BLOCK_STONE_STEP, 1.0F, 1.2F);
		}
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualDecor.framesInfo_page;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}