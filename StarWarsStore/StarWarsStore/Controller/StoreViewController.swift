//
//  FirstViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class StoreViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
	
	private var allProducts: [Item]!
	
	@IBOutlet weak var productsTableView: UITableView!
	
	override func viewDidLoad() {
        super.viewDidLoad()
		
		allProducts = []
		print(#function, "getHTTP")
		getHTTP(at: "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json") { (dict, error) in
			if error != nil {
				print(dict.count)
			}
			print(dict.count)
		}
    }
	
	//MARK:- Products TableView DataSource
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return allProducts.count
	}
	
	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		return UITableViewCell()
	}


}

