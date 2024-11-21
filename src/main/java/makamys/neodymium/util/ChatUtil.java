package makamys.neodymium.util;

import static makamys.neodymium.Constants.LOGGER;
import static makamys.neodymium.Constants.MODID;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.Minecraft;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumChatFormatting;

public class ChatUtil {
    
    private static Set<String> shownChatMessages = new HashSet<>();
    
    public static void showChatMessage(String text) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        
        if(player != null) {
            player.addChatMessage(text);
        } else {
            LOGGER.info(text);
        }
    }
    
    public static void showNeoChatMessage(String text, MessageVerbosity verbosity) {
        showNeoChatMessage(text, verbosity, false);
    }
    
    public static void showNeoChatMessage(String text, MessageVerbosity verbosity, boolean once) {
        if(once && shownChatMessages.contains(text)) return;
        
        String verbosityText =
                verbosity == MessageVerbosity.WARNING
                    ? EnumChatFormatting.YELLOW + "WARNING"
                : verbosity == MessageVerbosity.ERROR
                    ? EnumChatFormatting.RED + "ERROR"
                :
                    "INFO";
                
        ChatUtil.showChatMessage("[" + EnumChatFormatting.LIGHT_PURPLE + MODID + EnumChatFormatting.RESET + "/" + verbosityText + EnumChatFormatting.RESET + "] " + text);
        
        shownChatMessages.add(text);
    }
    
    public static void resetShownChatMessages() {
        shownChatMessages.clear();
    }
    
    public static enum MessageVerbosity {
        INFO, WARNING, ERROR
    }
    
}
