package com.nmmoc7.kingandkinght.data.dimension;

import com.google.gson.*;
import com.nmmoc7.kingandkinght.KingAndKnight;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author DustW
 */
public class DimensionTypeProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    private final ArrayList<DimensionTypeBuilder> dimensionTypeBuilders = new ArrayList<>();

    public DimensionTypeProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        addDimensionTypes();

        Path output = generator.getOutputFolder();

        dimensionTypeBuilders.forEach((dyb -> {
            try {
                IDataProvider.save(GSON, cache, dyb.getJson(), getPath(output, dyb.getRegName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private static Path getPath(Path path, ResourceLocation rl) {
        return path.resolve("data/" + rl.getNamespace() + "/dimension_type/" + rl.getPath() + ".json");
    }

    private void addDimensionTypes() {
        add(ModDimensionTypes.TEST_WORLD_TYPE);
    }

    @Override
    public String getName() {
        return "KAK Dimension Type";
    }

    public void add(DimensionTypeBuilder dimensionTypeBuilder) {
        dimensionTypeBuilders.add(dimensionTypeBuilder);
    }

    public static class DimensionTypeBuilder {
        private final JsonObject json = new JsonObject();
        private final ResourceLocation regName;

        public DimensionTypeBuilder(ResourceLocation regName) {
            this.regName = regName;
        }

        /** true 或 false, 该维度是否拥有一个基岩天花板，影响内部的算法，与实际是否拥有基岩天花板无关 */
        public DimensionTypeBuilder hasCeiling(boolean hasCeiling) {
            json.addProperty("has_ceiling", hasCeiling);
            return this;
        }

        /** 数值, 玩家使用紫颂果或下界传送门可以到达的最大高度 */
        public DimensionTypeBuilder logicalHeight(double logicalHeight) {
            json.addProperty("logical_height", logicalHeight);
            return this;
        }

        /** true 或 false, 玩家是否可以使用床 */
        public DimensionTypeBuilder bedWorks(boolean bedWorks) {
            json.addProperty("bed_works", bedWorks);
            return this;
        }

        /** true 或 false, 玩家是否可以使用重生锚 */
        public DimensionTypeBuilder respawnAnchorWorks(boolean respawnAnchorWorks) {
            json.addProperty("respawn_anchor_works", respawnAnchorWorks);
            return this;
        }

        /** true 或 false, 带有不祥之兆的玩家是否可以触发袭击 */
        public DimensionTypeBuilder hasRaids(boolean hasRaids) {
            json.addProperty("has_raids", hasRaids);
            return this;
        }

        /** true 或 false, 该维度是否有天空光照 */
        public DimensionTypeBuilder hasSkylight(boolean hasSkylight) {
            json.addProperty("has_skylight", hasSkylight);
            return this;
        }

        /** 0~1 之间的数值, 该维度拥有多少环境光 */
        public DimensionTypeBuilder ambientLight(double ambientLight) {
            json.addProperty("ambient_light", ambientLight);
            return this;
        }

        /** true 或 false, 当为 false 时，指南针会随机转动；当为 true 时，下界传送门会生成僵尸猪灵 */
        public DimensionTypeBuilder natural(boolean natural) {
            json.addProperty("natural", natural);
            return this;
        }

        /** true 或 false, 猪灵和疣猪兽是否不会僵尸化 */
        public DimensionTypeBuilder piglinSafe(boolean piglinSafe) {
            json.addProperty("piglin_safe", piglinSafe);
            return this;
        }

        /** 数值, 当前维度坐标放缩倍率，影响下界传送门和 execute in 指令使用相对坐标/局部坐标的情形 */
        public DimensionTypeBuilder coordinateScale(double coordinateScale) {
            json.addProperty("coordinate_scale", coordinateScale);
            return this;
        }

        /** true 或 false, 维度是否表现得类似于下界水会蒸发，湿海绵会干。这也会使得熔岩流动更快、扩散更远 */
        public DimensionTypeBuilder ultrawarm(boolean ultrawarm) {
            json.addProperty("ultrawarm", ultrawarm);
            return this;
        }

        /** 0~24000 之间的整数, 可选，游戏内的昼夜时间固定值, 缺省则有日夜循环 */
        public DimensionTypeBuilder fixedTime(int fixedTime) {
            json.addProperty("fixed_time", fixedTime);
            return this;
        }

        /** 数值, 此维度中可以存在方块的最低高度 */
        public DimensionTypeBuilder minY(int minY) {
            json.addProperty("min_y", minY);
            return this;
        }

        /** 不超过4096的16的倍数, 此维度中可以存在方块的总高度。维度中可以存在方块的最大高度的值等于min_y与height值之和 */
        public DimensionTypeBuilder height(int height) {
            json.addProperty("height", height);
            return this;
        }

        /** "方块标签命名空间ID" 决定该维度中火可以在什么方块上永久燃烧 */
        public DimensionTypeBuilder infiniburn(ResourceLocation tag) {
            json.addProperty("infiniburn", tag.toString());
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
