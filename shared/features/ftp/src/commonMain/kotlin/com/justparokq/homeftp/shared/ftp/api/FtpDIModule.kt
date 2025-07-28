package com.justparokq.homeftp.shared.ftp.api

import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapper
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapperImpl
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClient
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClientImpl
import com.justparokq.homeftp.shared.ftp.presentation.component.DefaultFtpExplorerComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.InitHelper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ftpModule = module {
    factory<FtpCommunicationHttpClient> { FtpCommunicationHttpClientImpl(httpClient = get()) }
    factory<FileSystemObjectMapper> { FileSystemObjectMapperImpl(urlResolver = get()) }
    factory { InitHelper() }
    factoryOf(::DefaultFtpExplorerComponent) bind FtpExplorerComponent::class
}