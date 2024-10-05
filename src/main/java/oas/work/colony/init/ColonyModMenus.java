
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package oas.work.colony.init;

import oas.work.colony.world.inventory.StackyGUIMenu;
import oas.work.colony.ColonyMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.Registries;

public class ColonyModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, ColonyMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<StackyGUIMenu>> STACKY_GUI = REGISTRY.register("stacky_gui", () -> IMenuTypeExtension.create(StackyGUIMenu::new));
}
