package com.ecospend.paybybank.data.remote.model.datalink

import com.ecospend.paybybank.data.remote.model.paylink.PayByBankCurrency
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FinancialReport(
    val filters: Filters,
    val parameters: FinancialReportParameters,
    val outputSettings: OutputSettings
)

data class OutputSettings(

    /**
     * Depending on the value, the PII (Personal Data) will be returned or
     * omitted from the response and the generated datalink pages. Default is 'false'
     */
    @SerializedName("display_pii")
    val displayPii: Boolean?
)

data class Filters(
    /**
     * Start date of the data which will be processed. Must be set the start date of the period you want.
     */
    @SerializedName("start_date")
    val startDate: String?,

    /**
     * Currency code in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     */
    val currency: PayByBankCurrency?
)

data class FinancialMultiParameters(
    /**
     * Set this property to 'true' if you want Financials data included in the response.
     */
    val financial: Boolean?
)

enum class AffordabilityType(val value: String) : Serializable {
    @SerializedName("MaxRent")
    MaxRent("MaxRent"),

    @SerializedName("MaxInstallments")
    MaxInstallments("MaxInstallments"),

    @SerializedName("GamblingLimit")
    GamblingLimit("GamblingLimit")
}

data class AffordabilityParameters(
    val type: List<AffordabilityType>?
)

data class VerificationParameters(

    /**
     * Provide a 'Name' for the account holder to verify agains AIS data.
     */
    val name: String?,

    /**
     * Provide an array of 'phone numbers' for the account holder to verify agains AIS data.
     */
    @SerializedName("phone_numbers")
    val phoneNumbers: List<String>?,

    /**
     * Provide an 'address' for the account holder to verify agains AIS data.
     */
    val address: String?,

    /**
     * Provide an 'email address' for the account holder to verify agains AIS data.
     */
    val email: String?
)

enum class DistrubutionPeriod(val value: String) : Serializable {
    @SerializedName("month")
    Month("month"),

    @SerializedName("quarter")
    Quarter("quarter"),

    @SerializedName("year")
    Year("year")
}

data class CategoryAggregationParameters(
    @SerializedName("distribution_period")
    val distributionPeriod: DistrubutionPeriod?
)

data class FinancialReportParameters(

    val affordability: List<AffordabilityParameters>?,
    val verification: VerificationParameters?,
    val financial: FinancialMultiParameters?,
    @SerializedName("category_aggregation")
    val categoryAggregation: CategoryAggregationParameters?
)
