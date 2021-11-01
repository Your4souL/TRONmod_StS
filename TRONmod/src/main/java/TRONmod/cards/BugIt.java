package TRONmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TRONmod.TRONMod;
import TRONmod.characters.TheANON;

import static TRONmod.TRONMod.makeCardPath;

public class BugIt extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(BugIt.class.getSimpleName());
    public static final String IMG = makeCardPath("BugIt.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = -1;

    private static final int MALLEABLE = 2;
    private static final int UPGRADE_PLUS_MALLEABLE = 1;

    private static final int BUG = 2;
    private static final int UPGRADE_PLUS_BUG = 1;

    public BugIt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MALLEABLE;
        secondMagicNumber = baseSecondMagicNumber = BUG;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MALLEABLE);
            this.upgradeSecondMagicNumber(UPGRADE_PLUS_BUG);
            this.initializeDescription();
        }
    }
}