package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.characters.TheANON;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static TRONmod.TRONMod.makeCardPath;

public class GravityAnchor extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(GravityAnchor.class.getSimpleName());
    public static final String IMG = makeCardPath("GravityAnchor.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCard.CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public GravityAnchor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) { return false;}

    public void triggerWhenDrawn() {
        addToTop(new DrawCardAction(AbstractDungeon.player, 2));
    }

    public void triggerOnExhaust() {
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, block), block));
    }

    public void thrownUse() {
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, block), block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

}
