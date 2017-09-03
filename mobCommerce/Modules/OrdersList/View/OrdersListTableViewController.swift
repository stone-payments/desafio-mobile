//
//  OrdersListTableViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import SVProgressHUD

class OrdersListTableViewController: UITableViewController {
    
    @IBOutlet weak var refresh: UIRefreshControl!
    
    var presenter: OrdersListPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
        
        self.refresh.backgroundColor = Colors.refreshControl.color
        self.refresh.attributedTitle = NSAttributedString(string: "Puxe para atualizar", attributes: [NSFontAttributeName: UIFont(name: "HelveticaNeue-Bold", size: 12.0)!,
                                                                                                      NSForegroundColorAttributeName: UIColor.darkGray])
        self.refresh.addTarget(self, action: #selector(refreshList), for: .valueChanged)
    }
}

// MARK: - UITableView -

extension OrdersListTableViewController {
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        
        var numberOfSections: Int = 0
        
        if presenter.orders.count > 0 {
            tableView.separatorStyle = .singleLine
            tableView.backgroundView = nil
            numberOfSections = 1
        }
        else {
            let emptyLabel: UILabel = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            emptyLabel.text = "Você não possui histórico de pedidos"
            emptyLabel.textColor = UIColor.darkGray
            emptyLabel.textAlignment = .center
            emptyLabel.font = UIFont(name: "HelveticaNeue", size: 16)
            tableView.backgroundView = emptyLabel
            tableView.separatorStyle = .none
        }
        
        return numberOfSections
    }
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return presenter.orders.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "OrderItemViewCell", for: indexPath) as! OrderItemViewCell
        
        let index: Int = indexPath.row
        let order: Order = presenter.orders[index]
        
        cell.orderHolderName.text = order.holderName
        cell.orderValue.text = order.orderValue.toCurrencyString
        cell.orderCreditCardNumber.text = order.cardNumber
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy HH:mm"
        cell.orderDate.text = formatter.string(from: order.orderDate)
        
        return cell
    }
}

// MARK: - Protocol methods -

extension OrdersListTableViewController: OrdersListViewProtocol {
    
    func showLoading() {
        SVProgressHUD.show()
    }
    
    func hideLoading() {
        SVProgressHUD.dismiss()
    }

    func reloadTableView() {
        UIView.transition(with: tableView!, duration: 0.35, options: .transitionCrossDissolve, animations: {
            self.tableView?.reloadData() })
    }
    
    func showAlertError(with title: String, message: String, buttonTitle: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonTitle, style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}

// MARK: - Private methods -

extension OrdersListTableViewController {
    
    fileprivate func initialize() {
        presenter = OrdersListPresenter(view: self)
        presenter.loadHistoryData()
    }
}

// MARK: - Action methods -

extension OrdersListTableViewController {
    
    @IBAction func refreshList() {
        //performSegue(withIdentifier: "sgConfirmPurchase", sender: self)
    }
    
}

