package TRONmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractCreature.class, method = "addBlock")
public class MonsterAddBlockPatch {
    @SpireInsertPatch(rloc = 9,localvars = {"tmp"})
    public static SpireReturn<Void> Insert(AbstractCreature instance, float tmp) {
        if (!instance.isPlayer) {
            for (AbstractPower p : instance.powers)
                p.onGainedBlock(tmp);
        }
        return SpireReturn.Continue();
    }
}
