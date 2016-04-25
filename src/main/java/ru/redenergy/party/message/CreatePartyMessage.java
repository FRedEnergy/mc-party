package ru.redenergy.party.message;


import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.redenergy.party.MCParty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreatePartyMessage implements IMessage{

    public Set<String> invited = new HashSet<>();

    public CreatePartyMessage() {}

    public CreatePartyMessage(Set<String> invited) {
        this.invited = invited;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeByte(invited.size());
        for(String name: invited)
            ByteBufUtils.writeUTF8String(byteBuf, name);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        byte size = byteBuf.readByte();
        for(int i = 0; i < size; i++)
            invited.add(ByteBufUtils.readUTF8String(byteBuf));
    }

    public static class Handler implements IMessageHandler<CreatePartyMessage, IMessage> {

        @Override
        public IMessage onMessage(CreatePartyMessage createPartyMessage, MessageContext messageContext) {
            MCParty.instance.controller().handleCreatePartyMsg(messageContext.getServerHandler().playerEntity, createPartyMessage);
            return null;
        }
    }
}
