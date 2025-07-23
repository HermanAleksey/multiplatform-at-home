import SwiftUI
import shared

// Android Equivalent: Application class + MainActivity entry point
@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            RootView(root: appDelegate.root)
                .ignoresSafeArea(.all)
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    // Lazy initialization of RootComponent (after Koin is set up)
    lazy var root: RootComponent = {
        DefaultRootComponent(
            componentContext: DefaultComponentContext(
                lifecycle: ApplicationLifecycle()
            )
        )
    }()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Initialize Koin first
        let contextFactory = UtilsContextFactory()
        KoinStarterKt.startKoinImpl(contextFactory: contextFactory)

        // Now RootComponent can safely use Koin dependencies
        return true
    }
}