package com.grim3212.mc.tools;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.tools.items.ToolsItems;

public class ToolsClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
		RenderHelper.renderItem(ToolsItems.backpack);
		RenderHelper.renderItem(ToolsItems.portable_workbench);
	}

}
