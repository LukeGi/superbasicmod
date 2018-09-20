package bluemonster122.sbm.tile;

import bluemonster122.sbm.Configs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileBasicFurnace extends TileEntityBasic implements ITickable {

    private static final int TIME_TO_SMELT_FOR = 100;
    public ItemStackHandler inventory_in = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };
    public ItemStackHandler inventory_out = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };
    public EnergyStorage battery = new EnergyStorage(10000, 10000, 0) {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            markDirty();
            return super.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            markDirty();
            return super.extractEnergy(maxExtract, simulate);
        }
    };
    public int smelt_time = -1;

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CAP_ITEM) {
            return true;
        }
        if (capability == CAP_ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CAP_ITEM) {
            if (facing == EnumFacing.UP) {
                return (T) inventory_in;
            }
            if (facing == EnumFacing.DOWN) {
                return (T) inventory_out;
            }
        }
        if (capability == CAP_ENERGY) {
            if (facing == null || facing.getAxis() != EnumFacing.Axis.Y) {
                return (T) battery;
            }
        }
        return super.getCapability(capability, facing);
    }

    protected void readTileData(NBTTagCompound compound) {
        if (compound.hasKey("item_in"))
            inventory_in.deserializeNBT(compound.getCompoundTag("item_in"));
        if (compound.hasKey("item_out"))
            inventory_out.deserializeNBT(compound.getCompoundTag("item_out"));
        if (compound.hasKey("battery"))
            battery = new EnergyStorage(10000, 10000, 0, compound.getInteger("battery"));
        if (compound.hasKey("smelt_time"))
            smelt_time = compound.getInteger("smelt_time");
    }

    protected void writeTileData(NBTTagCompound compound) {
        compound.setTag("item_in", inventory_in.serializeNBT());
        compound.setTag("item_out", inventory_out.serializeNBT());
        compound.setInteger("battery", battery.getEnergyStored());
        compound.setInteger("smelt_time", smelt_time);
    }

    @Override
    public void update() {
        if (world.isRemote) return;
        ItemStack input = inventory_in.getStackInSlot(0);
        if (input.isEmpty()) {
            smelt_time = -1;
            return;
        }
        ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(input);
        if (smeltingResult.isEmpty()) {
            smelt_time = -1;
            return;
        }
        syncData();
        ItemStack output = inventory_out.getStackInSlot(0);
        if (!output.isEmpty() && !ItemHandlerHelper.canItemStacksStack(output, smeltingResult)) {
            smelt_time = -1;
            return;
        }
        if (smelt_time > 0) {
            if (battery.extractEnergy(Configs.BASIC_FURNACE_FE_PER_TICK, true) == Configs.BASIC_FURNACE_FE_PER_TICK) {
                battery.extractEnergy(Configs.BASIC_FURNACE_FE_PER_TICK, false);
                smelt_time--;
            }
            return;
        }
        if (smelt_time == 0) {
            smelt_time--;
            input.shrink(1);
            if (output.isEmpty()) {
                output = smeltingResult.copy();
            } else {
                output.grow(smeltingResult.getCount());
            }
            inventory_in.setStackInSlot(0, input);
            inventory_out.setStackInSlot(0, output);
        }
        if (smelt_time == -1) {
            smelt_time = TIME_TO_SMELT_FOR;
        }
    }

    /**
     * @return progress percentage
     */
    public float getProgress() {
        return (float) (TIME_TO_SMELT_FOR - smelt_time) / (float) TIME_TO_SMELT_FOR;
    }
}
