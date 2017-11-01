//
//  TransactionListViewController.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit
import SDWebImage


class TransactionListViewController: TableViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.sections = [
            TransactionListSection(parentOutput: self)
        ]
    }
}
