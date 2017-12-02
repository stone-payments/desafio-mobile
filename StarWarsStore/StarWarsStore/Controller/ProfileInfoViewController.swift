//
//  ProfileInfoViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 01/12/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class ProfileInfoViewController: UIViewController {
	
	private let formatter: DateFormatter = DateFormatter()
	
	@IBOutlet weak var holderOrValueTitleLabel: UILabel!
	@IBOutlet weak var holderOrValueContentLabel: UILabel!
	
	@IBOutlet weak var numberOrDateTitleLabel: UILabel!
	@IBOutlet weak var numberOrDateContentLabel: UILabel!
	
	@IBOutlet weak var cvvOrLast4TitleLabel: UILabel!
	@IBOutlet weak var cvvOrLast4ContentLabel: UILabel!
	
	@IBOutlet weak var expDateOrHolderTitleLabel: UILabel!
	@IBOutlet weak var expDateOrHolderContentLabel: UILabel!
	
	@IBOutlet weak var deleteCardButton: UIButton!
	public var displayedCard: Card!
	public var displayedTransaction: Transaction!
	
	override func viewDidLoad() {
        super.viewDidLoad()
		
        deleteCardButton.layer.cornerRadius = 5
		deleteCardButton.isHidden = displayedCard == nil
		
		if displayedTransaction != nil {
			navigationItem.title = "Compra"
			prepareTransactionMode(to: displayedTransaction)
		}
		else {
			navigationItem.title = "Cartão"
			prepareCardMode(to: displayedCard)
		}
    }

	private func prepareCardMode(to card: Card) {
		displayedCard = card
		
		holderOrValueTitleLabel.text = "Nome no cartão"
		holderOrValueContentLabel.text = card.holderName
		
		numberOrDateTitleLabel.text = "Número"
		numberOrDateContentLabel.text = card.number
		
		cvvOrLast4TitleLabel.text = "Código de verificação (CVV)"
		cvvOrLast4ContentLabel.text = String(card.cvv)
		
		expDateOrHolderTitleLabel.text = "Data de validade"
		formatter.dateFormat = "MM/yyyy"
		expDateOrHolderContentLabel.text = formatter.string(from: card.expirationDate!)
	}
	
	private func prepareTransactionMode(to transaction: Transaction) {
		holderOrValueTitleLabel.text = "Valor total da compra"
		holderOrValueContentLabel.text = "R$" + String(format: "%.2f", transaction.value)
		
		numberOrDateTitleLabel.text = "Data e hora"
		formatter.dateFormat = "dd/MM/yyyy. HH:mm"
		let dateString = formatter.string(from: transaction.date!)
		let finalDateString = dateString.replacingOccurrences(of: ".", with: " às")
		numberOrDateContentLabel.text = finalDateString
		
		cvvOrLast4TitleLabel.text = "Últimos 4 dígitos do cartão usado"
		cvvOrLast4ContentLabel.text = transaction.cardLast4Numbers
		
		expDateOrHolderTitleLabel.text = "Nome no cartão usado"
		expDateOrHolderContentLabel.text = transaction.cardHolder
	}
	
	@IBAction func deleteCard(_ sender: Any) {
		let alertView = UIAlertController(title: "Cuidado!", message: "Após apagar seu cartão, volta não terá!", preferredStyle: .alert)
		alertView.addAction(UIAlertAction(title: "Cancelar", style: .default, handler: nil))
		alertView.addAction(UIAlertAction(title: "Apagar", style: .destructive, handler: { (alert) in
			User.shared.removeCard(self.displayedCard)
			self.navigationController?.popViewController(animated: true)
		}))
		
		present(alertView, animated: true, completion: nil)
	}
	
}
