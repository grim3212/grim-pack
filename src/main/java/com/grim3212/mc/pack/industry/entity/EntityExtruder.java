package com.grim3212.mc.pack.industry.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.industry.inventory.InventoryExtruder;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityExtruder extends Entity implements IEntityAdditionalSpawnData {

	/**
	 * The direction the extruder is facing
	 */
	private EnumFacing facing;
	/**
	 * The amount of fuel currently stored
	 */
	private int fuel;
	/**
	 * The speed modifier depending on the fuel inserted
	 */
	private float speedModifier;

	private InventoryExtruder extruderInv;
	/**
	 * The position that the extruder's tip is at depending on the
	 * extrusionCounter
	 */
	public float extrusionWave;
	/**
	 * The counter to get the right place for the extruder's tip
	 */
	public float extrusionCounter;

	private static final DataParameter<String> CUSTOM_NAME = EntityDataManager.createKey(EntityExtruder.class, DataSerializers.STRING);
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer> createKey(EntityExtruder.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.<Integer> createKey(EntityExtruder.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float> createKey(EntityExtruder.class, DataSerializers.FLOAT);

	public EntityExtruder(World worldIn) {
		super(worldIn);
		this.facing = EnumFacing.NORTH;
		this.extruderInv = new InventoryExtruder(this);
	}

	public EntityExtruder(World worldIn, EnumFacing facing, double x, double y, double z) {
		this(worldIn);
		this.setSize(1.0F, 1.0F);
		this.setPosition(x, y, z);
		this.setFacing(facing);
	}

	public void setFacing(EnumFacing facing) {
		this.facing = facing;

		if (facing == EnumFacing.UP) {
			setPositionAndRotation(posX, posY, posZ, 0.0F, -180F);
		} else if (facing == EnumFacing.DOWN) {
			setPositionAndRotation(posX, posY, posZ, 0.0F, 90F);
		} else {
			if (facing.getAxis() == EnumFacing.Axis.X)
				setPositionAndRotation(posX, posY, posZ, (facing.getOpposite().getHorizontalIndex() * 90 - 90) % 360, 0.0F);
			else
				setPositionAndRotation(posX, posY, posZ, (facing.getHorizontalIndex() * 90 - 90) % 360, 0.0F);
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.worldObj.isRemote || this.isDead)
			return true;

		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() + amount * 11.0F);
		this.setBeenAttacked();
		boolean flag = source.getEntity() instanceof EntityPlayer && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

		if (flag || this.getDamageTaken() > 40.0F) {
			if (!flag && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
				dropItem(IndustryItems.extruder, 1);

			}

			this.emptyItems();
			this.setDead();
		}

		return true;
	}

	@Override
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 10.0F);
	}

	private void emptyItems() {
		for (ItemStack stack : this.extruderInv.getExtruderContents()) {
			if (stack != null && stack.getItem() != null) {
				EntityItem entityitem = new EntityItem(worldObj, (float) posX, (float) posY, (float) posZ, stack);
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(CUSTOM_NAME, "");
		this.getDataManager().register(TIME_SINCE_HIT, 0);
		this.getDataManager().register(FORWARD_DIRECTION, 1);
		this.getDataManager().register(DAMAGE_TAKEN, 0.0F);
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) {
		if (!player.worldObj.isRemote)
			player.openGui(GrimPack.INSTANCE, PackGuiHandler.EXTRUDER_GUI_ID, player.worldObj, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());

		return EnumActionResult.SUCCESS;
	}

	public InventoryExtruder getExtruderInv() {
		return extruderInv;
	}

	public String getCustomName() {
		return this.getDataManager().get(CUSTOM_NAME);
	}

	public void setCustomName(String customName) {
		this.getDataManager().set(CUSTOM_NAME, customName);
	}

	public EnumFacing getFacing() {
		return facing;
	}

	public int getFuel() {
		return fuel;
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(float damageTaken) {
		this.dataManager.set(DAMAGE_TAKEN, damageTaken);
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken() {
		return this.dataManager.get(DAMAGE_TAKEN);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int timeSinceHit) {
		this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return this.dataManager.get(TIME_SINCE_HIT);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int forwardDirection) {
		this.dataManager.set(FORWARD_DIRECTION, forwardDirection);
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return this.dataManager.get(FORWARD_DIRECTION);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		facing = EnumFacing.getFront(compound.getByte("facing"));
		fuel = compound.getInteger("fuel");
		speedModifier = compound.getFloat("speedModifier");

		if (compound.hasKey("CustomName", 8)) {
			this.getDataManager().set(CUSTOM_NAME, compound.getString("CustomName"));
		}

		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.extruderInv.setExtruderContents(new ItemStack[extruderInv.getSizeInventory()]);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < this.extruderInv.getExtruderContents().length) {
				this.extruderInv.getExtruderContents()[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setByte("facing", (byte) facing.getIndex());
		compound.setInteger("fuel", fuel);
		compound.setFloat("speedModifier", speedModifier);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.getDataManager().get(CUSTOM_NAME));
		}

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.extruderInv.getExtruderContents().length; ++i) {
			if (this.extruderInv.getExtruderContents()[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.extruderInv.getExtruderContents()[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeByte((byte) facing.getIndex());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.facing = EnumFacing.getFront(additionalData.readByte());
	}

}
