package com.nmmoc7.kingandkinght.block.machine;

import com.nmmoc7.kingandkinght.block.base.ModBlockBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * @author DustW
 */
public abstract class AbstractMachine extends ModBlockBase {
    public AbstractMachine(String regName, Properties properties, ItemGroup group) {
        super(regName, properties, group);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return onBlockActivatedClient(state, worldIn, pos, player, handIn, hit);
        }
        else {
            return onBlockActivatedServer(state, worldIn, pos, player, handIn, hit);
        }
    }

    public abstract ActionResultType onBlockActivatedClient(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit);

    public abstract ActionResultType onBlockActivatedServer(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit);
}
