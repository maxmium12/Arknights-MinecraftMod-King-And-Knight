package com.nmmoc7.kingandkinght.tileentity.abstracts;

import com.nmmoc7.kingandkinght.network.ModNetworkManager;
import com.nmmoc7.kingandkinght.network.server.ItemHandlerSyncServer;
import com.nmmoc7.kingandkinght.tileentity.ModTileEntityOpeningHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DustW
 */
public abstract class AbstractTileEntity extends TileEntity implements ITickableTileEntity {
    protected int level;
    protected ModItemStackHandlerBase handler;
    protected String registerName;

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

        public NonNullList<ItemStack> getStacks() {
            return stacks;
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
            if (!world.isRemote) {
                ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach((player) -> {
                    if (player.getPosition().distanceSq(pos) < 16) {
                        ModNetworkManager.serverSendToPlayer(new ItemHandlerSyncServer(getStackInSlot(slot).serializeNBT(), slot, pos), player);
                    }
                });
            }
        }

        public void shrinkStackUnCheck(ItemStack stack) {
            AtomicInteger amount = new AtomicInteger(stack.getCount());

            getStacks().forEach(itemStack -> {
                if (itemStack.getItem() == stack.getItem()) {
                    amount.addAndGet(-itemStack.getCount());
                    itemStack.shrink(itemStack.getCount());
                }
            });
        }
    }
}
