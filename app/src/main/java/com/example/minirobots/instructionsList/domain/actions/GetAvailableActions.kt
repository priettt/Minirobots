package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.Action
import com.example.minirobots.instructionsList.domain.entities.UIAction
import com.example.minirobots.instructionsList.infrastructure.UIActionMapper
import javax.inject.Inject

class GetAvailableActions @Inject constructor(
    private val uiActionMapper: UIActionMapper
) {
    operator fun invoke(): List<UIAction> =
        Action.values().map {
            uiActionMapper.map(it)
        }

}
