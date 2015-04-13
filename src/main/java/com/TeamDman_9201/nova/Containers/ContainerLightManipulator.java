package com.TeamDman_9201.nova.Containers;

import com.TeamDman_9201.nova.Slots.SlotLight;
import com.TeamDman_9201.nova.Slots.SlotUpgrade;
import com.TeamDman_9201.nova.Tiles.TileLightManipulator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLightManipulator extends Container {

  private TileLightManipulator tileLightManipulator;

  public ContainerLightManipulator(InventoryPlayer playerInventory,
                                   TileLightManipulator tileLightManipulator) {
    this.tileLightManipulator = tileLightManipulator;
    playerInventory.openInventory();
    int column;
    byte b0 = 51;
    for (int i = 0; i < 7; i++) {
      this.addSlotToContainer(
          new SlotLight(tileLightManipulator, i, 44 - 18 - 18 + (18 * (i)), 20));
    }
    this.addSlotToContainer(new SlotUpgrade(tileLightManipulator, 7, 44 - 18 - 18 + 18 * 8, 20));

    for (column = 0; column < 3; ++column) {
      for (int row = 0; row < 9; ++row) {
        this.addSlotToContainer(
            new Slot(playerInventory, row + column * 9 + 9, 8 + row * 18, column * 18 + b0));
      }
    }

    for (column = 0; column < 9; ++column) {
      this.addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 58 + b0));
    }
  }

  public ItemStack transferStackInSlot(EntityPlayer player, int parSlot) {
    ItemStack itemstack = null;
    Slot slot = (Slot) this.inventorySlots.get(parSlot);

    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();

      if (parSlot < this.tileLightManipulator.getSizeInventory()) {
        if (!this.mergeItemStack(itemstack1, this.tileLightManipulator.getSizeInventory(),
                                 this.inventorySlots.size(), true)) {
          return null;
        }
      } else if (!this
          .mergeItemStack(itemstack1, 0, this.tileLightManipulator.getSizeInventory(), false)) {
        return null;
      }

      if (itemstack1.stackSize == 0) {
        slot.putStack((ItemStack) null);
      } else {
        slot.onSlotChanged();
      }
    }

    return itemstack;
  }

  @Override
  protected boolean mergeItemStack(ItemStack items, int par2Slot, int par3Slot, boolean inv) {
    boolean flag1 = false;
    int k = par2Slot;

    if (inv) {
      k = par3Slot - 1;
    }

    Slot slot;
    ItemStack itemstack1;

    if (items.isStackable()) {
      while (items.stackSize > 0 && (!inv && k < par3Slot || inv && k >= par2Slot)) {
        slot = (Slot) this.inventorySlots.get(k);
        itemstack1 = slot.getStack();

        if (itemstack1 != null && itemstack1.getItem() == items.getItem() && (
            !items.getHasSubtypes() || items.getItemDamage() == itemstack1.getItemDamage())
            && ItemStack.areItemStackTagsEqual(items, itemstack1) && slot.isItemValid(items)) {
          int l = itemstack1.stackSize + items.stackSize;

          if (l <= items.getMaxStackSize()) {
            items.stackSize = 0;
            itemstack1.stackSize = l;
            slot.onSlotChanged();
            flag1 = true;
          } else if (itemstack1.stackSize < items.getMaxStackSize()) {
            items.stackSize -= items.getMaxStackSize() - itemstack1.stackSize;
            itemstack1.stackSize = items.getMaxStackSize();
            slot.onSlotChanged();
            flag1 = true;
          }
        }

        if (inv) {
          --k;
        } else {
          ++k;
        }
      }
    }

    if (items.stackSize > 0) {
      if (inv) {
        k = par3Slot - 1;
      } else {
        k = par2Slot;
      }

      while (!inv && k < par3Slot || inv && k >= par2Slot) {
        slot = (Slot) this.inventorySlots.get(k);
        itemstack1 = slot.getStack();

        if (itemstack1 == null && slot.isItemValid(items)) {
          slot.putStack(items.copy());
          slot.onSlotChanged();
          items.stackSize = 0;
          flag1 = true;
          break;
        }

        if (inv) {
          --k;
        } else {
          ++k;
        }
      }
    }

    return flag1;
  }

  public void addCraftingToCrafters(ICrafting par1ICrafting) {
    super.addCraftingToCrafters(par1ICrafting);
  }

  @Override
  public boolean canInteractWith(EntityPlayer var1) {
    return true;
  }
}