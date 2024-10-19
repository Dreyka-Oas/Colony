
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package oas.work.colony.init;

import oas.work.colony.client.gui.StackyGUIScreen;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColonyModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(ColonyModMenus.STACKY_GUI.get(), StackyGUIScreen::new);
	}
}
