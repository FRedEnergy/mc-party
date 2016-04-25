package ru.redenergy.party.ui;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.TextBox;
import com.rabbit.gui.component.list.DisplayList;
import com.rabbit.gui.component.list.ScrollableDisplayList;
import com.rabbit.gui.component.list.entries.StringEntry;
import com.rabbit.gui.show.Show;
import ru.redenergy.party.MCParty;
import ru.redenergy.party.message.CreatePartyMessage;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class CreatePartyShow extends Show{


    @Override
    public void setup() {
        super.setup();
        registerComponent(new TextBox(this.width / 2 - 75, this.height / 2 - 30, 100, 20)
                        .setId("player_invite_field"));
        registerComponent(new Button(this.width / 2 + 110, this.height / 2 - 30, 40, 20, "Invite")
                        .setClickListener(it -> invitePlayer()));
        registerComponent(new ScrollableDisplayList(this.width / 2 - 75, this.height / 2, 150, this.height / 5, 25, new ArrayList<>())
                        .setId("invited_players"));
        registerComponent(new Button(this.width / 2 - 75, 2 * this.height / 10, 150, 20, "Create")
                        .setClickListener(it -> createParty()));
    }

    private void createParty(){
        DisplayList list = findComponentById("invited_players");
        Set<String> players = list.getContent().stream().map(it -> ((InvitationEntry)it).getTitle()).collect(Collectors.toSet());
        MCParty.instance.network().sendToServer(new CreatePartyMessage(players));
        this.getStage().close();
    }

    private void invitePlayer(){
        TextBox box = findComponentById("player_invite_field");
        String player = box.getText();
        box.setCursorPosition(0);
        box.setSelectionPos(0);
        box.setText("");
        invitePlayer(player);
    }

    private void invitePlayer(String player){
        ((DisplayList)findComponentById("invited_players")).add(new InvitationEntry(player));
    }

    private static class InvitationEntry extends StringEntry {

        private final String title;

        public InvitationEntry(String title) {
            super(title);
            this.title = title;
        }

        public InvitationEntry(String title, OnClickListener listener) {
            super(title, listener);
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
