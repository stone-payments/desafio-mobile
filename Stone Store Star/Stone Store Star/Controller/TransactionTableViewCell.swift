//
//  TransactionTableViewCell.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit

class TransactionTableViewCell: UITableViewCell {
    @IBOutlet weak var lastFourDigitsCardNumber: UILabel!
    @IBOutlet weak var holderName: UILabel!
    @IBOutlet weak var transactionValue: UILabel!
    @IBOutlet weak var transactionDate: UILabel!
    @IBOutlet weak var transactionTime: UILabel!
    @IBOutlet weak var valueViewBackground: UIView!
    var transaction: RTransaction?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
