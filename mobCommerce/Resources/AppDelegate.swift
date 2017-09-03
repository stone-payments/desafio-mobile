//
//  AppDelegate.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 21/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import IQKeyboardManager
import RealmSwift

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var purchaseItems: [Purchase] = []

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        IQKeyboardManager.shared().isEnabled = true
        self.setApperance()
        self.setDefaultRealm()
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {  }

    func applicationDidEnterBackground(_ application: UIApplication) { }

    func applicationWillEnterForeground(_ application: UIApplication) { }

    func applicationDidBecomeActive(_ application: UIApplication) { }

    func applicationWillTerminate(_ application: UIApplication) { }
    
}

// MARK: - Private methods -

extension AppDelegate {
    
    fileprivate func setApperance() {

        let navigationBarAppearance = UINavigationBar.appearance()
        navigationBarAppearance.tintColor = UIColor.white
        navigationBarAppearance.barTintColor = Colors.navigationBar.color
        navigationBarAppearance.isTranslucent = false
        navigationBarAppearance.titleTextAttributes = [NSForegroundColorAttributeName:UIColor.white,
                                                       NSFontAttributeName:UIFont(name:"HelveticaNeue-CondensedBold" , size:20)!]
        
        let tabBarAppearance = UITabBar.appearance()
        tabBarAppearance.barTintColor = Colors.tabBar(type: .background).color
        tabBarAppearance.tintColor = Colors.tabBar(type: .selected).color
        
        if #available(iOS 10.0, *) {
            tabBarAppearance.unselectedItemTintColor = Colors.tabBar(type: .unselected).color
        }
    }
    
    fileprivate func setDefaultRealm() {
        var config = Realm.Configuration()
        config.fileURL = config.fileURL!.deletingLastPathComponent().appendingPathComponent("mobCommerceData.realm")
        Realm.Configuration.defaultConfiguration = config
        
        print(">>> REALM PATH: \(String(describing: Realm.Configuration.defaultConfiguration.fileURL))")
    }
}
