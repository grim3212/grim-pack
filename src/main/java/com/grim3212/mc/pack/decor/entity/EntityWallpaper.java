package com.grim3212.mc.pack.decor.entity;

import java.util.List;

import com.grim3212.mc.pack.core.util.WorldHelper;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;

import io.netty.buffer.ByteBuf;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWallpaper extends EntityHanging implements IEntityAdditionalSpawnData {

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int direction;
	public boolean isBlockUp;
	public boolean isBlockDown;
	public boolean isBlockLeft;
	public boolean isBlockRight;
	public AxisAlignedBB fireboundingBox;
	public static final int[] colorValues = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 8816262, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 16777215, 2236962 };
	private BlockPos pos;

	private static final DataParameter<Boolean> BURNT = EntityDataManager.createKey(EntityFrame.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> WALLPAPER_ID = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_RED = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_GREEN = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_BLUE = EntityDataManager.createKey(EntityFrame.class, DataSerializers.VARINT);

	public EntityWallpaper(World world) {
		super(world);
		this.isBlockUp = false;
		this.isBlockDown = false;
		this.isBlockLeft = false;
		this.isBlockRight = false;
		this.fireboundingBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.direction = 0;
		setSize(0.5F, 0.5F);
	}

	public EntityWallpaper(World world, BlockPos pos, int direction) {
		this(world);
		this.xPosition = pos.getX();
		this.yPosition = pos.getY();
		this.zPosition = pos.getZ();
		this.pos = pos;
		setDirection(direction);
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

	public EntityWallpaper(World world, int x, int y, int z, int direction, int wallpaper) {
		this(world);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.getDataManager().set(WALLPAPER_ID, wallpaper);
		setDirection(direction);
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
			playWallpaperSound();

		return true;
	}

	public boolean updateWallpaper(int wallpaper) {
		this.getDataManager().set(WALLPAPER_ID, wallpaper);

		if (!this.worldObj.isRemote)
			playWallpaperSound();

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
				playWallpaperSound();
			}
		}
	}

	public void dyeWallpaper(int color) {
		dyeWallpaper(color, false);
	}

	public void setDirection(int var1) {
		this.direction = var1;
		this.prevRotationYaw = (this.rotationYaw = var1 * 90);
		float var2 = 16.0F;
		float var3 = 16.0F;
		float var4 = 16.0F;
		float var5 = DecorConfig.widthWallpaper;

		if ((var1 != 0) && (var1 != 2)) {
			var2 = var5;
		} else {
			var4 = var5;
		}

		var2 /= 32.0F;
		var3 /= 32.0F;
		var4 /= 32.0F;
		var5 /= 32.0F;
		float var6 = this.xPosition + 0.5F;
		float var7 = this.yPosition + 0.5F;
		float var8 = this.zPosition + 0.5F;

		if (var1 == 0) {
			var8 -= var5 + 0.5F;
		}

		if (var1 == 1) {
			var6 -= var5 + 0.5F;
		}

		if (var1 == 2) {
			var8 += var5 + 0.5F;
		}

		if (var1 == 3) {
			var6 += var5 + 0.5F;
		}

		setPosition(var6, var7, var8);
		this.setEntityBoundingBox(new AxisAlignedBB(var6 - var2, var7 - var3, var8 - var4, var6 + var2, var7 + var3, var8 + var4));
		var2 = 1.0F;
		var3 = 1.0F;
		var4 = 1.0F;
		this.fireboundingBox = new AxisAlignedBB(var6 - var2, var7 - var3, var8 - var4, var6 + var2, var7 + var3, var8 + var4);
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
	public boolean onValidSurface() {
		Material material = this.worldObj.getBlockState(pos).getMaterial();

		if (!material.isSolid()) {
			return false;
		}

		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);

		BlockPos newPos = new BlockPos(x, y, z);
		Material newMaterial = this.worldObj.getBlockState(newPos).getMaterial();

		if (newMaterial.isSolid()) {
			IBlockState state = this.worldObj.getBlockState(newPos);

			if (!state.isOpaqueCube()) {
				return false;
			}
		}

		List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
		for (int i = 0; i < entities.size(); i++) {
			if ((entities.get(i) instanceof EntityWallpaper)) {
				EntityWallpaper entWallpaper = (EntityWallpaper) entities.get(i);

				if (entWallpaper.direction == this.direction) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canBeCollidedWith() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = player.inventory.getCurrentItem();
		return (stack != null) && ((stack.getItem() == DecorItems.wallpaper) || (stack.getItem() instanceof ItemAxe) || (stack.getItem() == Items.DYE));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damage, float amount) {
		if ((!this.isDead) && (!this.worldObj.isRemote)) {
			setDead();
			setBeenAttacked();
			EntityPlayer var3 = null;

			if ((damage.getEntity() instanceof EntityPlayer)) {
				var3 = (EntityPlayer) damage.getEntity();
				playWallpaperSound();
			}

			if ((var3 != null) && (var3.capabilities.isCreativeMode)) {
				return true;
			}

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

			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(DecorItems.wallpaper)));
		}

		return true;
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

	public void playWallpaperSound() {
		this.playSound(SoundEvents.BLOCK_CLOTH_STEP, 1.0F, 0.8F);
	}

	public void playBurnSound() {
		this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setByte("Dir", (byte) this.direction);
		nbt.setInteger("Motive", this.getWallpaperID());
		nbt.setInteger("TileX", this.xPosition);
		nbt.setInteger("TileY", this.yPosition);
		nbt.setInteger("TileZ", this.zPosition);
		nbt.setInteger("Red", this.getWallpaperColor()[0]);
		nbt.setInteger("Green", this.getWallpaperColor()[1]);
		nbt.setInteger("Blue", this.getWallpaperColor()[2]);
		nbt.setBoolean("Burnt", this.getBurned());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		this.direction = nbt.getByte("Dir");
		this.xPosition = nbt.getInteger("TileX");
		this.yPosition = nbt.getInteger("TileY");
		this.zPosition = nbt.getInteger("TileZ");

		this.pos = new BlockPos(xPosition, yPosition, zPosition);

		this.getDataManager().set(WALLPAPER_ID, nbt.getInteger("Motive"));
		this.getDataManager().set(COLOR_RED, nbt.getInteger("Red"));
		this.getDataManager().set(COLOR_GREEN, nbt.getInteger("Green"));
		this.getDataManager().set(COLOR_BLUE, nbt.getInteger("Blue"));
		this.getDataManager().set(BURNT, nbt.getBoolean("Burnt"));
		setDirection(this.direction);
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
		buf.writeInt(this.xPosition);
		buf.writeInt(this.yPosition);
		buf.writeInt(this.zPosition);
		buf.writeInt(this.getWallpaperID());
		buf.writeInt(this.direction);
		buf.writeInt(this.getWallpaperColor()[0]);
		buf.writeInt(this.getWallpaperColor()[1]);
		buf.writeInt(this.getWallpaperColor()[2]);
		buf.writeBoolean(this.getBurned());
	}

	@Override
	public void readSpawnData(ByteBuf buf) {
		this.xPosition = buf.readInt();
		this.yPosition = buf.readInt();
		this.zPosition = buf.readInt();

		this.pos = new BlockPos(xPosition, yPosition, zPosition);

		this.getDataManager().set(WALLPAPER_ID, buf.readInt());
		setDirection(buf.readInt());
		this.getDataManager().set(COLOR_RED, buf.readInt());
		this.getDataManager().set(COLOR_GREEN, buf.readInt());
		this.getDataManager().set(COLOR_BLUE, buf.readInt());
		this.getDataManager().set(BURNT, buf.readBoolean());
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