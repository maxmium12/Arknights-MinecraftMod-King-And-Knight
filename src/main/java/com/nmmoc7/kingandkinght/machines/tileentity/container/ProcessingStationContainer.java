package com.nmmoc7.kingandkinght.machines.tileentity.container;

import com.nmmoc7.kingandkinght.machines.tileentity.InfrastructureTileEntity;
import com.nmmoc7.kingandkinght.machines.tileentity.ModContainers;
import com.nmmoc7.kingandkinght.machines.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ProcessingStationContainer extends Container {
    CraftingInventory craftingInv = new CraftingInventory(this, 1, 5);
    CraftResultInventory craftingResult = new CraftResultInventory();
    PlayerInventory playerInv;
    BlockPos pos;
    World world;

    NonNullList<ItemStack> lastChangeSlot;

    public ProcessingStationContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(ModContainers.PROCESSING_STATION_CONTAINER_TYPE, id);

        this.pos = pos;
        this.world = world;

        lastChangeSlot = NonNullList.withSize(5, ItemStack.EMPTY);

        playerInv = playerInventory;

        tileEntitySyncToContainer();

        layoutPlayerInventorySlots(playerInventory, 8, 84);
        layoutCraftingSlots();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (!world.isRemote) {
            if (clickTypeIn == ClickType.THROW || clickTypeIn == ClickType.PICKUP || clickTypeIn == ClickType.SWAP) {
                if (slotId >= 0 && getSlot(slotId).inventory == craftingInv) {
                    Slot tmpSlot = getSlot(slotId);

                    if (!lastChangeSlot.get(tmpSlot.getSlotIndex()).equals(tmpSlot.getStack(), false)) {
                        TileEntity tileentity = world.getTileEntity(pos);

                        if (tileentity instanceof AbstractTileEntity) {
                            ((AbstractTileEntity) tileentity).bindOpeningPlayer((ServerPlayerEntity) player);
                            ((InfrastructureTileEntity) tileentity).getHandler().setStackInSlot(tmpSlot.getSlotIndex(), tmpSlot.getStack());
                            tileEntitySyncToContainer();
                        }

                        lastChangeSlot.set(tmpSlot.getSlotIndex(), tmpSlot.getStack());
                    }
                }
            }
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return playerIn.getPositionVec().distanceTo(Vector3d.copyCentered(pos)) < 6
                && playerIn.world == world;
    }

    private void layoutCraftingSlots() {
        addSlot(new Slot(craftingInv, 0, 18, 18));
        addSlot(new Slot(craftingInv, 1, 38, 18));
        addSlot(new Slot(craftingInv, 2, 58, 18));
        addSlot(new Slot(craftingInv, 3, 18, 38));
        addSlot(new Slot(craftingInv, 4, 38, 38));

        addSlot(new CraftingResultSlot(playerInv.player, craftingInv, craftingResult, 5, 58, 58));
    }

    private void layoutPlayerInventorySlots(IInventory inventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hot bar
        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IInventory inventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(inventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IInventory inventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(inventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void tileEntitySyncToContainer() {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof AbstractTileEntity) {
            for (int i = 0; i < craftingInv.getSizeInventory(); i++) {
                ItemStack item = ((AbstractTileEntity) te).getHandler().getStackInSlot(i);
                this.craftingInv.setInventorySlotContents(i, item);
            }

            this.craftingResult.setInventorySlotContents(0, ((AbstractTileEntity) te).getHandler().getResult());
        }
    }
}
