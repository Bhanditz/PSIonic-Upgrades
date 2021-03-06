package wiresegal.psionup.common.spell.operator.block

import net.minecraft.util.EnumFacing
import vazkii.psi.api.internal.Vector3
import vazkii.psi.api.spell.Spell
import vazkii.psi.api.spell.SpellContext
import vazkii.psi.api.spell.SpellParam
import vazkii.psi.api.spell.SpellRuntimeException
import vazkii.psi.api.spell.param.ParamVector
import vazkii.psi.api.spell.piece.PieceOperator
import wiresegal.psionup.common.lib.LibMisc
import wiresegal.psionup.api.BlockProperties

/**
 * @author WireSegal
 * Created at 2:29 PM on 5/13/17.
 */
class PieceOperatorGetComparatorStrength(spell: Spell) : BasePieceOperatorProperties<Double>(spell) {

    private lateinit var axis: SpellParam

    override fun initParams() {
        super.initParams()
        axis = ParamVector("psi.spellparam.ray", SpellParam.BLUE, true, false)
        addParam(axis)
    }

    override fun getData(context: SpellContext, properties: BlockProperties): Double? {
        val direction = getParamValue<Vector3?>(context, axis)
        if (direction != null && (!direction.isAxial && direction.magSquared() != 0.0))
            throw SpellRuntimeException("${LibMisc.MOD_ID}.spellerror.nonaxial")
        val facing = if (direction == null || direction.magSquared() == 0.0) EnumFacing.UP else
                EnumFacing.getFacingFromVector(direction.x.toFloat(), direction.y.toFloat(), direction.z.toFloat())
        return properties.comparatorOutput(facing).toDouble()
    }

    override fun getEvaluationType() = Double::class.javaObjectType
}
