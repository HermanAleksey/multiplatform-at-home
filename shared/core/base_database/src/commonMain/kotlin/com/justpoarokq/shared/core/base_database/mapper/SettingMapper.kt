package com.justpoarokq.shared.core.base_database.mapper

import com.justpoarokq.shared.core.base_database.entity.BooleanSettingEntity
import com.justpoarokq.shared.core.base_database.entity.SettingCategory
import com.justpoarokq.shared.core.base_database.entity.StringSettingEntity
import com.justpoarokq.shared.core.base_database.api.SettingModel

internal class SettingMapper {

    fun toBooleanSettingEntity(setting: SettingModel): BooleanSettingEntity {
        val booleanValue = (setting.value as? SettingModel.Value.Boolean)?.value
            ?: error("Couldn't map")

        return BooleanSettingEntity(
            name = setting.name,
            description = setting.description,
            category = setting.category.toEntityCategory(),
            value = booleanValue
        )
    }

    fun toStringSettingEntity(setting: SettingModel): StringSettingEntity {
        val stringValue = (setting.value as? SettingModel.Value.String)?.value
            ?: error("Couldn't map")

        return StringSettingEntity(
            name = setting.name,
            description = setting.description,
            category = setting.category.toEntityCategory(),
            value = stringValue
        )
    }

    fun toSettingModel(entity: BooleanSettingEntity): SettingModel {
        return SettingModel(
            name = entity.name,
            description = entity.description,
            category = entity.category.toModelCategory(),
            value = SettingModel.Value.Boolean(entity.value)
        )
    }

    fun toSettingModel(entity: StringSettingEntity): SettingModel {
        return SettingModel(
            name = entity.name,
            description = entity.description,
            category = entity.category.toModelCategory(),
            value = SettingModel.Value.String(entity.value)
        )
    }

    private fun SettingCategory.toModelCategory(): SettingModel.Category {
        return when (this) {
            SettingCategory.Network -> SettingModel.Category.Network
            SettingCategory.Feature -> SettingModel.Category.Feature
            else -> throw IllegalArgumentException("Unknown category: $this")
        }
    }

    private fun SettingModel.Category.toEntityCategory(): SettingCategory {
        return when (this) {
            SettingModel.Category.Network -> SettingCategory.Network
            SettingModel.Category.Feature -> SettingCategory.Feature
        }
    }
}