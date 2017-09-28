//
//  ExtensionString.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 28/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import Foundation

extension String {
  /**
   
   */
  func grouping(every groupSize: String.IndexDistance, with separator: Character) -> String {
    let cleanedUpCopy = replacingOccurrences(of: String(separator), with: "")
    return String(cleanedUpCopy.characters.enumerated().map() {
      $0.offset % groupSize == 0 ? [separator, $0.element] : [$0.element]
      }.joined().dropFirst())
  }
}
