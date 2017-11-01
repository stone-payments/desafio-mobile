//
//  TransactionSection.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit

class TransactionSection: Section {
    
    override func heightForCell(atIndexPath indexPath: IndexPath) -> CGFloat {
        return UIScreen.main.bounds.size.height * 0.20
    }
    
    override func tableViewCell() -> UITableViewCell.Type {
        return TransactionCell.self
    }
    
    override func willDisplayCell(_ cell: UITableViewCell, atIndexPath indexPath: IndexPath) {
        
        guard let _cell = cell as? TransactionCell,
            let transaction = self.items[indexPath.row] as? Transaction else { return }
        
        _cell.setup(transaction: transaction)
    }
}
