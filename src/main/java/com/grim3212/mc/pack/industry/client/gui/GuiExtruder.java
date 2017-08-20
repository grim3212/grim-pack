package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.GuiGrimContainer;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.inventory.ContainerExtruder;
import com.grim3212.mc.pack.industry.inventory.InventoryExtruder;
import com.grim3212.mc.pack.industry.network.MessageExtruderDirection;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiExtruder extends GuiGrimContainer {

	private final InventoryPlayer playerInv;
	private final InventoryExtruder extruderInv;
	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/extruder.png");

	public GuiExtruder(InventoryPlayer playerInv, InventoryExtruder extruderInv) {
		super(new ContainerExtruder(playerInv, extruderInv));
		this.playerInv = playerInv;
		this.extruderInv = extruderInv;
		this.ySize = 244;
	}

	@Override
	public void initGui() {
		super.initGui();
		int x = (width - xSize) / 2 - 50;
		int y = (height - ySize) / 2 + 50;
		final int yAdjustment = 20;
		byte width = 40;
		byte height = 20;
		buttonList.clear();
		buttonList.add(new GuiButton(0, x, y + yAdjustment * 0, width, height, I18n.format("container.extruder.down")));
		buttonList.add(new GuiButton(1, x, y + yAdjustment * 1, width, height, I18n.format("container.extruder.up")));
		buttonList.add(new GuiButton(2, x, y + yAdjustment * 2, width, height, I18n.format("container.extruder.north")));
		buttonList.add(new GuiButton(3, x, y + yAdjustment * 3, width, height, I18n.format("container.extruder.south")));
		buttonList.add(new GuiButton(4, x, y + yAdjustment * 4, width, height, I18n.format("container.extruder.west")));
		buttonList.add(new GuiButton(5, x, y + yAdjustment * 5, width, height, I18n.format("container.extruder.east")));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		PacketDispatcher.sendToServer(new MessageExtruderDirection((byte) button.id, extruderInv.getExtruder().getEntityId()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.extruderInv.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 68, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.extruder.mined"), 8, (ySize - 173) + 2, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		if (extruderInv.getExtruder() != null) {
			this.fontRenderer.drawString(I18n.format("container.extruder.fuel", extruderInv.getExtruder().getFuelAmount()), 10, 24, 0x404040);
			if (extruderInv.getStackInSlot(0) != null) {
				int stackSize = extruderInv.getExtruderSlotStackSize(0);
				this.fontRenderer.drawString(I18n.format("container.extruder.exblocks", (extruderInv.getExtruder().getFuelAmount() * (stackSize + 1)) / IndustryConfig.fuelPerExtrudedBlock), 102, 18, 0x404040);
				this.fontRenderer.drawString(I18n.format("container.extruder.mineblocks", (extruderInv.getExtruder().getFuelAmount() * (stackSize + 1)) / IndustryConfig.fuelPerMinedBlock), 102, 32, 0x404040);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_LOCATION);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

}
