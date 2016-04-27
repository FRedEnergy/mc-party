package ru.redenergy.party.handler;


import com.rabbit.gui.GuiFoundation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.redenergy.party.ui.CreatePartyShow;
import ru.redenergy.party.ui.PartyInvitesShow;

@SideOnly(Side.CLIENT)
public class InvPartyMaintainer {

    @SubscribeEvent
    public void onGuiDisplay(GuiScreenEvent.InitGuiEvent event){
        GuiScreen gui = event.getGui();
        if(gui instanceof GuiInventory || gui instanceof GuiContainerCreative){
            int x = gui.width / 2 + 100;
            int y = gui.height / 2;
            event.getButtonList().add(new GuiButton(1000, x, y, 20, 20, "X"));
            event.getButtonList().add(new GuiButton(1001, x, y + 25, 20, 20, "I"));
        }
    }

    @SubscribeEvent
    public void onButtonPressed(GuiScreenEvent.ActionPerformedEvent event){
        if(event.getGui() instanceof GuiInventory || event.getGui() instanceof GuiContainerCreative)
            if(event.getButton().id == 1000)
                GuiFoundation.display(new CreatePartyShow());
            else if(event.getButton().id == 1001)
                GuiFoundation.display(new PartyInvitesShow());
    }

}
