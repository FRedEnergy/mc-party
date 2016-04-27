package ru.redenergy.party;

import net.minecraft.client.Minecraft;
import ru.redenergy.party.model.PartyInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Practically just operates information received from server
 */
public class ClientController {

    /** Party in which current user of the client is found*/
    private PartyInfo theParty = null;
    /** All invites received from server */
    private Set<Invite> invites = new HashSet<>();

    public Set<Invite> getInvites() {
        return invites;
    }

    public PartyInfo getTheParty() {
        return theParty;
    }

    public void setTheParty(PartyInfo theParty) {
        this.theParty = theParty;
    }

    public void addInvite(String inviter, UUID partyUid){
        invites.add(new Invite(Minecraft.getMinecraft().thePlayer.getName(), inviter, partyUid));
    }

    public static ClientController instance(){
        return Holder.instance;
    }

    private static class Holder {
        public static final ClientController instance = new ClientController();
    }
}
