//
//  TabBarController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

class TabBarController: UITabBarController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        let root = self.viewControllers![self.selectedIndex] as! UINavigationController
        root.popToRootViewController(animated: false)
    }
}
