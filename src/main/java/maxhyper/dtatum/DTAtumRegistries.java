package maxhyper.dtatum;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.fruit.Fruit;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.teammetallurgy.atum.world.gen.feature.DeadwoodFeature;
import maxhyper.dtatum.blocks.PalmFruit;
import maxhyper.dtatum.genfeatures.BrokenLeavesGenFeature;
import maxhyper.dtatum.genfeatures.PalmFruitGenFeature;
import maxhyper.dtatum.genfeatures.PalmVinesGenFeature;
import maxhyper.dtatum.growthlogic.DeadGrowthLogicKit;
import maxhyper.dtatum.trees.DeadwoodSpecies;
import maxhyper.dtatum.worldgen.DeadwoodFeatureCanceller;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTAtumRegistries {

    public static GenFeature PALM_FRUIT_FEATURE;
    public static GenFeature PALM_VINES_FEATURE;
    public static GenFeature BROKEN_LEAVES_FEATURE;

    public static GrowthLogicKit DEAD_GROWTH_LOGIC_KIT =
            new DeadGrowthLogicKit(new ResourceLocation(DynamicTreesAtum.MOD_ID, "dead"));

    @SubscribeEvent
    public static void registerSpeciesTypes(TypeRegistryEvent<Species> event) {
        event.registerType(new ResourceLocation(DynamicTreesAtum.MOD_ID, "deadwood"), DeadwoodSpecies.TYPE);
    }

    @SubscribeEvent
    public static void registerFruitTypes(TypeRegistryEvent<Fruit> event) {
        event.registerType(new ResourceLocation(DynamicTreesAtum.MOD_ID, "palm"), PalmFruit.TYPE);
    }

    /**
     * canceller for Atum's deadwood trees. Cancells all features of type {@link DeadwoodFeature}.
     */
    public static final FeatureCanceller DEADWOOD_CANCELLER =
            new DeadwoodFeatureCanceller<>(new ResourceLocation(DynamicTreesAtum.MOD_ID, "deadwood"));

    @SubscribeEvent
    public static void onFeatureCancellerRegistry(RegistryEvent<FeatureCanceller> event) {
        event.getRegistry().registerAll(DEADWOOD_CANCELLER);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(RegistryEvent<GenFeature> event) {
        PALM_FRUIT_FEATURE = new PalmFruitGenFeature(new ResourceLocation(DynamicTreesAtum.MOD_ID, "palm_fruit"));
        PALM_VINES_FEATURE = new PalmVinesGenFeature(new ResourceLocation(DynamicTreesAtum.MOD_ID, "palm_vines"));
        BROKEN_LEAVES_FEATURE =
                new BrokenLeavesGenFeature(new ResourceLocation(DynamicTreesAtum.MOD_ID, "broken_leaves"));

        event.getRegistry().registerAll(PALM_FRUIT_FEATURE, PALM_VINES_FEATURE, BROKEN_LEAVES_FEATURE);
    }

    @SubscribeEvent
    public static void onRegisterGrowthLogicKits(RegistryEvent<GrowthLogicKit> event) {
        event.getRegistry().registerAll(DEAD_GROWTH_LOGIC_KIT);
    }

}
