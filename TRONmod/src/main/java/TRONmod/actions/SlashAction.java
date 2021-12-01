package TRONmod.actions;

import TRONmod.cards.AbstractDynamicCard;
import TRONmod.cards.AbstractSlashCard;
import TRONmod.powers.DistractionPower;
import TRONmod.util.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SlashAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private AbstractCard.CardTarget targetType;
    private AbstractSlashCard sc;
    private AbstractGameAction.AttackEffect visualEffect;

    //public static int numPlaced;

    /*

    public static final String ID = TRONMod.makeID(ThrowAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("tronmod:ThrowAction");
    public static final String[] TEXT = uiStrings.TEXT;

     */

    private ArrayList<AbstractCard> nonDisks = new ArrayList<>();

    public SlashAction(AbstractPlayer p, AbstractMonster m, AbstractSlashCard c, AbstractCard.CardTarget targetType, AbstractGameAction.AttackEffect effect) {
        this.p = p;
        this.m = m;
        this.targetType = targetType;
        this.duration = Settings.ACTION_DUR_FAST;
        this.sc = c;
        this.actionType = ActionType.DAMAGE;
        this.visualEffect = effect;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            //DEFYING NON-DISK CARDS
            for (AbstractCard c : this.p.hand.group) {
                if (!c.hasTag(CustomTags.DISK)) this.nonDisks.add(c);
            }

            //CHECKING IF NOT ENOUGH CARDS TO CHOOSE FROM
            if (this.p.hand.group.size() - this.nonDisks.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (c.hasTag(CustomTags.DISK)) {
                        AbstractDynamicCard disk = (AbstractDynamicCard)c;
                        disk.superFlash();

                        disk.diskPreEffect(this.m, this.sc);
                        this.sc.calculateCardDamage(this.m);

                        addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.sc.damage, this.damageType), this.visualEffect));
                        addToBot(new ApplyPowerAction(this.m, this.p, new DistractionPower(this.m, this.p, this.sc.damage)));

                        disk.diskPostEffect(this.m, this.sc);
                        this.isDone = true;
                        return;
                    }
                }
            }

            //HIDING NON-ATTACK CARDS
            this.p.hand.group.removeAll(this.nonDisks);

            //SELECTING CARDS
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"", 1, false);
                tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            //OPERATE WITH SELECTED CARD
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDynamicCard disk = (AbstractDynamicCard)c;
                disk.superFlash();

                disk.diskPreEffect(this.m, this.sc);
                this.sc.calculateCardDamage(this.m);

                addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.sc.damage, this.damageType), this.visualEffect));
                addToBot(new ApplyPowerAction(this.m, this.p, new DistractionPower(this.m, this.p, this.sc.damage)));

                disk.diskPostEffect(this.m, this.sc);
                this.p.hand.addToTop(disk);
            }

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
}
