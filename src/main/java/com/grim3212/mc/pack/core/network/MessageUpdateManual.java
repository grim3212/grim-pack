package com.grim3212.mc.pack.core.network;

import java.io.IOException;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageUpdateManual extends AbstractMessage<MessageUpdateManual> {

	private String page;

	public MessageUpdateManual() {
	}

	public MessageUpdateManual(String page) {
		this.page = page;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.page = buffer.readString(250);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeString(page);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (side == Side.SERVER) {
			System.out.println("Server");
			if (player.isEntityAlive()) {
				PacketDispatcher.sendTo(new MessageUpdateManual(page), (EntityPlayerMP) player);
				player.openGui(GrimPack.INSTANCE, PackGuiHandler.MANUAL_GUI_ID, player.world, 0, 0, 0);
			}
		} else {
			System.out.println(page);
			GuiManualIndex.activeManualPage = ManualRegistry.getPageFromString(page).getLink().copySelf();
		}
	}

}
