import com.justparokq.ftp.mapper.FileResponseMapper
import com.justparokq.ftp.utils.FileSystemCommunicator
import com.justparokq.ftp.utils.PathProcessor
import com.justparokq.ftp.utils.PhotoProcessor
import com.justparokq.login.data.LoginRequestMapper
import com.justparokq.login.data.ProdRefreshTokenRepository
import com.justparokq.login.data.RefreshTokenRepository
import com.justparokq.login.data.TestRefreshTokenRepository
import com.justparokq.login.data.TestUserRepositoryImpl
import com.justparokq.login.data.TokenService
import com.justparokq.login.data.UserRepository
import com.justparokq.login.data.UserRepositoryImpl
import com.justparokq.routing.unauth.PROD_TOKEN_EXPIRATION_TIME
import com.justparokq.routing.unauth.REFRESH_TOKEN_EXPIRATION_TIME
import com.justparokq.routing.unauth.TEST_TOKEN_EXPIRATION_TIME

internal object Dependencies {

    private val loginRequestMapper = LoginRequestMapper()
    private val tokenService = TokenService()

    fun getTokenService(): TokenService {
        return tokenService
    }

    fun getUserRepository(isTest: Boolean): UserRepository {
        return if (isTest) {
            TestUserRepositoryImpl()
        } else {
            UserRepositoryImpl()
        }
    }

    private var ftpDependencies: FtpDependencies? = null
    fun getFtpDependencies(isTest: Boolean): FtpDependencies {
        ftpDependencies?.let { return it }

        val pathProcessor = if (isTest) {
            PathProcessor(
                rootPath = "./server/testStorageDirectory/",
                initialContentPath = "./server/testStorageDirectorySnapshot/"
            )
        } else {
            PathProcessor(rootPath = "./server/prodStorageDirectory/")
        }
        val communicator = FileSystemCommunicator(pathProcessor)
        val mapper = FileResponseMapper(pathProcessor)
        val photoProcessor = PhotoProcessor(pathProcessor)

        return FtpDependencies(pathProcessor, communicator, mapper, photoProcessor)
    }

    fun getLoginRequestMapper() = loginRequestMapper

    fun getTokenExpirationTime(isTest: Boolean): Long {
        return if (isTest) TEST_TOKEN_EXPIRATION_TIME else PROD_TOKEN_EXPIRATION_TIME
    }

    fun getRefreshTokenExpirationTime(isTest: Boolean): Long {
        // todo separate?
        return REFRESH_TOKEN_EXPIRATION_TIME
    }

    val prod: RefreshTokenRepository = ProdRefreshTokenRepository()
    val test: RefreshTokenRepository = TestRefreshTokenRepository()
    fun getRefreshTokenRepository(isTest: Boolean): RefreshTokenRepository =
        if (isTest) test else prod
}

internal class FtpDependencies(
    val pathProcessor: PathProcessor,
    val communicator: FileSystemCommunicator,
    val mapper: FileResponseMapper,
    val photoProcessor: PhotoProcessor
)