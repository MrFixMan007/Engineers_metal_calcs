package domain.usecase.temperatureIngot

import domain.usecase.temperatureIngot.inputParam.TemperatureIngotInputParam
import domain.usecase.temperatureIngot.outputParam.TemperatureIngotOutputParam

interface TemperatureIngotUseCase : (TemperatureIngotInputParam) -> TemperatureIngotOutputParam