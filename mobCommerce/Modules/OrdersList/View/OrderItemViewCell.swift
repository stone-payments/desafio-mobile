//
//  OrderItemViewCell.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

class OrderItemViewCell: UITableViewCell {
    
    @IBOutlet weak var orderDate: UILabel!
    @IBOutlet weak var orderValue: UILabel!
    @IBOutlet weak var orderHolderName: UILabel!
    @IBOutlet weak var orderCreditCardNumber: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
}
