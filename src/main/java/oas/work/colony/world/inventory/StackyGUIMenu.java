package oas.work.colony.world.inventory;

import oas.work.colony.init.ColonyModMenus;

import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.IItemHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

public class StackyGUIMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
	public static final HashMap<String, Object> guistate = new HashMap<>();
	private final Level world;
	private final Player entity;
	private final int x, y, z;
	private final ContainerLevelAccess access;
	private final IItemHandler internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private Entity boundEntity = null;
	private BlockEntity boundBlockEntity = null;

	public StackyGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(ColonyModMenus.STACKY_GUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.internal = new ItemStackHandler(0);

		BlockPos pos = extraData != null ? extraData.readBlockPos() : null;
		if (pos != null) {
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			this.access = ContainerLevelAccess.create(world, pos);
		} else {
			this.x = this.y = this.z = 0;  // Valeur par défaut si pos est null
			this.access = ContainerLevelAccess.NULL;
		}
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.bound) {
			if (this.boundItemMatcher != null)
				return this.boundItemMatcher.get();
			else if (this.boundBlockEntity != null)
				return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
			else if (this.boundEntity != null)
				return this.boundEntity.isAlive();
		}
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public Map<Integer, Slot> get() {
		return customSlots;
	}
}
