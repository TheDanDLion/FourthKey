package fourthKey.console;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

public class PurpleKeyAdd extends ConsoleCommand {

    public PurpleKeyAdd() {
        this.requiresPlayer = true;
        this.simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        Settings.isFinalActAvailable = true;
        if (!AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player))
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
    }
}
