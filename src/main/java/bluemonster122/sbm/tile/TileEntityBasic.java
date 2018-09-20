package bluemonster122.sbm.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import java.util.Objects;

public abstract class TileEntityBasic extends TileEntity {
    @CapabilityInject(IItemHandler.class)
    public static Capability<IItemHandler> CAP_ITEM = null;
    @CapabilityInject(IEnergyStorage.class)
    public static Capability<IEnergyStorage> CAP_ENERGY = null;

    private NBTTagCompound lastUpdate = null;

    public void syncData() {
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readTileData(compound);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeTileData(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeTileData(compound);
        if (lastUpdate == null) {
            lastUpdate = new NBTTagCompound();
        }
        // Remove the data already known on the other side
        NBTTagCompound toSend = new NBTTagCompound();
        for (String s : compound.getKeySet()) {
            if (lastUpdate.hasKey(s) && Objects.equals(lastUpdate.getTag(s), compound.getTag(s))) {
                continue;
            }
            toSend.setTag(s, compound.getTag(s).copy());
        }
        lastUpdate.merge(compound);
        return new SPacketUpdateTileEntity(getPos(), 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readTileData(pkt.getNbtCompound());
    }

    /**
     * Make sure you check if the compound contains a key before you attempt to access it.
     */
    protected abstract void readTileData(NBTTagCompound compound);

    protected abstract void writeTileData(NBTTagCompound compound);
}
