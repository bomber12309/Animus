package com.TeamDman_9201.nova.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockCompressedTorch extends ItemBlock {

  // Cazzar> Teamy, create another class, which extends that, and register it
  // with the block.
  Block cTorch;

  public ItemBlockCompressedTorch(Block arg) {
    super(arg);
    cTorch = arg;
  }

  public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
    if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Torches")) {
      list.add("Torches: " + itemStack.getTagCompound().getLong("Torches"));
      // list.add(EnumChatFormatting.GREEN + "code: " + code);
    }
  }
}