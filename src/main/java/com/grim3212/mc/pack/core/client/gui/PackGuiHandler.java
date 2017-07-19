package com.grim3212.mc.pack.core.client.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.decor.client.gui.GuiCage;
import com.grim3212.mc.pack.decor.client.gui.GuiGrill;
import com.grim3212.mc.pack.decor.inventory.ContainerCage;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;
import com.grim3212.mc.pack.industry.client.gui.GuiCraftingDiamond;
import com.grim3212.mc.pack.industry.client.gui.GuiCraftingIron;
import com.grim3212.mc.pack.industry.client.gui.GuiDiamondPortable;
import com.grim3212.mc.pack.industry.client.gui.GuiExtruder;
import com.grim3212.mc.pack.industry.client.gui.GuiFan;
import com.grim3212.mc.pack.industry.client.gui.GuiGoldSafe;
import com.grim3212.mc.pack.industry.client.gui.GuiIronPortable;
import com.grim3212.mc.pack.industry.client.gui.GuiItemTower;
import com.grim3212.mc.pack.industry.client.gui.GuiLocker;
import com.grim3212.mc.pack.industry.client.gui.GuiLocksmithWorkbench;
import com.grim3212.mc.pack.industry.client.gui.GuiMFurnace;
import com.grim3212.mc.pack.industry.client.gui.GuiMachine;
import com.grim3212.mc.pack.industry.client.gui.GuiSpecificSensor;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;
import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerExtruder;
import com.grim3212.mc.pack.industry.inventory.ContainerGoldSafe;
import com.grim3212.mc.pack.industry.inventory.ContainerIronWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerItemTower;
import com.grim3212.mc.pack.industry.inventory.ContainerLocker;
import com.grim3212.mc.pack.industry.inventory.ContainerLocksmithWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerMFurnace;
import com.grim3212.mc.pack.industry.inventory.ContainerMachine;
import com.grim3212.mc.pack.industry.inventory.ContainerSpecificSensor;
import com.grim3212.mc.pack.industry.inventory.InventoryDualLocker;
import com.grim3212.mc.pack.industry.inventory.InventoryItemTower;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;
import com.grim3212.mc.pack.industry.tile.TileEntityStorage;
import com.grim3212.mc.pack.tools.client.gui.GuiBackpack;
import com.grim3212.mc.pack.tools.client.gui.GuiPelletBag;
import com.grim3212.mc.pack.tools.client.gui.GuiPortable;
import com.grim3212.mc.pack.tools.inventory.BackpackInventory;
import com.grim3212.mc.pack.tools.inventory.ContainerBackpack;
import com.grim3212.mc.pack.tools.inventory.ContainerCustomWorkbench;
import com.grim3212.mc.pack.tools.inventory.ContainerPelletBag;
import com.grim3212.mc.pack.tools.inventory.PelletBagInventory;
import com.grim3212.mc.pack.util.client.gui.GuiGrave;
import com.grim3212.mc.pack.util.grave.ContainerGrave;
import com.grim3212.mc.pack.util.grave.TileEntityGrave;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class PackGuiHandler implements IGuiHandler {

	public static final int MANUAL_GUI_ID = 0;
	public static final int GRILL_GUI_ID = 1;
	public static final int IRON_GUI_ID = 2;
	public static final int DIAMOND_GUI_ID = 3;
	public static final int MODERN_FURNACE_GUI_ID = 4;
	public static final int DERRICK_GUI_ID = 5;
	public static final int REFINERY_GUI_ID = 6;
	public static final int BACKPACK_MAIN_GUI_ID = 7;
	public static final int BACKPACK_OFF_GUI_ID = 8;
	public static final int PORTABLE_MAIN_GUI_ID = 9;
	public static final int PORTABLE_OFF_GUI_ID = 10;
	public static final int EXTRUDER_GUI_ID = 11;
	public static final int FAN_GUI_ID = 12;
	public static final int CAGE_GUI_ID = 13;
	public static final int PORTABLE_DIAMOND_MAIN_GUI_ID = 14;
	public static final int PORTABLE_DIAMOND_OFF_GUI_ID = 15;
	public static final int PORTABLE_IRON_MAIN_GUI_ID = 16;
	public static final int PORTABLE_IRON_OFF_GUI_ID = 17;
	public static final int SPECIFIC_SENSOR_GUI_ID = 18;
	public static final int PELLET_BAG_MAIN_GUI_ID = 19;
	public static final int PELLET_BAG_OFF_GUI_ID = 20;
	public static final int GRAVE_GUI_ID = 21;
	public static final int STORAGE_DEFAULT_GUI_ID = 22;
	public static final int LOCKSMITH_WORKBENCH_GUI_ID = 23;
	public static final int GOLD_SAFE_GUI_ID = 24;
	public static final int LOCKER_GUI_ID = 25;
	public static final int ITEM_TOWER_GUI_ID = 26;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileentity = world.getTileEntity(pos);

		if ((ID == GRILL_GUI_ID) && ((tileentity instanceof TileEntityGrill))) {
			return new ContainerGrill(player.inventory, (TileEntityGrill) tileentity);
		} else if (ID == IRON_GUI_ID) {
			return new ContainerIronWorkbench(player, world, pos, false);
		} else if (ID == DIAMOND_GUI_ID) {
			return new ContainerDiamondWorkbench(player, world, pos, false);
		} else if (ID == MODERN_FURNACE_GUI_ID) {
			return new ContainerMFurnace(player.inventory, (TileEntityMFurnace) tileentity);
		} else if (ID == DERRICK_GUI_ID || ID == REFINERY_GUI_ID) {
			return new ContainerMachine(player.inventory, (TileEntityMachine) tileentity);
		} else if (ID == BACKPACK_MAIN_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemMainhand());
			return new ContainerBackpack(backpackInventory, player.inventory);
		} else if (ID == BACKPACK_OFF_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemOffhand());
			return new ContainerBackpack(backpackInventory, player.inventory);
		} else if (ID == PORTABLE_MAIN_GUI_ID || ID == PORTABLE_OFF_GUI_ID) {
			return new ContainerCustomWorkbench(player.inventory, world, pos);
		} else if (ID == PORTABLE_DIAMOND_MAIN_GUI_ID || ID == PORTABLE_DIAMOND_OFF_GUI_ID) {
			return new ContainerDiamondWorkbench(player, world, pos, true);
		} else if (ID == PORTABLE_IRON_MAIN_GUI_ID || ID == PORTABLE_IRON_OFF_GUI_ID) {
			return new ContainerIronWorkbench(player, world, pos, true);
		} else if (ID == EXTRUDER_GUI_ID) {
			return new ContainerExtruder(player.inventory, this.getEntityAt(world, x, y, z, EntityExtruder.class).getExtruderInv());
		} else if (ID == CAGE_GUI_ID) {
			return new ContainerCage((TileEntityCage) tileentity, player.inventory);
		} else if (ID == SPECIFIC_SENSOR_GUI_ID) {
			return new ContainerSpecificSensor(pos, player.inventory);
		} else if (ID == PELLET_BAG_MAIN_GUI_ID) {
			PelletBagInventory pelletBagInventory = new PelletBagInventory(player.getHeldItemMainhand());
			return new ContainerPelletBag(pelletBagInventory, player.inventory);
		} else if (ID == PELLET_BAG_OFF_GUI_ID) {
			PelletBagInventory pelletBagInventory = new PelletBagInventory(player.getHeldItemOffhand());
			return new ContainerPelletBag(pelletBagInventory, player.inventory);
		} else if (ID == GRAVE_GUI_ID) {
			return new ContainerGrave((TileEntityGrave) tileentity, player.inventory);
		} else if (ID == STORAGE_DEFAULT_GUI_ID) {
			return new ContainerChest(player.inventory, (TileEntityStorage) tileentity, player);
		} else if (ID == LOCKSMITH_WORKBENCH_GUI_ID) {
			return new ContainerLocksmithWorkbench(player.inventory, world, pos);
		} else if (ID == GOLD_SAFE_GUI_ID) {
			return new ContainerGoldSafe(player, (TileEntityStorage) tileentity);
		} else if (ID == LOCKER_GUI_ID) {
			return new ContainerLocker(player, new InventoryDualLocker((TileEntityStorage) tileentity, (TileEntityStorage) world.getTileEntity(pos.up())));
		} else if (ID == ITEM_TOWER_GUI_ID) {
			return this.getItemTowerContainer(player, world, x, y, z);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileentity = world.getTileEntity(pos);

		if (ID == MANUAL_GUI_ID) {
			return GuiManualIndex.activeManualPage;
		} else if (ID == IRON_GUI_ID) {
			return new GuiCraftingIron(player, world, pos);
		} else if (ID == DIAMOND_GUI_ID) {
			return new GuiCraftingDiamond(player, world, pos);
		} else if (ID == MODERN_FURNACE_GUI_ID) {
			return new GuiMFurnace(player.inventory, (TileEntityMFurnace) tileentity);
		} else if (ID == DERRICK_GUI_ID || ID == REFINERY_GUI_ID) {
			return new GuiMachine(player.inventory, (TileEntityMachine) tileentity);
		} else if ((ID == GRILL_GUI_ID) && ((tileentity instanceof TileEntityGrill))) {
			return new GuiGrill(player.inventory, (TileEntityGrill) tileentity);
		} else if (ID == BACKPACK_MAIN_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemMainhand());
			return new GuiBackpack(backpackInventory, player.inventory);
		} else if (ID == BACKPACK_OFF_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemOffhand());
			return new GuiBackpack(backpackInventory, player.inventory);
		} else if (ID == PORTABLE_MAIN_GUI_ID) {
			return new GuiPortable(player.inventory, world, pos, player.getHeldItemMainhand());
		} else if (ID == PORTABLE_OFF_GUI_ID) {
			return new GuiPortable(player.inventory, world, pos, player.getHeldItemOffhand());
		} else if (ID == EXTRUDER_GUI_ID) {
			return new GuiExtruder(player.inventory, this.getEntityAt(world, x, y, z, EntityExtruder.class).getExtruderInv());
		} else if ((ID == FAN_GUI_ID) && ((tileentity instanceof TileEntityFan))) {
			return new GuiFan((TileEntityFan) tileentity);
		} else if (ID == CAGE_GUI_ID) {
			return new GuiCage(player.inventory, (TileEntityCage) tileentity);
		} else if (ID == PORTABLE_DIAMOND_MAIN_GUI_ID) {
			return new GuiDiamondPortable(player, world, pos, player.getHeldItemMainhand());
		} else if (ID == PORTABLE_DIAMOND_OFF_GUI_ID) {
			return new GuiDiamondPortable(player, world, pos, player.getHeldItemOffhand());
		} else if (ID == PORTABLE_IRON_MAIN_GUI_ID) {
			return new GuiIronPortable(player, world, pos, player.getHeldItemMainhand());
		} else if (ID == PORTABLE_IRON_OFF_GUI_ID) {
			return new GuiIronPortable(player, world, pos, player.getHeldItemOffhand());
		} else if (ID == SPECIFIC_SENSOR_GUI_ID) {
			return new GuiSpecificSensor(player.inventory, (TileEntitySpecificSensor) tileentity, pos);
		} else if (ID == PELLET_BAG_MAIN_GUI_ID) {
			PelletBagInventory pelletBagInventory = new PelletBagInventory(player.getHeldItemMainhand());
			return new GuiPelletBag(pelletBagInventory, player.inventory);
		} else if (ID == PELLET_BAG_OFF_GUI_ID) {
			PelletBagInventory pelletBagInventory = new PelletBagInventory(player.getHeldItemOffhand());
			return new GuiPelletBag(pelletBagInventory, player.inventory);
		} else if (ID == GRAVE_GUI_ID) {
			return new GuiGrave(player.inventory, (TileEntityGrave) tileentity);
		} else if (ID == STORAGE_DEFAULT_GUI_ID) {
			return new GuiChest(player.inventory, (TileEntityStorage) tileentity);
		} else if (ID == LOCKSMITH_WORKBENCH_GUI_ID) {
			return new GuiLocksmithWorkbench(player.inventory, world, pos);
		} else if (ID == GOLD_SAFE_GUI_ID) {
			return new GuiGoldSafe(player, (TileEntityStorage) tileentity);
		} else if (ID == LOCKER_GUI_ID) {
			return new GuiLocker(player, new InventoryDualLocker((TileEntityStorage) tileentity, (TileEntityStorage) world.getTileEntity(pos.up())));
		} else if (ID == ITEM_TOWER_GUI_ID) {
			return this.getItemTowerGui(player, world, x, y, z);
		}

		return null;
	}

	public <T extends Entity> T getEntityAt(World world, int x, int y, int z, Class<T> type) {
		AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(x, y, z), new BlockPos(x + 1, y + 1, z + 1));

		List<T> entityList = world.getEntitiesWithinAABB(type, aabb);

		T entity1 = null;
		double d0 = Double.MAX_VALUE;

		for (int i = 0; i < entityList.size(); ++i) {
			T entity2 = entityList.get(i);

			if (EntitySelectors.NOT_SPECTATING.apply(entity2)) {

				double d1 = new BlockPos(x, y, z).distanceSq(entity2.posX, entity2.posY, entity2.posZ);

				if (d1 <= d0) {
					entity1 = entity2;
					d0 = d1;
				}
			}
		}

		return entity1;
	}

	private Object getItemTowerGui(EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
		List<TileEntityItemTower> itemTowers = Lists.newArrayList();
		// itemTowers.add((TileEntityItemTower) tileentity);

		int downBlocks = 1;
		while (world.getBlockState(pos.down(downBlocks)) == state) {
			downBlocks++;
		}

		int upBlocks = 1;
		BlockPos bottomPos = pos.down(downBlocks);
		while (world.getBlockState(bottomPos.up(upBlocks)) == state) {
			itemTowers.add((TileEntityItemTower) world.getTileEntity(bottomPos.up(upBlocks)));
			upBlocks++;
		}

		return new GuiItemTower(player, new InventoryItemTower(itemTowers));
	}

	private Object getItemTowerContainer(EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
		List<TileEntityItemTower> itemTowers = Lists.newArrayList();

		int downBlocks = 1;
		while (world.getBlockState(pos.down(downBlocks)) == state) {
			downBlocks++;
		}

		int upBlocks = 1;
		BlockPos bottomPos = pos.down(downBlocks);
		while (world.getBlockState(bottomPos.up(upBlocks)) == state) {
			itemTowers.add((TileEntityItemTower) world.getTileEntity(bottomPos.up(upBlocks)));
			upBlocks++;
		}

		return new ContainerItemTower(player, new InventoryItemTower(itemTowers));
	}
}