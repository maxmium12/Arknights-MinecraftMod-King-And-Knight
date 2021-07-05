package com.nmmoc7.kingandkinght.gui;

import com.nmmoc7.kingandkinght.KingAndKnight;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class TestGuiTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private Inventory inventory = new Inventory(1);
    private TestIntArray itemNumber = new TestIntArray();

    public TestGuiTileEntity() {
        super(Regsiter.TEST_TILE_ENTITY);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.king_and_knight.test_container");
    }

    @Nullable
    @Override
    public Container createMenu(int sycID, PlayerInventory inventory, PlayerEntity player) {
        return new TestContainer(sycID, inventory, this.pos, this.world, itemNumber);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        this.inventory.addItem(ItemStack.read(nbt.getCompound("item")));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ItemStack itemStack = this.inventory.getStackInSlot(0).copy();
        compound.put("item", itemStack.serializeNBT());
        return super.write(compound);
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            this.itemNumber.set(0, this.inventory.getStackInSlot(0).getCount());
        }
    }

    public static class TestIntArray implements IIntArray {
        int i = 0;

        @Override
        public int get(int index) {
            return i;
        }

        @Override
        public void set(int index, int value) {
            i = value;
        }

        @Override
        public int size() {
            return 1;
        }
    }
}
