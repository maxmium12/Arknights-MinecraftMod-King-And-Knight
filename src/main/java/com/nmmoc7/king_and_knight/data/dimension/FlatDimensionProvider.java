package com.nmmoc7.king_and_knight.data.dimension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author DustW
 */
public class FlatDimensionProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    private final ArrayList<FlatDimensionBuilder> flatDimensionBuilders = new ArrayList<>();

    public FlatDimensionProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void act(@NotNull DirectoryCache cache) throws IOException {
        addDimensions();

        Path output = generator.getOutputFolder();

        flatDimensionBuilders.forEach((dyb -> {
            try {
                IDataProvider.save(GSON, cache, dyb.getJson(), getPath(output, dyb.getRegName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private static Path getPath(Path path, ResourceLocation rl) {
        return path.resolve("data/" + rl.getNamespace() + "/dimension/" + rl.getPath() + ".json");
    }

    public void add(FlatDimensionBuilder flatDimensionBuilder) {
        this.flatDimensionBuilders.add(flatDimensionBuilder);
    }

    public void addDimensions() {
        this.add(ModFlatDimensions.EMPTY_WORLD);
    }

    @Override
    public @NotNull String getName() {
        return "KAK Flat Dimension";
    }

    public static class FlatDimensionBuilder {
        private final ResourceLocation regName;

        private final JsonObject json = new JsonObject();
        private final JsonObject generator = new JsonObject();
        private final JsonObject settings = new JsonObject();
        private final JsonArray layers = new JsonArray();
        private final JsonObject structures = new JsonObject();
        private final JsonObject structuresIn = new JsonObject();
        private final JsonObject stronghold = new JsonObject();

        public FlatDimensionBuilder(ResourceLocation regName, ResourceLocation dimensionTypeName) {
            this.regName = regName;

            json.addProperty("type", dimensionTypeName.toString());
            generator.addProperty("type", "minecraft:flat");
        }

        public FlatDimensionBuilder addLayer(int height, ResourceLocation blockName) {
            JsonObject layer = new JsonObject();
            layer.addProperty("height", height);
            layer.addProperty("block", blockName.toString());
            layers.add(layer);
            return this;
        }

        public FlatDimensionBuilder setBiome(ResourceLocation biomeName) {
            settings.addProperty("biome", biomeName.toString());
            return this;
        }
        /** 如果生成要塞，则要塞由原点向外逐个生成在一系列的环上，直到总数达到 count。
         * 其中第 i 个环的要塞数量为 spread × (i+1) × (i+2)/6 向下取整，第 i 个环到原点的平均距离为 distance × (6i-2) 个区块。
         *
         * @param count     1~4095 之间的整数,
         * @param spread    0~1023 之间的整数, 设置为0时会使世界中某些位置上反复生成多个要塞
         * @param distance  0~1023 之间的整数
         */
        public FlatDimensionBuilder setStronghold(int count, int spread, int distance) {
            stronghold.addProperty("count", count);
            stronghold.addProperty("spread", spread);
            stronghold.addProperty("distance", distance);
            return this;
        }

        /** 该维度中会生成的结构，如果需要生成要塞也需在此列出
         * @param structuresName "结构命名空间ID" 注意这不是 结构地物 命名空间ID，自定义的结构地物生成几率设置随同其对应的原版结构
         * @param spacing 正整数, 两个该种类的结构之间的平均距离，以区块为单位
         * @param separation 1~(spacing-1) 之间的整数, 两个该种类的结构之间的最小距离，以区块为单位
         * @param salt 整数 影响结构内部生成
         * */
        public FlatDimensionBuilder addStructures(ResourceLocation structuresName, int spacing, int separation, int salt) {
            JsonObject structures = new JsonObject();
            structures.addProperty("spacing", spacing);
            structures.addProperty("separation", separation);
            structures.addProperty("salt", salt);
            structuresIn.add(structuresName.toString(), structures);
            return this;
        }

        public FlatDimensionBuilder finish() {
            structures.add("stronghold", stronghold);
            structures.add("structures", structuresIn);
            settings.add("layers", layers);
            settings.add("structures", structures);
            generator.add("settings", settings);
            json.add("generator", generator);
            return this;
        }

        public JsonObject getJson() {
            return json;
        }

        public ResourceLocation getRegName() {
            return regName;
        }
    }
}