package makamys.neodymium.util;

//import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.minecraft.Minecraft;
import net.minecraft.EntityPlayer;
import net.minecraft.World;

public class CheatHelper {

//    private static final boolean IS_DEV_ENVIRONMENT = FabricLauncherBase.getLauncher().isDevelopment();
    
    public static boolean canCheat() {
//        if(IS_DEV_ENVIRONMENT) {
//            return true;
//        } else {
//            return isCreative(Minecraft.getMinecraft().thePlayer);
//        }
        return isCreative(Minecraft.getMinecraft().thePlayer);
    }

    public static boolean isCreative(EntityPlayer player) {
        return player != null && player.capabilities.isCreativeMode;
    }

    public static boolean isCreativeByName(String player) {
        World world = Minecraft.getMinecraft().theWorld;
        if(world != null) {
            return isCreative(world.getPlayerEntityByName(player));
        }
        return false;
    }
    
}
