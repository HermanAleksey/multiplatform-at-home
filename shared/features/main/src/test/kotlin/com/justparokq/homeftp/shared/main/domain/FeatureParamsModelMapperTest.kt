import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModelMapper
import com.justparokq.homeftp.shared.main.domain.FeatureToggle
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import kotlin.test.Test
import kotlin.test.assertEquals

class FeatureParamsModelMapperTest {
    private val mapper = FeatureParamsModelMapper()

    @Test
    fun `maps ftp feature correctly`() {
        val toggle = FeatureToggle(key = Setting.FeatureKey.Ftp, isEnabled = true)
        val expected = FeatureParamsModel(
            key = Setting.FeatureKey.Ftp,
            feature = ProjectFeature.FTP,
            isEnabled = true,
            imageUrl = null
        )
        val model = mapper.map(toggle)
        assertEquals(expected, model)
    }

    @Test
    fun `maps settings feature correctly`() {
        val toggle = FeatureToggle(key = Setting.FeatureKey.Settings, isEnabled = false)
        val expected = FeatureParamsModel(
            key = Setting.FeatureKey.Settings,
            feature = ProjectFeature.SETTINGS,
            isEnabled = false,
            imageUrl = null
        )
        val model = mapper.map(toggle)
        assertEquals(expected, model)
    }
} 