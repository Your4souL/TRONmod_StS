package TRONmod.actions;

import TRONmod.util.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

public class BreakDiskAction extends AbstractGameAction {
    private AbstractPlayer p;

    private boolean isRandom;
    public static int numPlaced;

    /*

    public static final String ID = TRONMod.makeID(ThrowAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("tronmod:ThrowAction");
    public static final String[] TEXT = uiStrings.TEXT;

     */

    private ArrayList<AbstractCard> cannotBreak = new ArrayList<>();

    public BreakDiskAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            //DEFYING CANNOT THROW CARDS
            for (AbstractCard c : this.p.hand.group) {
                if (!c.hasTag(CustomTags.DISK)) this.cannotBreak.add(c);
            }

            //CHECKING IF NOT ENOUGH CARDS TO CHOOSE FROM
            if (this.cannotBreak.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotBreak.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                    if (isAttack(c)) {
                        for (int i = 0; i < this.amount; i++) breakCard(c);
                        this.isDone = true;
                        return;
                    }
                }

            //HIDING NON-ATTACK CARDS
            this.p.hand.group.removeAll(this.cannotBreak);

            //RANDOMNESS
            if (this.p.hand.size() < this.amount)
                this.amount = this.p.hand.size();
            if (this.isRandom) {
                for (int i = 0; i < this.amount; i++) breakCard(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
            } else {

                //SELECTING CARDS
                if (this.p.hand.group.size() > this.amount) {
                    numPlaced = this.amount;
                    AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"break", this.amount, false);
                    tickDuration();
                    return;
                }
                for (int i = 0; i < this.p.hand.size(); i++) breakCard(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));

            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            //OPERATE WITH SELECTED CARDS
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) breakCard(c);

            //RETURN HIDDEN CARDS
            returnCards();
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.isDone = true;
            return;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotBreak)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

    private void breakCard(AbstractCard c) {
        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
        addToBot(new RemoveFromMasterDeckAction(c));
        AbstractDungeon.player.hand.removeCard(c);
    }

    private boolean isAttack(AbstractCard c) {
        return c.type.equals(AbstractCard.CardType.ATTACK);
    }
}
