package TRONmod.cards;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }

    public void thrownUse() {}

    public void diskPreEffect(AbstractMonster m, AbstractSlashCard c) {}

    public void diskPostEffect(AbstractMonster m, AbstractSlashCard c) {}

    public String getExDesc(int pos) {
        return languagePack.getCardStrings(String.valueOf(this)).EXTENDED_DESCRIPTION[pos];
    }

    public String getDesc() {
        return languagePack.getCardStrings(String.valueOf(this)).DESCRIPTION;
    }
}