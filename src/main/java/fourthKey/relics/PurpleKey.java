package fourthKey.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import basemod.abstracts.CustomRelic;
import fourthKey.FourthKeyInitializer;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;
import fourthKey.util.TextureLoader;

public class PurpleKey extends CustomRelic {

    public static final String ID = FourthKeyInitializer.makeID("PurpleKey");
    private static final Texture IMG = TextureLoader.getTexture("Blank.png");

    public PurpleKey() {
        super(ID, IMG, IMG, RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
        AbstractDungeon.player.loseRelic(ID);
    }
}
