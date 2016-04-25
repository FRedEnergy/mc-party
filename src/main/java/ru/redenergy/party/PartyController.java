package ru.redenergy.party;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.redenergy.party.message.CreatePartyMessage;
import ru.redenergy.party.model.PartyInfo;

import java.util.*;

public class PartyController {

    private Map<UUID, PartyInfo> parties = new HashMap<>();

    public void addParty(PartyInfo party){
        parties.put(party.getUid(), party);
    }

    public PartyInfo getParty(UUID uid){
        return parties.get(uid);
    }

    public List<PartyInfo> getParties(){
        return new ArrayList<>(parties.values());
    }

    public PartyInfo findPartyWith(String player){
        for(PartyInfo party: parties.values())
            if(party.getAllPlayers().contains(player))
                return party;
        return null;
    }

    public boolean inParty(String player){
        return findPartyWith(player) != null;
    }

    public void handleCreatePartyMsg(EntityPlayerMP sender, CreatePartyMessage message){
        Set<String> invited = message.invited;
        if(invited == null || invited.isEmpty()){
            sender.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You must invite somebody into party")); return;
        }
        if(invited.contains(sender.getName())){
            sender.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You can't invite yourself")); return;
        }
        if(invited.size() + 1 > PartyInfo.INITIAL_PARTY_LIMIT){
            sender.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Amount of invited players more than possible limit")); return;
        }
        if(inParty(sender.getName())){
            sender.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You're already in party")); return;
        }
        for(String player: invited)
            if(inParty(player)){
                sender.addChatComponentMessage(new TextComponentString(TextFormatting.RED + player + " already in party, can't invite him")); return;
            }
        PartyInfo party = new PartyInfo(sender.getName(), invited);
        addParty(party);
        sender.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "Party successfully created"));
    }
}
