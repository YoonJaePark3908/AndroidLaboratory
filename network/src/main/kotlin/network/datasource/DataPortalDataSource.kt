package network.datasource

import network.model.RespStockPriceInfo
import retrofit2.Response
interface DataPortalDataSource {
    suspend fun getStockPriceInfo(params: HashMap<String, String>): Response<RespStockPriceInfo>
}