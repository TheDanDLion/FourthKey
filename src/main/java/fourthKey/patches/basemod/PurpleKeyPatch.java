package fourthKey.patches.basemod;

import basemod.devcommands.key.KeyAdd;
import basemod.devcommands.key.KeyLose;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
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
            if(tokens[depth].equals("all") || tokens[depth].equals("purple"))
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
        }
    }

    @SpirePatch2(
        clz = KeyAdd.class,
        method = "extraOptions"
    )
    public static class AddPurpleKeyOptionPatch {
        @SpireInsertPatch(
            loc = 61,
            localvars = {"tmp"}
        )
        public static void Insert(ArrayList<String> tmp) {
            tmp.add("purple");
        }
    }

    @SpirePatch2(
        clz = KeyLose.class,
        method = "execute"
    )
    public static class LosePurpleKeyCommandPatch {
        public static void Postfix(String[] tokens, int depth) {
            if(tokens[depth].equals("all") || tokens[depth].equals("purple"))
                AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.set(AbstractDungeon.player, false);
        }
    }

    @SpirePatch2(
        clz = KeyLose.class,
        method = "extraOptions"
    )
    public static class LosePurpleKeyOptionPatch {
        @SpireInsertPatch(
            loc = 46,
            localvars = {"tmp"}
        )
        public static void Insert(ArrayList<String> tmp) {
            tmp.add("purple");
        }
    }
}
