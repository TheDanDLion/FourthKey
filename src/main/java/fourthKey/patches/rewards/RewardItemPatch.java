package fourthKey.patches.rewards;

import static fourthKey.FourthKeyInitializer.makeID;
import static fourthKey.FourthKeyInitializer.makeUIPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import basemod.ReflectionHacks;
import fourthKey.FourthKeyInitializer;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;
import fourthKey.util.TextureLoader;
import javassist.CtBehavior;

public class RewardItemPatch {

    @SpireEnum
    public static RewardItem.RewardType AMETHYST_KEY;

    @SpirePatch2(
        clz = RewardItem.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { RewardItem.class, RewardType.class }    
    )
    public static class PurpleKeyRewardPatch {
        public static void Postfix(RewardItem __instance) {
            if (__instance.type.equals(AMETHYST_KEY)) {
                __instance.text = CardCrawlGame.languagePack.getUIString(makeID("ShopScreenPatch")).TEXT[0];
                __instance.img = TextureLoader.getTexture(makeUIPath("amethystKey.png"));
                __instance.outlineImg = TextureLoader.getTexture(makeUIPath("amethystKey.png"));
            }
        }
    }

    @SpirePatch2(
        clz = RewardItem.class,
        method = "claimReward"
    )
    public static class ClaimPurpleKeyPatch {
        public static SpireReturn<Boolean> Prefix(RewardItem __instance) {
            if (__instance.type.equals(AMETHYST_KEY)) {
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
                FourthKeyInitializer.saveKeyOverride = true;
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(
        clz = RewardItem.class,
        method = "render"
    )
    public static class RenderPurpleKeyPatch {
        @SpireInsertPatch(
            locator = Locator.class
        )
        public static void Insert(RewardItem __instance, SpriteBatch sb) {
            if (__instance.type.equals(AMETHYST_KEY)) {
                ReflectionHacks.privateMethod(RewardItem.class, "renderKey", SpriteBatch.class).invoke(__instance, sb);
            }
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ct) throws Exception {
                return LineFinder.findInOrder(ct, new Matcher.FieldAccessMatcher(RewardItem.class, "type"));
            }

        }
    }
}
