package bluemonster122.sbm.block;

import bluemonster122.sbm.tile.TileEntityBasic;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class BlockMachine extends BlockBasic {
    private final BiFunction<World, IBlockState, ? extends TileEntityBasic> tileFunction;

    public <T extends TileEntityBasic> BlockMachine(String name, BiFunction<World, IBlockState, T> tileFunction) {
        super(Material.IRON, name);
        this.tileFunction = tileFunction;
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public static <T extends TileEntityBasic> T getTileEntity(World world, BlockPos pos) {
        return (T) world.getTileEntity(pos);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.blockState.getBaseState().withProperty(FACING, EnumFacing.HORIZONTALS[meta & 3]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntityBasic createTileEntity(World world, IBlockState state) {
        return tileFunction.apply(world, state);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        EnumFacing value = placer.isSneaking() ? placer.getHorizontalFacing() : placer.getHorizontalFacing().getOpposite();
        return state.withProperty(FACING, value);
    }
}
