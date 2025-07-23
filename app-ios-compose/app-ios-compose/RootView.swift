import SwiftUI
import shared

struct RootView: UIViewControllerRepresentable {
    let root: RootComponent
    
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = RootViewControllerKt.rootViewController(rootComponent: root)
        // controller.overrideUserInterfaceStyle = .light
        
        // Wrap in NavigationController to enable swipe-back gesture
        let navigationController = UINavigationController(rootViewController: controller)
        navigationController.setNavigationBarHidden(true, animated: false)
        navigationController.interactivePopGestureRecognizer?.isEnabled = true
        
        return navigationController
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
