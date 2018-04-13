package com.grim3212.mc.pack.tools.items;

import java.util.Iterator;
import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.util.EnumWandType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemMagicWand extends ItemManual {

	public ItemMagicWand() {
		super("magic_wand");
		setHasSubtypes(true);
		setMaxStackSize(1);
		setMaxDamage(0);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (EnumWandType.getWandType(stack) != null) {
			return super.getUnlocalizedName() + "." + EnumWandType.getWandType(stack).getUnlocalized();
		}

		return super.getUnlocalizedName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab))
			for (int j = 0; j < EnumWandType.values.length; ++j) {
				ItemStack wand = new ItemStack(this, 1, EnumWandType.values[j].getStoneType().getMeta());
				NBTHelper.setInteger(wand, "WandDamage", 0);
				items.add(wand);
			}
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return this.getDamage(stack) > 0;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		NBTHelper.setInteger(stack, "WandDamage", damage);

		if (this.getDamage(stack) < 0) {
			NBTHelper.setInteger(stack, "WandDamage", 0);
		}
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return EnumWandType.getWandType(stack).getMaxUses();
	}

	@Override
	public int getDamage(ItemStack stack) {
		return NBTHelper.getInt(stack, "WandDamage");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		EnumWandType type = EnumWandType.getWandType(stack);
		int damage = 0;

		// TODO: Check first if we can continue operating if damage exceeds 1
		switch (type) {
		case BOOM:
			worldIn.createExplosion(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, type.getRange(), true);
			damage = 1;
			break;
		case DEATH:
			damage = performDeath(worldIn, playerIn, type.getRange());
			break;
		case FIRE:
			damage = performFireBall(worldIn, playerIn, type.getRange());
			break;
		case POLYMORPH:
			damage = performPolymorph(worldIn, playerIn, type.getRange());
			break;
		case ARMOR:
			damage = performArmorRepair(worldIn, playerIn, type.getRange());
			break;
		case HEAL:
			damage = performHeal(worldIn, playerIn, type.getRange());
			break;
		case HOME:
			damage = performHome(worldIn, playerIn, type.getRange());
			break;
		case HUNGER:
			damage = performHunger(worldIn, playerIn, type.getRange());
			break;
		case ITEM:
			damage = performItemRepair(worldIn, playerIn, type.getRange());
			break;
		}

		playerIn.swingArm(handIn);

		if (!playerIn.isCreative())
			stack.damageItem(damage, playerIn);

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	public int performDeath(World world, EntityPlayer playerIn, float range) {
		int i = MathHelper.floor(playerIn.posX - (double) range - 1.0D);
		int j = MathHelper.floor(playerIn.posX + (double) range + 1.0D);
		int k = MathHelper.floor(playerIn.posY - (double) range - 1.0D);
		int l = MathHelper.floor(playerIn.posY + (double) range + 1.0D);
		int i1 = MathHelper.floor(playerIn.posZ - (double) range - 1.0D);
		int j1 = MathHelper.floor(playerIn.posZ + (double) range + 1.0D);

		int count = 0;

		// Construct the bounding box and grab all of the entities
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(i, k, i1, j, l, j1));

		for (int k1 = 0; k1 < list.size(); k1++) {
			Entity entity = (Entity) list.get(k1);
			if (entity instanceof EntityLivingBase) {
				// Attack the entities
				entity.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), 20);

				count++;
			}
		}

		return count;
	}

	public int performFireBall(World world, EntityPlayer playerIn, float range) {
		EntityFireball entityfireball = new EntitySmallFireball(world);
		entityfireball.setLocationAndAngles(playerIn.posX, playerIn.posY + (double) playerIn.getEyeHeight(), playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
		entityfireball.posX -= MathHelper.cos((entityfireball.rotationYaw / 180F) * 3.141593F) * 0.16F;
		entityfireball.posY -= 0.10000000149011612D;
		entityfireball.posZ -= MathHelper.sin((entityfireball.rotationYaw / 180F) * 3.141593F) * 0.16F;
		entityfireball.setPosition(entityfireball.posX, entityfireball.posY, entityfireball.posZ);

		entityfireball.accelerationX = -MathHelper.sin((entityfireball.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((entityfireball.rotationPitch / 180F) * 3.141593F);
		entityfireball.accelerationZ = MathHelper.cos((entityfireball.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((entityfireball.rotationPitch / 180F) * 3.141593F);
		entityfireball.accelerationY = -MathHelper.sin((entityfireball.rotationPitch / 180F) * 3.141593F);

		Vec3d vec3d = playerIn.getLook(1.0F);
		entityfireball.posX = entityfireball.posX + vec3d.x * 2D;
		entityfireball.posY = entityfireball.posY + vec3d.y * 2D;
		entityfireball.posZ = entityfireball.posZ + vec3d.z * 2D;
		entityfireball.shootingEntity = playerIn;

		if (!world.isRemote)
			world.spawnEntity(entityfireball);

		return 1;
	}

	public int performPolymorph(World world, EntityPlayer playerIn, float range) {
		int i = MathHelper.floor(playerIn.posX - (double) range - 1.0D);
		int j = MathHelper.floor(playerIn.posX + (double) range + 1.0D);
		int k = MathHelper.floor(playerIn.posY - (double) range - 1.0D);
		int l = MathHelper.floor(playerIn.posY + (double) range + 1.0D);
		int i1 = MathHelper.floor(playerIn.posZ - (double) range - 1.0D);
		int j1 = MathHelper.floor(playerIn.posZ + (double) range + 1.0D);

		int count = 0;

		// Construct the bounding box and grab all of the entities
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(i, k, i1, j, l, j1));

		for (int k1 = 0; k1 < list.size(); k1++) {
			Entity entity = (Entity) list.get(k1);

			if (!(entity instanceof EntityLivingBase)) {
				continue;
			}

			if (!(entity instanceof EntityMob) && !(entity instanceof EntityFlying)) {
				continue;
			}

			if (this.convertEntity(world, playerIn, entity)) {
				count++;
			}
		}

		return count;
	}

	public boolean convertEntity(World world, EntityPlayer playerIn, Entity entity) {
		Entity passiveEntity = EntityList.createEntityByIDFromName(new ResourceLocation(getRandomPassiveMob(world)), world);

		if (passiveEntity == null) {
			return false;
		}

		double d = entity.posX;
		double d1 = entity.posY;
		double d2 = entity.posZ;

		passiveEntity.setLocationAndAngles(d, d1, d2, world.rand.nextFloat() * 360F, 0.0F);
		if (!world.isRemote)
			world.spawnEntity(passiveEntity);
		spawnExplosionParticle(world, passiveEntity);
		entity.setDead();

		for (int i = 0; i < 20; i++) {
			double d3 = world.rand.nextGaussian() * 0.02D;
			double d4 = world.rand.nextGaussian() * 0.02D;
			double d5 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.HEART, passiveEntity.posX + (double) (world.rand.nextFloat() * passiveEntity.width), passiveEntity.posY + (double) (world.rand.nextFloat() * passiveEntity.height), passiveEntity.posZ + (double) (world.rand.nextFloat() * passiveEntity.width), d3, d4, d5);
		}

		return true;
	}

	public void spawnExplosionParticle(World world, Entity entity) {
		for (int i = 0; i < 20; i++) {
			double d = world.rand.nextGaussian() * 0.02D;
			double d1 = world.rand.nextGaussian() * 0.02D;
			double d2 = world.rand.nextGaussian() * 0.02D;
			double d3 = 10D;
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (entity.posX + (double) (world.rand.nextFloat() * entity.width * 2.0F)) - (double) entity.width - d * d3, (entity.posY + (double) (world.rand.nextFloat() * entity.height)) - d1 * d3, (entity.posZ + (double) (world.rand.nextFloat() * entity.width * 2.0F)) - (double) entity.width - d2 * d3, d, d1, d2);
		}
	}

	public int performHeal(World world, EntityPlayer playerIn, float range) {
		if (playerIn.getHealth() < playerIn.getMaxHealth()) {

			float toHeal = MathHelper.clamp((playerIn.getMaxHealth() - playerIn.getHealth()), 0, 10);

			playerIn.heal(toHeal);

			for (int i = 0; i < 20; i++) {
				double d3 = world.rand.nextGaussian() * 0.02D;
				double d4 = world.rand.nextGaussian() * 0.02D;
				double d5 = world.rand.nextGaussian() * 0.02D;
				world.spawnParticle(EnumParticleTypes.HEART, playerIn.posX + (double) (world.rand.nextFloat() * playerIn.width), playerIn.posY + (double) (world.rand.nextFloat() * playerIn.height), playerIn.posZ + (double) (world.rand.nextFloat() * playerIn.width), d3, d4, d5);
			}

			return (int) toHeal;
		}

		return 0;
	}

	public int performHunger(World world, EntityPlayer playerIn, float range) {
		if (playerIn.getFoodStats().needFood()) {

			int toEat = MathHelper.clamp((20 - playerIn.getFoodStats().getFoodLevel()), 0, 10);

			playerIn.getFoodStats().addStats(toEat, 0.3f);

			for (int i = 0; i < 20; i++) {
				double d3 = world.rand.nextGaussian() * 0.02D;
				double d4 = world.rand.nextGaussian() * 0.02D;
				double d5 = world.rand.nextGaussian() * 0.02D;
				world.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, playerIn.posX + (double) (world.rand.nextFloat() * playerIn.width), playerIn.posY + (double) (world.rand.nextFloat() * playerIn.height), playerIn.posZ + (double) (world.rand.nextFloat() * playerIn.width), d3, d4, d5);
			}

			return toEat;
		}

		return 0;
	}

	public int performItemRepair(World world, EntityPlayer playerIn, float range) {
		int count = 0;

		for (int i = 0; i < 9; i++) {
			ItemStack stack = playerIn.inventory.getStackInSlot(i);

			if (!stack.isEmpty() && stack.isItemStackDamageable()) {
				int toRepair = stack.getMaxDamage() - stack.getItemDamage();

				stack.damageItem(-toRepair, null);

				count += toRepair / 2;
			}
		}

		// Also check offhand
		ItemStack offStack = playerIn.inventory.offHandInventory.get(0);

		if (!offStack.isEmpty() && offStack.isItemStackDamageable()) {
			int toRepair = offStack.getMaxDamage() - offStack.getItemDamage();

			offStack.damageItem(-toRepair, null);

			count += toRepair / 2;
		}

		return count;
	}

	public int performArmorRepair(World world, EntityPlayer playerIn, float range) {
		Iterator<ItemStack> itr = playerIn.getArmorInventoryList().iterator();

		int count = 0;

		while (itr.hasNext()) {
			ItemStack stack = itr.next();

			if (!stack.isEmpty() && stack.getItem() instanceof ItemArmor && stack.getItemDamage() != 0) {
				int toRepair = stack.getMaxDamage() - stack.getItemDamage();

				stack.damageItem(-toRepair, null);

				count += toRepair / 2;
			}
		}

		return count;
	}

	public int performHome(World world, EntityPlayer playerIn, float range) {
		float x = world.getSpawnPoint().getX();
		float y = world.getSpawnPoint().getY();
		float z = world.getSpawnPoint().getZ();
		if (!world.isRemote) {
			if (playerIn instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) playerIn;
				BlockPos spawnPoint = player.getBedLocation(player.world.provider.getDimension());
				boolean flag = playerIn.isSpawnForced(world.provider.getDimension());
				
				if (spawnPoint != null) {
					
					BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation(world, spawnPoint, flag);
					
					if (blockpos1 != null)
		            {
		                player.setSpawnPoint(spawnPoint, flag);
		                
		                if (player.timeUntilPortal == 0) {
							player.connection.setPlayerLocation(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), player.cameraYaw, player.cameraPitch);

							while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty() && player.posY < 256.0D) {
								player.connection.setPlayerLocation(player.posX, player.posY + 1.0D, player.posZ, player.cameraYaw, player.cameraPitch);
							}

							player.timeUntilPortal = 3;
						}
		            }
		            else
		            {
		            	player.connection.sendPacket(new SPacketChangeGameState(0, 0.0F));
		            	
		            	BlockPos pos  = world.getSpawnPoint();
		            	
		            	if (player.timeUntilPortal == 0) {
							player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), player.cameraYaw, player.cameraPitch);

							while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty() && player.posY < 256.0D) {
								player.connection.setPlayerLocation(player.posX, player.posY + 1.0D, player.posZ, player.cameraYaw, player.cameraPitch);
							}

							player.timeUntilPortal = 3;
						}
		            }
				} else {
					if (player.timeUntilPortal == 0) {
						player.connection.setPlayerLocation(x, y, z, player.cameraYaw, player.cameraPitch);

						while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty() && player.posY < 256.0D) {
							player.connection.setPlayerLocation(player.posX, player.posY + 1.0D, player.posZ, player.cameraYaw, player.cameraPitch);
						}

						player.timeUntilPortal = 3;
					}
				}
			} else {
				playerIn.setPosition(x, y, z);

				while (!world.getCollisionBoxes(playerIn, playerIn.getEntityBoundingBox()).isEmpty() && playerIn.posY < 256.0D) {
					playerIn.setPosition(playerIn.posX, playerIn.posY + 1.0D, playerIn.posZ);
				}
			}
		}
		
		playerIn.world.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.AMBIENT, 1, 1);
		
		for (int i = 0; i < 20; i++) {
			double d3 = world.rand.nextGaussian() * 0.02D;
			double d4 = world.rand.nextGaussian() * 0.02D;
			double d5 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.PORTAL, playerIn.posX + (double) (world.rand.nextFloat() * playerIn.width), playerIn.posY + (double) (world.rand.nextFloat() * playerIn.height), playerIn.posZ + (double) (world.rand.nextFloat() * playerIn.width), d3, d4, d5);
		}

		return 1;
	}

	// TODO: Make this a config option
	public String getRandomPassiveMob(World world) {
		int i = world.rand.nextInt(17);
		if (i == 0) {
			return "minecraft:rabbit";
		} else if (i == 1) {
			return "minecraft:cow";
		} else if (i == 2) {
			return "minecraft:polar_bear";
		} else if (i == 3) {
			return "minecraft:pig";
		} else if (i == 4) {
			return "minecraft:snowman";
		} else if (i == 5) {
			return "minecraft:bat";
		} else if (i == 6) {
			return "minecraft:llama";
		} else if (i == 7) {
			return "minecraft:chicken";
		} else if (i == 8) {
			return "minecraft:donkey";
		} else if (i == 9) {
			return "minecraft:parrot";
		} else if (i == 10) {
			return "minecraft:mule";
		} else if (i == 11) {
			return "minecraft:sheep";
		} else if (i == 12) {
			return "minecraft:ocelot";
		} else if (i == 13) {
			return "minecraft:wolf";
		} else if (i == 14) {
			return "minecraft:mooshroom";
		} else if (i == 15) {
			return "minecraft:horse";
		} else if (i == 16) {
			return "minecraft:squid";
		} else {
			return "minecraft:chicken";
		}
	}
}