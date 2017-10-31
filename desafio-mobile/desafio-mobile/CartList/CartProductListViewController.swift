//
//  CartProductListViewController.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit
import SDWebImage


class CartProductListViewController: TableViewController {
    
    static let segueDetails = "showProductDetails"
    
    var selectedProduct: Product?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.sections = [
            CartProductListSection(parentOutput: self)
        ]
    }
}
