//
//  StoreViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

public let detailsSegue = "itemDetailsSegue"

class StoreViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UISearchResultsUpdating {
	
	private var allItems: [Item]!
	private var selectedItem: Item!
	
	private var searchController: UISearchController!
	private var filteredItems: [Item]!
	
	@IBOutlet weak var productsTableView: UITableView!
	
	override func viewDidLoad() {
        super.viewDidLoad()
		
		makeSearchController()
		
		productsTableView.delegate = self
		productsTableView.dataSource = self
		
		allItems = []
		getHTTP(at: "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json") { (dict, error) in
			if error == nil {
				for i in 0...dict!.count - 1 {
					self.allItems.append(Item(from: dict![i], { (image) in
						self.productsTableView.reloadRows(at: [IndexPath(row: i, section: 0)], with: .none)
					})!)
				}
				
				self.productsTableView.reloadData()
			}
			else {
				print(error!)
			}
		}
    }
	
	// MARK: - Products TableView DataSource
	
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		if searchController.isActive {
			return filteredItems.count
		}
		
		return allItems.count
	}
	
	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = tableView.dequeueReusableCell(withIdentifier: "ItemCell", for: indexPath) as! ItemCell
		var currentItem = allItems[indexPath.row]
		
		if searchController.isActive && searchController.searchBar.text != "" {
			currentItem = filteredItems[indexPath.row]
		}
		
		cell.nameLabel.text = currentItem.name
		cell.sellerLabel.text = currentItem.sellerName
		cell.priceLabel.text = "R$" + String(format: "%.2f", currentItem.price)
		
		if currentItem.photo != nil {
			cell.backgoundView.image = currentItem.photo
		}
		
		return cell
	}
	
	// MARK: - Products TableView Delegate
	
	func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
		return 130
	}
	
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		selectedItem = allItems[indexPath.row]
		
		if searchController.isActive && searchController.searchBar.text != "" {
			selectedItem = filteredItems[indexPath.row]
		}
		
		performSegue(withIdentifier: detailsSegue, sender: nil)
	}
	
	// MARK: - Search Results Updating
	
	private func makeSearchController() {
		let starYellow = UIColor(red: 255/255, green: 232/255, blue: 31/255, alpha: 1)
		searchController = UISearchController(searchResultsController: nil)
		searchController.searchBar.tintColor = starYellow
		UITextField.appearance(whenContainedInInstancesOf: [UISearchBar.self]).defaultTextAttributes = [NSAttributedStringKey.foregroundColor.rawValue: starYellow]
		
		searchController.searchResultsUpdater = self
		searchController.dimsBackgroundDuringPresentation = false
		definesPresentationContext = true
		
		navigationItem.searchController = searchController
		navigationItem.hidesSearchBarWhenScrolling = false
	}
	
	func filterContentForSearchText(searchText: String, scope: String = "All") {
		filteredItems = allItems.filter { item in
			return item.name.lowercased().contains(searchText.lowercased()) ||
					item.sellerName.lowercased().contains(searchText.lowercased()) ||
					String(format: "%.2f", item.price).lowercased().contains(searchText.lowercased())
		}
		
		productsTableView.reloadData()
	}
	
	func updateSearchResults(for searchController: UISearchController) {
		filterContentForSearchText(searchText: searchController.searchBar.text!)
	}
	
	// MARK: - Navigation
	
	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		if segue.identifier == detailsSegue {
			let nextVC = segue.destination as! ItemDetailsViewController
			
			nextVC.displayedItem = selectedItem
		}
	}

}
