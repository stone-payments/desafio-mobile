//
//  DAO.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 30/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit
import CoreData

public class DAO {
	
	private let appDelegate: AppDelegate!
	private let managedContext: NSManagedObjectContext!
	
	public var allTransactions: [Transaction]!
	public var allCards: [Card]!
	
	// MARK: - Singleton Definition
	
	private static var theOnlyInstance: DAO?
	static var shared: DAO {
		get {
			if theOnlyInstance == nil {
				theOnlyInstance = DAO()
			}
			return theOnlyInstance!
		}
	}
	
	private init() {
		allTransactions = []
		allCards = []
		appDelegate = UIApplication.shared.delegate as? AppDelegate
		
		if appDelegate != nil {
			managedContext = appDelegate.persistentContainer.viewContext
		}
		else {
			managedContext = nil
			print("Could not access the App Delegate.")
		}
	}
	
	public func initialize() {
		fetchAllTransactions()
		fetchAllCards()
	}
	
	// MARK: - Create funcions
	
	/// Create a new instance of a Transaction.
	///
	/// - Returns: The new Transaction instance.
	public func createTransaction() -> Transaction {
		let transactionEntity = NSEntityDescription.entity(forEntityName: "Transaction", in: managedContext)
		
		return Transaction(entity: transactionEntity!, insertInto: managedContext)
	}
	
	/// Create a new instance of a Card.
	///
	/// - Returns: The new Card instance.
	public func createCard() -> Card {
		let cardEntity = NSEntityDescription.entity(forEntityName: "Card", in: managedContext)
		
		return Card(entity: cardEntity!, insertInto: managedContext)
	}
	
	// MARK: - Save funcions
	
	/// Save a new transaction on the local CoreData database.
	///
	/// - Parameter newTransactoin: The new Transaction instance to be saved.
	public func saveTransaction(_ newTransaction: Transaction) {
		appDelegate.saveContext()
		allTransactions.append(newTransaction)
	}
	
	/// Save a new card on the local CoreData database.
	///
	/// - Parameter newTransactoin: The new Card instance to be saved.
	public func saveCard(_ newCard: Card) {
		appDelegate.saveContext()
		allCards.append(newCard)
	}
	
	// MARK: - Delete funcions
	
	/// Delete a card on the local CoreData database.
	///
	/// - Parameter newTransactoin: The new Card instance to be saved.
	public func deleteCard(_ cardToDelete: Card) {
		managedContext.delete(cardToDelete)
		appDelegate.saveContext()
		
		let i = allCards.index(where: { (card) -> Bool in
			return card.holderName == cardToDelete.holderName && card.number == cardToDelete.number
		})
		
		allCards.remove(at: i!)
	}
	
	// MARK: - Fetch funcions
	
	/// Fetch all of the transactions on the local CoreData database.
	///
	/// - Returns: An array containing all the transactions fetched.
	private func fetchAllTransactions() {
		let request: NSFetchRequest<Transaction> = Transaction.fetchRequest()
		
		do {
			allTransactions = try managedContext.fetch(request)
		}
		catch let error {
			print("Fetching Transactions failed: ", error)
		}
	}
	
	/// Fetch all of the cards on the local CoreData database.
	///
	/// - Returns: An array containing all the cards fetched.
	private func fetchAllCards() {
		let request: NSFetchRequest<Card> = Card.fetchRequest()
		
		do {
			allCards = try managedContext.fetch(request)
		}
		catch let error {
			print("Fetching Cards failed: ", error)
		}
	}
	
	// MARK: - Auxiliary funcions
	
	/// Transform the transaction to its JSON form.
	///
	/// - Returns: JSON form of the transaction.
	public func jsonToPOST(from transaction: Transaction, with card: Card) -> [String : Any] {
		// Model JSON to make POST request
		//	{
		//		"card_number":"1234123412341234",
		//		"value":7990,
		//		"cvv":789,
		//		"card_holder_name":"Luke Skywalker",
		//		"exp_date":"12/24"
		//	}
		
		var jsonToPOST = [String : Any]()
		
		var doubleStrings = transaction.value.description.components(separatedBy: ".")
		if doubleStrings[1].count == 1 {
			_ = jsonToPOST.updateValue(Int(doubleStrings[0] + doubleStrings[1] + "0")!, forKey: "value")
		}
		else {
			_ = jsonToPOST.updateValue(Int(doubleStrings[0] + doubleStrings[1])!, forKey: "value")
		}
		
		_ = jsonToPOST.updateValue(card.holderName!, forKey: "card_holder_name")
		_ = jsonToPOST.updateValue(card.number!, forKey: "card_number")
		_ = jsonToPOST.updateValue(card.cvv, forKey: "cvv")
		
		let formatter = DateFormatter()
		formatter.dateFormat = "MM/yy"
		_ = jsonToPOST.updateValue(formatter.string(from: card.expirationDate!), forKey: "exp_date")
		
		if JSONSerialization.isValidJSONObject(jsonToPOST) {
			return jsonToPOST
		}
		
		print("Could not validate JSON.")
		return [:]
	}
	
}

