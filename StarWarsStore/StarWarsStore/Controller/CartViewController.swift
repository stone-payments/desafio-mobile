//
//  CartViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class CartViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
	
	private var user: User!
	private var selectedItem: Item!

	private var emptyCartLabel: UILabel!
	@IBOutlet weak var cartTableView: UITableView!
	@IBOutlet weak var finishPurchaseView: UIView!
	@IBOutlet weak var totalValueLabel: UILabel!
	@IBOutlet weak var finishPurchaseButton: UIButton!
	
	// MARK: - View Lifecylcle
	
	// Using viewWillAppear instead of viewDidLoad so that
	// when the user adds a new item on the cart, the local cart and the cartTableView are reloaded.
	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		
		user = User.shared
		
		finishPurchaseView.isHidden = user.cart.isEmpty
		if !user.cart.isEmpty {
			personalizeFinishPurchaseComponents()
			totalValueLabel.text = "Valor total: R$" + String(format: "%.2f", user.totalCartPrice())
		}
		
		cartTableView.delegate = self
		cartTableView.dataSource = self
		
		personalizeEmptyLabel()
		cartTableView.reloadData()
	}
	
	private func personalizeEmptyLabel() {
		emptyCartLabel = UILabel(frame: CGRect(x: 0, y: 0, width: view.frame.width, height: view.frame.height))
		emptyCartLabel.font = UIFont(name: "AvenirNext-Medium", size: 20)
		emptyCartLabel.numberOfLines = 2
		emptyCartLabel.text = "Vazio seu carrinho ainda está!\nPorque fazer compras você não vai?"
		emptyCartLabel.textAlignment = .center
		emptyCartLabel.adjustsFontSizeToFitWidth = true
		emptyCartLabel.isHidden = true
		
		cartTableView.backgroundView = emptyCartLabel
	}
	
	private func personalizeFinishPurchaseComponents() {
		finishPurchaseButton.layer.cornerRadius = 5
		
		finishPurchaseView.layer.shadowColor = UIColor.black.cgColor
		finishPurchaseView.layer.shadowOpacity = 0.5
		finishPurchaseView.layer.shadowOffset = CGSize.zero
		finishPurchaseView.layer.shadowRadius = 5
		finishPurchaseView.layer.shadowPath = UIBezierPath(rect: finishPurchaseView.bounds).cgPath
		finishPurchaseView.layer.shouldRasterize = true
	}
	
	/// Displays a label saying that the table view is empty.
	///
	/// - Parameter isShown: Bool indicating wheter or not the table view is empty.
	private func showEmptyLabel(_ isShown: Bool) {
		emptyCartLabel.isHidden = !isShown
		cartTableView.isScrollEnabled = !isShown
		
		if isShown {
			cartTableView.separatorStyle = .none
		}
		else {
			cartTableView.separatorStyle = .singleLine
		}
	}
	
	/// Reload the view, its content and its data sources
	private func reloadView() {
		finishPurchaseView.isHidden = user.cart.isEmpty
		if !user.cart.isEmpty {
			personalizeFinishPurchaseComponents()
			totalValueLabel.text = "Valor total: R$" + String(format: "%.2f", user.totalCartPrice())
		}
		
		reloadTabBadge()
		cartTableView.reloadData()
	}
	
	private func reloadTabBadge() {
		if let tabItems = tabBarController?.tabBar.items as NSArray! {
			let cartTab = tabItems[2] as! UITabBarItem
			
			cartTab.badgeColor = UIColor(red: 200/255, green: 50/255, blue: 40/255, alpha: 1)
			if user.cart.isEmpty {
				cartTab.badgeValue = nil
			}
			else {
				cartTab.badgeValue = String(user.cart.count)
			}
		}
	}

	// MARK: - Products TableView DataSource
	
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		showEmptyLabel(user.cart.isEmpty)
		return user.cart.count
	}
	
	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let itemCell = tableView.dequeueReusableCell(withIdentifier: "ItemCell", for: indexPath) as! ItemCell
		let currentItem = user.cart[indexPath.row]
		
		itemCell.nameLabel.text = currentItem.name
		itemCell.sellerLabel.text = currentItem.sellerName
		itemCell.priceLabel.text = "R$" + String(format: "%.2f", currentItem.price)
		
		if currentItem.photo != nil {
			itemCell.backgoundView.image = currentItem.photo
		}
		
		return itemCell
	}
	
	func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
		if editingStyle == .delete {
			user.removeItemFromCart(user.cart[indexPath.row])
			cartTableView.reloadRows(at: [indexPath], with: .fade)
			reloadTabBadge()
		}
	}
	
	// MARK: - Products TableView Delegate
	
	func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
		return 130
	}
	
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		selectedItem = user.cart[indexPath.row]
		performSegue(withIdentifier: detailsSegue, sender: nil)
	}
	
	// MARK: - Buttons' Actions
	
	@IBAction func finishPurchase(_ sender: Any) {
		if user.cards.isEmpty {
			let alertView = UIAlertController(title: "Oops...", message: "Adicionar 1 cartão primeiro você deve!", preferredStyle: .alert)
			alertView.addAction(UIAlertAction(title: "Adicionar cartão", style: .default, handler: { (action) in
				self.performSegue(withIdentifier: "addCardSegue", sender: nil)
			}))
			alertView.addAction(UIAlertAction(title: "Ok", style: .cancel, handler: nil))
			
			present(alertView, animated: true, completion: nil)
		}
		else {
			let cardsView = UIAlertController(title: "Escolha o cartão desejado", message: "(Nome no cartão - últimos 4 dígitos)", preferredStyle: .actionSheet)
			
			for card in user.cards {
				let holderFirstName = card.holderName?.components(separatedBy: " ")[0]
				let last4Numbers = String(describing: card.number!.dropFirst(12))
				
				cardsView.addAction(UIAlertAction(title: holderFirstName! + " - Final: " + last4Numbers, style: .default, handler: { (alert) in
					self.buy(with: card)
				}))
			}
			
			cardsView.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler: nil))
			
			present(cardsView, animated: true, completion: nil)
		}
	}
	
	private func buy(with card: Card) {
		let totalValue = user.totalCartPrice()
		User.shared.finishPurchase(with: totalValue, and: card) { (error) -> Void in
			var title, message : String
			if error == nil {
				title = "Que a força esteja com você!"
				message = "Do seu carrinho todos os itens comprados foram!\nTotal da compra: R$" + String(format: "%.2f", totalValue)
			}
			else {
				title = "Oops..."
				message = "Algo na sua compra errado deu. Mais tarde tente, por favor."
			}
			
			let alertView = UIAlertController(title: title, message: message, preferredStyle: .alert)
			alertView.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action) in
				if error == nil {
					self.reloadView()
				}
			}))
			
			self.present(alertView, animated: true, completion: nil)
		}
	}
	
	// MARK: - Navigation
	
	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		if segue.identifier == detailsSegue {
			let nextVC = segue.destination as! ItemDetailsViewController
			
			nextVC.displayedItem = selectedItem
		}
	}

}

