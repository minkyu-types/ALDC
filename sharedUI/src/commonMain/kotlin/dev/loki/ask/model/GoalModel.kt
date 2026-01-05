package dev.loki.ask.model

import dev.loki.domain.type.Category
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class GoalModel(
    val id: Int,
    val title: String,
    val category: Category,
    val registeredDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val endDate: LocalDate,
    val mileStones: List<MilestoneModel> = emptyList(),
    val receiveNotification: Boolean,
) {
    /**
     * Goal의 전체 진행률을 계산합니다.
     * - 완료된 Milestone: 전체 비율만큼 추가
     * - 미완료 Milestone: (전체 비율 * Milestone의 progress) 만큼 추가
     * 
     * @return 0.0 ~ 100.0 범위의 진행률
     */
    val totalProgress: Float
        get() {
            if (mileStones.isEmpty()) return 0f
            
            val progressPerMilestone = 100f / mileStones.size
            
            return mileStones.sumOf { milestone ->
                if (milestone.isComplete) {
                    progressPerMilestone.toDouble()
                } else {
                    (progressPerMilestone * milestone.progress / 100f).toDouble()
                }
            }.toFloat()
        }
}
