package fourthKey.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * TODO: not sure how to get the BustKeyEffect since class loader
 * is being thrown away. Just copying code for now don't be mad.
 */

public class BustKeyEffectCopy extends AbstractGameEffect {

  private boolean hasBusted;

  private Color screenColor;

  public BustKeyEffectCopy() {
    this.hasBusted = false;
    this.screenColor = AbstractDungeon.fadeColor.cpy();
    this.duration = 2.0F;
    this.screenColor.a = 0.0F;
    ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
  }
  
  public void update() {
    this.duration -= Gdx.graphics.getDeltaTime();
    updateBlackScreenColor();
    if (this.duration < 1.0F && !this.hasBusted) {
      this.hasBusted = true;
      CardCrawlGame.sound.playA("BLOCK_BREAK", 1.5F);
    } 
    if (this.duration < 0.0F) {
      this.isDone = true;
      if (AbstractDungeon.getCurrRoom() instanceof RestRoom)
        ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn(); 
    } 
  }
  
  private void updateBlackScreenColor() {
    if (this.duration > 1.5F) {
      this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
    } else if (this.duration < 1.0F) {
      this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
    } else {
      this.screenColor.a = 1.0F;
    } 
  }
  
  public void render(SpriteBatch sb) {
    sb.setColor(this.screenColor);
    sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
  }
  
  public void dispose() {}
}
