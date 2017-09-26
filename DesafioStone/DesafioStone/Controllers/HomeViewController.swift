//
//  ViewController.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController {
  
  
  @IBOutlet weak var homeLabel: UILabel!
  @IBOutlet weak var transactionsButton: UIButton!
  @IBOutlet weak var shopButton: UIButton!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    UIApplication.shared.isStatusBarHidden = false
    UIApplication.shared.statusBarStyle = .lightContent
    
    self.homeLabel.alpha = 0.0
    self.transactionsButton.alpha = 0.0
    self.shopButton.alpha = 0.0
  }

  override func viewWillAppear(_ animated: Bool) {
    UIView.animate(withDuration: 1.0, delay: 0.5, options: .curveEaseOut, animations: {
      self.homeLabel.alpha = 1.0
      self.transactionsButton.alpha = 1.0
      self.shopButton.alpha = 1.0
    }, completion: nil)
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }


}

