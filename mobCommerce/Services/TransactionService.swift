//
//  TransactionService.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 31/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation
import SwiftyJSON

final class TransactionService {
    
    func postTransaction(with parameters: [String: Any], success: @escaping (_ categories: Bool) -> Void, fail: @escaping (_ error: String) -> Void) {
        
        ServiceManager.shared
            .Post(url: "http://private-f17fa-mobcommerce.apiary-mock.com/transaction",
                 parameters: parameters,
                 success: {
                    result in
                    success(true) },
                 failure: {
                    failure in
                    fail(failure.description) }
        )
    }
}
