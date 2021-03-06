package TRONmod.cards;

import TRONmod.actions.AirAssaultAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import TRONmod.TRONMod;
import TRONmod.characters.TheANON;

import static TRONmod.TRONMod.makeCardPath;

public class AirAssault extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(AirAssault.class.getSimpleName());
    public static final String IMG = makeCardPath("AirAssault.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = -1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int PARKOUR_DAMAGE = 2;
    private static final int UPGRADE_PLUS_PARKOUR_DMG = 1;

    public AirAssault() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        isMultiDamage = true;

        this.baseMagicNumber = PARKOUR_DAMAGE;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = EnergyPanel.totalCount;
        this.damage += this.secondMagicNumber;
        calculateCardDamage(m);
        addToBot(new AirAssaultAction(p, this.damage, this.damageTypeForTurn, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
    }

    public void applyPowers() {
        AbstractPower parkour = AbstractDungeon.player.getPower("Parkour");
        if (parkour != null) this.secondMagicNumber = (parkour.amount / 5) * this.magicNumber;
        else this.secondMagicNumber = 0;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.secondMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower parkour = AbstractDungeon.player.getPower("Parkour");
        if (parkour != null) this.secondMagicNumber = (parkour.amount / 5) * this.magicNumber;
        else this.secondMagicNumber = 0;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.secondMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_PARKOUR_DMG);
            initializeDescription();
        }
    }
}