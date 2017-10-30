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
    
    static let segueDetails = "showProductDetails"
    
    var selectedProduct: Product?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.sections = [
            ProductListSection(parentOutput: self)
        ]
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == ProductListViewController.segueDetails {
            
//            if let destination = segue.destination as? ProductDetailsController,
//                let product = self.selectedProduct {
//                
//                destination.product = product
//            }
        }
    }
}

extension ProductListViewController: ProductListOutput {
    
    func didSelect(product: Product) {
        
//        self.selectedProduct = product
//        performSegue(withIdentifier: ProductListViewController.segueDetails, sender: self)
    }
}
