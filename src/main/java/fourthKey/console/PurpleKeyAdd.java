package fourthKey.console;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.ModInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

public class PurpleKeyAdd extends ConsoleCommand {

    public PurpleKeyAdd() {
        this.requiresPlayer = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (!ModInitializer.disableAmethystKey) {
            Settings.isFinalActAvailable = true;
            if (!AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player))
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
        }
    }
}
