package ru.redenergy.party;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.server.FMLServerHandler;
import ru.redenergy.party.message.CreatePartyMessage;
import ru.redenergy.party.message.PartyInvitationMessage;
import ru.redenergy.party.message.ResponseInvitationMessage;
import ru.redenergy.party.model.PartyInfo;

import java.util.*;

public class PartyController {

    /** Pending invites which has been sent to users. Signature: <Invitee (String), Invite></> */
    private Multimap<UUID, Invite> pendingInvites = HashMultimap.create();
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

    public PartyInfo findPartyWith(UUID player){
        for(PartyInfo party: parties.values())
            if(party.getAllUsers().contains(player))
                return party;
        return null;
    }

    public Multimap<UUID, Invite> getInvites() {
        return pendingInvites;
    }

    public void invite(PartyInfo party, EntityPlayerMP invited, String inviter){
        Invite invite = new Invite(invited.getName(), inviter, party.getUid());
        getInvites().put(invite.getInvitee(), invite);
        PartyInvitationMessage message = new PartyInvitationMessage(invite.getGroupUid(), invite.getInviter());
        MCParty.instance.network().sendTo(message, invited);
    }

    public boolean inParty(UUID player){
        return findPartyWith(player) != null;
    }

    public void sendToMembers(PartyInfo party, IMessage message){
        sendToEveryone(party.getMembers(), message);
    }

    public void sendToParty(PartyInfo party, IMessage message){
        sendToEveryone(party.getAllUsers(), message);
    }

    public void sendToEveryone(Set<String> user, IMessage message){
        user.stream()
                .map(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()::getPlayerByUsername)
                .forEach(p -> MCParty.instance.network().sendTo(message, p));
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
        if(inParty(sender.getUniqueID())){
            sender.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You're already in party")); return;
        }
        Set<UUID> members = new HashSet<>();
        for(String invitee: invited){
            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(invitee);
            if(player == null){
                sender.addChatMessage(new TextComponentString(TextFormatting.RED + invitee + " unable to find"));
                continue;
            }
            if(inParty(player.getUniqueID())){
                sender.addChatMessage(new TextComponentString(TextFormatting.RED + invitee + " already in party"));
                continue;
            }
            members.add(player.getUniqueID());
        }
        if(invited.isEmpty()){
            sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Unable to invite anybody")); return;
        }
        PartyInfo party = new PartyInfo(sender.getUniqueID());
        addParty(party);
        sender.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "Party successfully created"));
        invited.stream()
                .map(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()::getPlayerByUsername)
                .forEach(p -> invite(party, p, party.getMaster()));
    }

    public void handleInvitationAnswer(ResponseInvitationMessage message){

    }
}
