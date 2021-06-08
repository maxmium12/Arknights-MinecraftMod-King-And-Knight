package com.nmmoc7.kingandkinght.block.machine;

import com.nmmoc7.kingandkinght.itemgroup.ModItemGroups;
import com.nmmoc7.kingandkinght.machines.tileentity.InfrastructureTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
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
    public ActionResultType onBlockActivatedClient(BlockState state, World worldIn, BlockPos pos, ClientPlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResultType onBlockActivatedServer(BlockState state, World worldIn, BlockPos pos, ServerPlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        InfrastructureTileEntity tileEntity = (InfrastructureTileEntity) worldIn.getTileEntity(pos);
        openGui(player, tileEntity);
        return ActionResultType.SUCCESS;
    }

    public static <T extends TileEntity & INamedContainerProvider> void openGui(ServerPlayerEntity player, T container) {
        NetworkHooks.openGui(player, container, (PacketBuffer packerBuffer) -> {
            packerBuffer.writeBlockPos(container.getPos());
        });
    }
}
