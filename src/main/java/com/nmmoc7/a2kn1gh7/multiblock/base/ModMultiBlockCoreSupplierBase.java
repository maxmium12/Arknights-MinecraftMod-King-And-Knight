package com.nmmoc7.a2kn1gh7.multiblock.base;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import com.nmmoc7.a2kn1gh7.itemgroup.ModItemGroups;
import com.nmmoc7.a2kn1gh7.multiblock.ModMultiBlockCores;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class ModMultiBlockCoreSupplierBase implements Supplier<ModMultiBlockCoreSupplierBase.ModMultiBlockCoreBase> {
    private ModMultiBlockCoreBase CORE;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape OUT_SHAPE = VoxelShapes.fullCube();
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            OUT_SHAPE, Block.makeCuboidShape(
                    2.0D, 2.0D, 2.0D,
                    14.0D, 16.0D, 14.0D),
            IBooleanFunction.ONLY_FIRST);

    public ModMultiBlockCoreSupplierBase(String name, Material material, Lazy<TileEntity> tileEntity) {
        AbstractBlock.Properties properties = AbstractBlock.Properties.create(material)
                .hardnessAndResistance(1)
                .harvestLevel(1)
                .doesNotBlockMovement()
                .harvestTool(ToolType.PICKAXE);
        this.CORE = new ModMultiBlockCoreBase(name, properties, tileEntity);

        Item.Properties blockItemProperties = new Item.Properties().group(ModItemGroups.MULTI_BLOCK_GROUP);
        new ModMultiBlockCoreItemBase(CORE, blockItemProperties);
    }

    @Override
    public ModMultiBlockCoreBase get() {
        return CORE;
    }

    protected class ModMultiBlockCoreItemBase extends BlockItem {
        public ModMultiBlockCoreItemBase(Block blockIn, Properties builder) {
            super(blockIn, builder);

            this.setRegistryName(CORE.getRegistryName());

            ModItems.ITEM_LIST.add(this);
        }
    }

    protected class ModMultiBlockCoreBase extends Block {
        private Lazy<TileEntity> tileEntity;

        public ModMultiBlockCoreBase(String regName, Properties properties, Lazy<TileEntity> tileEntity) {
            super(properties);

            this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, regName));
            this.tileEntity = tileEntity;
            setDefaultState(getDefaultState().with(FACING, Direction.NORTH));

            ModMultiBlockCores.CORES.add(this);
        }

        @Nullable
        @Override
        public TileEntity createTileEntity(BlockState state, IBlockReader world) {
            return tileEntity.get();
        }

        @Override
        public boolean hasTileEntity(BlockState state) {
            return true;
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockItemUseContext context) {
            return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
            super.fillStateContainer(builder);
            builder.add(FACING);
        }

        @Override
        public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
            if (worldIn.getTileEntity(pos) instanceof ModMultiBlockTileEntityBase) {
                if (worldIn.isRemote) {
                    return ((ModMultiBlockTileEntityBase) worldIn.getTileEntity(pos)).onBlockActivatedClient(state, worldIn, pos, player, handIn, hit);
                }
                else {
                    return ((ModMultiBlockTileEntityBase) worldIn.getTileEntity(pos)).onBlockActivatedServer(state, worldIn, pos, player, handIn, hit);
                }
            }

            return ActionResultType.FAIL;
        }

        @Override
        public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return OUT_SHAPE;
        }

        @Override
        public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
            return OUT_SHAPE;
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return OUT_SHAPE;
        }
    }
}
