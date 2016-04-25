package ru.redenergy.party.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Describes group. Contains reference to it' master and other members
 */
public class PartyInfo {

    public static final int INITIAL_PARTY_LIMIT = 5;
    private int limit = INITIAL_PARTY_LIMIT;

    private UUID uid;
    private String master;
    private Set<String> members;

    public PartyInfo(String master, Set<String> members) {
        if(members.size() + 1 > limit) //+1 because master also counted as member
            throw new IllegalArgumentException("Members amount cannot be more than limit: " + (members.size() + 1) + " > " + limit);
        this.master = master;
        this.members = members;
        this.uid = UUID.randomUUID();
    }

    public PartyInfo(String master, String ... members){
        this(master, new HashSet(Arrays.asList(members)));
    }

    /**
     * Adds new member to the party if it's possible
     * @param member - member to add
     * @return <code>true</code> if member was successfully added and <code>false</code> if party already contained that member or limit reached
     */
    public boolean addMember(String member) {
        return members.size() + 2 <= limit && this.members.add(member);
    }

    public String getMaster() {
        return master;
    }

    public Set<String> getMembers() {
        return members;
    }

    /**
     * @return Returns master and all member of the party
     */
    public Set<String> getAllPlayers(){
        Set<String> players = new HashSet<String>(members);
        players.add(master);
        return players;
    }

    /**
     * Unique identifier of the party
     */
    public UUID getUid() {
        return uid;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
