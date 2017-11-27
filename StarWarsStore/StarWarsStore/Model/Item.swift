//
//  Item.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class Item {
    public var name: String!
    public var price: Double!
    public var sellerName: String!
	public var sellerZipCode: String!
	public var postedDate: Date!
    public var photo: UIImage!
	
	init(name: String, price: Double, sellerName: String, sellerZipCode: String, postedDate: Date, photo: UIImage) {
		self.name = name
		self.price = price
		self.sellerName = sellerName
		self.sellerZipCode = sellerZipCode
		self.postedDate = postedDate
		self.photo = photo
	}
	
	init?(from json: [String : Any], _ completion: ((_ image: UIImage?) -> Void)? = nil) {
		/// Example of JSON Item
		//	{
		//		"title": "Blusa do Imperio",
		//		"price": 7990,
		//		"zipcode": "78993-000",
		//		"seller": "Jo\u00e3o da Silva",
		//		"thumbnailHd": "https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg",
		//		"date": "26/11/2015"
		//	}
		
		if let name = json["title"] as? String { self.name = name }
		if let price = json["price"] as? Double { self.price = price }
		if let sellerName = json["seller"] as? String { self.sellerName = sellerName }
		if let sellerZipCode = json["zipcode"] as? String { self.sellerZipCode = sellerZipCode }
		
		if let dateString = json["date"] as? String {
			var dateComponents = DateComponents()
			let dateStringComps = dateString.components(separatedBy: "/")
			
			dateComponents.day = Int(dateStringComps[0])
			dateComponents.month = Int(dateStringComps[1])
			dateComponents.year = Int(dateStringComps[2])
			
			postedDate = Calendar.current.date(from: dateComponents)
		}
		
		if let photoURL = json["thumbnailHd"] as? String {
			let url = URL(string: photoURL)
			
			URLSession.shared.dataTask(with: url!, completionHandler: { (data, response, error) in
				if error == nil {
					DispatchQueue.main.async {
						self.photo = UIImage(data: data!)
						
						if completion != nil {
							completion!(UIImage(data: data!)!)
						}
					}
				}
				else {
					if completion != nil {
						completion!(nil)
					}
				}
			}).resume()
		}
	}
}
