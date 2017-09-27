//
//  ExtensionDouble.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import Foundation

extension Double {
  
  /**
   Creates a string using the double value, formated as: `R$ {VALUE}`
   */
  func formatedAsCurreny() -> String {
      return String(format: "R$ %.2f", locale: Locale(identifier: "pt_BR"), (self/100.0))
  }
}
