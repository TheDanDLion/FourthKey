package fourthKey.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch2(
    clz = AbstractPlayer.class,
    method = SpirePatch.CLASS
)
public class AbstractPlayerPatch {

    public static SpireField<Boolean> hasAmethystKey = new SpireField<>(() -> false);

}
