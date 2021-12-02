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
    private boolean SelectDescription = false;

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
            for (AbstractCard c : p.hand.group) {
                if (!c.hasTag(CustomTags.DISK)) nonDisks.add(c);
            }

            //CHECKING IF NOT ENOUGH CARDS TO CHOOSE FROM
            if (p.hand.group.size() - nonDisks.size() == 1) {
                for (AbstractCard c : p.hand.group) {
                    if (c.hasTag(CustomTags.DISK)) {
                        slash(c);
                        this.isDone = true;
                        return;
                    }
                }
            }

            //HIDING NON-ATTACK CARDS
            p.hand.group.removeAll(nonDisks);

            //SELECTING CARDS
            if (p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(/*TEXT[0]*/"", 1, false);
                if (!SelectDescription) {
                    for (AbstractCard c : p.hand.group) {
                        AbstractDynamicCard d = (AbstractDynamicCard)c;
                        d.rawDescription = d.getExDesc(0);
                        d.initializeDescription();
                    }
                    SelectDescription = true;
                }
                tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            //OPERATE WITH SELECTED CARD
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                slash(c);
                p.hand.addToTop(c);
            }

            if (SelectDescription) {
                for (AbstractCard c : p.hand.group) {
                    AbstractDynamicCard d = (AbstractDynamicCard)c;
                    d.rawDescription = d.getDesc();
                    d.initializeDescription();
                }
                SelectDescription = false;
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
        for (AbstractCard c : nonDisks)
            p.hand.addToTop(c);
        p.hand.refreshHandLayout();
    }

    private void slash(AbstractCard c) {
        AbstractDynamicCard disk = (AbstractDynamicCard)c;
        disk.superFlash();

        disk.diskPreEffect(m, sc);
        sc.calculateCardDamage(m);

        addToBot(new DamageAction(m, new DamageInfo(p, sc.damage, damageType), visualEffect));
        addToBot(new ApplyPowerAction(m, p, new DistractionPower(m, p, sc.damage)));

        disk.diskPostEffect(m, sc);
    }
}
