package ru.redenergy.party;

import com.google.common.base.Objects;

import java.util.UUID;

public class Invite {

    private final String invitee;
    private final String inviter;
    private final UUID groupUid;

    public Invite(String invitee, String inviter, UUID groupUid) {
        this.invitee = invitee;
        this.inviter = inviter;
        this.groupUid = groupUid;
    }

    public String getInvitee() {
        return invitee;
    }

    public String getInviter() {
        return inviter;
    }

    public UUID getGroupUid() {
        return groupUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invite invite = (Invite) o;
        return Objects.equal(invitee, invite.invitee) &&
                Objects.equal(inviter, invite.inviter) &&
                Objects.equal(groupUid, invite.groupUid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(invitee, inviter, groupUid);
    }
}