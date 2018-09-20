package bluemonster122.sbm;

import bluemonster122.sbm.tile.TileBasicFurnace;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "sbm", name = "Super Basic Mod", version = "1.12.2-1.0.0.0-beta1")
public class SBM {

    public static final CreativeTabs TAB = new CreativeTabs("sbm") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.DYE, 1, 9);
        }
    };

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileBasicFurnace.class, ModBlocks.BASIC_FURNACE.getName());
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }
}
