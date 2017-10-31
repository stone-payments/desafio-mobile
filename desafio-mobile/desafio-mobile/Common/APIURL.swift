//
//  APIURL.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import Foundation
import Alamofire

internal struct APIURL: URLConvertible {
    
    var url: String
    
    init(urlString: String) {
        url = urlString
    }
    
    public func asURL() throws -> URL {
        return URL(string: url)!
    }
}
