package com.nmmoc7.kingandkinght.data;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.data.dimension.DimensionTypeProvider;
import com.nmmoc7.kingandkinght.data.dimension.FlatDimensionProvider;
import com.nmmoc7.kingandkinght.data.item.ItemModel;
import com.nmmoc7.kingandkinght.data.lang.LangEnUs;
import com.nmmoc7.kingandkinght.data.lang.LangZhCn;
import com.nmmoc7.kingandkinght.data.recipes.ModRecipesProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(modid = KingAndKnight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenHandler {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new ItemModel(generator, helper));
        }

        if (event.includeServer()) {
            generator.addProvider(new DimensionTypeProvider(generator));
            generator.addProvider(new FlatDimensionProvider(generator));
            generator.addProvider(new LangZhCn(generator));
            generator.addProvider(new LangEnUs(generator));
            generator.addProvider(new ModRecipesProvider(generator));
        }
    }
}
