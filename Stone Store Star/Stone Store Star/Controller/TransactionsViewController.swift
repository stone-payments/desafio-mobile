//
//  TransactionViewController.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit

class TransactionsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var transactionsTableView: UITableView!
    let dataHelper = DataHelper()
    
    func numberOfSections(in tableView: UITableView) -> Int {
        let transactions = dataHelper.getAllTransactions()
        return transactions.count
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let transactions = dataHelper.getAllTransactions()
        let cell = tableView.dequeueReusableCell(withIdentifier: "transactionCell", for: indexPath) as! TransactionTableViewCell
        let transaction = transactions[indexPath.section]
        cell.holderName.text = transaction.card?.holder_name
        // Show only the last four digits
        // To do: Check if save only four digits is a rule.
        cell.lastFourDigitsCardNumber.text = "\((transaction.card?.number.suffix(4))!)"
        
        // To do: Create a helper function to format price values.
        let price = transaction.value
        var strPrice = String(price)
        strPrice.insert(".", at: strPrice.index(strPrice.endIndex, offsetBy: -2))
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = NSLocale(localeIdentifier: "pt_BR") as Locale!
        let priceText = formatter.string(from: NSNumber.init(floatLiteral: Double(strPrice)!))
        
        cell.transactionValue.text = String(priceText!)
        
        // To do: Creat helper functions to convert string into date and the opposite
        let calendar = Calendar.current
        let minute = calendar.component(.minute, from: transaction.created_at)
        let hour = calendar.component(.hour, from: transaction.created_at)
        let day = calendar.component(.day, from: transaction.created_at)
        let month = calendar.component(.month, from: transaction.created_at)
        let year = calendar.component(.year, from: transaction.created_at)
        cell.transactionDate.text = "\(day)/\(month)/\(year)"
        cell.transactionTime.text = "\(hour):\(minute)"
        cell.layer.cornerRadius = 6.0
        cell.clipsToBounds = true
        
        let maskPath = UIBezierPath.init(roundedRect: cell.valueViewBackground.bounds,
                                         byRoundingCorners: UIRectCorner.bottomLeft,
                                         cornerRadii: CGSize.init(width: 6.0, height: 6.0))
        let maskLayer = CAShapeLayer()
        maskLayer.frame = cell.valueViewBackground.bounds
        maskLayer.path = maskPath.cgPath
        cell.valueViewBackground.layer.mask = maskLayer
        
        return cell
    }
    
    // Margin between the rows(headers)
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 14.0
    }

    // Just clearing the background color
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView()
        view.backgroundColor = UIColor.clear
        return view
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        // Reload data from transaction table view, considering
        //the block of alamofire async task
        group.notify(queue: .main) {
            self.transactionsTableView.reloadData()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
