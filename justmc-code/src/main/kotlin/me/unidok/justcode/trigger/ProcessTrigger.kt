package me.unidok.justcode.trigger

import me.unidok.justcode.operation.Operation
import me.unidok.justcode.value.ItemValue
import me.unidok.justcode.value.LocalizedText
import me.unidok.justcode.value.parameter.Parameter

class ProcessTrigger(
    operations: List<Operation>,
    name: String,
    parameters: MutableList<Parameter> = mutableListOf(),
    displayName: LocalizedText? = null,
    displayDescription: LocalizedText? = null,
    isHidden: Boolean = false,
    icon: ItemValue? = null,
) : CallableTrigger(
    Type.PROCESS,
    operations, name, parameters,
    displayName, displayDescription,
    isHidden, icon
)