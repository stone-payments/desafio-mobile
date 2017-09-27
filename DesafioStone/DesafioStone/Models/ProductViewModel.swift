//
//  ProductViewModel.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import Foundation
import UIKit

struct ProductViewModel {
  
  private var model: ProductModel
  
  var name: String {
    return model.title
  }
  var price: String {
    return model.price.formatedAsCurreny()
  }
  var seller: String {
    return "Vendedor: \(model.seller)"
  }
  var imageURL: URL? {
    return URL(string: model.thumbnailHd)
  }
  
  init(_ model: ProductModel) {
    self.model = model
  }
}
