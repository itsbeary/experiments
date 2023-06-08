package github.itsbeary.experiments.listeners;

import github.itsbeary.experiments.Experiments;
import github.itsbeary.experiments.fakeblock.FakeBlock;
import github.itsbeary.experiments.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.util.Vector;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlayerInteract(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Interaction interaction))
            return;
        if (Experiments.getInstance().getBlockManager().getFakeBlock(interaction.getEntityId()) == null)
            return;
        FakeBlock fakeBlock = Experiments.getInstance().getBlockManager().getFakeBlock(interaction.getEntityId());
        fakeBlock.destroy();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().isBlock())
            return;
        Location location = event.getBlockPlaced().getLocation().getBlock().getRelative(getBlockFace(event.getPlayer().getLocation().getDirection()).getOppositeFace()).getLocation();
        new FakeBlock(location, event.getPlayer().getInventory().getItemInMainHand().getType().createBlockData());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Interaction interaction)
                || Experiments.getInstance().getBlockManager().getFakeBlock(interaction.getEntityId())  == null
                || !event.getPlayer().getInventory().getItemInMainHand().getType().isBlock())
            return;
        FakeBlock interacted = Experiments.getInstance().getBlockManager().getFakeBlock(interaction.getEntityId());
        Location location = interacted.getInteraction().getLocation().getBlock().getRelative(getBlockFace(event.getPlayer().getLocation().getDirection()).getOppositeFace()).getLocation();
        new FakeBlock(location, event.getPlayer().getInventory().getItemInMainHand().getType().createBlockData());
    }

    public static BlockFace getBlockFace(Vector direction) {
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();

        if (Math.abs(y) > Math.max(Math.abs(x), Math.abs(z))) {
            return (y > 0) ? BlockFace.UP : BlockFace.DOWN;
        } else if (Math.abs(x) > Math.abs(z)) {
            return (x > 0) ? BlockFace.EAST : BlockFace.WEST;
        } else {
            return (z > 0) ? BlockFace.SOUTH : BlockFace.NORTH;
        }
    }

}
