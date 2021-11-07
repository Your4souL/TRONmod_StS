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

    private static final int CURL_UP = 2;

    private static final int BUG = 3;
    private static final int UPGRADE_PLUS_BUG = 5;

    public BugIt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = CURL_UP;
        this.secondMagicNumber = this.baseSecondMagicNumber = BUG;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagicNumber(UPGRADE_PLUS_BUG);
            this.initializeDescription();
        }
    }
}