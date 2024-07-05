import SwiftUI
import GoogleSignIn
import shared

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        KoinInitializerKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
            ContentView().onOpenURL { url in
                GIDSignIn.sharedInstance.handle(url)
            }
		}
	}
}
