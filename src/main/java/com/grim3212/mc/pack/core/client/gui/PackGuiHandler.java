package com.grim3212.mc.pack.core.client.gui;

import java.util.List;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.decor.client.gui.GuiGrill;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;
import com.grim3212.mc.pack.industry.client.gui.GuiCraftingDiamond;
import com.grim3212.mc.pack.industry.client.gui.GuiCraftingIron;
import com.grim3212.mc.pack.industry.client.gui.GuiExtruder;
import com.grim3212.mc.pack.industry.client.gui.GuiFan;
import com.grim3212.mc.pack.industry.client.gui.GuiMFurnace;
import com.grim3212.mc.pack.industry.client.gui.GuiMachine;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;
import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerExtruder;
import com.grim3212.mc.pack.industry.inventory.ContainerIronWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerMFurnace;
import com.grim3212.mc.pack.industry.inventory.ContainerMachine;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;
import com.grim3212.mc.pack.tools.client.gui.GuiBackpack;
import com.grim3212.mc.pack.tools.client.gui.GuiPortable;
import com.grim3212.mc.pack.tools.inventory.BackpackInventory;
import com.grim3212.mc.pack.tools.inventory.ContainerBackpack;
import com.grim3212.mc.pack.tools.inventory.ContainerCustomWorkbench;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileentity = world.getTileEntity(pos);

		if ((ID == GRILL_GUI_ID) && ((tileentity instanceof TileEntityGrill))) {
			return new ContainerGrill(player.inventory, (TileEntityGrill) tileentity);
		} else if (ID == IRON_GUI_ID) {
			return new ContainerIronWorkbench(player.inventory, world, pos);
		} else if (ID == DIAMOND_GUI_ID) {
			return new ContainerDiamondWorkbench(player.inventory, world, pos);
		} else if (ID == MODERN_FURNACE_GUI_ID) {
			return new ContainerMFurnace(player.inventory, (TileEntityMFurnace) tileentity);
		} else if (ID == DERRICK_GUI_ID || ID == REFINERY_GUI_ID) {
			return new ContainerMachine(player.inventory, (TileEntityMachine) tileentity);
		} else if (ID == BACKPACK_MAIN_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemMainhand(), player, 0);
			return new ContainerBackpack(backpackInventory, player.inventory);
		} else if (ID == BACKPACK_OFF_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemOffhand(), player, 0);
			return new ContainerBackpack(backpackInventory, player.inventory);
		} else if (ID == PORTABLE_MAIN_GUI_ID || ID == PORTABLE_OFF_GUI_ID) {
			return new ContainerCustomWorkbench(player.inventory, world, pos);
		} else if (ID == EXTRUDER_GUI_ID) {
			return new ContainerExtruder(player.inventory, this.getEntityAt(world, x, y, z, EntityExtruder.class).getExtruderInv());
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
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemMainhand(), player, 0);
			return new GuiBackpack(backpackInventory, player.inventory);
		} else if (ID == BACKPACK_OFF_GUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.getHeldItemOffhand(), player, 0);
			return new GuiBackpack(backpackInventory, player.inventory);
		} else if (ID == PORTABLE_MAIN_GUI_ID) {
			return new GuiPortable(player.inventory, world, pos, player.getHeldItemMainhand());
		} else if (ID == PORTABLE_OFF_GUI_ID) {
			return new GuiPortable(player.inventory, world, pos, player.getHeldItemOffhand());
		} else if (ID == EXTRUDER_GUI_ID) {
			return new GuiExtruder(player.inventory, this.getEntityAt(world, x, y, z, EntityExtruder.class).getExtruderInv());
		} else if ((ID == FAN_GUI_ID) && ((tileentity instanceof TileEntityFan))) {
			return new GuiFan((TileEntityFan) tileentity);
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
}
