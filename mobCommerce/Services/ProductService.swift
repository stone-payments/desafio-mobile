//
//  ProductService.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation
import SwiftyJSON

final class ProductService {
    
    func getProducts(success: @escaping (_ categories: [Product]) -> Void, fail: @escaping (_ error: String) -> Void) {
     
        ServiceManager.shared
            .Get(url: ServiceURL.products.value, parameters: nil,
                 success: {
                    result in
                    success(self.parseProducts(json: JSON(result).arrayValue)) },
                 failure: {
                    failure in
                    fail(failure.description) }
        )
    }
}

// MARK: - JSON Parse -

extension ProductService {
    
    func parseProducts(json: [JSON]) -> [Product] {
        return json.flatMap { parseProduct(json: $0) }
    }
    
    private func parseProduct(json: JSON) -> Product {
        
        let title = json["title"].stringValue
        let price = json["price"].intValue
        let zipCode = json["zipcode"].stringValue
        let seller = json["seller"].stringValue
        let thumbnail = json["thumbnailHd"].stringValue
        let date = json["date"].stringValue
        
        return Product(title: title, price: price, zipCode: zipCode, seller: seller, thumbnail: thumbnail, date: date)
    }
}
