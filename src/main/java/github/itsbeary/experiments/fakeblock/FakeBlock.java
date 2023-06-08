package github.itsbeary.experiments.fakeblock;

import github.itsbeary.experiments.Experiments;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Interaction;

@Getter
public class FakeBlock {

    private final Location location;
    private final BlockDisplay blockDisplay;
    private final Interaction interaction;
    public FakeBlock(Location location, BlockData blockData) {
        this.location = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5);
        this.blockDisplay = getLocation().getWorld().spawn(getLocation().clone().subtract(0.5, 0, 0.5), BlockDisplay.class);
        blockDisplay.setBlock(blockData);
        blockDisplay.setGravity(false);
        this.interaction = getLocation().getWorld().spawn(getLocation(), Interaction.class);
        interaction.setResponsive(true);
        Experiments.getInstance().getBlockManager().getFakeBlocks().add(this);
    }

    public void destroy() {
        Experiments.getInstance().getLogger().info("Destroying FakeBlock ID " + interaction.getEntityId());
        blockDisplay.remove();
        interaction.remove();
    }
}
