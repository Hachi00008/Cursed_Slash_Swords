package hachi00008.cursed_slash_swords.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jspecify.annotations.Nullable;

public class SoulForge extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalfSide> HALF_SIDE = EnumProperty.create("half_side", DoubleBlockHalfSide.class);

    public SoulForge(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF_SIDE, DoubleBlockHalfSide.LEFT));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        Direction rightDir = facing.getClockWise();
        BlockPos rightPos = pos.relative(rightDir);

        if (level.getBlockState(rightPos).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, facing).setValue(HALF_SIDE, DoubleBlockHalfSide.LEFT);
        }

        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity by, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, by, itemStack);
        if (!level.isClientSide()) {
            Direction facing = state.getValue(FACING);
            BlockPos rightPos = pos.relative(facing.getClockWise());
            level.setBlock(rightPos, state.setValue(HALF_SIDE, DoubleBlockHalfSide.RIGHT), 3);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        Direction facing = state.getValue(FACING);
        DoubleBlockHalfSide half = state.getValue(HALF_SIDE);

        Direction partnerDirection = (half == DoubleBlockHalfSide.LEFT) ? facing.getClockWise() : facing.getCounterClockWise();

        if (directionToNeighbour == partnerDirection && !neighbourState.is(this)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF_SIDE);
    }
}
