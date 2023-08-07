package fourthKey.console;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.devcommands.ConsoleCommand;
import fourthKey.patches.characters.AbstractPlayerPatch;

public class PurpleKeyLose extends ConsoleCommand {

    public PurpleKeyLose() {
        this.simpleCheck = true;
        this.requiresPlayer = true;
    }

    @Override
    protected void execute(String[] arg0, int arg1) {
        AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, false);
    }
}
