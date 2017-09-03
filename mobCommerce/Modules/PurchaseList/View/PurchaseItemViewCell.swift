//
//  PurchaseItemViewCell.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 28/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

class PurchaseItemViewCell: UITableViewCell {
    
    @IBOutlet weak var itemDescription: UILabel!
    @IBOutlet weak var itemPrice: UILabel!
    @IBOutlet weak var itemTotal: UILabel!
    @IBOutlet weak var itemImage: UIImageView!
    @IBOutlet weak var deleteButton: UIButton!
    @IBOutlet weak var itemQuantity: UITextField!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.deleteButton.tintColor = Colors.buttonRemoveItem.color
    }
}
