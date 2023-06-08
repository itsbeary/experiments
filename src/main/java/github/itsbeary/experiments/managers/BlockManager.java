package github.itsbeary.experiments.managers;

import github.itsbeary.experiments.fakeblock.FakeBlock;
import lombok.Getter;
import org.bukkit.Location;

import java.util.HashSet;

public class BlockManager {

    @Getter
    private final HashSet<FakeBlock> fakeBlocks = new HashSet<>();

    public FakeBlock getFakeBlock(int id) {
        FakeBlock fakeBlock = null;
        for(FakeBlock block : fakeBlocks)
            if(block.getInteraction().getEntityId() == id)
                fakeBlock = block;
        return fakeBlock;
    }
    public boolean isOccupied(Location location) {
        for(FakeBlock block : fakeBlocks)
            if(block.getInteraction().getLocation() == location)
                return true;
        return false;
    }

}

