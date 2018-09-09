package bluemonster122.sbm.item;

import bluemonster122.sbm.SBM;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemBasic extends Item {
    private ResourceLocation name;

    public ItemBasic(String name) {
        this.name = new ResourceLocation("sbm", name);
        setRegistryName(getName());
        setTranslationKey(getName().toString());
        setCreativeTab(SBM.TAB);
    }

    public ResourceLocation getName() {
        return name;
    }
}
