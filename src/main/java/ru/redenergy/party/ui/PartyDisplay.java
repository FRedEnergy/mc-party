package ru.redenergy.party.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import ru.redenergy.party.model.PartyInfo;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PartyDisplay extends GuiScreen {


    public PartyInfo displayParty = null;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(displayParty == null) return;

        List<EntityPlayer> players = displayParty.getAllPlayers().stream()
                .map(Minecraft.getMinecraft().theWorld::getPlayerEntityByName).collect(Collectors.toList());

        int offset = 0;
        for(EntityPlayer player: players){
            offset += 40;
            fontRendererObj.drawString(player.getName(), 40, offset, Color.WHITE.getRGB());
            fontRendererObj.drawString(player.getHealth() + " / " + player.getMaxHealth(), 40, offset + 10, Color.WHITE.getRGB());
        }
    }
}
