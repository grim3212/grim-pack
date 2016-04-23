package com.grim3212.mc.tools.items;

import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.tools.config.ToolsConfig;
import com.grim3212.mc.tools.util.WandCoord3D;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemBuildingWand extends ItemWand {

	private static final int BUILD_BOX = 100;
	private static final int BUILD_ROOM = 10;
	private static final int BUILD_FRAME = 1;
	private static final int BUILD_WATER = 110;
	private static final int BUILD_TORCHES = 101;
	private static final int BUILD_CAVES = 11;
	private static final int BUILD_LAVA = 111;

	public ItemBuildingWand(boolean reinforced) {
		super(reinforced);
		this.setMaxDamage(reinforced ? 200 : 30);
	}

	@Override
	protected boolean canBreak(int keys, Block block) {
		if (block == Blocks.air || block == Blocks.leaves || block == Blocks.snow || block == Blocks.fire || block == Blocks.vine || block instanceof BlockCrops || block instanceof BlockFlower) {
			return true;
		}
		switch (keys) {
		case BUILD_BOX:
		case BUILD_ROOM:
		case BUILD_FRAME:
		case BUILD_CAVES:
			return (block instanceof BlockLiquid);
		case BUILD_WATER:
		case BUILD_LAVA:
			return (block == Blocks.torch || block instanceof BlockLiquid);
		}
		return false;
	}

	@Override
	protected boolean isTooFar(int range, int maxDiff, int range2D, int keys) {
		switch (keys) {
		case BUILD_BOX:
		case BUILD_FRAME:
		case BUILD_ROOM:
		case BUILD_WATER:
		case BUILD_LAVA:
		case BUILD_TORCHES:
			return range - 400 > maxDiff;
		case BUILD_CAVES:
			return range2D - 1600 > maxDiff;
		}
		return true;
	}

	@Override
	protected double[] getParticleColor() {
		return new double[] { 1.0D, 0.8D, 0.0D };
	}

	@Override
	protected boolean isIncompatible(Block block) {
		return block == Blocks.air || block == Blocks.piston_extension || block == Blocks.piston_head || block instanceof BlockBed || block instanceof BlockDoor || block instanceof BlockSign;
	}

	private boolean canPlace(World world, BlockPos pos, Block block, int keys) {
		if (canBreak(keys, world.getBlockState(pos).getBlock())) {
			if (block.canPlaceBlockAt(world, pos))
				return true;
			if (block == Blocks.cactus || block == Blocks.reeds || block == Blocks.redstone_wire || block == Blocks.stone_pressure_plate || block == Blocks.wooden_pressure_plate || block == Blocks.snow) {
				return false;
			}
			if (block instanceof BlockTorch || block instanceof BlockFlower) {
				return false;
			}
			return true;
		}
		return false;
	}

	protected boolean consumeItems(ItemStack neededStack, EntityPlayer entityplayer, int neededItems, WandCoord3D clicked) {
		if (ToolsConfig.ENABLE_free_build_mode || entityplayer.capabilities.isCreativeMode) {
			return true;
		}
		int invItems = 0;
		// count items in inv.
		for (int t = 0; t < entityplayer.inventory.getSizeInventory(); t++) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null && currentItem.isItemEqual(neededStack)) {
				invItems += currentItem.stackSize;
				if (invItems == neededItems)
					break; // enough, no need to continue counting.
			}
		}
		if (neededItems > invItems) {
			sendMessage(entityplayer, StatCollector.translateToLocal("error.wand.toofewitems") + "(" + StatCollector.translateToLocal("error.wand.toofewitems.needed") + " " + neededItems + ", " + StatCollector.translateToLocal("error.wand.toofewitems.have") + " " + invItems + ").", false);
			return false; // abort
		}
		// remove blocks from inventory, highest positions first (quickbar last)
		for (int t = entityplayer.inventory.getSizeInventory() - 1; t >= 0; t--) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null && currentItem.isItemEqual(neededStack)) {
				int stackSize = currentItem.stackSize;
				if (stackSize < neededItems) {
					entityplayer.inventory.setInventorySlotContents(t, null);
					neededItems -= stackSize;
				} else if (stackSize >= neededItems) {
					entityplayer.inventory.decrStackSize(t, neededItems);
					neededItems = 0;
					break;
				}
			}
		}
		return true;
	}

	private boolean emptyBuckets(EntityPlayer entityplayer, int neededItems, boolean lava) {
		Item vanillaBucket = lava ? Items.lava_bucket : Items.water_bucket;

		if (ToolsConfig.ENABLE_free_build_mode || entityplayer.capabilities.isCreativeMode) {
			return true;
		}
		int itemsInInventory = 0;
		for (int t = 0; t < entityplayer.inventory.getSizeInventory(); t++) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null) {
				if (currentItem.getItem() == vanillaBucket) {
					itemsInInventory++;
				} else if (currentItem.getItem() instanceof ItemBetterBucket) {
					if (ItemBetterBucket.isEmptyOrContains(currentItem, lava ? "lava" : "water")) {
						itemsInInventory += NBTHelper.getInt(currentItem, "Amount") / FluidContainerRegistry.BUCKET_VOLUME;
					}
				}
			}
		}
		// ? error - not enough items!
		if (itemsInInventory < neededItems) {
			return false;
		}
		// remove buckets from inventory, highest positions first (quickbar
		// last)
		for (int t = entityplayer.inventory.getSizeInventory() - 1; t >= 0; t--) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null) {
				if (currentItem.getItem() == vanillaBucket) {
					entityplayer.inventory.setInventorySlotContents(t, null);
					entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.bucket));
					if (--neededItems == 0)
						return true;
				} else if (currentItem.getItem() instanceof ItemBetterBucket) {
					if (ItemBetterBucket.isEmptyOrContains(currentItem, lava ? "lava" : "water")) {
						int bucketAmount = NBTHelper.getInt(currentItem, "Amount") / FluidContainerRegistry.BUCKET_VOLUME;
						if (bucketAmount > 0) {
							int amount = neededItems - bucketAmount;

							if (amount >= 0) {
								NBTHelper.setString(currentItem, "FluidName", "empty");
								NBTHelper.setInteger(currentItem, "Amount", 0);
								neededItems -= bucketAmount;
							} else {
								amount *= -1;
								if ((NBTHelper.getInt(currentItem, "Amount") - (amount * FluidContainerRegistry.BUCKET_VOLUME)) <= 0) {
									NBTHelper.setString(currentItem, "FluidName", "empty");
									NBTHelper.setInteger(currentItem, "Amount", 0);
									return true;
								} else {
									NBTHelper.setInteger(currentItem, "Amount", amount * FluidContainerRegistry.BUCKET_VOLUME);
									return true;
								}
							}
						}

						if (neededItems <= 0)
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, Block block, int meta) {
		if (block != id1 && (keys == BUILD_BOX || keys == BUILD_ROOM || keys == BUILD_FRAME || keys == BUILD_TORCHES)) {
			error(entityplayer, clicked, "notsamecorner");
			return false;
		}
		if (meta != this.meta1 && (keys == BUILD_BOX || keys == BUILD_ROOM || keys == BUILD_FRAME || keys == BUILD_TORCHES) && !(block == Blocks.cactus || block == Blocks.reeds)) {
			if ((block != Blocks.leaves) || ((meta & 3) != (this.meta1 & 3))) {
				error(entityplayer, clicked, "notsamecorner");
				return false;
			}
		}
		boolean flag = do_Building(world, start, end, clicked, keys, entityplayer, idOrig, meta);
		if (flag && keys != BUILD_WATER && keys != BUILD_LAVA)
			world.playSoundEffect(clicked.pos.getX(), clicked.pos.getY(), clicked.pos.getZ(), "random.pop", (world.rand.nextFloat() + 0.7F) / 2.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
		if (flag && keys == BUILD_WATER)
			world.playSoundEffect(clicked.pos.getX(), clicked.pos.getY(), clicked.pos.getZ(), "liquid.water", (world.rand.nextFloat() + 0.7F) / 2.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
		if (flag && keys == BUILD_WATER)
			world.playSoundEffect(clicked.pos.getX(), clicked.pos.getY(), clicked.pos.getZ(), "liquid.lavapop", (world.rand.nextFloat() + 0.7F) / 2.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);

		return flag;
	}

	private boolean do_Building(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer, Block block, int meta) {
		int X = 0;
		int Y = 0;
		int Z = 0;
		Block blockAt = Blocks.air;
		ItemStack neededStack = getNeededItem(block, meta);
		int multiplier = getNeededCount(block, meta);
		int neededItems = 0;
		int affected = 0;
		switch (keys) {
		case BUILD_BOX:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						if (canPlace(world, new BlockPos(X, Y, Z), block, keys)) {
							neededItems += multiplier;
						}
					}
				}

			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);

							if (canPlace(world, newPos, block, keys)) {
								world.setBlockState(newPos, block.getStateFromMeta(meta), 3);
								if (this.rand.nextInt(neededItems / 50 + 1) == 0)
									particles(world, newPos, 0);
								affected++;
							}
						}
					}
				}

				if ((this.idOrig == Blocks.grass) && (affected > 0)) {
					for (int run = 0; run <= 1; run++) {
						if (run == 0)
							Y = start.pos.getY();
						if (run == 1) {
							Y = end.pos.getY();
						}
						for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
							for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
								BlockPos newPos = new BlockPos(X, Y, Z);

								if ((world.getBlockState(newPos).getBlock() == Blocks.dirt) && ((world.getBlockState(newPos.up()).getBlock() == null) || (!world.getBlockState(newPos.up()).getBlock().isOpaqueCube()))) {
									world.setBlockState(newPos, Blocks.grass.getDefaultState());
								}
							}
						}
					}
				}

				return affected > 0;
			}
			return false;
		case BUILD_ROOM:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						if (((X == start.pos.getX()) || (Y == start.pos.getY()) || (Z == start.pos.getZ()) || (X == end.pos.getX()) || (Y == end.pos.getY()) || (Z == end.pos.getZ())) && (canPlace(world, new BlockPos(X, Y, Z), block, keys))) {
							neededItems += multiplier;
						}
					}
				}

			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							if (((X == start.pos.getX()) || (Y == start.pos.getY()) || (Z == start.pos.getZ()) || (X == end.pos.getX()) || (Y == end.pos.getY()) || (Z == end.pos.getZ())) && (canPlace(world, newPos, block, keys))) {
								world.setBlockState(newPos, block.getStateFromMeta(meta), 3);
								if (this.rand.nextInt(neededItems / 50 + 1) == 0)
									particles(world, newPos, 0);
								affected++;
							}
						}
					}

				}

				if ((this.idOrig == Blocks.grass) && (affected > 0)) {
					for (int run = 0; run <= 1; run++) {
						if (run == 0)
							Y = start.pos.getY();
						if (run == 1) {
							Y = end.pos.getY();
						}
						for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
							for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
								BlockPos newPos = new BlockPos(X, Y, Z);

								if ((world.getBlockState(newPos).getBlock() == Blocks.dirt) && ((world.getBlockState(newPos.up()).getBlock() == null) || (!world.getBlockState(newPos.up()).getBlock().isOpaqueCube()))) {
									world.setBlockState(newPos, Blocks.grass.getDefaultState());
								}
							}
						}
					}
				}
				return affected > 0;
			}
			return false;
		case BUILD_FRAME:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						if (((X == start.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == start.pos.getX())) || ((X == start.pos.getX()) && (Y == end.pos.getY())) || ((X == end.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == end.pos.getZ())) || ((Y == end.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == end.pos.getX())) || ((Z == end.pos.getZ()) && (X == start.pos.getX())) || ((X == end.pos.getX()) && (Y == end.pos.getY()))
								|| ((Y == end.pos.getY()) && (Z == end.pos.getZ())) || ((Z == end.pos.getZ()) && (X == end.pos.getX()) && (canPlace(world, new BlockPos(X, Y, Z), block, keys)))) {
							neededItems += multiplier;
						}
					}
				}

			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							if (((X == start.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == start.pos.getX())) || ((X == start.pos.getX()) && (Y == end.pos.getY())) || ((X == end.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == end.pos.getZ())) || ((Y == end.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == end.pos.getX())) || ((Z == end.pos.getZ()) && (X == start.pos.getX())) || ((X == end.pos.getX()) && (Y == end.pos.getY()))
									|| ((Y == end.pos.getY()) && (Z == end.pos.getZ())) || ((Z == end.pos.getZ()) && (X == end.pos.getX()))) {
								BlockPos newPos = new BlockPos(X, Y, Z);
								blockAt = world.getBlockState(newPos).getBlock();

								if (canPlace(world, newPos, block, keys)) {
									world.setBlockState(newPos, block.getStateFromMeta(meta), 3);
									if (this.rand.nextInt(neededItems / 50 + 1) == 0)
										particles(world, newPos, 0);
									affected++;
								}
							}
						}
					}
				}

				if ((this.idOrig == Blocks.grass) && (affected > 0)) {
					for (int run = 0; run <= 1; run++) {
						if (run == 0)
							Y = start.pos.getY();
						if (run == 1) {
							Y = end.pos.getY();
						}
						for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
							for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
								BlockPos newPos = new BlockPos(X, Y, Z);
								if (((X == start.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == start.pos.getX())) || ((X == start.pos.getX()) && (Y == end.pos.getY())) || ((X == end.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == end.pos.getZ())) || ((Y == end.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == end.pos.getX())) || ((Z == end.pos.getZ()) && (X == start.pos.getX())) || ((X == end.pos.getX()) && (Y == end.pos.getY()))
										|| ((Y == end.pos.getY()) && (Z == end.pos.getZ())) || ((Z == end.pos.getZ()) && (X == end.pos.getX()) && (world.getBlockState(newPos).getBlock() == Blocks.dirt) && ((world.getBlockState(newPos.up()).getBlock() == null) || (!world.getBlockState(newPos.up()).getBlock().isOpaqueCube())))) {
									world.setBlockState(newPos, Blocks.grass.getDefaultState());
								}
							}
						}
					}

				}

				return affected > 0;
			}
			return false;
		case BUILD_TORCHES:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X += 5) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z += 5) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						BlockPos newPos = new BlockPos(X, Y, Z);
						blockAt = world.getBlockState(newPos).getBlock();

						if (canPlace(world, newPos, block, keys)) {
							neededItems += multiplier;
						}
					}
				}
			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X += 5) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z += 5) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();

							if (canPlace(world, newPos, block, keys)) {
								world.setBlockState(newPos, block.getStateFromMeta(meta), 3);
								particles(world, newPos, 0);
								affected++;
							}
						}
					}
				}
				return affected > 0;
			}
			return false;
		case BUILD_WATER:
			if ((!this.reinforced) && (!FREE)) {
				error(entityplayer, clicked, "cantfillwater");
				return false;
			}

			if (!FREE) {
				neededItems = 0;

				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();

							if (canBreak(keys, blockAt)) {
								neededItems++;
							}
						}
					}
				}

				if (neededItems == 0) {
					if (!world.isRemote)
						error(entityplayer, clicked, "nowork");
					return false;
				}
			}

			if (emptyBuckets(entityplayer, 2, false)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();

							if (canBreak(keys, blockAt)) {
								world.setBlockState(newPos, Blocks.flowing_water.getDefaultState());
								affected++;
							}
						}
					}
				}

				if (affected == 0) {
					return false;
				}
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();

							if (blockAt == Blocks.flowing_water) {
								world.notifyBlockOfStateChange(newPos, Blocks.flowing_water);
								if (world.getBlockState(newPos.up()).getBlock() == Blocks.air)
									particles(world, newPos, 2);
							}
						}
					}
				}
				return true;
			}
			error(entityplayer, clicked, "toofewwater");

			return false;
		case BUILD_LAVA:
			if ((!this.reinforced) && (!FREE)) {
				error(entityplayer, clicked, "cantfilllava");
				return false;
			}

			neededItems = 0;

			if (!FREE) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();

							if (canBreak(keys, blockAt)) {
								neededItems++;
							}
						}
					}
				}
				if (neededItems == 0) {
					if (!world.isRemote)
						error(entityplayer, clicked, "nowork");
					return false;
				}
			}

			if (emptyBuckets(entityplayer, neededItems, true)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();
							if (canBreak(keys, blockAt)) {
								world.setBlockState(newPos, Blocks.flowing_lava.getDefaultState());
								affected++;
							}
						}
					}
				}

				if (affected == 0) {
					return false;
				}
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							blockAt = world.getBlockState(newPos).getBlock();
							if (blockAt == Blocks.flowing_lava) {
								world.notifyBlockOfStateChange(newPos, Blocks.flowing_lava);
							}
						}
					}
				}
				return true;
			}
			error(entityplayer, clicked, "toofewlava");

			return false;
		case BUILD_CAVES:
			if ((!this.reinforced) && (!FREE)) {
				error(entityplayer, clicked, "cantfillcave");
				return false;
			}

			boolean underground = false;
			long cnt = 0L;
			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					underground = false;
					for (Y = 127; Y > 1; Y--) {
						BlockPos newPos = new BlockPos(X, Y, Z);
						blockAt = world.getBlockState(newPos).getBlock();

						boolean surfaceBlock = isSurface(blockAt);

						if ((!underground) && (surfaceBlock)) {
							underground = true;
						} else if (underground) {
							if (canBreak(keys, blockAt)) {
								world.setBlockState(newPos, Blocks.stone.getDefaultState());
								cnt += 1L;
							}
						}
					}
				}
			}
			if (cnt > 0L) {
				if (!world.isRemote)
					sendMessage(entityplayer, cnt + StatCollector.translateToLocal("result.wand.fill"), false);
				return true;
			} else {
				if (!world.isRemote)
					error(entityplayer, clicked, "nocave");
				return false;
			}
		}

		return false;
	}
}
