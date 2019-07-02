package com.grim3212.mc.pack.decor.entity;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;

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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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

public class EntityWallpaper extends HangingEntity implements IEntityAdditionalSpawnData, IManualEntity {

	public boolean isBlockUp;
	public boolean isBlockDown;
	public boolean isBlockLeft;
	public boolean isBlockRight;

	public AxisAlignedBB fireboundingBox;

	private static final DataParameter<Boolean> BURNT = EntityDataManager.<Boolean>createKey(EntityWallpaper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> WALLPAPER_ID = EntityDataManager.<Integer>createKey(EntityWallpaper.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_RED = EntityDataManager.<Integer>createKey(EntityWallpaper.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_GREEN = EntityDataManager.<Integer>createKey(EntityWallpaper.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_BLUE = EntityDataManager.<Integer>createKey(EntityWallpaper.class, DataSerializers.VARINT);

	public EntityWallpaper(EntityType<? extends EntityWallpaper> type, World world) {
		super(type, world);
		this.isBlockUp = false;
		this.isBlockDown = false;
		this.isBlockLeft = false;
		this.isBlockRight = false;
		this.fireboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.facingDirection = Direction.SOUTH;
	}

	public EntityWallpaper(World world) {
		this(DecorEntities.WALLPAPER, world);
	}

	public EntityWallpaper(World world, BlockPos pos, Direction direction) {
		this(world);
		this.hangingPosition = pos;
		this.updateFacingWithBoundingBox(direction);

		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.fireboundingBox);

		for (int i = 0; i < entities.size(); i++) {
			if ((entities.get(i) instanceof EntityWallpaper)) {
				EntityWallpaper entWallpaper = (EntityWallpaper) entities.get(i);
				this.getDataManager().set(WALLPAPER_ID, entWallpaper.getWallpaperID());

				if ((DecorConfig.copyDye.get()) && (!entWallpaper.getBurned())) {
					this.getDataManager().set(COLOR_RED, entWallpaper.getWallpaperColor()[0]);
					this.getDataManager().set(COLOR_GREEN, entWallpaper.getWallpaperColor()[1]);
					this.getDataManager().set(COLOR_BLUE, entWallpaper.getWallpaperColor()[2]);
				}
			}
		}
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty()) {
			if ((DecorConfig.dyeWallpaper.get())) {
				DyeColor color = DyeColor.getColor(stack);
				if (color != null) {
					dyeWallpaper(color);
					stack.shrink(1);
					return true;
				}

			}

			if (stack.getItem() == DecorItems.wallpaper) {
				return updateWallpaper();
			}
		}

		return false;
	}

	@Override
	protected void registerData() {
		this.getDataManager().register(WALLPAPER_ID, 0);
		this.getDataManager().register(COLOR_RED, 256);
		this.getDataManager().register(COLOR_GREEN, 256);
		this.getDataManager().register(COLOR_BLUE, 256);
		this.getDataManager().register(BURNT, false);
	}

	public boolean updateWallpaper() {
		int newWallpaper = this.getWallpaperID() + 1;

		if (newWallpaper >= DecorConfig.numWallpapers.get()) {
			newWallpaper = 0;
		}

		this.getDataManager().set(WALLPAPER_ID, newWallpaper);
		if (!this.world.isRemote)
			playPlaceSound();

		return true;
	}

	public boolean updateWallpaper(int wallpaper) {
		this.getDataManager().set(WALLPAPER_ID, wallpaper);

		if (!this.world.isRemote)
			playPlaceSound();

		return true;
	}

	public void dyeWallpaper(DyeColor color, boolean burn) {
		int colorValue = color.getFireworkColor();
		int newred = ((colorValue & 0xFF0000) >> 16);
		int newgreen = ((colorValue & 0xFF00) >> 8);
		int newblue = (colorValue & 0xFF);

		if (color != DyeColor.BLACK) {
			this.getDataManager().set(BURNT, false);
		}

		this.getDataManager().set(COLOR_RED, newred);
		this.getDataManager().set(COLOR_GREEN, newgreen);
		this.getDataManager().set(COLOR_BLUE, newblue);

		if (!this.world.isRemote) {
			if (burn) {
				playBurnSound();
			} else {
				playPlaceSound();
			}
		}
	}

	public void dyeWallpaper(DyeColor color) {
		dyeWallpaper(color, false);
	}

	@Override
	public void tick() {
		if (!this.world.isRemote) {
			if (!onValidSurface()) {
				remove();
				this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
			}
		}

		if ((DecorConfig.burnWallpaper.get()) && (world.isMaterialInBB(this.fireboundingBox.expand(-0.001D, -0.001D, -0.001D), Material.FIRE)) && (!this.getBurned())) {
			dyeWallpaper(DyeColor.BLACK, true);
			this.getDataManager().set(BURNT, true);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean canBeCollidedWith() {
		PlayerEntity player = Minecraft.getInstance().player;

		for (Hand hand : Hand.values()) {
			ItemStack handStack = player.getHeldItem(hand);

			return !handStack.isEmpty() && ((handStack.getItem() == DecorItems.wallpaper) || (handStack.getItem() instanceof AxeItem) || (DyeColor.getColor(handStack) != null));
		}

		return false;
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public boolean attackEntityFrom(DamageSource damage, float amount) {
		if (this.isInvulnerableTo(damage)) {
			return false;
		} else {
			if (this.isAlive() && (!this.world.isRemote)) {
				remove();
				markVelocityChanged();
				PlayerEntity player = null;

				if ((damage.getTrueSource() instanceof PlayerEntity)) {
					player = (PlayerEntity) damage.getTrueSource();
					playPlaceSound();
				}

				if ((player != null) && (player.abilities.isCreativeMode)) {
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

				this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
			}
			return true;
		}
	}

	@Override
	protected void updateFacingWithBoundingBox(Direction facingDirectionIn) {
		Validate.notNull(facingDirectionIn);
		Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
		this.facingDirection = facingDirectionIn;

		if (facingDirectionIn == Direction.SOUTH || facingDirectionIn == Direction.NORTH)
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getOpposite().getHorizontalIndex() * 90);
		else
			this.prevRotationYaw = this.rotationYaw = (float) (this.facingDirection.getHorizontalIndex() * 90);

		double d0 = (double) this.hangingPosition.getX() + 0.5D;
		double d1 = (double) this.hangingPosition.getY() + 0.5D;
		double d2 = (double) this.hangingPosition.getZ() + 0.5D;
		double d4 = this.offs(this.getWidthPixels());
		double d5 = this.offs(this.getHeightPixels());
		d0 = d0 - (double) this.facingDirection.getXOffset() * 0.46875D;
		d2 = d2 - (double) this.facingDirection.getZOffset() * 0.46875D;
		d1 = d1 + d5;
		Direction enumfacing = this.facingDirection.rotateYCCW();
		d0 = d0 + d4 * (double) enumfacing.getXOffset();
		d2 = d2 + d4 * (double) enumfacing.getZOffset();
		this.posX = d0;
		this.posY = d1;
		this.posZ = d2;
		double d6 = (double) this.getWidthPixels();
		double d7 = (double) this.getHeightPixels();
		double d8 = (double) this.getWidthPixels();

		if (this.facingDirection.getAxis() == Direction.Axis.Z) {
			d8 = 1.0D;
		} else {
			d6 = 1.0D;
		}

		d6 = d6 / 32.0D;
		d7 = d7 / 32.0D;
		d8 = d8 / 32.0D;
		this.setBoundingBox(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
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
		if (!this.world.areCollisionShapesEmpty(this)) {
			return false;
		} else {
			int i = Math.max(1, this.getWidthPixels() / 16);
			int j = Math.max(1, this.getHeightPixels() / 16);
			BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
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

			List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox());
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
	public void writeAdditional(CompoundNBT nbt) {
		super.writeAdditional(nbt);
		nbt.putInt("Motive", this.getWallpaperID());
		nbt.putInt("Red", this.getWallpaperColor()[0]);
		nbt.putInt("Green", this.getWallpaperColor()[1]);
		nbt.putInt("Blue", this.getWallpaperColor()[2]);
		nbt.putBoolean("Burnt", this.getBurned());
	}

	@Override
	public void readAdditional(CompoundNBT nbt) {
		super.readAdditional(nbt);
		this.getDataManager().set(WALLPAPER_ID, nbt.getInt("Motive"));
		this.getDataManager().set(COLOR_RED, nbt.getInt("Red"));
		this.getDataManager().set(COLOR_GREEN, nbt.getInt("Green"));
		this.getDataManager().set(COLOR_BLUE, nbt.getInt("Blue"));
		this.getDataManager().set(BURNT, nbt.getBoolean("Burnt"));
	}

	@Override
	public void move(MoverType type, Vec3d pos) {
		if ((!this.world.isRemote) && this.isAlive() && (pos.x * pos.x + pos.y * pos.y + pos.z * pos.z > 0.0D)) {
			remove();
			this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
		}
	}

	@Override
	public void addVelocity(double var1, double var3, double var5) {
		if ((!this.world.isRemote) && this.isAlive() && (var1 * var1 + var3 * var3 + var5 * var5 > 0.0D)) {
			remove();
			this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
		}
	}

	@Override
	public void writeSpawnData(PacketBuffer buf) {
		buf.writeBlockPos(this.hangingPosition);
		buf.writeInt(this.getWallpaperID());
		buf.writeInt(this.facingDirection.getHorizontalIndex());
		buf.writeInt(this.getWallpaperColor()[0]);
		buf.writeInt(this.getWallpaperColor()[1]);
		buf.writeInt(this.getWallpaperColor()[2]);
		buf.writeBoolean(this.getBurned());
	}

	@Override
	public void readSpawnData(PacketBuffer buf) {
		this.hangingPosition = buf.readBlockPos();
		this.getDataManager().set(WALLPAPER_ID, buf.readInt());
		updateFacingWithBoundingBox(Direction.byHorizontalIndex(buf.readInt()));
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
		this.playSound(SoundEvents.BLOCK_WOOL_STEP, 1.0F, 0.8F);
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualDecor.wallpaperInfo_page;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}