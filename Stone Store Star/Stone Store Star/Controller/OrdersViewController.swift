//
//  OrdersViewController.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit

class OrdersViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var totalLabel: UILabel!
    let dataHelper = DataHelper()
    
    func numberOfSections(in tableView: UITableView) -> Int {
        let order = dataHelper.getOpenedOrder()
        return order.products.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 14.0
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView()
        view.backgroundColor = UIColor.clear
        return view
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let order = dataHelper.getOpenedOrder()
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "orderCell", for: indexPath) as! OrderTableViewCell
        
        cell.product = order.products[indexPath.section]
        
        let price = (cell.product?.price)!
        var strPrice = String(price)
        if strPrice.count > 2 {
            strPrice.insert(".", at: strPrice.index(strPrice.endIndex, offsetBy: -2))
        }
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = NSLocale(localeIdentifier: "pt_BR") as Locale!
        let priceText = formatter.string(from: NSNumber.init(floatLiteral: Double(strPrice)!))
        
        let url = URL(string: (cell.product?.thumbnailHd)!)!
        cell.productImage.af_setImage(withURL: url, placeholderImage: UIImage(named: "background")!)
        cell.productValueLabel.text = "\(String(describing: priceText!))"
        cell.productNameLabel.text = cell.product?.title
        cell.productSellerLabel.text = cell.product?.seller
        cell.quantityLabel.text = "x \(cell.quantity)"
        
        let totalPrice = (cell.product?.price)!*cell.quantity
        strPrice = String(describing: totalPrice)
        strPrice.insert(".", at: strPrice.index(strPrice.endIndex, offsetBy: -2))
        let TotalPriceText = formatter.string(from: NSNumber.init(floatLiteral: Double(strPrice)!))
        cell.totalLabel.text = "\(String(describing: TotalPriceText!))"
        cell.layer.cornerRadius = 6.0
        cell.clipsToBounds = true
        
        return cell
    }
    
    @IBAction func stonePayment(_ sender: Any) {
        let order = dataHelper.getOpenedOrder()
        
        if order.products.count > 0 {
            dataHelper.closeOrder()
            let alert = UIAlertController(title: "Stone Payment", message: "Transação aprovada.", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: {action in self.performSegue(withIdentifier: "fromOrderToTransactions", sender: self)}))
            self.present(alert, animated: true, completion: nil)
            //        DispatchQueue.main.async {
            //        self.performSegue(withIdentifier: "fromOrderToProducts", sender: self)
//            self.performSegue(withIdentifier: "fromOrderToTransactions", sender: self)
            //        }
            //        if transactionCreated {
            //        performSegue(withIdentifier: "fromOrderToProducts", sender: self)
            //        }
            //        let transactionViewController = TransactionsViewController()
            //        present(transactionViewController, animated: false)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.calculateTotalPrice()
    }
    
    func calculateTotalPrice(){
        let order = dataHelper.getOpenedOrder()

        var totalPrice = 0
        for product in order.products {
            totalPrice += product.price
        }
        
        var strPrice = String(totalPrice)
        if strPrice.count > 2 {
            strPrice.insert(".", at: strPrice.index(strPrice.endIndex, offsetBy: -2))
        }
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = NSLocale(localeIdentifier: "pt_BR") as Locale!
        let priceText = formatter.string(from: NSNumber.init(floatLiteral: Double(strPrice)!))
        totalLabel.text = "\(String(describing: priceText!))"
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
