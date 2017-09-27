//
//  TransactionsViewController.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit

class TransactionsViewController: UIViewController {
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    // Do any additional setup after loading the view.
  }
  
  override func viewWillAppear(_ animated: Bool) {
    UIApplication.shared.statusBarStyle = .default
  }
  
  @IBAction func closeButtonTouched() {
    self.dismiss(animated: true, completion: nil)
  }
  
}
