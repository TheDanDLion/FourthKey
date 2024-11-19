package fourthKey.patches.basemod;

import basemod.devcommands.key.KeyAdd;
import basemod.devcommands.key.KeyLose;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import java.util.ArrayList;

import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

public class PurpleKeyPatch {
    @SpirePatch2(
        clz = KeyAdd.class,
        method = "execute"
    )
    public static class AddPurpleKeyCommandPatch {
        public static void Postfix(String[] tokens, int depth) {
            if(tokens[depth].equals("all") || tokens[depth].equals("amethyst"))
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
        }
    }

    @SpirePatch2(
        clz = KeyAdd.class,
        method = "extraOptions"
    )
    public static class AddPurpleKeyOptionPatch {
        public static ArrayList<String> Postfix(ArrayList<String> __result) {
            __result.add(3, "amethyst");
            return __result;
        }
    }

    @SpirePatch2(
        clz = KeyLose.class,
        method = "execute"
    )
    public static class LosePurpleKeyCommandPatch {
        public static void Postfix(String[] tokens, int depth) {
            if(tokens[depth].equals("all") || tokens[depth].equals("amethyst"))
                AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.set(AbstractDungeon.player, false);
        }
    }

    @SpirePatch2(
        clz = KeyLose.class,
        method = "extraOptions"
    )
    public static class LosePurpleKeyOptionPatch {
        public static ArrayList<String> Postfix(ArrayList<String> __result) {
            __result.add(3, "amethyst");
            return __result;
        }
    }
}
