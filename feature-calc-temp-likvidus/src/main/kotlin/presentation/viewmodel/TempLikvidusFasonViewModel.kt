package presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculation.Round
import data.model.CalcSave
import data.model.CalcType
import data.model.TypeEnum
import data.model.container.TempLikvidusFasonContainer
import domain.usecase.saveCalc.SaveCalcUseCase
import domain.usecase.temperatureFason.TemperatureFasonUseCase
import domain.usecase.temperatureFason.inputParam.TemperatureFasonInputParam
import domain.usecase.temperatureFason.outputParam.TemperatureFasonOutputParam

class TempLikvidusFasonViewModel(
    private val calcUseCase: TemperatureFasonUseCase,
    private val saveCalcUseCase: SaveCalcUseCase,
    private val countRound: Int,
) : ViewModel() {

    private val _resultLiveMutable = MutableLiveData<TemperatureFasonOutputParam>()
    val resultLive: LiveData<TemperatureFasonOutputParam> = _resultLiveMutable

    private fun calc(param: TemperatureFasonInputParam){
        val res = Round.invoke(calcUseCase.invoke(param), countRound)
        _resultLiveMutable.value = TemperatureFasonOutputParam(
            res = res.res,
            resLower = res.resLower,
            resUpper = res.resUpper,
            resLowerInFurnace = res.resLowerInFurnace,
            resUpperInFurnace = res.resUpperInFurnace,
        )
    }

    private suspend fun save(param: TemperatureFasonInputParam, name: String, description: String) : Boolean{
        val result = Round.invoke(calcUseCase.invoke(param), countRound)

        return saveCalcUseCase.invoke(
            CalcSave(
            type = CalcType(TypeEnum.TemperatureFason),
            name = name,
            description = description,
            container = TempLikvidusFasonContainer(
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
    suspend fun send(event: TempLikvidusFasonEvent) : Boolean{
        when (event){
            is SaveCalcTempLikvidFasonEvent -> {
                return save(event.param, event.name, event.description)
            }
            is CalcFasonEvent -> {
                calc(param = event.param)
            }
        }
        return false
    }
}