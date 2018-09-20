package bluemonster122.sbm.block;

import bluemonster122.sbm.SBM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class BlockBasic extends Block {
    public static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);

    private ResourceLocation name;

    public BlockBasic(Material material, String name) {
        super(material);
        this.name = new ResourceLocation("sbm", name);
        setRegistryName(getName());
        setTranslationKey(getName().toString());
        setCreativeTab(SBM.TAB);
    }

    public ResourceLocation getName() {
        return name;
    }
}
