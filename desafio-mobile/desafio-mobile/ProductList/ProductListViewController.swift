//
//  ProductListViewController.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit
import SDWebImage


class ProductListViewController: TableViewController {
    
    var selectedProduct: Product?
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.sections = [
            ProductListSection(parentOutput: self)
        ]
    }
}
