package github.itsbeary.experiments;

import github.itsbeary.experiments.fakeblock.FakeBlock;
import github.itsbeary.experiments.listeners.BlockListener;
import github.itsbeary.experiments.managers.BlockManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiConsumer;

@Getter
public class Experiments extends JavaPlugin {

    @Getter
    private static Experiments instance;

    private final BlockManager blockManager = new BlockManager();

    @Override
    public void onEnable() {
        instance = this;
        registerListeners(new BlockListener());
        registerCommand("summondisplay", ((player, args) -> new FakeBlock(player.getLocation(), Material.STONE.createBlockData())));
    }
    private void registerCommand(String command, BiConsumer<Player, String[]> executor) {
        getCommand(command).setExecutor((sender, command1, label, args) -> {
            executor.accept((Player) sender, args);
            return true;
        });
    }
    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            Bukkit.getPluginManager().registerEvents(listener, this);

    }
}
