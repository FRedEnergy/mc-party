package ru.redenergy.party.handler;


import com.rabbit.gui.GuiFoundation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.redenergy.party.ui.CreatePartyShow;

public class InvPartyMaintainer {

    @SubscribeEvent
    public void onGuiDisplay(GuiScreenEvent.InitGuiEvent event){
        GuiScreen gui = event.getGui();
        if(gui instanceof GuiInventory){
            event.getButtonList().add(new GuiButton(1000, gui.width / 2 + 90, gui.height / 2, 20, 20, "X"));
        }
    }

    @SubscribeEvent
    public void onButtonPressed(GuiScreenEvent.ActionPerformedEvent event){
        if(event.getButton().id == 1000)
            GuiFoundation.display(new CreatePartyShow());
    }

}
