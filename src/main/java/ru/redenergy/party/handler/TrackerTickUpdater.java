package ru.redenergy.party.handler;


import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.redenergy.party.PartyController;
import ru.redenergy.party.model.PartyInfo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackerTickUpdater {

    private final PartyController controller;

    public TrackerTickUpdater(PartyController controller) {
        this.controller = controller;
    }

    @SubscribeEvent
    public void update(TickEvent.ServerTickEvent event){
        if(event.phase != TickEvent.Phase.END) return;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        EntityTracker tracker = server.worldServers[0].getEntityTracker();
        for(PartyInfo party: controller.getParties()){
            List<EntityPlayerMP> players = namesToPlayers(party.getAllPlayers(), server);
            for(EntityPlayerMP player: players) {
                EntityTrackerEntry entry = tracker.trackedEntityHashTable.lookup(player.getEntityId());
                entry.trackingPlayers.addAll(players);
            }
        }
    }

    private List<EntityPlayerMP> namesToPlayers(Set<String> names, MinecraftServer server){
        return names.stream().map(server.getPlayerList()::getPlayerByUsername).collect(Collectors.toList());
    }
}
