package com.grim3212.mc.pack.decor.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.google.common.base.Predicate;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFlatItemFrame extends EntityItemFrame implements IEntityAdditionalSpawnData, IManualEntity {

	private static final DataParameter<ItemStack> ITEM = EntityDataManager.<ItemStack>createKey(EntityItemFrame.class, DataSerializers.OPTIONAL_ITEM_STACK);
	private static final DataParameter<Integer> ROTATION = EntityDataManager.<Integer>createKey(EntityItemFrame.class, DataSerializers.VARINT);
	/** Chance for this item frame's item to drop from the frame. */
	private float itemDropChance = 1.0F;

	public EntityFlatItemFrame(World worldIn) {
		super(worldIn);
	}

	public EntityFlatItemFrame(World worldIn, BlockPos pos, EnumFacing facing) {
		super(worldIn, pos, facing);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		ItemStack held = this.getDisplayedItem();
		if (held.isEmpty()) {
			return new ItemStack(DecorItems.flat_item_frame);
		} else {
			return held.copy();
		}
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(ITEM, ItemStack.EMPTY);
		this.getDataManager().register(ROTATION, 0);
	}

	@Override
	public void dropItemOrSelf(@Nullable Entity entityIn, boolean dropSelf) {
		if (this.world.getGameRules().getBoolean("doEntityDrops")) {
			ItemStack itemstack = this.getDisplayedItem();

			if (entityIn instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entityIn;

				if (entityplayer.capabilities.isCreativeMode) {
					this.removeFrameFromMap(itemstack);
					return;
				}
			}

			if (dropSelf) {
				this.entityDropItem(new ItemStack(DecorItems.flat_item_frame), 0.0F);
			}

			if (!itemstack.isEmpty() && this.rand.nextFloat() < this.itemDropChance) {
				itemstack = itemstack.copy();
				this.removeFrameFromMap(itemstack);
				this.entityDropItem(itemstack, 0.0F);
			}
		}
	}

	private void removeFrameFromMap(ItemStack stack) {
		if (!stack.isEmpty()) {
			if (stack.getItem() instanceof net.minecraft.item.ItemMap) {
				MapData mapdata = ((ItemMap) stack.getItem()).getMapData(stack, this.world);
				mapdata.mapDecorations.remove("frame-" + this.getEntityId());
			}

			stack.setItemFrame((EntityItemFrame) null);
		}
	}

	@Override
	public String getName() {
		if (this.hasCustomName()) {
			return this.getCustomNameTag();
		} else {
			return new ItemStack(DecorItems.flat_item_frame).getDisplayName();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		if (!this.getDisplayedItem().isEmpty()) {
			compound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
			compound.setByte("ItemRotation", (byte) this.getRotation());
			compound.setFloat("ItemDropChance", this.itemDropChance);
		}

		compound.setByte("Facing", (byte) this.facingDirection.getIndex());
		BlockPos blockpos = this.getHangingPosition();
		compound.setInteger("TileX", blockpos.getX());
		compound.setInteger("TileY", blockpos.getY());
		compound.setInteger("TileZ", blockpos.getZ());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");

		if (nbttagcompound != null && !nbttagcompound.hasNoTags()) {
			this.setDisplayedItemWithUpdate(new ItemStack(nbttagcompound), false);
			this.setRotation(compound.getByte("ItemRotation"), false);

			if (compound.hasKey("ItemDropChance", 99)) {
				this.itemDropChance = compound.getFloat("ItemDropChance");
			}
		}

		this.hangingPosition = new BlockPos(compound.getInteger("TileX"), compound.getInteger("TileY"), compound.getInteger("TileZ"));
		this.updateFacingWithBoundingBox(EnumFacing.getFront(compound.getByte("Facing")));
	}

	@Override
	public ItemStack getDisplayedItem() {
		return this.getDataManager().get(ITEM);
	}

	@Override
	public void setDisplayedItem(@Nonnull ItemStack stack) {
		this.setDisplayedItemWithUpdate(stack, true);
	}

	private void setDisplayedItemWithUpdate(@Nonnull ItemStack stack, boolean flag) {
		if (!stack.isEmpty()) {
			stack = stack.copy();
			stack.setCount(1);
			stack.setItemFrame(this);
		}

		this.getDataManager().set(ITEM, stack);
		this.getDataManager().setDirty(ITEM);

		if (!stack.isEmpty()) {
			this.playSound(SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, 1.0F, 1.0F);
		}

		if (flag && this.hangingPosition != null) {
			this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
		}
	}

	@Override
	public int getRotation() {
		return this.getDataManager().get(ROTATION);
	}

	@Override
	public void setItemRotation(int rotationIn) {
		this.setRotation(rotationIn, true);
	}

	private void setRotation(int rotationIn, boolean flag) {
		this.getDataManager().set(ROTATION, Integer.valueOf(rotationIn % 8));

		if (flag && this.hangingPosition != null) {
			this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
		}
	}

	@Override
	protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
		Validate.notNull(facingDirectionIn);
		this.facingDirection = facingDirectionIn;

		if (facingDirectionIn.getAxis() != EnumFacing.Axis.Y) {
			this.rotationYaw = this.facingDirection.getHorizontalIndex() * 90;
			this.prevRotationYaw = this.rotationYaw;
		} else {
			if (facingDirectionIn == EnumFacing.UP)
				this.rotationPitch = 90;
			else
				this.rotationPitch = -90;
			this.prevRotationPitch = this.rotationPitch;
		}

		this.updateBoundingBox();
	}

	@Override
	protected void updateBoundingBox() {
		if (this.facingDirection != null) {
			if (this.facingDirection.getAxis() != EnumFacing.Axis.Y) {
				double d0 = (double) this.hangingPosition.getX() + 0.5D;
				double d1 = (double) this.hangingPosition.getY() + 0.5D;
				double d2 = (double) this.hangingPosition.getZ() + 0.5D;
				double d4 = this.getSize(this.getWidthPixels());
				double d5 = this.getSize(this.getHeightPixels());
				d0 = d0 - (double) this.facingDirection.getFrontOffsetX() * 0.46875D;
				d2 = d2 - (double) this.facingDirection.getFrontOffsetZ() * 0.46875D;
				d1 = d1 + d5;
				EnumFacing enumfacing = this.facingDirection.rotateAround(Axis.Y);
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
			} else {
				this.posX = getHangingPosition().getX() + 0.5D;
				this.posY = getHangingPosition().getY() + 0.5D;
				this.posZ = getHangingPosition().getZ() + 0.5D;

				if (this.facingDirection == EnumFacing.UP)
					this.setEntityBoundingBox(new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 0.065F, 0.875F).offset(getHangingPosition()));
				else
					this.setEntityBoundingBox(new AxisAlignedBB(0.125F, 0.935, 0.125F, 0.875F, 1.0F, 0.875F).offset(getHangingPosition()));
			}
		}
	}

	private double getSize(int width) {
		return width % 32 == 0 ? 0.5D : 0.0D;
	}

	@Override
	public boolean onValidSurface() {
		if (!this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
			return false;
		} else {
			int i = Math.max(1, this.getWidthPixels() / 16);
			int j = Math.max(1, this.getHeightPixels() / 16);
			BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
			EnumFacing enumfacing = this.facingDirection.rotateAround(Axis.Y);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k = 0; k < i; ++k) {
				for (int l = 0; l < j; ++l) {
					int i1 = (i - 1) / -2;
					int j1 = (j - 1) / -2;
					blockpos$mutableblockpos.setPos(blockpos).move(enumfacing, k + i1).move(EnumFacing.UP, l + j1);
					IBlockState iblockstate = this.world.getBlockState(blockpos$mutableblockpos);

					if (iblockstate.isSideSolid(this.world, blockpos$mutableblockpos, this.facingDirection))
						continue;

					if (!iblockstate.getMaterial().isSolid() && !BlockRedstoneDiode.isDiode(iblockstate)) {
						return false;
					}
				}
			}

			return this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), IS_HANGING_ENTITY).isEmpty();
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeShort(this.facingDirection.getIndex());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		EnumFacing facing = EnumFacing.getFront(additionalData.readShort());
		updateFacingWithBoundingBox(facing);
	}

	private static final Predicate<Entity> IS_HANGING_ENTITY = new Predicate<Entity>() {
		public boolean apply(@Nullable Entity entity) {
			return entity instanceof EntityHanging;
		}
	};

	@Override
	public Page getPage(Entity entity) {
		return ManualDecor.flatItemFrame_page;
	}
}
