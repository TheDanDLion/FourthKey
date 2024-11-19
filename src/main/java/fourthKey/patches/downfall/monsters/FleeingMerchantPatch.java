package fourthKey.patches.downfall.monsters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import fourthKey.patches.rewards.RewardItemPatch;

public class FleeingMerchantPatch {
    
    @SpirePatch2(cls = "downfall.monsters.FleeingMerchant", method = "die", requiredModId = "downfall")
    public static class AddKeyToRewardsOnDiePatch {
        public static void Prefix() {
            AbstractDungeon.getCurrMapNode().getRoom().rewards.add(new RewardItem(null, RewardItemPatch.AMETHYST_KEY));
        }
    }
}
