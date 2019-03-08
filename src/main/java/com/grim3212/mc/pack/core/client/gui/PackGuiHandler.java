package com.grim3212.mc.pack.core.client.gui;

import java.util.List;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class PackGuiHandler {

	public static GuiScreen openGui(FMLPlayMessages.OpenContainer openContainer) {
		if (openContainer.getId().equals(new ResourceLocation("grimpack:instruction_manual"))) {
			return GuiManualIndex.activeManualPage;
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

			if (EntitySelectors.NOT_SPECTATING.test(entity2)) {

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