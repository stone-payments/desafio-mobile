//
//  ExtensionDate.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 28/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import Foundation

extension Date {
  
  public func fmtdDate(format: String) -> String {
    
    let formatter = DateFormatter()
    formatter.locale = Locale(identifier: "pt_BR")
    formatter.timeZone = TimeZone(identifier: "America/Sao_Paulo")
    
    formatter.dateFormat = "\(format) HH:mm:ss"
    formatter.dateFormat = format
    
    return formatter.string(from: self)
  }
}
