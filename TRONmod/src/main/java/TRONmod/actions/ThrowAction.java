package TRONmod.actions;

import TRONmod.cards.AbstractDynamicCard;
import TRONmod.relics.BasisDiskRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.status.VoidCard;

import java.util.ArrayList;

public class ThrowAction extends AbstractGameAction {
    private AbstractPlayer p;

    private int ricochetAmount;
    private boolean anyNumber;
    //public static int numPlaced;

    /*

    public static final String ID = TRONMod.makeID(ThrowAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("tronmod:ThrowAction");
    public static final String[] TEXT = uiStrings.TEXT;

     */

    private ArrayList<AbstractCard> cannotThrow = new ArrayList<>();

    public ThrowAction(AbstractCreature target, AbstractCreature source, int amount, int ricochetAmount, boolean anyNumber) {
        this.target = target;
        this.p = AbstractDungeon.player;
        this.anyNumber = anyNumber;
        setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.ricochetAmount = ricochetAmount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            //DEFYING CANNOT THROW CARDS
            for (AbstractCard c : this.p.hand.group) {
                if (!isThrowable(c))
                    this.cannotThrow.add(c);
            }

            //CHECKING IF NOT ENOUGH CARDS TO CHOOSE FROM
            if (this.cannotThrow.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotThrow.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                    if (isThrowable(c)) {
                        throwCard(c);
                        this.isDone = true;
                        return;
                    }
                }

            //HIDING NON-ATTACK CARDS
            this.p.hand.group.removeAll(this.cannotThrow);

            if (this.p.hand.group.size() >= this.amount)
            {
                AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"throw (exhaust or shuffle to draw pile)", this.amount, anyNumber);
            } else {
                AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"throw (exhaust or shuffle to draw pile)", this.p.hand.group.size(), anyNumber);
            }
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            //OPERATE WITH SELECTED CARDS
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) throwCard(c);

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
        for (AbstractCard c : this.cannotThrow)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

    private void throwCard(AbstractCard c) {
        if (c.cost == -2) {
            if (c instanceof AbstractDynamicCard) {
                ((AbstractDynamicCard) c).thrownUse();
                if (this.ricochetAmount > 0) for (int i = 0; i < this.ricochetAmount - 1; i++) ((AbstractDynamicCard) c).thrownUse();
            }
            this.p.hand.moveToDeck(c, true);
        } else {
            c.exhaust = true;
            c.freeToPlayOnce = true;
            addToBot(new NewQueueCardAction(c, true, false, true));
            if (this.ricochetAmount > 0) {
                for (int i = 0; i < this.ricochetAmount - 1; i++) {
                    AbstractCard tmp = c.makeStatEquivalentCopy();
                    tmp.purgeOnUse = true;
                    addToBot(new NewQueueCardAction(tmp, true, false, true));
                    tryAddVoid();
                }
            }
            tryAddVoid();
        }
    }

    private boolean isThrowable(AbstractCard c) {
        return !c.type.equals(AbstractCard.CardType.POWER);
    }

    private void tryAddVoid() {
        if (p.hasRelic(BasisDiskRelic.ID) && p.getRelic(BasisDiskRelic.ID).counter > 0) {
            p.getRelic(BasisDiskRelic.ID).onTrigger();
        } else {
            addToBot(new WaitAction(1.0f));
            addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, false, false));
        }
    }
}
