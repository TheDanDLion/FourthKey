package fourthKey.patches.monsters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import fourthKey.ModInitializer;

public class AbstractMonsterPatch {
    @SpirePatch2(
        clz = AbstractMonster.class,
        method = "onFinalBossVictoryLogic",
        requiredModId = "downfall"
    )
    public static class ResetEvilModeFlagPatch {
        public static void Prefix() {
            if (AbstractDungeon.actNum == 4)
                ModInitializer.downfallEvilMode = false;
            ModInitializer.logger.info(ModInitializer.downfallEvilMode);
        }
    }
}
