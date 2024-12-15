package com.justparokq.homeftp.shared.main.presentation

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.MainScreenModel

interface MainComponent {

    val state: Value<MainScreenModel>

    fun onInitialize()

    fun onFeatureClicked(feature: FeatureParamsModel)
}
