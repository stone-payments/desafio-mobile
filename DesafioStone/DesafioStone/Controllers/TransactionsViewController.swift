//
//  TransactionsViewController.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit
import CoreData

class TransactionsViewController: UIViewController {
  
  var transactions: [NSManagedObject]?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
  }
  
  override func viewWillAppear(_ animated: Bool) {
    UIApplication.shared.statusBarStyle = .default
    
    guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
        return
    }
    
    let managedContext = appDelegate.persistentContainer.viewContext
    let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Transaction")
    
    do {
      transactions = try managedContext.fetch(fetchRequest)
    } catch let error as NSError {
      print("Could not fetch. \(error), \(error.userInfo)")
    }
  }
  
  @IBAction func closeButtonTouched() {
    self.dismiss(animated: true, completion: nil)
  }
  
}

extension TransactionsViewController: UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let transactionsList = self.transactions else {
      return UITableViewCell()
    }
    
    let cell = tableView.dequeueReusableCell(withIdentifier: "transactionCell") as! TransactionTableViewCell
    cell.amountLabel.text = transactionsList[indexPath.row].value(forKey: "amount") as? String
    cell.cardHolderNameLabel.text = transactionsList[indexPath.row].value(forKey: "card_holder_name") as? String
    
    let requestDate = transactionsList[indexPath.row].value(forKey: "date") as? Date
    cell.transactionDateLabel.text = requestDate?.fmtdDate(format: "dd/MM/yy à's' HH:mm")
    
    if let cardFullNumber = transactionsList[indexPath.row].value(forKey: "card_last_digits") as? String {
      cell.cardNumberLabel.text = "**** **** **** \(cardFullNumber.suffix(4))"
    }
    
    return cell
  }
  
  func numberOfSections(in tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    guard let transactionsList = self.transactions else {
      return 0
    }
    return transactionsList.count
  }
}
