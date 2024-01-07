package fourthKey.patches.rooms;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import java.util.ArrayList;

import fourthKey.FourthKeyInitializer;
import fourthKey.campfire.BustPurpleKeyOption;
import fourthKey.patches.characters.AbstractPlayerPatch;

public class CampfireUIPatch {

    @SpirePatch2 (
        clz = CampfireUI.class,
        method = "initializeButtons",
        requiredModId = "downfall"
    )

    public static class AddBustKeyOption {
        @SpireInsertPatch(
            loc = 97
        )
        public static void Insert(ArrayList<AbstractCampfireOption> ___buttons) {
            if (FourthKeyInitializer.downfallEvilMode
                && AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)
                && !AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenAmethystKey.get(AbstractDungeon.player)) {
                ___buttons.add(new BustPurpleKeyOption());
            }
        }
    }
}
