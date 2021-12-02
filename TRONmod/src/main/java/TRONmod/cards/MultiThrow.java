package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.actions.ThrowAction;
import TRONmod.characters.TheANON;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;

public class MultiThrow extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(MultiThrow.class.getSimpleName());
    public static final String IMG = makeCardPath("MultiThrow.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 3;
    private static final int COST_UPGRADE = 2;

    public MultiThrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ThrowAction(p, p, 3, 0, true));
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