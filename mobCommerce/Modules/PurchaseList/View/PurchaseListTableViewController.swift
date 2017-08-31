//
//  PurchaseListTableViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 28/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import AlamofireImage
import SVProgressHUD

class PurchaseListTableViewController: UITableViewController, UITextFieldDelegate {
    
    fileprivate var purchaseItems: [Purchase] = []
    var presenter: PurchaseListPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.reloadTableView()
    }
}

// MARK: - UITableView -

extension PurchaseListTableViewController {
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
    
    override func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return 80
    }
    
    override func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        let footerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 80))
        footerView.backgroundColor = UIColor.white
        
        let button = UIButton(frame: CGRect(x: 15, y: 15, width: tableView.frame.size.width - 30, height: 50))
        button.backgroundColor = Colors.buttonAddItem.color
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-CondensedBold", size: 18)
        button.titleLabel?.textColor = UIColor.white
        button.setTitle("Finalizar Compra", for: .normal)
        button.addTarget(self, action: #selector(finishCart(_:)), for: .touchUpInside)
        
        footerView.addSubview(button)
        
        return footerView
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
        
        let index: Int = indexPath.row
        let item: Purchase = purchaseItems[index]
        
        cell.itemDescription.text = item.title
        cell.itemPrice.text = item.price.toCurrencyString
        cell.itemTotal.text = item.total.toCurrencyString
        cell.itemImage.af_setImage(withURL: URL(string: item.thumbnail)!)
        cell.deleteButton.tag = index
        cell.itemQuantity.tag = index
        cell.itemQuantity.text = "\(item.quantity)"
        cell.itemQuantity.delegate = self
        
        return cell
    }
}

// MARK: - UITexField -

extension PurchaseListTableViewController {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        guard let text = textField.text else { return true }
        let newLength = text.characters.count + string.characters.count - range.length
        let isMax = newLength <= 2
        return isMax
    }
   
    func textFieldDidEndEditing(_ textField: UITextField) {
        presenter.calculateOrder(with: Int(textField.text!)!, at: textField.tag)
    }
    
}

// MARK: - Protocol methods -

extension PurchaseListTableViewController: PurchaseListViewProtocol {
    
    func reloadTableView() {
        self.loadData()
        
        UIView.transition(with: tableView!, duration: 0.35, options: .transitionCrossDissolve, animations: {
            self.tableView?.reloadData() })
    }
    
    func reloadTableViewCell(at indexPath: IndexPath) {
        self.loadData()
        self.tableView?.reloadRows(at: [indexPath], with: .fade)
    }
    
    func updateBadgeToValue(with value: String) {
        self.navigationController?.tabBarController?.tabBar.items![1].badgeValue = value
    }
    
    func showAlertError(with title: String, message: String, buttonTitle: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonTitle, style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}

// MARK: - Private methods -

extension PurchaseListTableViewController {
    
    fileprivate func initialize() {
        presenter = PurchaseListPresenter(view: self)
    }
    
    fileprivate func loadData() {
        purchaseItems = (UIApplication.shared.delegate as! AppDelegate).purchaseItems
    }
}

// MARK: - Action methods -

extension PurchaseListTableViewController {
    
    @IBAction func finishCart(_ sender: UIButton) {
        performSegue(withIdentifier: "sgConfirmPurchase", sender: self)
    }
    
    @IBAction func removeItem(_ sender: UIButton) {
        presenter.removeItem(with: purchaseItems[sender.tag], at: sender.tag)
    }
}
