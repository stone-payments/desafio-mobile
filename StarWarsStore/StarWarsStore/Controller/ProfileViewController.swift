//
//  ProfileViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class ProfileViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
	
	private var allTransactions: [Transaction]!
	private var allCards: [Card]!
	private var selectedTransaction: Transaction!
	private var selectedCard: Card!

	@IBOutlet weak var profileTableView: UITableView!
	
	// MARK: - View lifecycle
	
	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		
		profileTableView.delegate = self
		profileTableView.dataSource = self
		
		allTransactions = DAO.shared.allTransactions.sorted(by: { (t1, t2) -> Bool in
			return t1.date! > t2.date!
		})
		allCards = User.shared.cards
		
		selectedTransaction = nil
		selectedCard = nil
		
		profileTableView.reloadData()
	}
	
	// MARK: - Products TableView DataSource
	
	func numberOfSections(in tableView: UITableView) -> Int {
		return 2
	}
	
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		if section == 0 { // Cards Section
			if allCards.isEmpty {
				return 1
			}
			else {
				return allCards.count
			}
		}
		else { // Transaction history Section
			if allTransactions.isEmpty {
				return 1
			}
			else {
				return allTransactions.count
			}
		}
	}
	
	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		var cell = UITableViewCell()
		
		if indexPath.section == 0 { // Cards Section
			if allCards.isEmpty {
				cell.textLabel?.adjustsFontSizeToFitWidth = true
				cell.textLabel?.text = "Nenhum cartão até o momento salvo."
				cell.textLabel?.textAlignment = .center
			}
			else {
				let cardCell = tableView.dequeueReusableCell(withIdentifier: "ProfileInfoCell", for: indexPath) as! ProfileInfoCell
				let currentCard = allCards[indexPath.row]
				
				cardCell.cardHolderLabel.text = currentCard.holderName
				cardCell.detailInfoLabel.text = currentCard.number
				
				cell = cardCell
			}
		}
		else { // Transaction history Section
			if allTransactions.isEmpty {
				cell.textLabel?.adjustsFontSizeToFitWidth = true
				cell.textLabel?.text = "Nenhuma compra até o momento feita."
				cell.textLabel?.textAlignment = .center
			}
			else {
				let transactionCell = tableView.dequeueReusableCell(withIdentifier: "ProfileInfoCell", for: indexPath) as! ProfileInfoCell
				let currentTransaction = allTransactions[indexPath.row]
				let formatter = DateFormatter()
				formatter.dateFormat = "dd/MM/yyyy. HH:mm"
				let dateString = formatter.string(from: currentTransaction.date!)
				let finalDateString = dateString.replacingOccurrences(of: ".", with: " às")
				
				transactionCell.cardHolderLabel.text = currentTransaction.cardHolder
				transactionCell.cardHolderLabel.textAlignment = .left
				transactionCell.detailInfoLabel.text = finalDateString
				
				cell = transactionCell
			}
		}
		
		return cell
	}
	
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		if indexPath.section == 0 {
			selectedCard = allCards[indexPath.row]
			selectedTransaction = nil
		}
		else {
			selectedCard = nil
			selectedTransaction = allTransactions[indexPath.row]
		}
		
		performSegue(withIdentifier: "detailInfoSegue", sender: nil)
	}
	
	// MARK: - Products TableView Delegate
	
	func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
		return 70
	}
	
	func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
		return 30
	}
	
	func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
		let headerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: 30))
		headerView.backgroundColor = UIColor(red: 240/255, green: 240/255, blue: 240/255, alpha: 1)
		
		let titleLabel = UILabel(frame: CGRect(x: 0, y: 0, width: headerView.frame.width, height: headerView.frame.height))
		titleLabel.backgroundColor = .clear
		titleLabel.textAlignment = .center
		titleLabel.font = UIFont(name: "AvenirNext-Medium", size: 20)
		titleLabel.textColor = .black
		
		if section == 0 {
			titleLabel.text = "Cartões"
		}
		else {
			titleLabel.text = "Histórico de compras"
		}
		
		headerView.addSubview(titleLabel)
		
		return headerView
	}
	
	// MARK: - Navigation
	
	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		if segue.identifier == "detailInfoSegue" {
			let detailVC = segue.destination as! ProfileInfoViewController
			
			detailVC.displayedCard = selectedCard
			detailVC.displayedTransaction = selectedTransaction
		}
	}
	
}
