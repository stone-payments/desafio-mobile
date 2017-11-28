//
//  TransactionViewController.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit
import RealmSwift

class TransactionsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var transactionsTableView: UITableView!
    
    func numberOfSections(in tableView: UITableView) -> Int {
        let realm = try! Realm()
        return realm.objects(RTransaction.self).count
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let realm = try! Realm()
        let transactions = realm.objects(RTransaction.self)
        let cell = tableView.dequeueReusableCell(withIdentifier: "transactionCell", for: indexPath) as! TransactionTableViewCell
        let transaction = transactions[indexPath.section]
        cell.holderName.text = transaction.card?.holder_name
        cell.lastFourDigitsCardNumber.text = "\((transaction.card?.number.suffix(4))!)"
        
        let price = transaction.value
        var strPrice = String(price)
        strPrice.insert(".", at: strPrice.index(strPrice.endIndex, offsetBy: -2))
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = NSLocale(localeIdentifier: "pt_BR") as Locale!
        let priceText = formatter.string(from: NSNumber.init(floatLiteral: Double(strPrice)!))
        
        cell.transactionValue.text = String(priceText!)
        
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
        
//        UIBezierPath *maskPath = [UIBezierPath bezierPathWithRoundedRect:self.viewOutlet.bounds byRoundingCorners:(UIRectCornerTopLeft | UIRectCornerBottomLeft | UIRectCornerBottomRight) cornerRadii:CGSizeMake(10.0, 10.0)];
//
//        CAShapeLayer *maskLayer = [[CAShapeLayer alloc] init];
//        maskLayer.frame = self.view.bounds;
//        maskLayer.path  = maskPath.CGPath;
        
        let maskPath = UIBezierPath.init(roundedRect: cell.valueViewBackground.bounds,
                                         byRoundingCorners: UIRectCorner.bottomLeft,
                                         cornerRadii: CGSize.init(width: 6.0, height: 6.0))
        let maskLayer = CAShapeLayer()
        maskLayer.frame = cell.valueViewBackground.bounds
        maskLayer.path = maskPath.cgPath
        cell.valueViewBackground.layer.mask = maskLayer
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 14.0
    }
    
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

        group.notify(queue: .main) {
            self.transactionsTableView.reloadData()
        }
        
//        DispatchQueue.main.async {
//            self.transactionsTableView.reloadData()
//        }
        print("should reload the transactions")
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
