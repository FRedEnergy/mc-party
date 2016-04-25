package ru.redenergy.party.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PartyInvitationMessage implements IMessage{

    private UUID partyUid;
    private String initiator;

    public PartyInvitationMessage() {}

    public PartyInvitationMessage(UUID partyUid, String initiator) {
        this.partyUid = partyUid;
        this.initiator = initiator;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        ByteBufUtils.writeUTF8String(byteBuf, partyUid.toString());
        ByteBufUtils.writeUTF8String(byteBuf, initiator);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        partyUid = UUID.fromString(ByteBufUtils.readUTF8String(byteBuf));
        initiator = ByteBufUtils.readUTF8String(byteBuf);
    }

    public static class Handler implements IMessageHandler<PartyInvitationMessage, IMessage>{

        @Override
        public IMessage onMessage(PartyInvitationMessage partyInvitationMessage, MessageContext messageContext) {
            System.out.println("Received invitation from " + partyInvitationMessage.initiator + " to " + partyInvitationMessage.partyUid);
            return null;
        }
    }
}
