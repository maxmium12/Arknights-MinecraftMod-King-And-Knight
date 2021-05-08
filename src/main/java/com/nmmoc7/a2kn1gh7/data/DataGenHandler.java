package com.nmmoc7.a2kn1gh7.data;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.data.item.ItemModel;
import com.nmmoc7.a2kn1gh7.data.lang.LangEnUs;
import com.nmmoc7.a2kn1gh7.data.lang.LangZhCn;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(modid = A2kn1gh7.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenHandler {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new ItemModel(generator, helper));
        }

        if (event.includeServer()) {
            generator.addProvider(new LangZhCn(generator));
            generator.addProvider(new LangEnUs(generator));
        }
    }
}
