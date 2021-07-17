package com.nmmoc7.king_and_knight.newgui;

import com.nmmoc7.king_and_knight.block.base.ModBlockBase;
import com.nmmoc7.king_and_knight.itemgroup.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class WeaponUpgraderBlock extends ModBlockBase {
    public WeaponUpgraderBlock() {
        super("weapon_upgrader", Material.IRON, ModItemGroups.BLOCK_GROUP);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WeaponUpgraderTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
            WeaponUpgraderTileEntity tile = (WeaponUpgraderTileEntity) worldIn.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, tile, (PacketBuffer packerBuffer) -> {
                packerBuffer.writeBlockPos(tile.getPos());
            });
        }
        return ActionResultType.SUCCESS;
    }
}
