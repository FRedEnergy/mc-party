package ru.redenergy.party.handler;


import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.redenergy.party.ui.PartyDisplay;

public class HUDRenderHandler {

    public PartyDisplay display = null;

    public HUDRenderHandler(PartyDisplay display) {
        this.display = display;
    }

    public HUDRenderHandler(){}


    @SubscribeEvent
    public void draw(RenderGameOverlayEvent.Post event){
        if(display != null) {
            display.width = event.getResolution().getScaledWidth();
            display.height = event.getResolution().getScaledHeight();
            display.drawScreen(0, 0, event.getPartialTicks()); //don't really care about mouse position
        }
    }
}
