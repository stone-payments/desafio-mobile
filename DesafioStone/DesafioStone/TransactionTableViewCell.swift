//
//  TransactionTableViewCell.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 28/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit

class TransactionTableViewCell: UITableViewCell {
  
  @IBOutlet weak var amountLabel: UILabel!
  @IBOutlet weak var cardHolderNameLabel: UILabel!
  @IBOutlet weak var cardNumberLabel: UILabel!
  @IBOutlet weak var transactionDateLabel: UILabel!
  
  override func awakeFromNib() {
    super.awakeFromNib()
    // Initialization code
  }
  
  override func setSelected(_ selected: Bool, animated: Bool) {
    super.setSelected(selected, animated: animated)
    
    // Configure the view for the selected state
  }
  
}
