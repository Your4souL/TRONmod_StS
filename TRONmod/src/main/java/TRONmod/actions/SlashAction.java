package TRONmod.actions;

import TRONmod.util.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SlashAction extends AbstractGameAction {
    private AbstractPlayer p;

    //public static int numPlaced;

    /*

    public static final String ID = TRONMod.makeID(ThrowAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("tronmod:ThrowAction");
    public static final String[] TEXT = uiStrings.TEXT;

     */

    private ArrayList<AbstractCard> nonDisks = new ArrayList<>();

    public SlashAction(AbstractCreature target, AbstractCreature source, int amount, int ricochetAmount, boolean isRandom) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            //DEFYING NON-DISK CARDS
            for (AbstractCard c : this.p.hand.group) {
                if (!c.hasTag(CustomTags.DISK)) this.nonDisks.add(c);
            }

            //CHECKING IF NOT ENOUGH CARDS TO CHOOSE FROM
            if (this.p.hand.group.size() - this.nonDisks.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                    if (!c.hasTag(CustomTags.DISK)) {
                        //diskSubAction(c);
                        this.isDone = true;
                        return;
                    }
                }

            //HIDING NON-ATTACK CARDS
            this.p.hand.group.removeAll(this.nonDisks);

            //RANDOMNESS
            if (this.p.hand.size() < this.amount)
                this.amount = this.p.hand.size();
            //SELECTING CARDS
            if (this.p.hand.group.size() > this.amount) {
                AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"throw (exhaust or shuffle to draw pile)", this.amount, false);
                tickDuration();
                return;
            }
            //diskSubAction(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));


        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            //OPERATE WITH SELECTED CARDS
            //for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) diskSubAction(c);

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
        for (AbstractCard c : this.nonDisks)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

    private void diskSubAction(AbstractCard c, AbstractMonster mo) {

    }

}
