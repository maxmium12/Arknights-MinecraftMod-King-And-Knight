package com.nmmoc7.a2kn1gh7.multiblock;

import com.nmmoc7.a2kn1gh7.block.ModBlocks;
import com.nmmoc7.a2kn1gh7.multiblock.base.ModMultiBlockCoreSupplierBase;
import com.nmmoc7.a2kn1gh7.multiblock.base.ModMultiBlockTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * @author DustW
 */
public class TestMultiBlockTileEntity extends ModMultiBlockTileEntityBase {
    public TestMultiBlockTileEntity() {
        super(5,
                new String[][] {
                        {
                                "AAAAA",
                                "ACCCA",
                                "ACCCA",
                                "ACCCA",
                                "AAAAA"
                        },
                        {
                                "AAAAA",
                                "ACCCA",
                                "ACCCA",
                                "ACCCA",
                                "AAAAA"
                        },
                        {
                                "AAAAA",
                                "ACCCA",
                                "AC0CA",
                                "ACCCA",
                                "AAAAA"
                        }
                },
                'A', ModBlocks.BUILDING_MATERIAL.get(),
                '0', ModMultiBlockCores.TEST_CORE.get(),
                'C', Blocks.AIR);
    }

    @Override
    public ActionResultType onBlockActivatedServer(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ActionResultType.PASS;
    }

    @Override
    public ActionResultType onBlockActivatedClient(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ActionResultType.PASS;
    }

    /**
     * tick的内容
     */
    @Override
    public void tickContext() {
        if (world.isRemote) {
            Minecraft.getInstance().player.sendChatMessage("121");
            Minecraft.getInstance().player.sendChatMessage(getBlockState().getValues().get(ModMultiBlockCoreSupplierBase.FACING).toString());
        }
    }
}
