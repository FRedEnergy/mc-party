package ru.redenergy.party.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;

/**
 * This test validates that all messages do not violate to/fromBytes methods <br>
 * implementation and could be successfully transferred by SimpleNetworkWrapper
 */
public class TestMessagesTransfer {

    @Test
    public void testCreateMessage() {
        CreatePartyMessage originalMessage = new CreatePartyMessage(new HashSet<>(Arrays.asList("A", "B", "C")));
        ByteBuf buf = write(originalMessage);
        CreatePartyMessage receivedMessage = new CreatePartyMessage();
        receivedMessage.fromBytes(buf);
        assertEquals(originalMessage.invited, receivedMessage.invited);
    }

    @Test
    public void testInviteMessage(){
        PartyInvitationMessage message = new PartyInvitationMessage(UUID.randomUUID(), "Test");
        ByteBuf buf = write(message);
        PartyInvitationMessage received = new PartyInvitationMessage();
        received.fromBytes(buf);
        assertEquals(message.initiator, received.initiator);
        assertEquals(message.partyUid, received.partyUid);
    }

    @Test
    public void testResponseMessage(){
        ResponseInvitationMessage message = new ResponseInvitationMessage(ResponseInvitationMessage.Response.ACCEPT, UUID.randomUUID());
        ByteBuf buf = write(message);
        ResponseInvitationMessage received = new ResponseInvitationMessage();
        received.fromBytes(buf);
        assertEquals(message.response, received.response);
        assertEquals(message.groupUid, received.groupUid);
    }

    private static ByteBuf write(IMessage message) {
        ByteBuf buf = new PacketBuffer(Unpooled.buffer());
        message.toBytes(buf);
        return buf;
    }
}