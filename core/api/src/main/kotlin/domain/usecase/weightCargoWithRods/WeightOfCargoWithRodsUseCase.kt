package domain.usecase.weightCargoWithRods

import domain.model.weight.Weight
import domain.usecase.weightCargoWithRods.inputParam.WeightOfCargoWithRodsParam

interface WeightOfCargoWithRodsUseCase : (WeightOfCargoWithRodsParam) -> Weight