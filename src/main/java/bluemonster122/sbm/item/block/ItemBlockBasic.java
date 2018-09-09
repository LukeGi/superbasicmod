package bluemonster122.sbm.item.block;

import bluemonster122.sbm.block.BlockBasic;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class ItemBlockBasic extends ItemBlock {

    private BlockBasic block;

    public ItemBlockBasic(BlockBasic block) {
        super(block);
        this.block = block;
        setRegistryName(block.getName());
    }

    public ResourceLocation getName() {
        return block.getName();
    }
}
