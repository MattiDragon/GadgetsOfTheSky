package io.github.mattidragon.gadgets_of_the_sky.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SkyAltarBlock extends Block implements BlockEntityProvider {
    public static final IntProperty CHARGES = IntProperty.of("charges", 0, 3);
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            Block.createCuboidShape(4, 0, 4, 12, 4, 12),
            Block.createCuboidShape(2, 4, 2, 14, 8, 14),
            BooleanBiFunction.OR
    );

    public SkyAltarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(CHARGES, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof SkyAltarBlockEntity blockEntity) {
                ItemScatterer.spawn(world, pos, blockEntity.getDropInventory());
                world.updateComparators(pos, state.getBlock());
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var stack = player.getStackInHand(hand);

        if (world.getBlockEntity(pos) instanceof SkyAltarBlockEntity blockEntity) {
            if (blockEntity.isCrafting()) return ActionResult.PASS;

            var storedStack = blockEntity.getStack(0);
            if (storedStack.isEmpty() && blockEntity.isValid(0, stack)) {
                blockEntity.setStack(0, stack.copyWithCount(1));
                if (!player.getAbilities().creativeMode) stack.decrement(1);
                return ActionResult.SUCCESS;
            } else if (!storedStack.isEmpty()) {
                if (!world.isClient) {
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.2f, 1);
                }
                player.getInventory().offerOrDrop(storedStack);
                blockEntity.setStack(0, ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!(world.getBlockEntity(pos) instanceof SkyAltarBlockEntity blockEntity) || !blockEntity.isActive()) return;

        spawnFlames(world, pos, ParticleTypes.SMALL_FLAME);
    }

    public static void spawnCraftingFlames(World world, BlockPos pos) {
        spawnFlames(world, pos, ParticleTypes.FLAME);
    }

    private static void spawnFlames(World world, BlockPos pos, ParticleEffect particle) {
        var offset = 0.15625;
        var negativeOffset = 1 - offset;
        var x = (double) pos.getX();
        var y = (double) pos.getY();
        var z = (double) pos.getZ();
        world.addParticle(particle, x + offset, y + 0.65, z + negativeOffset, 0, 0, 0);
        world.addParticle(particle, x + negativeOffset, y + 0.65, z + negativeOffset, 0, 0, 0);
        world.addParticle(particle, x + offset, y + 0.65, z + offset, 0, 0, 0);
        world.addParticle(particle, x + negativeOffset, y + 0.65, z + offset, 0, 0, 0);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SkyAltarBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type == ModBlocks.SKY_ALTAR_BLOCK_ENTITY) {
            return (world1, pos, state1, blockEntity) -> ((SkyAltarBlockEntity) blockEntity).tick();
        }
        return null;
    }
}
