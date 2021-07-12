package com.nmmoc7.king_and_knight.tileentity.abstracts;

import com.google.common.primitives.Ints;
import com.nmmoc7.king_and_knight.network.ModNetworkManager;
import com.nmmoc7.king_and_knight.network.server.ItemHandlerSyncServer;
import com.nmmoc7.king_and_knight.tileentity.ModTileEntityOpeningHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("handler", getHandler().serializeNBT());
        compound.putInt("level", level);
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        getHandler().deserializeNBT(nbt.getCompound("handler"));
        level = nbt.getInt("level");
    }

    public class ModItemStackHandlerBase extends ItemStackHandler {
        ItemStack result = ItemStack.EMPTY;
        /** ArrayList<Integer> 0: count 1+: slot */
        Map<Item, ArrayList<Integer>> count = new HashMap<>();
        int pointer = -1;

        public ModItemStackHandlerBase(int size) {
            super(size);
        }

        public NonNullList<ItemStack> getStacks() {
            return stacks;
        }

        private ArrayList<Integer> getCountSafe(Item key) {
            ArrayList<Integer> value = new ArrayList<>();
            value.add(0);
            count.put(key, value);
            return count.get(key);
        }

        public boolean push(ItemStack itemStack) {
            if (pointer < getSlots() - 1) {
                Item key = itemStack.getItem();
                pointer++;
                setStackInSlot(pointer, new ItemStack(key, 1));
                ArrayList<Integer> value = getCountSafe(key);
                value.set(0, value.size() > 0 ? value.get(0) + 1 : 1);
                value.add(pointer);
                itemStack.shrink(1);
                return true;
            }

            return false;
        }

        public ItemStack pop() {
            if (!getResult().isEmpty()) {
                return getResult();
            }

            ItemStack result = ItemStack.EMPTY;

            if (pointer >= 0) {
                result = getStackInSlot(pointer).copy();
                Item key = result.getItem();
                getCountSafe(result.getItem()).set(0, getCountSafe(result.getItem()).get(0) - 1);
                removeSlotFromArray(key, pointer);
                sortSlot(key);
                setStackInSlot(pointer, ItemStack.EMPTY);
                pointer--;
            }

            return result;
        }

        public void removeItem(ItemStack itemStack) {
            removeItem(itemStack.getItem(), itemStack.getCount());
        }

        public void removeItem(Item key, int count) {
            ArrayList<Integer> value = getCountSafe(key);
            value.set(0, value.get(0) - count);
            for (int i = 1; i < count; i++) {
                setStackInSlot(value.get(i), ItemStack.EMPTY);
                removeSlotFromArray(key, i);
            }
            sortSlot(key);
            sortStack();
        }

        public void removeSlotFromArray(Item key, int slot) {
            for (int i = 1; i < getCountSafe(key).size(); i++) {
                if (getCountSafe(key).get(i) == slot) {
                    getCountSafe(key).set(i, -1);
                    break;
                }
            }
        }

        public void sortSlot(Item key) {
            ArrayList<Integer> value = getCountSafe(key);
            int pointerTemp = 1;
            for (int i = 1; i < value.size(); i++) {
                if (value.get(i) != -1) {
                    pointerTemp++;
                }
                else {
                    value.set(pointerTemp, value.get(i));
                    pointerTemp++;
                    value.set(i, -1);
                }
            }

            if (value.size() > pointerTemp) {
                value.subList(pointerTemp, value.size()).clear();
            }
        }

        public void sortStack() {
            int pointerTemp = -1;
            for (int i = 0; i < getSlots(); i++) {
                ItemStack stack = getStackInSlot(i);
                if (stack.isEmpty() && pointerTemp == - 1) {
                    pointerTemp = i;
                }
                else if (pointerTemp != -1) {
                    setStackInSlot(pointerTemp, getStackInSlot(i).copy());
                    onContentsChanged(pointerTemp);
                    pointerTemp++;
                }
            }
        }

        @Override
        public void onContentsChanged(int slot) {
            customOnSlotChange(slot);
            markDirty();
            sync(slot);
        }

        public boolean contains(ItemStack itemStack) {
            return itemStack != null && !itemStack.isEmpty() && getCountSafe(itemStack.getItem()).get(0) >= itemStack.getCount();
        }

        public void setResult(ItemStack result) {
            this.result = result;
        }

        public ItemStack getResult() {
            return result;
        }

        public void sync(int slot) {
            String threadGroupName = Thread.currentThread().getThreadGroup().getName();
            if ("SERVER".equals(threadGroupName)) {
                ServerLifecycleHooks.getCurrentServer().getPlayerList()
                        .getPlayers().forEach((player) -> {
                    if (player.getPosition().distanceSq(pos) < 16) {
                        ModNetworkManager.serverSendToPlayer(new ItemHandlerSyncServer(getStackInSlot(slot).serializeNBT(), slot, pos), player);
                    }
                });
            }
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT result = new CompoundNBT();
            ListNBT stacks = new ListNBT();

            for (int i = 0; i < getSlots(); i++) {
                if (!getStackInSlot(i).isEmpty()) {
                    CompoundNBT stack = new CompoundNBT();
                    CompoundNBT stackIn = new CompoundNBT();
                    getStackInSlot(i).write(stackIn);
                    stack.put("stack", stackIn);
                    stack.putInt("slot", i);
                    stacks.add(stack);
                }
            }

            ListNBT countMap = new ListNBT();

            for (Map.Entry<Item, ArrayList<Integer>> entry : this.count.entrySet()) {
                CompoundNBT count = new CompoundNBT();
                count.putString("item", entry.getKey().getRegistryName().toString());
                count.put("slots", new IntArrayNBT(entry.getValue()));
                countMap.add(count);
            }

            CompoundNBT resultItem = new CompoundNBT();
            getResult().write(resultItem);

            result.put("stacks", stacks);
            result.putInt("pointer", pointer);
            result.put("count", countMap);
            result.put("result", resultItem);

            return result;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            ListNBT stacks = nbt.getList("stacks", 10);
            stacks.forEach(stackNBT -> {
                setStackInSlot(((CompoundNBT) stackNBT).getInt("slot"), ItemStack.read(((CompoundNBT) stackNBT).getCompound("stack")));
            });
            this.pointer = nbt.getInt("pointer");
            ListNBT count = nbt.getList("count", 10);
            count.forEach(countNBT -> {
                this.count.put(Registry.ITEM.getOrDefault(new ResourceLocation(((CompoundNBT) countNBT).getString("item"))),
                        new ArrayList<>(Ints.asList(((IntArrayNBT) ((CompoundNBT) countNBT).get("slots")).getIntArray())));
            });
            setResult(ItemStack.read(nbt.getCompound("result")));
        }
    }
}
