//
//  PurchaseListTableViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 28/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import AlamofireImage

class PurchaseListTableViewController: UITableViewController {
    
    fileprivate var purchaseItems: [Purchase] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        purchaseItems = (UIApplication.shared.delegate as! AppDelegate).purchaseItems
        self.tableView.reloadData()
    }
}

// MARK: - UITableView -

extension PurchaseListTableViewController {
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
    
        var numberOfSections: Int = 0
        
        if purchaseItems.count > 0 {
            tableView.separatorStyle = .singleLine
            tableView.backgroundView = nil
            numberOfSections = 1
        }
        else {
            let emptyLabel: UILabel = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            emptyLabel.text = "Carrinho vazio"
            emptyLabel.textColor = UIColor.darkGray
            emptyLabel.textAlignment = .center
            emptyLabel.font = UIFont(name: "HelveticaNeue", size: 16)
            tableView.backgroundView = emptyLabel
            tableView.separatorStyle = .none
        }
        
        return numberOfSections
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return purchaseItems.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "TableViewCell", for: indexPath) as! PurchaseItemViewCell
        
        let item: Purchase = purchaseItems[indexPath.row]
        
        cell.itemDescription.text = item.title
        cell.itemPrice.text = item.price.toCurrencyString
        cell.itemTotal.text = item.total.toCurrencyString
        cell.itemQuantity.text = "\(item.quantity)"
        cell.itemImage.af_setImage(withURL: URL(string: item.thumbnail)!)
        
        return cell
    }
    
}
