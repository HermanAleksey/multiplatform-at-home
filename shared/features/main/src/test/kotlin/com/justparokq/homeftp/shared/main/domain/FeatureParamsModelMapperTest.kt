import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModelMapper
import com.justparokq.homeftp.shared.main.domain.FeatureToggle
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FeatureParamsModelMapperTest {
    private val mapper = FeatureParamsModelMapper()

    @Test
    fun `maps ftp feature correctly`() {
        val toggle = FeatureToggle(name = "ftp", isEnabled = true)
        val expected = FeatureParamsModel(
            name = "ftp",
            feature = ProjectFeature.FTP,
            isEnabled = true,
            imageUrl = null
        )
        val model = mapper.map(toggle)
        assertEquals(expected, model)
    }

    @Test
    fun `maps settings feature correctly`() {
        val toggle = FeatureToggle(name = "settings", isEnabled = false)
        val expected = FeatureParamsModel(
            name = "settings",
            feature = ProjectFeature.SETTINGS,
            isEnabled = false,
            imageUrl = null
        )
        val model = mapper.map(toggle)
        assertEquals(expected, model)
    }

    @Test
    fun `throws on unknown feature`() {
        val toggle = FeatureToggle(name = "unknown", isEnabled = true)
        assertFailsWith<IllegalStateException> {
            mapper.map(toggle)
        }
    }
}