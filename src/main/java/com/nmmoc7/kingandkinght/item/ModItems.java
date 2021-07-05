package com.nmmoc7.kingandkinght.item;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.item.base.ModItemBase;
import com.nmmoc7.kingandkinght.itemgroup.ModItemGroups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author DustW
 */
public class ModItems {
    public static final ArrayList<Item> ITEM_LIST = new ArrayList<>();

    public static final ModItemBase SOURCE_ROCKS = new ModItemBase("source_rocks", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase HIGH_PURE_SOURCE_ROCKS = new ModItemBase("high_pure_source_rocks", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase CRAFTING_JADE = new ModItemBase("crafting_jade", ModItemGroups.ITEM_GROUP);

    /** T1 */
    public static final ModItemBase SOURCE_ROCK = new ModItemBase("source_rock", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase DAMAGED_DEVICE = new ModItemBase("damaged_device", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase ESTER = new ModItemBase("ester", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase W_KETONE = new ModItemBase("w_ketone", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase ORIRON_SHARD = new ModItemBase("oriron_shard", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase SUGAR_SUBSTITUTE = new ModItemBase("sugar_substitute", ModItemGroups.ITEM_GROUP);

    public static final ModItemBase TEST_ITEM = new ModItemBase("test_item", ModItemGroups.ITEM_GROUP) {
        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
            if (!worldIn.isRemote) {
                ServerWorld world = (ServerWorld) worldIn;
                TemplateManager templatemanager = world.getStructureTemplateManager();
                Template template;

                try {
                    template = templatemanager.getTemplate(new ResourceLocation(KingAndKnight.MOD_ID, "dadadada"));
                } catch (ResourceLocationException resourcelocationexception) {
                    return ActionResult.resultFail(playerIn.getHeldItem(handIn));
                }

                PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null);

                BlockPos placePos = new BlockPos(0, 0, 0);
                BlockPos placePosF = rayTrace(playerIn, 6).getPos().add(placePos);
                template.func_237144_a_(world, placePosF, placementsettings, getRandom(0));
            }
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        private Random getRandom(long seed) {
            return seed == 0L ? new Random(Util.milliTime()) : new Random(seed);
        }

        public BlockRayTraceResult rayTrace(Entity entity, double playerReach) {
            Vector3d eyePosition = entity.getEyePosition(1);
            Vector3d lookVector = entity.getLook(1);
            Vector3d traceEnd = eyePosition.add(lookVector.x * playerReach, lookVector.y * playerReach, lookVector.z * playerReach);

            RayTraceContext context = new RayTraceContext(eyePosition, traceEnd, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity);
            return entity.getEntityWorld().rayTraceBlocks(context);
        }
    };
}
