package bluemonster122.sbm;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = "superbasicmod")
public class Configs {
    @RangeInt(min = 1, max = 10000)
    @LangKey("sbm.config.furnace_fe")
    @Comment("The amount of Forge Energy used by the Basic Furnace every tick.")
    public static int BASIC_FURNACE_FE_PER_TICK = 50;
}
