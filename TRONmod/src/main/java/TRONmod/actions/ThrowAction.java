package TRONmod.actions;

import TRONmod.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ThrowAction extends AbstractGameAction {
    private AbstractPlayer p;

    private boolean isRandom;
    public static int numPlaced;

    /*

    public static final String ID = TRONMod.makeID(ThrowAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("tronmod:ThrowAction");
    public static final String[] TEXT = uiStrings.TEXT;

     */

    private ArrayList<AbstractCard> cannotThrow = new ArrayList<>();

    public ThrowAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            //DEFYING CANNOT THROW CARDS
            for (AbstractCard c : this.p.hand.group) {
                if (!isAttack(c))
                    this.cannotThrow.add(c);
            }

            //CHECKING IF NOT ENOUGH CARDS TO CHOOSE FROM
            if (this.cannotThrow.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotThrow.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                    if (isAttack(c)) {
                        for (int i = 0; i < this.amount; i++) throwCard(c);
                        this.isDone = true;
                        return;
                    }
                }

            //HIDING NON-ATTACK CARDS
            this.p.hand.group.removeAll(this.cannotThrow);

            //RANDOMNESS
            if (this.p.hand.size() < this.amount)
                this.amount = this.p.hand.size();
            if (this.isRandom) {
                for (int i = 0; i < this.amount; i++) throwCard(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
            } else {

                //SELECTING CARDS
                if (this.p.hand.group.size() > this.amount) {
                    numPlaced = this.amount;
                    AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"throw (exhaust or shuffle to draw pile)", this.amount, false);
                    tickDuration();
                    return;
                }
                for (int i = 0; i < this.p.hand.size(); i++) throwCard(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));

            }
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
            ((AbstractDynamicCard) c).thrownUse();
            this.p.hand.moveToBottomOfDeck(c);
        } else {
            this.p.hand.moveToExhaustPile(c);
        }
        this.isDone = true;

    }

    private boolean isAttack(AbstractCard c) {
        return c.type.equals(AbstractCard.CardType.ATTACK);
    }
}
