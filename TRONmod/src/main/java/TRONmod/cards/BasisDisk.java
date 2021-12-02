package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.characters.TheANON;
import TRONmod.util.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class BasisDisk extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(BasisDisk.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BasisDisk.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = -2;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_M_N = 2;

    public BasisDisk() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC_NUMBER;
        this.selfRetain = true;
        this.isInnate = true;
        this.tags.add(CustomTags.DISK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void thrownUse() {
        super.thrownUse();
        addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void diskPreEffect(AbstractMonster m, AbstractSlashCard c) {
        m.loseBlock(this.magicNumber, false);
        c.baseDamage += this.magicNumber;
    }

    public void diskPostEffect(AbstractMonster m, AbstractSlashCard c) {
        c.baseDamage -= this.magicNumber;
    }

    public String getExDesc(int pos) { return EXTENDED_DESCRIPTION[pos];}

    public String getDesc() {
        return DESCRIPTION;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_M_N);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
