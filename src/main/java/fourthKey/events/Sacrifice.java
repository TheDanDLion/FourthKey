package fourthKey.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import fourthKey.patches.vfx.ObtainKeyEffectPatch;

import static fourthKey.ModInitializer.makeID;
import static fourthKey.ModInitializer.makeEventPath;

public class Sacrifice extends AbstractImageEvent {

    public static final String ID = makeID("Sacrifice");

    private static EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private AbstractRelic relicSacrifice;
    private AbstractCard cardSacrifice;
    private int healthSacrifice;
    private static final int GOLD_SACRIFICE = 49;

    private State state;

    private enum State {
        INTRO,
        LEAVING
    }

    public Sacrifice(String title, String body, String imgUrl) {
        super(NAME, DESCRIPTIONS[0], makeEventPath("Sacrifice.png"));

        this.healthSacrifice = (int)(AbstractDungeon.player.maxHealth * 0.25);
        this.relicSacrifice = AbstractDungeon.player.relics.get(AbstractDungeon.cardRandomRng.random(AbstractDungeon.player.relics.size() - 1));

        CardGroup sacrificeableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        sacrificeableCards.clear();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.rarity == AbstractCard.CardRarity.RARE) {
                sacrificeableCards.addToTop(card);
            }
        }
        cardSacrifice = sacrificeableCards.getRandomCard(true);

        this.imageEventText.setDialogOption(OPTIONS[0] + healthSacrifice + " HP.");
        this.imageEventText.setDialogOption(OPTIONS[1] + GOLD_SACRIFICE + " gold.");
        this.imageEventText.setDialogOption(OPTIONS[2]); // potions sacrifice
        this.imageEventText.setDialogOption(OPTIONS[1] + cardSacrifice.name);
        this.imageEventText.setDialogOption(OPTIONS[1] + relicSacrifice.name);
        this.imageEventText.setDialogOption(OPTIONS[3]); // leave

        this.state = State.INTRO;

        AbstractDungeon.getCurrRoom().rewards.clear();
    }

    private void gainPurpleKey() {
        AbstractDungeon.effectsQueue.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
    }

    @Override
    protected void buttonEffect(int button) {
        switch(state) {
            case INTRO:
                switch(button) {
                    case 0:
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        AbstractDungeon.player.damage(new DamageInfo(null, healthSacrifice));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        gainPurpleKey();
                        break;
                    case 1:
                        CardCrawlGame.sound.play("GOLD_JINGLE");
                        AbstractDungeon.player.loseGold(GOLD_SACRIFICE);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        gainPurpleKey();
                        break;
                    case 2:
                        CardCrawlGame.sound.play("POTION_1");
                        AbstractDungeon.player.potions.clear();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        gainPurpleKey();
                        break;
                    case 3:
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(cardSacrifice, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        AbstractDungeon.player.masterDeck.removeCard(cardSacrifice);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        gainPurpleKey();
                        break;
                    case 4:
                        relicSacrifice.playLandingSFX();
                        AbstractDungeon.player.loseRelic(relicSacrifice.relicId);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        gainPurpleKey();
                        break;
                    case 5:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                        break;
                }
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                this.state = State.LEAVING;
                AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                break;
            case LEAVING:
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                openMap();
                break;
        }
    }
}
