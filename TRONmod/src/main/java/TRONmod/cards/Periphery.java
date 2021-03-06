package TRONmod.cards;

import TRONmod.powers.PeripheryDexterityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TRONmod.TRONMod;
import TRONmod.characters.TheANON;
import TRONmod.powers.PeripheryStrengthPower;

import static TRONmod.TRONMod.makeCardPath;

public class Periphery extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(Periphery.class.getSimpleName());
    public static final String IMG = makeCardPath("Periphery.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 1;
    private static final int MAGIC = 1;

    public Periphery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PeripheryStrengthPower(p, p, magicNumber), magicNumber));
        if (upgraded) addToBot(new ApplyPowerAction(p, p, new PeripheryDexterityPower(p, p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}