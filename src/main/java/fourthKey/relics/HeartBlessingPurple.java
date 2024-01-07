package fourthKey.relics;

import basemod.abstracts.CustomRelic;
import fourthKey.campfire.BustPurpleKeyOption;
import fourthKey.util.TextureLoader;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static fourthKey.FourthKeyInitializer.makeID;
import static fourthKey.FourthKeyInitializer.makeRelicPath;

public class HeartBlessingPurple extends CustomRelic {

    public static final String ID = makeID("HeartBlessingPurple");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeartBlessingPurple.png"));

    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicPath("outline/HeartBlessingPurple.png"));

    public HeartBlessingPurple() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if ((AbstractDungeon.getCurrRoom()).monsters.monsters.stream().anyMatch(q -> (q.type == AbstractMonster.EnemyType.BOSS))) {
            this.flash();
            addToBot(AbstractDungeon.cardRandomRng.randomBoolean()
                ? new GainEnergyAction(BustPurpleKeyOption.EXTRA_DRAW_OR_ENERGY)
                : new DrawCardAction(BustPurpleKeyOption.EXTRA_DRAW_OR_ENERGY));
        }
    }
}
