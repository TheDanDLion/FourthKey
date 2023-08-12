package fourthKey.campfire;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.relics.HeartBlessingPurple;
import fourthKey.util.TextureLoader;
import fourthKey.vfx.campfire.BustKeyEffectCopy;

import static fourthKey.ModInitializer.makeID;
import static fourthKey.ModInitializer.makeUIPath;

/**
 * Most of the following code is copied from the BustKeyOption class in the
 * Downfall mod. I am using it as a reference to make this mod compatible with Downfall.
 */

public class BustPurpleKeyOption extends AbstractCampfireOption {

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("BustPurpleKeyOption")).TEXT;

    public static final int EXTRA_DRAW_OR_ENERGY = 1;

    private int cost;

    private boolean used;

    private boolean hacked;

    public BustPurpleKeyOption() {
        this.cost = (AbstractDungeon.player.hasRelic("Ectoplasm") ? 0 : 50);
        this.usable = AbstractDungeon.player.gold < this.cost;
        this.label = TEXT[0];
        updateImage();
    }

    public void updateImage() {
        this.description = (AbstractDungeon.player.hasRelic("Ectoplasm") ? TEXT[6] : TEXT[1]);
        this.img = TextureLoader.getTexture(makeUIPath("campfire/" + (this.usable ? "amethyst" : "amethystDisabled") + ".png"));
        if (!this.used) {
            if (this.usable)
                this.description += TEXT[2];
            else
                this.description = TEXT[4] + this.cost + TEXT[5];
        } else {
            this.description = TEXT[3];
        }
    }

    @Override
    public void update() {
        float hackScale = ((Float)ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale")).floatValue();
        if (this.hb.hovered) {
            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", Float.valueOf(MathHelper.scaleLerpSnap(hackScale, Settings.scale)));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", Float.valueOf(MathHelper.scaleLerpSnap(hackScale, Settings.scale)));
            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", Float.valueOf(MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale)));
            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", Float.valueOf(MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale)));
        }
        super.update();
        if (!this.used) {
            if (AbstractDungeon.player.gold < this.cost && this.usable) {
                this.usable = false;
                updateImage();
            }
            if (AbstractDungeon.player.gold >= this.cost && !this.usable) {
                this.usable = true;
                updateImage();
            }
        }
        CampfireUI campfire = ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI;
        if (this.used && !this.hacked) {
            this.hacked = true;
            campfire.somethingSelected = false;
            campfire.touchOption = null;
            campfire.confirmButton.hide();
            campfire.confirmButton.hideInstantly();
            campfire.confirmButton.isDisabled = true;
            AbstractDungeon.overlayMenu.proceedButton.hide();
            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectsQueue.add(new BustKeyEffectCopy());
            AbstractPlayer p = AbstractDungeon.player;
            p.loseGold(cost);
            this.used = true;
            this.usable = false;
            AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenAmethystKey.set(p, true);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new HeartBlessingPurple());
            updateImage();
        }
    }
}
