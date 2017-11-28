//
//  ProductCollectionViewCell.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit

class ProductCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var priceLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var sellerLabel: UILabel!
    @IBOutlet weak var cartButton: UIButton!
    var product: RProduct?
    
    @IBAction func addOrRemoveItemOfOrder(_ sender: Any) {
        let dataHelper = DataHelper()

        if self.cartButton.titleLabel?.text == "       Adicionar"{
            self.cartButton.setTitle("Remover", for: .normal)
            self.cartButton.backgroundColor = self.cartButton.titleLabel?.textColor
            self.cartButton.setTitleColor(UIColor.white, for: .normal)
            
            dataHelper.addToOpenedOrder(product: self.product!)
        }else{
            self.cartButton.setTitle("       Adicionar", for: .normal)
            self.cartButton.setTitleColor(self.cartButton.backgroundColor, for: .normal)
            self.cartButton.backgroundColor = UIColor.clear
            
            dataHelper.removeFromOpenedOrder(product: self.product!)
        }
    }
}
