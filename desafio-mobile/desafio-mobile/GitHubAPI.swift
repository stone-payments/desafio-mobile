//
//  GitHubAPI.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import Foundation
import ObjectMapper
import Alamofire


class GitHubAPI {
    
    static let base_api_url = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master"
    
    static func getProducts(completionHandler: @escaping Product.blockCompletion) {
        
        let url = APIURL(urlString: "\(GitHubAPI.base_api_url)/products.json")
        
        _ = Alamofire.request(url).responseJSON(completionHandler: { (response) in
            
            guard let json = GitHubAPIParser.parseJSON(response.result.value) as? [[String : Any]],
                let products = Mapper<Product>().mapArray(JSONObject: json),
                response.result.error?._code != NSURLErrorCancelled else {
                    return
            }
            
            completionHandler(products, nil)
        })
    }
}
