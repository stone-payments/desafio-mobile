//
//  ItemDetailsViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 29/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class ItemDetailsViewController: UIViewController {
	
	private var user: User!
	
	@IBOutlet weak var photoView: UIImageView!
	@IBOutlet weak var nameLabel: UILabel!
	@IBOutlet weak var priceLabel: UILabel!
	@IBOutlet weak var postedDateLabe: UILabel!
	@IBOutlet weak var sellerLabel: UILabel!
	@IBOutlet weak var zipcodeLabel: UILabel!
	@IBOutlet weak var cartButton: UIButton!
	@IBOutlet weak var buyButton: UIButton!
	
	public var displayedItem: Item!

	// MARK: - View Lifecylcle
	
    override func viewDidLoad() {
        super.viewDidLoad()
		
		user = User.shared
		
		navigationItem.largeTitleDisplayMode = .never
		navigationItem.title = displayedItem.name
		
		photoView.image = displayedItem.photo
		nameLabel.text = displayedItem.name
		priceLabel.text = "R$" + String(format: "%.2f", displayedItem.price)
		
		let formatter = DateFormatter()
		formatter.dateFormat = "dd/MM/yyyy"
		postedDateLabe.text = "Postado em " + formatter.string(from: displayedItem.postedDate)
		
		sellerLabel.text = displayedItem.sellerName
		zipcodeLabel.text = displayedItem.sellerZipCode
		
		personalizeButtons()
    }
	
	private func personalizeButtons() {
		cartButton.layer.cornerRadius = 5
		cartButton.titleLabel?.numberOfLines = 1
		cartButton.titleLabel?.adjustsFontSizeToFitWidth = true
		
		buyButton.layer.cornerRadius = 5
		cartButton.titleLabel?.numberOfLines = 1
		cartButton.titleLabel?.adjustsFontSizeToFitWidth = true
	}
	
	
	// MARK: - Buttons' Actions
	
	@IBAction func addItemToCart(_ sender: Any) {
		user.addItemToCart(displayedItem)
		
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
		
		let alertView = UIAlertController(title: "Que a força esteja com você!",
		                                  message: "Adicionado ao seu carrinho foi " + displayedItem.name,
		                                  preferredStyle: .alert)
		alertView.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action) in
			self.navigationController?.popViewController(animated: true)
		}))
		
		present(alertView, animated: true, completion: nil)
	}
	
	@IBAction func buyItem(_ sender: Any) {
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
		user.finishPurchase(with: displayedItem.price, and: card) { (error) -> Void in
			var title, message : String
			if error == nil {
				title = "Que a força esteja com você!"
				message = "1 " + self.displayedItem.name + " foi comprado!"
			}
			else {
				title = "Oops..."
				message = error!
			}
			
			let alertView = UIAlertController(title: title, message: message, preferredStyle: .alert)
			alertView.addAction(UIAlertAction(title: "Ok", style: .cancel, handler: { (action) in
				if error == nil {
					self.navigationController?.popViewController(animated: true)
				}
			}))
			
			self.present(alertView, animated: true, completion: nil)
		}
	}
	
}
