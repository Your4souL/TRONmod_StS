package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.actions.ThrowAction;
import TRONmod.characters.TheANON;
import TRONmod.powers.ParkourPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;

public class Throw extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(Throw.class.getSimpleName());
    public static final String IMG = makeCardPath("Throw.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 1;
    private static final int COST_UPGRADE = 0;

    public Throw() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ThrowAction(p, p, 1, 0, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(COST_UPGRADE);
            initializeDescription();
        }
    }
}