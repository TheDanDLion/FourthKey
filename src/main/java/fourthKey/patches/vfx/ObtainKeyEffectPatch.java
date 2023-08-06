package fourthKey.patches.vfx;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor;

import fourthKey.patches.characters.AbstractPlayerPatch;

public class ObtainKeyEffectPatch {

    private static final Texture AMETHYST_KEY = ImageMaster.loadImage("fourthKeyResources/images/ui/purpleKey.png");

    @SpireEnum
    public static ObtainKeyEffect.KeyColor PURPLE;

    @SpirePatch2(
        clz = ObtainKeyEffect.class,
        method = SpirePatch.CONSTRUCTOR
    )
    public static class ObtainPurpleKeyEffectPatch {
        public static void Postfix(Texture ___img, KeyColor keyColor) {
            if (keyColor == PURPLE) {
                ___img = AMETHYST_KEY;
                AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, true);
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
        public static void Insert(Texture ___img, KeyColor ___keyColor) {
            if (___keyColor == PURPLE) {
                ___img = AMETHYST_KEY;
                AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, true);
            }
        }
    }
}
