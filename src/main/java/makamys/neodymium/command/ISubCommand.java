package makamys.neodymium.command;

import net.minecraft.ICommandSender;

public interface ISubCommand {
    
    void processCommand(ICommandSender sender, String[] args);
    default boolean isSecret() { return false; }
    
}
