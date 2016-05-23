package com.grim3212.mc.pack.decor.entity;

import java.util.List;

import com.grim3212.mc.pack.core.util.WorldHelper;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.util.EnumFrame;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFrame extends EntityHanging implements IEntityAdditionalSpawnData {

	public int direction;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int material = 0;
	public float resistance = 0.0F;
	public AxisAlignedBB setupboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	public AxisAlignedBB fireboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	public static final int[] colorValues = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 8816262, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 16777215 };
	private BlockPos pos;

	private static final DataParameter<Boolean> BURNT = EntityDataManager.createKey(EntityFrame.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> FRAME_ID = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_RED = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_GREEN = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_BLUE = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);

	public EntityFrame(World world) {
		super(world);
	}

	public EntityFrame(World world, BlockPos pos, int direction, int material) {
		super(world, pos);
		this.xPosition = pos.getX();
		this.yPosition = pos.getY();
		this.zPosition = pos.getZ();
		this.pos = pos;
		this.material = material;

		for (int i = 0; i < EnumFrame.values().length; i++) {
			EnumFrame tryFrame = EnumFrame.values()[i];
			this.getDataManager().set(FRAME_ID, tryFrame.id);
			setDirectionAndSize(direction);
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
		if (player.canPlayerEdit(pos, EnumFacing.getHorizontal(this.direction), itemstack)) {
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
			for (int i = 0; i < EnumFrame.values().length; i++) {
				EnumFrame tryFrame = EnumFrame.values()[i];
				this.getDataManager().set(FRAME_ID, tryFrame.id);
				setDirectionAndSize(this.direction);
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
				}
			}
		}

		if (!this.worldObj.isRemote)
			playFrameSound();

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
				playFrameSound();
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

	public void setDirectionAndSize(int direction) {
		this.direction = direction;

		this.prevRotationYaw = (this.rotationYaw = direction * 90);

		EnumFrame currentFrame = getCurrentFrame();

		float sizeX = currentFrame.sizeX;
		float sizeY = currentFrame.sizeY;
		float sizeZ = currentFrame.sizeX;
		float width = 0.5F;

		if ((direction != 0) && (direction != 2)) {
			sizeX = width;
		} else {
			sizeZ = width;
		}

		sizeX /= 32.0F;
		sizeY /= 32.0F;
		sizeZ /= 32.0F;
		width /= 32.0F;

		float xPos = this.xPosition + 0.5F;
		float yPos = this.yPosition + 0.5F;
		float zPos = this.zPosition + 0.5F;

		yPos += sizeOffset(currentFrame.sizeY);
		if (direction == 0) {
			zPos -= width + 0.5F;
			xPos -= sizeOffset(currentFrame.sizeX);
		}
		if (direction == 1) {
			xPos -= width + 0.5F;
			zPos += sizeOffset(currentFrame.sizeX);
		}
		if (direction == 2) {
			zPos += width + 0.5F;
			xPos += sizeOffset(currentFrame.sizeX);
		}
		if (direction == 3) {
			xPos += width + 0.5F;
			zPos -= sizeOffset(currentFrame.sizeX);
		}

		setPosition(xPos, yPos, zPos);
		this.setEntityBoundingBox(new AxisAlignedBB(xPos - sizeX, yPos - sizeY, zPos - sizeZ, xPos + sizeX, yPos + sizeY, zPos + sizeZ));

		if ((direction == 0) || (direction == 2)) {
			sizeX -= 0.1F;
			sizeY -= 0.1F;
		} else {
			sizeY -= 0.1F;
			sizeZ -= 0.1F;
		}
		this.setupboundingBox = new AxisAlignedBB(xPos - sizeX, yPos - sizeY, zPos - sizeZ, xPos + sizeX, yPos + sizeY, zPos + sizeZ);

		if ((direction == 0) || (direction == 2)) {
			sizeX += 0.1F;
			sizeY += 0.1F;
			sizeZ = 1.0F;
		} else {
			sizeX = 1.0F;
			sizeY += 0.1F;
			sizeZ += 0.1F;
		}
		this.fireboundingBox = new AxisAlignedBB(xPos - sizeX, yPos - sizeY, zPos - sizeZ, xPos + sizeX, yPos + sizeY, zPos + sizeZ);
	}

	private float sizeOffset(int size) {
		if (size == 32) {
			return 0.5F;
		}
		return size != 64 ? 0.0F : 0.5F;
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
		if (this.worldObj.getCollisionBoxes(this, this.setupboundingBox).size() > 0) {
			return false;
		}

		EnumFrame currentFrame = getCurrentFrame();

		int sizeX = currentFrame.sizeX / 16;
		int sizeY = currentFrame.sizeY / 16;
		int x = this.xPosition;
		int y = this.yPosition;
		int z = this.zPosition;

		boolean onWall = true;

		Material mainSupportingBlock = this.worldObj.getBlockState(pos).getMaterial();

		if (!mainSupportingBlock.isSolid()) {
			onWall = false;
		}

		if (this.direction == 0) {
			x = MathHelper.floor_double(this.posX - currentFrame.sizeX / 32.0F);
		}
		if (this.direction == 1) {
			z = MathHelper.floor_double(this.posZ - currentFrame.sizeX / 32.0F);
		}
		if (this.direction == 2) {
			x = MathHelper.floor_double(this.posX - currentFrame.sizeX / 32.0F);
		}
		if (this.direction == 3) {
			z = MathHelper.floor_double(this.posZ - currentFrame.sizeX / 32.0F);
		}

		y = MathHelper.floor_double(this.posY - currentFrame.sizeY / 32.0F);

		if (onWall) {
			for (int xOffset = 0; xOffset < sizeX; xOffset++) {
				for (int yOffset = 0; yOffset < sizeY; yOffset++) {
					Material supportingBlock;
					if ((this.direction == 0) || (this.direction == 2)) {
						supportingBlock = this.worldObj.getBlockState(new BlockPos(x + xOffset, y + yOffset, z)).getMaterial();
					} else {
						supportingBlock = this.worldObj.getBlockState(new BlockPos(x, y + yOffset, z + xOffset)).getMaterial();
					}

					if (!supportingBlock.isSolid()) {
						return false;
					}
				}
			}

		}

		if (currentFrame.isCollidable) {
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
			for (int l1 = 0; l1 < list.size(); l1++) {
				if ((list.get(l1) instanceof EntityFrame)) {
					EntityFrame lNeighbourFrame = (EntityFrame) list.get(l1);
					if (lNeighbourFrame.direction == this.direction) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return super.getEntityBoundingBox();
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
	public boolean attackEntityFrom(DamageSource damagesource, float damage) {
		if (!this.isDead) {

			switch (direction) {
			case 0:
				this.posZ--;
				this.posY -= 0.5D;
				break;
			case 1:
				this.posX--;
				this.posY -= 0.5D;
				break;
			case 2:
				this.posZ++;
				this.posY -= 0.5D;
				break;
			case 3:
				this.posX++;
				this.posY -= 0.5D;
				break;
			}

			if ((damagesource.getEntity() instanceof EntityPlayer)) {
				EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (!entityplayer.canPlayerEdit(pos, EnumFacing.getHorizontal(this.direction), itemstack)) {
					return false;
				}

				setDead();
				setBeenAttacked();
				playFrameSound();

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

	public void playFrameSound() {
		switch (this.material) {
		case 0:
			this.playSound(SoundEvents.BLOCK_WOOD_STEP, 1.0F, 0.8F);
			break;
		case 1:
			this.playSound(SoundEvents.BLOCK_STONE_STEP, 1.0F, 1.2F);
		}
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
		nbttagcompound.setByte("Dir", (byte) this.direction);
		nbttagcompound.setInteger("Motive", this.getFrameID());
		nbttagcompound.setInteger("TileX", this.xPosition);
		nbttagcompound.setInteger("TileY", this.yPosition);
		nbttagcompound.setInteger("TileZ", this.zPosition);

		int[] color = getFrameColor();
		nbttagcompound.setInteger("Red", color[0]);
		nbttagcompound.setInteger("Green", color[1]);
		nbttagcompound.setInteger("Blue", color[2]);
		nbttagcompound.setInteger("Material", this.material);
		nbttagcompound.setBoolean("Burnt", getBurned());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.direction = nbttagcompound.getByte("Dir");
		this.xPosition = nbttagcompound.getInteger("TileX");
		this.yPosition = nbttagcompound.getInteger("TileY");
		this.zPosition = nbttagcompound.getInteger("TileZ");

		this.pos = new BlockPos(xPosition, yPosition, zPosition);
		this.getDataManager().set(COLOR_RED, nbttagcompound.getInteger("Red"));
		this.getDataManager().set(COLOR_GREEN, nbttagcompound.getInteger("Green"));
		this.getDataManager().set(COLOR_BLUE, nbttagcompound.getInteger("Blue"));
		this.material = nbttagcompound.getInteger("Material");
		this.getDataManager().set(FRAME_ID, nbttagcompound.getInteger("Motive"));
		this.getDataManager().set(BURNT, nbttagcompound.getBoolean("Burnt"));

		setDirectionAndSize(this.direction);
		setResistance(this.material);
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeInt(this.direction);
		data.writeInt(this.xPosition);
		data.writeInt(this.yPosition);
		data.writeInt(this.zPosition);
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
		this.direction = data.readInt();
		this.xPosition = data.readInt();
		this.yPosition = data.readInt();
		this.zPosition = data.readInt();
		this.pos = new BlockPos(xPosition, yPosition, zPosition);
		this.material = data.readInt();
		this.getDataManager().set(FRAME_ID, data.readInt());

		setDirectionAndSize(this.direction);
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
		return 0;
	}

	@Override
	public int getHeightPixels() {
		return 0;
	}

	@Override
	public void onBroken(Entity entity) {
	}

	@Override
	public void playPlaceSound() {
	}
}