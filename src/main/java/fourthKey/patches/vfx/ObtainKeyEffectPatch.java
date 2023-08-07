package fourthKey.patches.vfx;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor;

import fourthKey.ModInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;

public class ObtainKeyEffectPatch {

    public static Texture amethystKey;
    public static Texture emeraldKey;
    public static Texture rubyKey;
    public static Texture sapphireKey;

    @SpireEnum
    public static ObtainKeyEffect.KeyColor PURPLE;

    @SpirePatch2(
        clz = ObtainKeyEffect.class,
        method = SpirePatch.CONSTRUCTOR
    )
    public static class ObtainPurpleKeyEffectPatch {
        public static void Postfix(@ByRef Texture[] ___img, KeyColor keyColor) {
            if (!ModInitializer.disableAmethystKey) {
                switch (keyColor) {
                    case GREEN:
                        ___img[0] = emeraldKey;
                        break;
                    case RED:
                        ___img[0] = rubyKey;
                        break;
                    case BLUE:
                        ___img[0] = sapphireKey;
                        break;
                }
                if (keyColor == PURPLE) {
                    ___img[0] = amethystKey;
                    AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, true);
                }
            }
        }
    }

    @SpirePatch2(
        clz = ObtainKeyEffect.class,
        method = "update"
    )
    public static class UpdatePurpleKeyPatch {

        @SpireInsertPatch(
            loc = 56
        )
        public static SpireReturn<Void> Insert(@ByRef Texture[] ___img, KeyColor ___keyColor) {
            if (!ModInitializer.disableAmethystKey) {
                switch (___keyColor) {
                    case GREEN:
                        ___img[0] = emeraldKey;
                        Settings.hasEmeraldKey = true;
                        break;
                    case RED:
                        ___img[0] = rubyKey;
                        Settings.hasRubyKey = true;
                        break;
                    case BLUE:
                        ___img[0] = sapphireKey;
                        Settings.hasSapphireKey = true;
                        break;
                }
                if (___keyColor == PURPLE) {
                    ___img[0] = amethystKey;
                    AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, true);
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
