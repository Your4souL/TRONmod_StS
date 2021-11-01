package TRONmod.actions;

import TRONmod.powers.ParkourPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class DoubleNextTurnBlockAction extends AbstractGameAction {

    public DoubleNextTurnBlockAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
    }

    public void update() {
        if (this.target != null && this.target.hasPower("Next Turn Block"))
            addToTop(new ApplyPowerAction(this.target, this.source, new NextTurnBlockPower(this.target, (this.target.getPower("Next Turn Block")).amount), (this.target.getPower("Next Turn Block")).amount));
        this.isDone = true;
    }
}