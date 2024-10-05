
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package oas.work.colony.init;

import oas.work.colony.item.StackyItem;
import oas.work.colony.ColonyMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;

public class ColonyModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ColonyMod.MODID);
	public static final DeferredItem<Item> STACKY = REGISTRY.register("stacky", StackyItem::new);
	// Start of user code block custom items
	// End of user code block custom items
}
