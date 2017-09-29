//
//  CellViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 24/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit

class CollectionCellViewController: UICollectionViewCell {
    
    @IBOutlet weak var imagem: UIImageView!
    @IBOutlet weak var preco: UILabel!
    @IBOutlet weak var produto: UILabel!
    @IBOutlet weak var vendedor: UILabel!

}

class CartTableCellViewController: UITableViewCell {
    
    @IBOutlet weak var imagem: UIImageView!
    @IBOutlet weak var precounitario: UILabel!
    @IBOutlet weak var preco: UILabel!
    @IBOutlet weak var produto: UILabel!
    @IBOutlet weak var quantidade: UILabel!
    
}


class ConfigTableCellViewController: UITableViewCell {
    @IBOutlet weak var registros: UILabel!
}

class RegistrosTableCellViewController: UITableViewCell {
    @IBOutlet weak var order: UILabel!
}

class DetailsTableCellViewController: UITableViewCell {
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var detail: UILabel!

}

class CheckoutTableCellViewController: UITableViewCell {
    @IBOutlet weak var input: UITextField!
    
}

class TableSectionHeader: UITableViewHeaderFooterView {
    @IBOutlet weak var titleLabel: UILabel!
}
