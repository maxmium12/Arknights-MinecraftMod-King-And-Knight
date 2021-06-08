package com.nmmoc7.kingandkinght.machines.tileentity.abstracts;

import com.nmmoc7.kingandkinght.machines.tileentity.ModTileEntityOpeningHandler;
import com.nmmoc7.kingandkinght.network.ModNetworkManager;
import com.nmmoc7.kingandkinght.network.server.ItemHandlerSyncServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 */
public abstract class AbstractTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    protected int level;
    protected ModItemStackHandlerBase handler;
    protected ModItemNumberBase itemNumber;
    protected String registerName;
    protected ServerPlayerEntity openingPlayer;

    private AbstractTileEntity(){ super(null); }

    public AbstractTileEntity(TileEntityType<?> tileEntityTypeIn, int machineSlotSize, String registerName) {
        super(tileEntityTypeIn);

        handler = new ModItemStackHandlerBase(machineSlotSize);
        this.registerName = registerName;
    }

    @Override
    public void tick() {

    }

    public void customOnSlotChange(int slot) {

    }

    public void bindOpeningPlayer(ServerPlayerEntity player) {
        this.openingPlayer = player;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ModItemStackHandlerBase getHandler() {
        return handler;
    }

    public List<ServerPlayerEntity> getOpeningPlayers() {
        ArrayList<ServerPlayerEntity> players = new ArrayList<>();

        ModTileEntityOpeningHandler.PLAYER_OPENING_TILE_ENTITY_MAP.forEach((key, value) -> {
            if (value == getType()) {
                players.add(key);
            }
        });

        return players;
    }

    public class ModItemStackHandlerBase extends ItemStackHandler {
        ItemStack result = ItemStack.EMPTY;

        public ModItemStackHandlerBase(int size) {
            super(size);
        }

        @Override
        public void onContentsChanged(int slot) {
            customOnSlotChange(slot);
            sync(slot);
        }

        public void setResult(ItemStack result) {
            this.result = result;
        }

        public ItemStack getResult() {
            return result;
        }

        public void sync(int slot) {
            getOpeningPlayers().forEach((player) -> {
                ModNetworkManager.serverSendToPlayer(new ItemHandlerSyncServer(getStackInSlot(slot).serializeNBT(), slot, pos), player);
            });
        }
    }

    public static class ModItemNumberBase implements IIntArray {
        int[] numbers;

        public ModItemNumberBase(int size) {
            numbers = new int[size];
        }

        @Override
        public int get(int index) {
            return numbers[index];
        }

        @Override
        public void set(int index, int value) {
            numbers[index] = value;
        }

        @Override
        public int size() {
            return numbers.length;
        }
    }
}
