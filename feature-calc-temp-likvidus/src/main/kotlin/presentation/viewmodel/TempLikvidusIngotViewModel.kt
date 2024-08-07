package presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculation.Round
import data.model.CalcSave
import data.model.CalcType
import data.model.TypeEnum
import data.model.container.TempLikvidusIngotContainer
import domain.usecase.saveCalc.SaveCalcUseCase
import domain.usecase.temperatureIngot.TemperatureIngotUseCase
import domain.usecase.temperatureIngot.inputParam.TemperatureIngotInputParam
import domain.usecase.temperatureIngot.outputParam.TemperatureIngotOutputParam

class TempLikvidusIngotViewModel (
    private val calcUseCase: TemperatureIngotUseCase,
    private val saveCalcUseCase: SaveCalcUseCase,
    private val countRound: Int,
) : ViewModel() {

    private val _resultLiveMutable = MutableLiveData<TemperatureIngotOutputParam>()
    val resultLive: LiveData<TemperatureIngotOutputParam> = _resultLiveMutable

    private fun calc(param: TemperatureIngotInputParam){
        val res = Round.invoke(calcUseCase.invoke(param), countRound)
        _resultLiveMutable.value = TemperatureIngotOutputParam(
            res = res.res,
            resLower = res.resLower,
            resUpper = res.resUpper,
            resLowerInFurnace = res.resLowerInFurnace,
            resUpperInFurnace = res.resUpperInFurnace,
        )
    }

    private suspend fun save(param: TemperatureIngotInputParam, name: String, description: String) : Boolean{
        val result = Round.invoke(calcUseCase.invoke(param), countRound)

        return saveCalcUseCase.invoke(
            CalcSave(
                type = CalcType(TypeEnum.TemperatureIngot),
                name = name,
                description = description,
                container = TempLikvidusIngotContainer(
                    w = param.w,
                    cr = param.cr,
                    co = param.co,
                    mo = param.mo,
                    v = param.v,
                    al = param.al,
                    ni = param.ni,
                    mn = param.mn,
                    cu = param.cu,
                    si = param.si,
                    ti = param.ti,
                    s = param.s,
                    p = param.p,
                    c = param.c,
                    res = result.res
                )
            )
        )
    }
    suspend fun send(event: TempLikvidusIngotEvent) : Boolean{
        when (event){
            is SaveCalcTempLikvidIngotEvent -> {
                return save(event.param, event.name, event.description)
            }
            is CalcIngotEvent -> {
                calc(param = event.param)
            }
        }
        return false
    }
}