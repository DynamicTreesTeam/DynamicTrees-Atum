package maxhyper.dtatum.blocks;

import com.ferreusveritas.dynamictrees.blocks.FruitBlock;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class PalmFruitBlock extends FruitBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private final PalmFruit palmFruit;

    public PalmFruitBlock(Properties properties, PalmFruit fruit) {
        super(properties, fruit);
        this.palmFruit = fruit;
    }

    protected void createBlockStateDefinition(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public boolean isSupported(IBlockReader world, BlockPos pos, BlockState state) {
        Direction dir = state.getValue(FACING);
        BlockState stateFacing = world.getBlockState(pos.offset(dir.getNormal()));
        BlockState stateAbove = world.getBlockState(pos.offset(dir.getNormal()).above());
        return stateFacing.getBlock() instanceof BranchBlock && stateAbove.getBlock() instanceof DynamicLeavesBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        final int age = getAge(state);
        final Direction facing = state.getValue(FACING);
        return palmFruit.getBlockShape(age, facing);
    }

}