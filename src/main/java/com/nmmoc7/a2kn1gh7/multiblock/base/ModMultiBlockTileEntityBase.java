package com.nmmoc7.a2kn1gh7.multiblock.base;

import com.nmmoc7.a2kn1gh7.multiblock.MultiBlockTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * @author DustW
 */
public abstract class ModMultiBlockTileEntityBase extends TileEntity implements ITickableTileEntity {
    private final Lazy<IMultiblock> MULTI_BLOCK;
    private final MultiBlockItemHandler ITEM_HANDLER;

    public ModMultiBlockTileEntityBase(int slot, String[][] content, Object... targets) {
        super(MultiBlockTileEntities.MULTI_BLOCK_BASE.get());

        this.MULTI_BLOCK = () -> PatchouliAPI.get().makeMultiblock(content, targets);
        this.ITEM_HANDLER = new MultiBlockItemHandler(slot);
    }

    @Override
    public void tick() {
        if (MULTI_BLOCK.get().validate(world, pos) != null) {
            tickContext();
        }
    }

    public abstract ActionResultType onBlockActivatedServer(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit);

    public abstract ActionResultType onBlockActivatedClient(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit);

    /**
     * tick的内容
     */
    public abstract void tickContext();

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();

        write(nbt);

        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        compound.put("itemH", ITEM_HANDLER.serializeNBT());

        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        ITEM_HANDLER.deserializeNBT((CompoundNBT) nbt.get("itemH"));
    }

    private class MultiBlockItemHandler extends ItemStackHandler {
        private MultiBlockItemHandler(int slot) {
            super(slot);
        }

        @Override
        protected void onContentsChanged(int slot) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
            markDirty();
        }
    }
}
