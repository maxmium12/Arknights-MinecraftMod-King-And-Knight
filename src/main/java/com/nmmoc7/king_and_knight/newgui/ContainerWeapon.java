package com.nmmoc7.king_and_knight.newgui;

import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

/**
 * @author MaxSeth
 */
public class ContainerWeapon extends Container {
    private WeaponUpgraderTileEntity tile;
    protected ContainerWeapon(int id, PlayerInventory playerInv, World world, BlockPos pos) {
        super(ContainerRegister.WEAPON_UPGRADER_CONTAINER.get(), id);
        tile = (WeaponUpgraderTileEntity) world.getTileEntity(pos);
        IInventory inv = tile.getInventory();
        //创建一个玩家够不到的格子
        addSlot(new Slot(inv, 0, 10000, 10000){
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof AbstractWeapon;
            }
        });

        this.addSlot(new Slot(inv, 1, 116, 35) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof ItemExperienceCard;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return tile.getPos().distanceSq(playerIn.getPosition()) <= 64;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            ItemStack stack = slotStack.copy();
            if (index == 1) {
                if (!this.mergeItemStack(slotStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, stack);
            }else if(slotStack.getItem() instanceof ItemExperienceCard){
                if (!this.mergeItemStack(slotStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.mergeItemStack(slotStack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.mergeItemStack(slotStack, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }
        return ItemStack.EMPTY;
    }
}
