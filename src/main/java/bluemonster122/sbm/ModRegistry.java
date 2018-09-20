package bluemonster122.sbm;

import bluemonster122.sbm.block.BlockBasic;
import bluemonster122.sbm.block.BlockMachine;
import bluemonster122.sbm.item.ItemBasic;
import bluemonster122.sbm.item.block.ItemBlockBasic;
import bluemonster122.sbm.item.block.ItemBlockMachine;
import bluemonster122.sbm.tile.TileBasicFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid = "sbm")
public class ModRegistry {
    @SubscribeEvent
    public static void onRegisterBlocks(Register<Block> event) {
        event.getRegistry().registerAll(
                new BlockBasic(Material.WOOD, "basic_block"),
                new BlockMachine("basic_furnace", (world, state) -> new TileBasicFurnace()));
    }

    @SubscribeEvent
    public static void onRegisterItems(Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemBlockBasic(ModBlocks.BASIC_BLOCK),
                new ItemBlockMachine(ModBlocks.BASIC_FURNACE),
                new ItemBasic("basic_item"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegisterModels(ModelRegistryEvent event) {
        setInventoryModel(ModItems.BASIC_BLOCK);
        setInventoryModel(ModItems.BASIC_FURNACE);
        setInventoryModel(ModItems.BASIC_ITEM);
    }

    private static void setInventoryModel(ItemBlockBasic item) {
        setInventoryModel(item, 0, item.getName());
    }

    private static void setInventoryModel(ItemBasic item) {
        setInventoryModel(item, 0, item.getName());
    }

    private static void setInventoryModel(Item item, int meta, ResourceLocation name) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }
}
