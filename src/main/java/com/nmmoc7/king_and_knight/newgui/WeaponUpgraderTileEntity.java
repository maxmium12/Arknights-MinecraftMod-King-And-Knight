package com.nmmoc7.king_and_knight.newgui;

import com.nmmoc7.king_and_knight.tileentity.ModTileEntities;
import com.nmmoc7.king_and_knight.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.Nullable;

/**
 * @author MaxSeth
 */
public class WeaponUpgraderTileEntity extends AbstractTileEntity implements INamedContainerProvider {
    private final IInventory inventory = new Inventory(2);

    public WeaponUpgraderTileEntity() {
        super(ModTileEntities.WEAPON_UPGRADER_TILE_ENTITY, 2, "weapon_upgrader");
    }


    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("king_and_knights.weapon_upgrade.title");
    }

    @Nullable
    @Override
    public Container createMenu(int sycID, PlayerInventory playerInv, PlayerEntity player) {
        return new ContainerWeapon(sycID, playerInv, world, pos);
    }

    public IInventory getInventory() {
        return inventory;
    }
}
