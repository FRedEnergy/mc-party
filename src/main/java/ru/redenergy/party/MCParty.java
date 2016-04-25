package ru.redenergy.party;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.redenergy.party.handler.HUDRenderHandler;
import ru.redenergy.party.handler.InvPartyMaintainer;
import ru.redenergy.party.handler.TrackerTickUpdater;
import ru.redenergy.party.message.CreatePartyMessage;
import ru.redenergy.party.message.PartyInvitationMessage;
import ru.redenergy.party.ui.PartyDisplay;

@Mod(modid = "mc-party", name = "MC Party")
public class MCParty {

    @Mod.Instance("mc-party")
    public static MCParty instance;

    private PartyController controller;

    private HUDRenderHandler hudRenderer;

    private SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        network = new SimpleNetworkWrapper("mc-party");
        registerPackets();
        controller = new PartyController();
        MinecraftForge.EVENT_BUS.register(new TrackerTickUpdater(controller));
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(hudRenderer = new HUDRenderHandler(new PartyDisplay()));
            MinecraftForge.EVENT_BUS.register(new InvPartyMaintainer());
        }
    }

    private void registerPackets(){
        network.registerMessage(CreatePartyMessage.Handler.class, CreatePartyMessage.class, 0, Side.SERVER);
        network.registerMessage(PartyInvitationMessage.Handler.class, PartyInvitationMessage.class, 1, Side.CLIENT);
    }

    public PartyController controller() {
        return controller;
    }

    public SimpleNetworkWrapper network() {
        return network;
    }
}
