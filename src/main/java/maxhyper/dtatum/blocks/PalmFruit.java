package maxhyper.dtatum.blocks;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.FruitBlock;
import com.ferreusveritas.dynamictrees.systems.fruit.Fruit;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * @author Harley O'Connor
 */
public final class PalmFruit extends Fruit {

    public static final TypedRegistry.EntryType<Fruit> TYPE = TypedRegistry.newType(PalmFruit::new);

    private float[] shapeFacingOffsets = {6 / 16f, 6 / 16f, 6 / 16f, 6 / 16f};

    public PalmFruit(ResourceLocation registryName) {
        super(registryName);
    }

    public void setShapeFacingOffsets(float[] shapeFacingOffsets) {
        this.shapeFacingOffsets = shapeFacingOffsets;
    }

    public float[] getShapeFacingOffsets() {
        return shapeFacingOffsets;
    }

    @Override
    protected FruitBlock createBlock(AbstractBlock.Properties properties) {
        return new PalmFruitBlock(properties, this);
    }

    public VoxelShape getBlockShape(int age, Direction facing) {
        return offsetShape(getBlockShape(age), facing, shapeFacingOffsets[age]);
    }

    protected VoxelShape offsetShape(VoxelShape shape, Direction direction, float offset) {
        return shape.move(direction.getStepX() * offset, direction.getStepY() * offset, direction.getStepZ() * offset);
    }

    public void place(IWorld world, BlockPos pos, Direction facing, @Nullable Float seasonValue) {
        BlockState state = getState(0, facing);
        world.setBlock(pos, state, Constants.BlockFlags.DEFAULT);
    }

    public void placeDuringWorldGen(IWorld world, BlockPos pos, Direction facing, @Nullable Float seasonValue) {
        BlockState state = getState(getAgeForWorldGen(world, pos, seasonValue), facing);
        world.setBlock(pos, state, Constants.BlockFlags.DEFAULT);
    }

    private BlockState getState(int age, Direction facing) {
        return getStateForAge(age).setValue(PalmFruitBlock.FACING, facing);
    }

    @Override
    public LootTable.Builder createBlockDrops() {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                        ItemLootEntry.lootTableItem(getItemStack().getItem())
                                .when(
                                        BlockStateProperty.hasBlockStateProperties(getBlock())
                                                .setProperties(
                                                        StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(getAgeProperty(), getMaxAge())
                                                )
                                )
                                .apply(SetCount.setCount(RandomValueRange.between(2.0F, 5.0F)))
                                .apply(ExplosionDecay.explosionDecay())
                )
        ).setParamSet(LootParameterSets.BLOCK);
    }

}
