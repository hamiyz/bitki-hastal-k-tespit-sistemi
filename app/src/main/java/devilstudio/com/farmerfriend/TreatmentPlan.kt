package devilstudio.com.farmerfriend

data class TreatmentPlan(
    val diseaseName: String,
    val riskLevel: String,
    val summary: String,
    val steps: ArrayList<String>
)