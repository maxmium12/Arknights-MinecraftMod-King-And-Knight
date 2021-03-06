package com.nmmoc7.king_and_knight.block.machine;

import com.nmmoc7.king_and_knight.itemgroup.ModItemGroups;
import com.nmmoc7.king_and_knight.tileentity.InfrastructureTileEntity;
import com.nmmoc7.king_and_knight.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.Nullable;

/**
 * @author DustW
 */
public class InfrastructureMachineBase extends AbstractMachine {
    public final int level;

    public InfrastructureMachineBase(int level) {
        super("infrastructure_" + level,
                Properties
                        .create(Material.IRON)
                        .harvestLevel(2)
                        .setRequiresTool()
                        .harvestTool(ToolType.PICKAXE),
                ModItemGroups.MACHINE_GROUP
        );

        this.level = level;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        InfrastructureTileEntity result = new InfrastructureTileEntity();
        result.setLevel(level);
        return result;
    }

    @Override
    public ActionResultType onBlockActivatedClient(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResultType onBlockActivatedServer(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        AbstractTileEntity tileEntity = getTileEntity(worldIn, pos);
        AbstractTileEntity.ModItemStackHandlerBase handler = tileEntity.getHandler();

        if (!player.isSneaking()) {
            ItemStack heldItem = player.getHeldItem(handIn);

            handler.push(heldItem);
        }
        else {
            worldIn.addEntity(
                    new ItemEntity(
                            worldIn,
                            player.getPosX(), player.getPosY(), player.getPosZ(),
                            handler.pop())
            );
        }

        return ActionResultType.SUCCESS;
    }

    public AbstractTileEntity getTileEntity(World world, BlockPos pos) {
        return (AbstractTileEntity) world.getTileEntity(pos);
    }
}
