//
//  HistoryViewController.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 28/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import UIKit

class HistoryViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    // Outlets
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set tableView data source and delegate as self
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: Table View Data Source
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return TransactionsManager.sharedInstance.transactions.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier2", for: indexPath) as! HistoryTableViewCell
        cell.name.text = "Nome: \(TransactionsManager.sharedInstance.transactions[indexPath.row].cardholderName!)"
        cell.price.text = "Preço: \(priceToCurrency(price: TransactionsManager.sharedInstance.transactions[indexPath.row].value!))"
        cell.dateTime.text = "Data: \(TransactionsManager.sharedInstance.transactions[indexPath.row].dateTime!)"
        cell.last4digits.text = "Últimos Dígitos Cartão: \(TransactionsManager.sharedInstance.transactions[indexPath.row].last4Digits!)"
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
