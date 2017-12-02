//
//  User.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import Foundation
import CoreData

class User {
    public var cards: [Card]!
	public var defaultCard: Card!
    public var cart: [Item]!
	
	private var dao: DAO!
	
	//MARK: Singleton Definition
	private static var theOnlyInstance: User?
	static var shared: User {
		get {
			if theOnlyInstance == nil {
				theOnlyInstance = User()
			}
			return theOnlyInstance!
		}
	}
	
	private init() {
		cart = []
		dao = DAO.shared
		
		cards = dao.allCards
		if !cards.isEmpty {
			defaultCard = cards[0]
		}
	}
    
	public func addCard(withNumber number: String, cvv: Int16, holderName: String, expirationDate: Date) {
		let newCard = dao.createCard()
		
		newCard.holderName = holderName
		newCard.number = number
		newCard.cvv = cvv
		newCard.expirationDate = expirationDate
		
		dao.saveCard(newCard)
        cards = dao.allCards
		
		// First card added
		if !cards.isEmpty {
			defaultCard = cards[0]
		}
    }
	
	public func removeCard(_ cardToDelete: Card) {
		dao.deleteCard(cardToDelete)
		cards = dao.allCards
	}
	
	public func addItemToCart(_ newItem: Item) {
		newItem.isOnCart = true
		cart.append(newItem)
	}
	
	public func removeItemFromCart(_ itemToDelete: Item) {
		let i = cart.index(where: { (item) -> Bool in
			return item.name == itemToDelete.name && item.sellerName == itemToDelete.sellerName
		})
		
		cart.remove(at: i!)
	}
    
    /// Calculate the total price of the user's current cart.
    ///
    /// - Returns: Cart's total price.
    public func totalCartPrice() -> Double {
        var totalPrice = 0.0
        
        for item in cart {
            totalPrice += item.price
        }
        
        return totalPrice
    }
	
	/// Finish the user's purchase and post the transaction.
	///
	/// - Parameters:
	///   - value: The total value of the purchase.
	///   - completion: The block to be called after the purchase transaction is finished.
	public func finishPurchase(with value: Double, and card: Card, _ completion: @escaping (String?) -> Void) {
		let newTransaction = dao.createTransaction()
		
		newTransaction.value = value
		newTransaction.date = Date()
		newTransaction.cardHolder = card.holderName
		newTransaction.cardLast4Numbers = String(describing: card.number!.dropFirst(12))
		
		postHTTP(at: "https://private-83dc26-desafiostonelucasferraco.apiary-mock.com/transactions/", withBody: dao.jsonToPOST(from: newTransaction, with: card)) { (error) in
			if error == nil {
				self.dao.saveTransaction(newTransaction)
				self.cart.removeAll()
				completion(nil)
			}
			else {
				completion(error!)
			}
		}
	}
}
