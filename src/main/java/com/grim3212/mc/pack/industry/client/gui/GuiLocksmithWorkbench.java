package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.industry.inventory.ContainerLocksmithWorkbench;
import com.grim3212.mc.pack.industry.network.MessageSetLock;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLocksmithWorkbench extends GuiContainer {

	private String password = "";
	private BlockPos pos;
	private final ContainerLocksmithWorkbench locksmith;
	private static final ResourceLocation GUILocksmith = new ResourceLocation(GrimPack.modID, "textures/gui/gui_locksmith.png");

	public GuiLocksmithWorkbench(InventoryPlayer inventory, World world, BlockPos pos) {
		super(new ContainerLocksmithWorkbench(inventory, world, pos));
		this.pos = pos;
		this.locksmith = (ContainerLocksmithWorkbench) this.inventorySlots;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public void keyTyped(char c, int i) throws IOException {
		super.keyTyped(c, i);

		if ((i == Keyboard.KEY_BACK) && (this.password.length() > 0)) {
			this.password = this.password.substring(0, this.password.length() - 1);
			this.locksmith.updateLock(this.password);
			this.mc.world.playSound(this.mc.player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.PLAYERS, 1.0F, 1.0F);
			PacketDispatcher.sendToServer(new MessageSetLock(password));
		}
		
		if ((this.password.length() < 5) && (Character.isDigit(c))) {
			this.password += c;
			this.locksmith.updateLock(this.password);
			this.mc.world.playSound(this.mc.player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.PLAYERS, 1.0F, 1.0F);
			PacketDispatcher.sendToServer(new MessageSetLock(password));
		}

		// if ((i == 28) && (this.password.length() > 0)) {
		// ItemStack leftslot = this.inventorySlots.getSlot(0).getStack();
		// if ((this.password.length() > 0) && (leftslot != null) &&
		// ((leftslot.getItem() instanceof ItemCombination))) {
		// this.mc.world.playSound(this.mc.player, pos,
		// SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.PLAYERS, 1.0F,
		// 1.0F);
		//
		// PacketDispatcher.sendToServer(new
		// MessageMoreStorage(Integer.parseInt(this.password), 0));
		// }
		// }
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(I18n.format("container.locksmith_workbench"), 38, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.locksmith_workbench.combo"), 59, 17, 7368816);
		this.fontRendererObj.drawString(this.password, 74, 40, 22015);
		// ItemStack leftslot = this.inventorySlots.getSlot(0).getStack();
		// if ((this.password.length() > 0) && (leftslot != null) &&
		// ((leftslot.getItem() instanceof ItemCombination)))
		// this.fontRendererObj.drawString(I18n.format("container.locksmith_workbench.enter_to_craft"),
		// 37, 60, 28672);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUILocksmith);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}